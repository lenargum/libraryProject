import javax.xml.crypto.Data;
import java.sql.SQLException;

public class Librarian extends User {

    Librarian(String login, String password, String name, String surname, String phone, String address){
        super(login, password,name,surname,phone,address);
    }

    /**
     * @param : Document
     */
    public void addBook(Book book, Database database) throws SQLException {
        database.insertBook(book);
    }

    /**
     * @param : Patron
     */
    public void RegisterPatron(Patron patron, Database database) throws SQLException {
        database.insertPatron(patron);
    }

    /**
     * @param : id of document
     */
    public void deleteDocument(int idDocument, Database database) throws SQLException {
        database.deleteDocument(idDocument);
    }

    /**
     *
     * @param idPatron
     */
    public void deletePatron(int idPatron, Database database) throws SQLException {
        database.deleteUser(idPatron);
    }

    public void modifyDocumentPrice(int idDoicument, Database database, double price) throws SQLException {
        database.getDocument(idDoicument).setPrice(price);
        database.editDocumentColumn(idDoicument, "price", Double.toString(price));
    }

    public void modifyBookEdition(int idBook, Database database, int edition) throws SQLException {
        database.getBook(idBook).setEdition(edition);
        database.editDocumentColumn(idBook, "edition", Integer.toString(edition));
    }

    public void modifyDocumentAllowness(int idDocument, Database database, boolean isAllowedForStudents) throws SQLException {
        database.getDocument(idDocument).setAllowedForStudents(isAllowedForStudents);
        database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowedForStudents));
    }

    public void modifyBookBestseller(int idBook, Database database, boolean bestseller) throws SQLException {
        database.getBook(idBook).setBestseller(bestseller);
        database.editDocumentColumn(idBook, "bestseller", Boolean.toString(bestseller));
    }

    public void modifePatronSurname(int idPatron, Database database, String surname) throws SQLException {
        database.getPatron(idPatron).setSurname(surname);
        database.editUserColumn(idPatron, "lastname", surname);
    }

    public void modifePatronAddress(int idPatron, Database database, String address) throws SQLException {
        database.getPatron(idPatron).setAddress(address);
        database.editUserColumn(idPatron, "address", address);
    }

    public void modifePatronPhoneNumber(int idPatron, Database database, String phoneNumber) throws SQLException {
        database.getPatron(idPatron).setPhoneNumber(phoneNumber);
        database.editUserColumn(idPatron, "phone", phoneNumber);
    }

}
