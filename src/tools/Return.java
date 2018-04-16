package tools;

import users.Patron;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.NoSuchElementException;

public class Return {



    /**
     * Return the book to the library.
     *
     * @param bookID   documents.Book ID.
     * @param database tools.Database that stores the information.
     */
    public void returnBook(int bookID, int patronId,  Database database) throws SQLException {
        Patron patron = database.getPatron(patronId);
        try {
            for (int i = 0; i < patron.getListOfDocumentsPatron().size(); i++) {
                if (patron.getListOfDocumentsPatron().get(i).equals(bookID)) {
                    patron.getListOfDocumentsPatron().remove(i);
                    break;
                }
            }
            database.getBook(bookID).addCopy();
            increaseCountOfCopies(bookID, database);
            int debtID = database.findDebtID(patronId, bookID);
            database.deleteDebt(debtID);
        } catch (NoSuchElementException | SQLException e) {
            System.out.println("Incorrect id");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect input");
        }
    }

    /**
     * Return the article to the library.
     *
     * @param articleID Article ID.
     * @param database  tools.Database that stores the information.
     */
    public void returnArticle(int articleID, int patronId, Database database) throws ParseException {
        try {
            Patron patron = database.getPatron(patronId);
            for (int i = 0; i < patron.getListOfDocumentsPatron().size(); i++) {
                if (patron.getListOfDocumentsPatron().get(i).equals(articleID)) {
                    patron.getListOfDocumentsPatron().remove(i);
                    break;
                }
            }
            database.getArticle(articleID).addCopy();
            increaseCountOfCopies(articleID, database);
            int debtID = database.findDebtID(patronId, articleID);
            database.deleteDebt(debtID);
        } catch (NoSuchElementException | SQLException e) {
            System.out.println("Incorrect id");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect input");
        }
    }

    /**
     * Return the audio/video to the library.
     *
     * @param avID     Audio/video ID.
     * @param database tools.Database that stores the information.
     */
    public void returnAV(int avID,int patronId,  Database database) {
        try {
            Patron patron = database.getPatron(patronId);
            for (int i = 0; i < patron.getListOfDocumentsPatron().size(); i++) {
                if (patron.getListOfDocumentsPatron().get(i).equals(avID)) {
                    patron.getListOfDocumentsPatron().remove(i);
                    break;
                }
            }
            database.getAV(avID).addCopy();
            increaseCountOfCopies(avID, database);
            int debtID = database.findDebtID(patronId, avID);
            database.deleteDebt(debtID);
        } catch (NoSuchElementException | SQLException e) {
            System.out.println("Incorrect id");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect input");
        }
    }

    /**
     * Return the document to the library.
     *
     * @param documentID documents.Document ID.
     * @param database   tools.Database that stores the information.
     */
    public void returnDocument(int documentID, int patronId, Database database) {
        try {
            Patron patron = database.getPatron(patronId);
            for (int i = 0; i < patron.getListOfDocumentsPatron().size(); i++) {
                if (patron.getListOfDocumentsPatron().get(i).equals(documentID)) {
                    patron.getListOfDocumentsPatron().remove(i);
                    break;
                }
            }
            database.getDocument(documentID).addCopy();
            increaseCountOfCopies(documentID, database);
            System.out.println("Return confirmed!");
            int debtID = database.findDebtID(patronId, documentID);
            database.deleteDebt(debtID);
        } catch (NoSuchElementException | SQLException e) {
            System.out.println("Incorrect id");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect input");
        }
    }

    /**
     * Decrease the number of copies of specified document by one.
     *
     * @param documentID documents.Document ID.
     * @param database   tools.Database that stores the information.
     * @throws SQLException If passed the wrong document ID.
     */
    private void decreaseCountOfCopies(int documentID, Database database) throws SQLException {
        int count = database.getDocument(documentID).getNumberOfCopies();
        database.editDocumentColumn(documentID, "num_of_copies", Integer.toString(count - 1));
    }

    /**
     * Increase the number of copies of specified document by one.
     *
     * @param documentID documents.Document ID.
     * @param database   tools.Database that stores the information.
     * @throws SQLException If passed the wrong document ID.
     */
    private void increaseCountOfCopies(int documentID, Database database) throws SQLException {
        int count = database.getDocument(documentID).getNumberOfCopies();
        database.editDocumentColumn(documentID, "num_of_copies", Integer.toString(count + 1));
    }
}
