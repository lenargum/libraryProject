import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Database {

    private Connection con;

    /**
     * connection process
     */
    public void connect() {
        try {
            if (isConnected()) {
                System.out.println("Database: Already connected");
                return;
            }

            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                throw new Exception("Database: Database driver not found");
            }

            String connectionURL = "jdbc:sqlite:library.db";

            con = DriverManager.getConnection(connectionURL);
            System.out.println("Database: Connection successful");
        } catch (Exception e) {

            System.out.println("Database: Connection failed");
        }
    }

    /**
     * @return is connected to database
     */
    public boolean isConnected() {
        return this.con != null;
    }

    /**
     * closing connection
     * (you should better do it after usage)
     */
    public void close() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Database: Connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //basic methods
    private void executeUpdate(String MySQLStatement) throws SQLException {
        Statement statement = con.createStatement();
        statement.executeUpdate(MySQLStatement);
        statement.close();
    }

    private void execute(String MySQLStatement) throws SQLException {
        Statement statement = con.createStatement();
        statement.execute(MySQLStatement);
        statement.close();
    }

    private ResultSet executeQuery(String MySQLStatement) throws SQLException {
        Statement statement = con.createStatement();
        return statement.executeQuery(MySQLStatement);
    }


    //main features: insertion

    public void insertUser(String login, String password, String status,
                           String firstname, String lastname, String phone, String address) throws SQLException {
        this.execute("INSERT INTO users(login, password, status, firstname, lastname, phone, address)" +
                " VALUES('" + login + "','" + password + "','" + status + "','" + firstname + "','" + lastname + "','" + phone + "','" + address + "')");
    }

    public void insertPatron(Patron patron) throws SQLException {
        String status = "STUDENT";
        if(patron.getStatus().toLowerCase().equals("faculty")) {
            status = "FACULTY";
        }
        insertUser(patron.getLogin(),patron.getPassword(),status,patron.getName(),patron.getSurname(),patron.getPhoneNumber(),patron.getAddress());
    }

    public void insertLibrarian(Librarian librarian) throws SQLException {
        insertUser(librarian.getLogin(),librarian.getPassword(),"LIBRARIAN",librarian.getName(),librarian.getSurname(),librarian.getPhoneNumber(),librarian.getAddress());
    }

    //main insertion(other methods just implement it for custom types)
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

    public void insertBook(Book book) throws SQLException {
        String type = "BOOK";
        String journal_name = "-";
        String issue = "-";
        String editor = "-";
        String publication_date = "NULL";
        insertDocument(book.getTitle(), book.getAuthors(), book.isAllowedForStudents(), book.getNumberOfCopies(),
                book.isReference(), book.getPrice(), book.getKeyWords(), type, book.getPublisher(), book.getEdition(),
                book.isBestseller(), "-", "-", "-", "NULL");
    }

    public void insertAV(AudioVideoMaterial av) throws SQLException {
        insertDocument(av.getTitle(), av.getAuthors(), av.isAllowedForStudents(), av.getNumberOfCopies(),
                av.isReference(), av.getPrice(), av.getKeyWords(), "AV", "-", 0, false,
                "-", "-", "-", "NULL");
    }

    public void insertArticle(JournalArticle article) throws SQLException {
        insertDocument(article.getTitle(), article.getAuthors(), article.isAllowedForStudents(),
                article.getNumberOfCopies(), article.isReference(), article.getPrice(), article.getKeyWords(),
                "ARTICLE", article.getPublisher(), 0, false, article.getJournalName(),
                article.getIssue(), article.getEditor(),
                (new SimpleDateFormat("dd-MMM-yyyy")).format(article.getPublicationDate()));
    }

    public void insertDebt(Debt debt) throws SQLException {
        execute("INSERT INTO debts(patron_id, document_id, booking_date, expire_date, fee, can_renew," +
                " is_renewed)" +" VALUES(" + debt.getPatronId() + ", " + debt.getDocumentId() + ", \'"
                + debt.getBookingDate() + "\', \'" + debt.getExpireDate() + "\', " + debt.getFee() + ", \'"
                + debt.canRenew() + "\')");
    }


    //main features: receiving lists

    public ArrayList<Patron> getPatronList() throws SQLException {
        ResultSet patronSet = executeQuery("SELECT * FROM users where status = 'FACULTY' or status = 'STUDENT'");
        ArrayList<Patron> patronList = new ArrayList<>();
        while(patronSet.next()) {
            Patron temp = new Patron(patronSet.getString(2),
                    patronSet.getString(3),patronSet.getString(4),patronSet.getString(5),
                    patronSet.getString(6),patronSet.getString(7),patronSet.getString(8));
            temp.setId(patronSet.getInt(1));
            patronList.add(temp);
        }
        return patronList;
    }

    public ArrayList<Librarian> getLibrarianList() throws SQLException {
        ResultSet librarianSet = executeQuery("SELECT * FROM users where status = 'LIBRARIAN'");
        ArrayList<Librarian> librarianList = new ArrayList<>();
        while(librarianSet.next()) {
            Librarian temp = new Librarian(librarianSet.getString(2),
                    librarianSet.getString(3),librarianSet.getString(5),
                    librarianSet.getString(6),librarianSet.getString(7),librarianSet.getString(8));
            temp.setId(librarianSet.getInt(1));
            librarianList.add(temp);
        }
        return librarianList;
    }

    public ArrayList<Document> getDocumentList() throws SQLException {
        ResultSet documentSet = executeQuery("SELECT * FROM documents");
        ArrayList<Document> documentList = new ArrayList<>();
        while (documentSet.next()) {
            Document temp = new Document(documentSet.getString(2),
                    documentSet.getString(3),documentSet.getBoolean(4),
                    documentSet.getInt(5),documentSet.getBoolean(6),
                    documentSet.getDouble(7),documentSet.getString(8));
            temp.setID(documentSet.getInt(1));
            documentList.add(temp);
        }
        return documentList;
    }

    public ArrayList<String> getDocumentStringList() throws SQLException {
        ResultSet documentSet = executeQuery("SELECT * FROM documents");
        ArrayList<String> documentsTitleList = new ArrayList<>();
        while (documentSet.next()) {
            documentsTitleList.add(documentSet.getInt(1) + " \"" + documentSet.getString(2) + "\" " + documentSet.getString(3) + " (" + documentSet.getString("type")+")");
        }
        return documentsTitleList;
    }

    public ArrayList<Book> getBookList() throws SQLException {
        ResultSet bookSet = executeQuery("SELECT * FROM documents where type = \'BOOK\'");
        ArrayList<Book> bookList = new ArrayList<>();
        while (bookSet.next()) {
            Book temp = new Book(bookSet.getString(2),
                    bookSet.getString(3), bookSet.getBoolean(4), bookSet.getInt(5),
                    bookSet.getBoolean(6), bookSet.getDouble(7), bookSet.getString(8),
                    bookSet.getString(10), bookSet.getInt(11), bookSet.getBoolean(12));
            temp.setID(bookSet.getInt(1));
            bookList.add(temp);
        }
        return bookList;
    }

    public ArrayList<AudioVideoMaterial> getAVList() throws SQLException {
        ResultSet AVSet = executeQuery("SELECT * FROM documents where type = \'AV\'");
        ArrayList<AudioVideoMaterial> AVList = new ArrayList<>();
        while (AVSet.next()) {
            AudioVideoMaterial temp = new AudioVideoMaterial(AVSet.getString(2),
                    AVSet.getString(3), AVSet.getBoolean(4), AVSet.getInt(5),
                    AVSet.getBoolean(6), AVSet.getDouble(7), AVSet.getString(8));
            temp.setID(AVSet.getInt(1));
            AVList.add(temp);
        }
        return AVList;
    }

    public ArrayList<JournalArticle> getArticleList() throws SQLException, ParseException {
        ResultSet articleSet = executeQuery("SELECT * FROM documents where type = \'ARTICLE\'");
        ArrayList<JournalArticle> articleList = new ArrayList<>();
        while (articleSet.next()) {
            JournalArticle temp = new JournalArticle(articleSet.getString(2),
                    articleSet.getString(3), articleSet.getBoolean(4),
                    articleSet.getInt(5), articleSet.getBoolean(6),
                    articleSet.getDouble(7), articleSet.getString(8),
                    articleSet.getString(13), articleSet.getString(10),
                    articleSet.getString(14), articleSet.getString(15),
                    new SimpleDateFormat("yyyy-MM-dd").parse(articleSet.getString(16)));
            temp.setID(articleSet.getInt(1));
            articleList.add(temp);
        }
        return articleList;
    }

    public ArrayList<Debt> getDebtsList() throws SQLException, ParseException {
        ResultSet debtsSet = executeQuery("SELECT * FROM debts");
        ArrayList<Debt> debtsList = new ArrayList<>();
        while (debtsSet.next()) {
            Debt temp = new Debt(debtsSet.getInt(2), debtsSet.getInt(3),
                    new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(4)),
                    new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(5)),
                    debtsSet.getInt(6),debtsSet.getBoolean(7));
            temp.setDebtId(debtsSet.getInt(1));
            debtsList.add(temp);
        }
        return debtsList;
    }

    public Patron getPatron(int id) throws SQLException {
        ResultSet patronSet = executeQuery("SELECT * FROM users where (status = 'FACULTY' or status = 'STUDENT') and id = "+id);
        if(patronSet.next()) {
            Patron temp = new Patron(patronSet.getString(2),
                    patronSet.getString(3),patronSet.getString(4),patronSet.getString(5),
                    patronSet.getString(6),patronSet.getString(7),patronSet.getString(8));
            temp.setId(patronSet.getInt(1));
            return temp;
        }
        throw new NoSuchElementException();
    }

    public Librarian getLibrarian(int id) throws SQLException {
        ResultSet librarianSet = executeQuery("SELECT * FROM users where (status = 'LIBRARIAN') and id = " + id);
        if(librarianSet.next()) {
            Librarian temp = new Librarian(librarianSet.getString(2),
                    librarianSet.getString(3),librarianSet.getString(5),
                    librarianSet.getString(6),librarianSet.getString(7),librarianSet.getString(8));
            temp.setId(librarianSet.getInt(1));
            return temp;
        }

        throw new NoSuchElementException();
    }

    public Document getDocument(int id) throws SQLException {
        ResultSet documentSet = executeQuery("SELECT * FROM documents where id = "+ id);
        if (documentSet.next()) {
            Document temp = new Document(documentSet.getString(2),
                    documentSet.getString(3),documentSet.getBoolean(4),
                    documentSet.getInt(5),documentSet.getBoolean(6),
                    documentSet.getDouble(7),documentSet.getString(8));
            temp.setID(documentSet.getInt(1));
            return temp;
        }
        throw new NoSuchElementException();
    }

    public Book getBook(int id) throws SQLException {
        ResultSet bookSet = executeQuery("SELECT * FROM documents where type = \'BOOK\' and id ="+id);
        if (bookSet.next()) {
            Book temp = new Book(bookSet.getString(2),
                    bookSet.getString(3), bookSet.getBoolean(4), bookSet.getInt(5),
                    bookSet.getBoolean(6), bookSet.getDouble(7), bookSet.getString(8),
                    bookSet.getString(10), bookSet.getInt(11), bookSet.getBoolean(12));
            temp.setID(bookSet.getInt(1));
            return temp;
        }
        throw new NoSuchElementException();
    }

    public AudioVideoMaterial getAV(int id) throws SQLException {
        ResultSet AVSet = executeQuery("SELECT * FROM documents where type = \'AV\' and id = "+id);
        if (AVSet.next()) {
            AudioVideoMaterial temp = new AudioVideoMaterial(AVSet.getString(2),
                    AVSet.getString(3), AVSet.getBoolean(4), AVSet.getInt(5),
                    AVSet.getBoolean(6), AVSet.getDouble(7), AVSet.getString(8));
            temp.setID(AVSet.getInt(1));
            return temp;
        }
        throw new NoSuchElementException();
    }

    public JournalArticle getArticle(int id) throws SQLException, ParseException {
        ResultSet articleSet = executeQuery("SELECT * FROM documents where type = \'ARTICLE\' and id = " + id);
        if (articleSet.next()) {
            JournalArticle temp = new JournalArticle(articleSet.getString(2),
                    articleSet.getString(3), articleSet.getBoolean(4),
                    articleSet.getInt(5), articleSet.getBoolean(6),
                    articleSet.getDouble(7), articleSet.getString(8),
                    articleSet.getString(13), articleSet.getString(10),
                    articleSet.getString(14), articleSet.getString(15),
                    new SimpleDateFormat("yyyy-MM-dd").parse(articleSet.getString(16)));
            temp.setID(articleSet.getInt(1));
            return temp;
        }
        throw new NoSuchElementException();
    }

    public Debt getDebt(int id) throws SQLException, ParseException {
        ResultSet debtsSet = executeQuery("SELECT * FROM debts WHERE id = "+id);
        if (debtsSet.next()) {
            Debt temp = new Debt(debtsSet.getInt(2), debtsSet.getInt(3),
                    new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(4)),
                    new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(5)),
                    debtsSet.getInt(6),debtsSet.getBoolean(7));
            temp.setDebtId(debtsSet.getInt(1));
            return temp;
        }
        throw new NoSuchElementException();
    }

    public void soutDocs() throws SQLException {
        System.out.println("\nAll documents in database: ");
        for (String temp : getDocumentStringList()) {
            System.out.println(temp);
        }
        System.out.println();
    }

    public void soutUsers() throws SQLException {
        ResultSet usersSet = executeQuery("SELECT * FROM users");
        System.out.println("\nAll users in database:");
        while(usersSet.next()) {
            System.out.println(usersSet.getInt(1)+" "+usersSet.getString(2)+" "
                    +usersSet.getString(3)+" "+usersSet.getString(4)+" "
                    +usersSet.getString(5)+" "+usersSet.getString(6)+" "
                    +usersSet.getString(7)+" "+usersSet.getString(8));
        }
        System.out.println();
    }


    //main features: deletion

    public void deleteUser(int id) throws SQLException {
        this.executeUpdate("DELETE FROM users WHERE id=" + id);
    }

    public void deleteDocument(int id) throws SQLException {
        this.executeUpdate("DELETE FROM documents WHERE id=" + id);
    }

    public void deleteDebt(int debtId) throws SQLException {
        this.executeUpdate("DELETE FROM debts WHERE debt_id=" + debtId);
    }


    //main features: edit

    public void editUserColumn(int id, String column, String value) throws SQLException {
        String quotes1 = "";
        String quotes2 = "";
        try {
            int temp = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            quotes1 = "\'";
            quotes2 = "\'";
        }
        this.executeUpdate("UPDATE users " +
                "SET " + column + " = " + quotes1 + value + quotes2 + " WHERE id = " + id);
    }

    public void editDocumentColumn(int id, String column, String value) throws SQLException {
        String quotes1 = "";
        String quotes2 = "";
        try {
            int temp = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            quotes1 = "\'";
            quotes2 = "\'";
        }
        this.executeUpdate("UPDATE documents " +
                "SET " + column + " = " + quotes1 + value + quotes2 + " WHERE id = " + id);
    }

    public void editDebtColumn(int debtId, String column, String value) throws SQLException {
        String quotes1 = "";
        String quotes2 = "";
        try {
            int temp = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            quotes1 = "\'";
            quotes2 = "\'";
        }
        this.executeUpdate("UPDATE debts " +
                "SET " + column + " = " + quotes1 + value + quotes2 + " WHERE debt_id = " + debtId);
    }


    public boolean login(String login, String password) throws SQLException {
        ResultSet answer = executeQuery("SELECT * FROM users WHERE login = \'"+login+"\' and password = \'"+password+"\'");
        return answer.next();
    }

    public int loginId(String login, String password) throws SQLException {
        ResultSet answer = executeQuery("SELECT id FROM users WHERE login = \'"+login+"\' and password = \'"+password+"\'");
        if(answer.next()) {
            return answer.getInt(1);
        }
        throw new NoSuchElementException();
    }

    public String loginStatus(int id) throws SQLException {
        ResultSet answer = executeQuery("SELECT status FROM users WHERE id = "+id);
        if(answer.next()) {
            return answer.getString(1);
        }
        throw new NoSuchElementException();
    }

    public int findDebtID(int patronID, int docId) throws  SQLException{
        ResultSet debt = executeQuery("SELECT debt_id FROM debts WHERE patron_id = "+patronID+" AND document_id = "+docId);
        if(debt.next()){
            return debt.getInt(1);
        }
        throw  new NoSuchElementException();
    }
}