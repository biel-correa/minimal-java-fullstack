package dataAccess;

import java.sql.Connection;
import java.sql.ResultSet;

public interface IDatabaseConnector {
    void connect();
    void migrate();
    Connection getConnection();
    ResultSet select(String query, Object... params);
    void execute(String query, Object... params);
}
