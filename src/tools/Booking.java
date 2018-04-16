package tools;
import documents.Book;
import users.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


public class Booking {


    /**
     * Checks whether patron can take the following book.
     *
     * @param DocumentID   documents.Book ID to check.
     * @param database tools.Database that stores the information.
     * @return {@code true} if this patron can get the book, otherwise {@code false}.
     */
    public static boolean canRequestDocument(int DocumentID, int patronId, Database database) {
        return false;
    }


    public static void takeDocument(int documentId, int patronId, Database database){

    }

    /**
     * Take the following book ang give it to this patron.
     *
     * @param bookID   documents.Book to take.
     * @param database tools.Database that stores the information.
     */
    public static void takeBook(int bookID, int patronId, Database database) {
        try {
            Patron patron = database.getPatron(patronId);
            patron.getListOfDocumentsPatron().add(bookID);
            database.getBook(bookID).deleteCopy();
            decreaseCountOfCopies(bookID, database);
            Date dateBook = new Date();
            Date dateExpire = new Date();
            if (database.getBook(bookID).isBestseller() && !patron.getStatus().toLowerCase().equals("vp"))
                dateExpire.setTime(dateExpire.getTime() + 14 * 24 * 60 * 60 * 1000);
            else {
                switch (patron.getStatus().toLowerCase()) {
                    case "student":
                        dateExpire.setTime(dateExpire.getTime() + 21 * 24 * 60 * 60 * 1000);
                        break;
                    case "instructor":
                    case "ta":
                    case "professor":
                        dateExpire.setTime(dateExpire.getTime() + 28L * 24 * 60 * 60 * 1000);
                        break;
                    default:
                        throw new WrongUserTypeException("Patron <- There is no patron present " +
                                "in system with type " + patron.getStatus());
                }
            }

            Debt debt = new Debt(patronId, bookID, dateBook, dateExpire, 0, true);
            database.insertDebt(debt);

        } catch (SQLException | NoSuchElementException e) {
            System.out.println("Incorrect id=" + bookID);
        }
    }



    /**
     * Take the following audio/video ang give it to this patron.
     *
     * @param avID     Audio/video to take.
     * @param database tools.Database that stores the information.
     */
    public static void takeAV(int avID, int patronId, Database database) {
        try {
            if (canRequestDocument(avID, patronId, database)) {
                Patron patron = database.getPatron(patronId);
                patron.getListOfDocumentsPatron().add(avID);
                database.getAV(avID).deleteCopy();
                decreaseCountOfCopies(avID, database);
                Date dateBook = new Date();
                Date dateReturn = new Date();
                if (patron.getStatus().toLowerCase().equals("vp"))
                    dateReturn.setTime(dateReturn.getTime() + 7 * 24 * 60 * 60 * 1000);
                else dateReturn.setTime(dateReturn.getTime() + 14 * 24 * 60 * 60 * 1000);
                Debt debt = new Debt(patronId, avID, dateBook, dateReturn, 0, true);
                database.insertDebt(debt);
            }
        } catch (SQLException | NoSuchElementException e) {
            System.out.println("Incorrect id" + avID);
        }
    }

    /**
     * Take the following article ang give it to this patron.
     *
     * @param articleID Article to take.
     * @param database  tools.Database that stores the information.
     */
    public static void takeArticle(int articleID, int patronId, Database database) {
        try {
            if (canRequestDocument(articleID, patronId, database)) {
                Patron patron = database.getPatron(patronId);
                patron.getListOfDocumentsPatron().add(articleID);
                database.getArticle(articleID).deleteCopy();
                decreaseCountOfCopies(articleID, database);
                Date dateBook = new Date();
                Date dateReturn = new Date();
                if (patron.getStatus().toLowerCase().equals("vp"))
                    dateReturn.setTime(dateReturn.getTime() + 7 * 24 * 60 * 60 * 1000);
                else dateReturn.setTime(dateReturn.getTime() + 14 * 60 * 60 * 1000 * 24);
                Debt debt = new Debt(patronId, articleID, dateBook, dateReturn, 0, true);

                database.insertDebt(debt);
            }
        } catch (SQLException | NoSuchElementException e) {
            System.out.println("Incorrect id" + articleID);
        } catch (ParseException e) {
            System.out.println("Data parsing failed");
        }
    }

    /**
     *
     * @param documentID
     * @param patronId
     * @param database
     * @return
     * @throws SQLException
     * @throws ParseException
     */
    public static boolean findInRequests(int documentID, int patronId, Database database) throws SQLException, ParseException {
        List<Request> requests = database.getRequestsForPatron(patronId);
        for (Request i : requests) {
            if (i.getIdDocument() == documentID && i.getIdPatron() == patronId) return true;
        }
        return false;
    }

    /**
     * Decrease the number of copies of specified document by one.
     *
     * @param documentID documents.Document ID.
     * @param database   tools.Database that stores the information.
     * @throws SQLException If passed the wrong document ID.
     */
    private static void decreaseCountOfCopies(int documentID, Database database) throws SQLException {
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
