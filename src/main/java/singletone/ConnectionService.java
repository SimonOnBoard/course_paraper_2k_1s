package singletone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    public static Connection connection;
    private final String url = "jdbc:postgresql://localhost:5432/auth_test";
    private final String name = "postgres";
    private final String password = "QAZedctgb123";

    private ConnectionService(){
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url,name,password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Connection getConnection(){
        if (connection == null) {
            ConnectionService connectionService = new ConnectionService();
        }
        return connection;
    }


}
