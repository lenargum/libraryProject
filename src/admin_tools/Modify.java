package admin_tools;

import documents.Document;
import tools.Database;


import java.sql.SQLException;

public class Modify {

    public void editLibrarianName(int idLibrarian, String newName, Database database)  {
        try {
            database.editUserColumn(idLibrarian, "firstname", newName);
            database.getLibrarian(idLibrarian).setName(newName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editLibrarianSurname(int idLibrarian, String newSurname, Database database) {
        try {
            database.editUserColumn(idLibrarian, "lastname", newSurname);
            database.getLibrarian(idLibrarian).setSurname(newSurname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editLibrarianAddress(int idLibrarian, String newAddress, Database database)  {
        try {
            database.editUserColumn(idLibrarian, "address", newAddress);
            database.getLibrarian(idLibrarian).setAddress(newAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editLibrarianPhone(int idLibrarian, String newPhone, Database database) {
        try {
            database.editUserColumn(idLibrarian, "phone", newPhone);
            database.getLibrarian(idLibrarian).setPhoneNumber(newPhone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editPatronName(int idPatron, String newName, Database database)  {
        try {
            database.editUserColumn(idPatron, "firstname", newName);
            database.getPatron(idPatron).setName(newName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void editPatronSurname(int idPatron, String newSurname, Database database)  {
        try {
            database.editUserColumn(idPatron, "lastname", newSurname);
            database.getPatron(idPatron).setSurname(newSurname);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void editPatronAddress(int idPatron, String newAddress, Database database) {
        try {
            database.editUserColumn(idPatron, "address", newAddress);
            database.getPatron(idPatron).setAddress(newAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void editPatronPhone(int idPatron, String newPhone, Database database)  {
        try {
            database.editUserColumn(idPatron, "phone", newPhone);
            database.getPatron(idPatron).setPhoneNumber(newPhone);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editPatronStatus(int idPatron, String newStatus, Database database) {
        try {
            database.editUserColumn(idPatron, "status", newStatus);
            database.getPatron(idPatron).setStatus(newStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePatron(int idPatron, Database database) {
        try {
            if (database.getPatron(idPatron).getListOfDocumentsPatron().isEmpty())
                database.deleteUser(idPatron);
            else
                System.out.println("  This user did not return documents!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editDocumentNumberOdCopies(int idDocument, int newNumberOfCopies, Database database) {
        try {
            database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(newNumberOfCopies));
            database.getDocument(idDocument).setNumberOfCopies(newNumberOfCopies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editDocumentIsAllowedForStudents(int idDocument, boolean isAllowed, Database database) {
        try {
            database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowed));
            database.getDocument(idDocument).setAllowedForStudents(isAllowed);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void editDocumentPrice(int idDocument, double newPrice, Database database) {
        try {
            database.editDocumentColumn(idDocument, "price", Double.toString(newPrice));
            database.getDocument(idDocument).setPrice(newPrice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editDocumentIsReference(int idDocument, boolean isReference, Database database) {
        try {
            database.editDocumentColumn(idDocument, "is_reference", Boolean.toString(isReference));
            database.getDocument(idDocument).setReference(isReference);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editBookEdition(int idBook, int newEdition, Database database){
        try {
            database.editDocumentColumn(idBook, "edition", Integer.toString(newEdition));
            database.getBook(idBook).setEdition(newEdition);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editBookIsBestseller(int idBook, boolean isBestseller, Database database) {
        try {
            database.editDocumentColumn(idBook, "bestseller", Boolean.toString(isBestseller));
            database.getBook(idBook).setBestseller(isBestseller);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
