package dataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseConnector extends AutoCloseable {
    void connect();
    void migrate();
    PreparedStatement prepareStatement(String query, Object... params) throws SQLException;
    ResultSet select(String query, Object... params) throws SQLException;
    void execute(String query, Object... params);

    @Override
    void close();
}
