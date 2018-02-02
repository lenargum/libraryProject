import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection con;

    public void connect() throws Exception {
       try{
           if (con != null) return;

           try {
               Class.forName("com.mysql.jdbc.Driver");
           } catch (ClassNotFoundException e) {
               throw new Exception("Database driver not found");
           }

           String connectionURL = "jdbc:mysql://localhost:1337/mydb?autoReconnect=true&useSSL=false";

           con = DriverManager.getConnection(connectionURL, "root", "qwerty");
           System.out.println("Connection successful!");
       }catch(Exception e){
           System.out.println("Connection failed");
       }

    }

    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeUpdate(String MySQLStatement) throws SQLException {
        Statement statement = con.createStatement();
        statement.executeUpdate(MySQLStatement);
    }

    public void execute(String MySQLStatement) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(MySQLStatement);
    }
}