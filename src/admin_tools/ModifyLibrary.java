package admin_tools;

import documents.Document;
import tools.Database;
import users.Librarian;

import java.sql.SQLException;

public class ModifyLibrary {
    public void addLibrarian(Librarian librarian, Database database)  {
        try {
            database.insertLibrarian(librarian);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLibrarian(int idLibrarian, Database database)  {
        try {
            //TODO: This is necessary?
//        Librarian librarian = database.getLibrarian(idLibrarian);
//        librarian.setName(null);
//        librarian.setSurname(null);
//        librarian.setAddress(null);
//        librarian.setPhoneNumber(null);
//        librarian.setLogin(null);
//        librarian.setPassword(null);
            database.deleteUser(idLibrarian);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteDocument(int idDocument, Database database) {
        try {
            //TODO: Notification for all users, who has request this document
            //TODO: Check if this document in Debts

            database.deleteDocument(idDocument);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
