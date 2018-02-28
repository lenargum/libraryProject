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
                               boolean is_reference, double price, String keywords, String type, String publisher,
                               int edition, boolean bestseller, String journal_name, String issue, String editor,
                               String publication_date) throws SQLException {
        this.execute("INSERT INTO documents(name, authors, is_allowed_for_students," +
                " num_of_copies, is_reference, price, keywords, type, publisher, edition, bestseller," +
                " journal_name, issue, editor, publication_date)" +
                " VALUES('" + name + "','" + authors + "','" + is_allowed_for_students + "'," + num_of_copies + ",'" + is_reference + "'," + price + ",'" + keywords + "','" + type + "','"
                + publisher + "'," + edition + ",'" + bestseller + "','" + journal_name + "','" + issue + "','" + editor + "','" + publication_date + "')");
    }


    public void insertBook(String name, String authors, boolean is_allowed_for_students, int num_of_copies,
                           boolean is_reference, double price, String keywords, String publisher,
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
                         boolean is_reference, double price, String keywords) throws SQLException {
        String type = "AV";
        String publisher = "-";
        int edition = 0;
        String journal_name = "-";
        String issue = "-";
        String editor = "-";
        String publication_date = "0000-00-00";
        insertDocument(name, authors, is_allowed_for_students, num_of_copies, is_reference, price, keywords, type
                , publisher, edition, false, journal_name, issue, editor, publication_date);
    }

    public void insertArticle(String name, String authors, boolean is_allowed_for_students, int num_of_copies,
                              boolean is_reference, double price, String keywords, String journal_name, String publisher,
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
        ResultSet documentSet = executeQuery("SELECT * FROM mydb.documents");
        ArrayList<String> documentsTitleList = new ArrayList<>();
        while (documentSet.next()) {
            documentsTitleList.add("\"" + documentSet.getString(2) + "\" " + documentSet.getString(3));
        }
        return documentsTitleList;
    }

    public ArrayList<Book> getBookList() throws SQLException {
        ResultSet bookSet = executeQuery("SELECT * FROM mydb.documents where type = \'BOOK\'");
        ArrayList<Book> bookList = new ArrayList<>();
        while(bookSet.next()) {
            bookList.add(new Book(bookSet.getInt(1),bookSet.getString(2),
                    bookSet.getString(3),bookSet.getBoolean(4),bookSet.getInt(5),
                    bookSet.getBoolean(6),bookSet.getDouble(7),bookSet.getString(8),
                    bookSet.getString(10),bookSet.getInt(11),bookSet.getBoolean(12)));
        }
        return bookList;
    }

    public ArrayList<AudioVideoMaterial> getAVList() throws SQLException {
        ResultSet AVSet = executeQuery("SELECT * FROM mydb.documents where type = \'AV\'");
        ArrayList<AudioVideoMaterial> AVList = new ArrayList<>();
        while(AVSet.next()) {
            AVList.add(new AudioVideoMaterial(AVSet.getInt(1),AVSet.getString(2),
                    AVSet.getString(3),AVSet.getBoolean(4),AVSet.getInt(5),
                    AVSet.getBoolean(6),AVSet.getDouble(7),AVSet.getString(8)));
        }
        return AVList;
    }

    public ArrayList<JournalArticle> getArticleList() throws SQLException {
        ResultSet articleSet = executeQuery("SELECT * FROM mydb.documents where type = \'ARTICLE\'");
        ArrayList<JournalArticle> articleList = new ArrayList<>();
        while(articleSet.next()) {
            articleList.add(new JournalArticle(articleSet.getInt(1),articleSet.getString(2),
                    articleSet.getString(3),articleSet.getBoolean(4),articleSet.getInt(5),
                    articleSet.getBoolean(6),articleSet.getDouble(7),articleSet.getString(8),articleSet.getString(13),articleSet.getString(10),articleSet.getString(14),articleSet.getString(15),articleSet.getDate(16)));
        }
        return articleList;
    }
}