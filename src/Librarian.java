import java.sql.SQLException;

public class Librarian extends User {

    Librarian(String name, String surname, String address, int id, String login, String pasword){
        super(name, surname, address, id, login, pasword);
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
    public void deleteDocument(){

    }

    /**
     *
     * @param idPatron
     */
    public void deletePatron(int idPatron){

    }
}
