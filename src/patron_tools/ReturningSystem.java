package patron_tools;

import tools.Database;
import users.Patron;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class ReturningSystem {

    private void increaseCountOfCopies(int idDocument, Database database) throws SQLException {
        int count = database.getDocument(idDocument).getNumberOfCopies();
        database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count + 1));
    }

    /**
     * Return the document to the library.
     *
     * @param idDocument documents.Document ID.
     * @param database   tools.Database that stores the information.
     */
    public void returnDocument(Patron patron, int idDocument, Database database) {
        try {
            for (int i = 0; i < patron.getListOfDocumentsPatron().size(); i++) {
                if (patron.getListOfDocumentsPatron().get(i).equals(idDocument)) {
                    patron.getListOfDocumentsPatron().remove(i);
                    break;
                }
            }
            database.getDocument(idDocument).addCopy();
            increaseCountOfCopies(idDocument, database);
            System.out.println("Return confirmed!");
            int debtID = database.findDebtID(patron.getId(), idDocument);
            database.deleteDebt(debtID);
        } catch (NoSuchElementException | SQLException e) {
            System.out.println("Incorrect id");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect input");
        }
    }



}
