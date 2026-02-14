package dataAccess;

import environment.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Stream;

public class SqliteDatabase implements DatabaseConnector {
    private static final String DB_URL = "jdbc:sqlite:db.db";

    private Connection connection;

    public SqliteDatabase() {
        connect();
    }

    @Override
    public void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            if (connection == null) {
                System.err.println("Failed to connect to SQLite.");
                return;
            }

            System.out.println("Connected to SQLite!");
        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }

    @Override
    public void migrate() {
        final Path path = Path.of(Environment.getInstance().get("MIGRATIONS_PATH"));

        if (!Files.exists(path)) {
            System.out.println("Migrations directory does not exist: " + path);
            return;
        }

        try {
            createMigrationsTable();

            try (Stream<Path> files = Files.list(path)) {
                files.filter(file -> Files.isRegularFile(file) && file.toString().endsWith(".sql"))
                     .sorted()
                     .forEach(this::executeMigration);
            }

            System.out.println("Migrations completed successfully.");
        } catch (IOException e) {
            System.err.println("Error reading migrations directory: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error during migration: " + e.getMessage());
        }
    }

    private void createMigrationsTable() throws SQLException {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS schema_migrations (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                filename TEXT NOT NULL UNIQUE,
                executed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    private void executeMigration(Path migrationFile) {
        String filename = migrationFile.getFileName().toString();

        try {
            if (isMigrationExecuted(filename)) {
                return;
            }

            String sql = Files.readString(migrationFile);

            try (Statement stmt = connection.createStatement()) {
                stmt.execute(sql);
            }

            recordMigration(filename);

            System.out.println("Executed migration: " + filename);
        } catch (IOException e) {
            throw new RuntimeException("Error reading migration file " + filename + ": " + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException("Error executing migration " + filename + ": " + e.getMessage());
        }
    }

    private boolean isMigrationExecuted(String filename) throws SQLException {
        String query = "SELECT COUNT(*) FROM schema_migrations WHERE filename = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, filename);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private void recordMigration(String filename) throws SQLException {
        String insert = "INSERT INTO schema_migrations (filename) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insert)) {
            pstmt.setString(1, filename);
            pstmt.executeUpdate();
        }
    }

    @Override
    public PreparedStatement prepareStatement(String query, Object... params) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        return pstmt;
    }

    /**
     * Executes a SELECT query and returns the ResultSet.
     * Note: The caller is responsible for closing the ResultSet, which will also close
     * the underlying PreparedStatement. Use try-with-resources for proper resource management.
     */
    @Override
    public ResultSet select(String query, Object... params) throws SQLException {
        PreparedStatement pstmt = prepareStatement(query, params);
        return pstmt.executeQuery();
    }

    @Override
    public void execute(String query, Object... params) {
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
}
