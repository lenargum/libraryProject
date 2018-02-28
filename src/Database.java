import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection con;

    public void connect() {
        try {
            if (isConnected()) {
                System.out.println("Already connected");
                return;
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new Exception("Database driver not found");
            }

            String connectionURL = "jdbc:mysql://localhost:1337/mydb?autoReconnect=true&useSSL=false";

            con = DriverManager.getConnection(connectionURL, "root", "qwerty");
            System.out.println("Connection successful");
        } catch (Exception e) {

            System.out.println("Connection failed");
        }
    }

    public boolean isConnected() {
        return this.con != null;
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

    public void insertUser(String login, String password, String status,
                           String firstname, String lastname, String phone, String address) throws SQLException {
        this.execute("INSERT INTO users(login, password, status, firstname, lastname, phone, address)" +
                " VALUES('" + login + "','" + password + "','" + status + "','" + firstname + "','" + lastname + "','" + phone + "','" + address + "')");
    }

    public void insertDocument(String name, String authors, boolean is_allowed_for_students, int num_of_copies,
                               boolean is_reference, int price, String keywords, String type, String publisher,
                               int edition, boolean bestseller, String journal_name, String issue, String editor,
                               String publication_date) throws SQLException {
        System.out.println("INSERT INTO documents(name, authors, is_allowed_for_students," +
                " num_of_copies, is_reference, price, keywords, type, publisher, edition, bestseller," +
                " journal_name, issue, editor, publication_date)" +
                " VALUES('" + name + "','" + authors + "','" + is_allowed_for_students + "'," + num_of_copies + ",'" + is_reference + "'," + price + ",'" + keywords + "','" + type + "','"
                + publisher + "'," + edition + ",'" + bestseller + "','" + journal_name + "','" + issue + "','" + editor + "'," + publication_date + ")");
        this.execute("INSERT INTO documents(name, authors, is_allowed_for_students," +
                " num_of_copies, is_reference, price, keywords, type, publisher, edition, bestseller," +
                " journal_name, issue, editor, publication_date)" +
                " VALUES('" + name + "','" + authors + "','" + is_allowed_for_students + "'," + num_of_copies + ",'" + is_reference + "'," + price + ",'" + keywords + "','" + type + "','"
                + publisher + "'," + edition + ",'" + bestseller + "','" + journal_name + "','" + issue + "','" + editor + "'," + publication_date + ")");
    }


    public void insertBook(String name, String authors, boolean is_allowed_for_students, int num_of_copies,
                           boolean is_reference, int price, String keywords, String publisher,
                           int edition, boolean bestseller) throws SQLException {
        String type = "BOOK";
        String journal_name = "-";
        String issue = "-";
        String editor = "-";
        String publication_date = "NULL";
        insertDocument(name, authors, is_allowed_for_students, num_of_copies, is_reference, price, keywords, type
                , publisher, edition, bestseller, journal_name, issue, editor, publication_date);
    }

    public void insertAV(String name, String authors, boolean is_allowed_for_students, int num_of_copies,
                         boolean is_reference, int price, String keywords) throws SQLException {
        String type = "AV";
        String publisher = "-";
        int edition = 0;
        String journal_name = "-";
        String issue = "-";
        String editor = "-";
        String publication_date = "NULL";
        insertDocument(name, authors, is_allowed_for_students, num_of_copies, is_reference, price, keywords, type
                , publisher, edition, false, journal_name, issue, editor, publication_date);
    }

    public void insertArticle(String name, String authors, boolean is_allowed_for_students, int num_of_copies,
                              boolean is_reference, int price, String keywords, String journal_name, String publisher,
                              String issue, String editor, String publication_date) throws SQLException {
        String type = "ARTICLE";
        int edition = 0;
        insertDocument(name, authors, is_allowed_for_students, num_of_copies, is_reference, price, keywords, type
                , publisher, edition, false, journal_name, issue, editor, publication_date);
    }


    //insert,update,delete
    private void executeUpdate(String MySQLStatement) throws SQLException {
        Statement statement = con.createStatement();
        statement.executeUpdate(MySQLStatement);
    }

    //select

    private void execute(String MySQLStatement) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(MySQLStatement);
    }

    private ResultSet executeQuery(String MySQLStatement) throws SQLException {
        Statement statement = con.createStatement();
        return statement.executeQuery(MySQLStatement);
    }

    public ArrayList<String> getDocumentList() throws SQLException {
        ResultSet documentSet = executeQuery("SELECT * FROM mydb.documents;");
        ArrayList<String> documentsTitleList = new ArrayList<>();
        while (documentSet.next()) {
            documentsTitleList.add("\"" + documentSet.getString(2) + "\" " + documentSet.getString(3));
        }
        return documentsTitleList;
    }
}