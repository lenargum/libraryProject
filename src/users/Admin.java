package users;

import documents.Document;
import tools.Database;

import java.sql.SQLException;
import java.text.ParseException;

public class Admin {
    private String name;
    private String surname;
    private String login;
    private String password;
    private

    Admin(String name, String surname, String login, String password){
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public void addLibrarian(Librarian librarian, Database database) throws SQLException {
        database.insertLibrarian(librarian);
    }

    public void editLibrarianName(int idLibrarian, String newName,  Database database) throws SQLException {
        database.editUserColumn(idLibrarian, "firstname", newName);
        database.getLibrarian(idLibrarian).setName(newName);
    }

    public void editLibrarianSurname(int idLibrarian, String newSurname, Database database) throws SQLException {
        database.editUserColumn(idLibrarian, "lastname", newSurname);
        database.getLibrarian(idLibrarian).setSurname(newSurname);
    }

    public void editLibrarianAddress(int idLibrarian, String newAddress, Database database) throws SQLException {
        database.editUserColumn(idLibrarian, "address", newAddress);
        database.getLibrarian(idLibrarian).setAddress(newAddress);
    }

    public void editLibrarianPhone(int idLibrarian, String newPhone, Database database) throws SQLException {
        database.editUserColumn(idLibrarian, "phone", newPhone);
        database.getLibrarian(idLibrarian).setPhoneNumber(newPhone);
    }

    public void deleteLibrarian(int idLibrarian, Database database) throws SQLException {
        //TODO: This is necessary?
//        Librarian librarian = database.getLibrarian(idLibrarian);
//        librarian.setName(null);
//        librarian.setSurname(null);
//        librarian.setAddress(null);
//        librarian.setPhoneNumber(null);
//        librarian.setLogin(null);
//        librarian.setPassword(null);

        database.deleteUser(idLibrarian);
    }

    public void editPatronName(int idPatron, String newName, Database database) throws SQLException {
        database.editUserColumn(idPatron, "firstname", newName);
        database.getPatron(idPatron).setName(newName);
    }

    public void editPatronSurname(int idPatron, String newSurname, Database database) throws SQLException {
        database.editUserColumn(idPatron, "lastname", newSurname);
        database.getPatron(idPatron).setSurname(newSurname);
    }

    public void editPatronAddress(int idPatron, String newAddress, Database database) throws SQLException {
        database.editUserColumn(idPatron, "address", newAddress);
        database.getPatron(idPatron).setAddress(newAddress);
    }

    public void editPatronPhone(int idPatron, String newPhone, Database database) throws SQLException {
        database.editUserColumn(idPatron, "phone", newPhone);
        database.getPatron(idPatron).setPhoneNumber(newPhone);
    }

    public void editPatronStatus(int idPatron, String newStatus, Database database) throws SQLException {
        database.editUserColumn(idPatron, "status", newStatus);
        database.getPatron(idPatron).setStatus(newStatus);
    }

    public void deletePatron(int idPatron, Database database) throws SQLException {
        if (database.getPatron(idPatron).getListOfDocumentsPatron().isEmpty())
            database.deleteUser(idPatron);
        else
            System.out.println("  This user did not return documents!");
    }

    public void editDocumentNumberOdCopies(int idDocument, int newNumberOfCopies, Database database) throws SQLException {
        database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(newNumberOfCopies));
        database.getDocument(idDocument).setNumberOfCopies(newNumberOfCopies);
    }

    public void editDocumentIsAllowedForStudents(int idDocument, boolean isAllowed, Database database) throws SQLException {
        database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowed));
        database.getDocument(idDocument).setAllowedForStudents(isAllowed);
    }

    public void editDocumentPrice(int idDocument, double newPrice, Database database) throws SQLException {
        database.editDocumentColumn(idDocument, "price", Double.toString(newPrice));
        database.getDocument(idDocument).setPrice(newPrice);
    }

    public void editDocumentIsReference(int idDocument, boolean isReference, Database database) throws SQLException {
        database.editDocumentColumn(idDocument, "is_reference", Boolean.toString(isReference));
        database.getDocument(idDocument).setReference(isReference);
    }

    public void editBookEdition(int idBook, int newEdition, Database database) throws SQLException {
        database.editDocumentColumn(idBook, "edition", Integer.toString(newEdition));
        database.getBook(idBook).setEdition(newEdition);
    }

    public void editBookIsBestseller(int idBook, boolean isBestseller, Database database) throws SQLException {
        database.editDocumentColumn(idBook, "bestseller", Boolean.toString(isBestseller));
        database.getBook(idBook).setBestseller(isBestseller);
    }

    public void deleteDocument(int idDocument, Database database) throws SQLException, ParseException {
        Document document = database.getDocument(idDocument);
        //TODO: Notification for all users, who has request this document
        //TODO: Check if this document in Debts

        database.deleteDocument(idDocument);

    }
}
