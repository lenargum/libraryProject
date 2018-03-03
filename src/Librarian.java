import javax.xml.crypto.Data;
import java.sql.SQLException;

public class Librarian extends User {

    Librarian(int id, String login, String password, String name, String surname, String phone, String address){
        super(name, surname, address, id, login, password, phone);
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
        for (int i = 0; i < database.getPatronList().size(); i++){
            if (database.getPatron(i).getListOfDocumentsPatron().contains(idDocument)){
                database.getPatron(i).getListOfDocumentsPatron().remove(i);
            }
        }
        database.deleteDocument(idDocument);
    }

    /**
     *
     * @param idPatron
     */
    public void deletePatron(int idPatron, Database database) throws SQLException {
        database.deleteUser(idPatron);
    }
}
