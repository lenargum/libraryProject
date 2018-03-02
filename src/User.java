import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class User {

    private int id;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;

    private String login;
    private String password;

    User(String name, String surname, String address, int id, String login, String password){
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getLibrary(Database database) throws SQLException {
        return database.getDocumentList();
    }

    public ArrayList<Book> getListOfBooks(Database database) throws SQLException {
        return database.getBookList();
    }

    public ArrayList<AudioVideoMaterial> getListOfAVs(Database database) throws SQLException {
        return database.getAVList();
    }

    public ArrayList<JournalArticle> getListOfArticles(Database database) throws SQLException, ParseException {
        return database.getArticleList();
    }
}
