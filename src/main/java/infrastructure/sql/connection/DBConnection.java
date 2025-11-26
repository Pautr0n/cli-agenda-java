package infrastructure.sql.connection;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static Connection connection = null;

    public static Connection getConnection() {


        try

        {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Properties props = new Properties();

            InputStream input = DBConnection.class.getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("File db.properties not found");
            }

            props.load(input);


            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            if (url == null || url.isBlank()) {
                throw new RuntimeException("db.url not defined");
            }

            if (user == null || user.isBlank()) {
                throw new RuntimeException("db.user not defined");
            }

            if (password == null || password.isBlank()) {
                throw new RuntimeException("db.password not defined");
            }

            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful");

            return connection;

        }catch(
                Exception e){
            throw new RuntimeException("Error connecting to the database: " + e.getMessage());

        }
    }
}
