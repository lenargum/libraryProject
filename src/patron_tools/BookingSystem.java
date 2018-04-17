package patron_tools;

import documents.Document;
import tools.Database;
import tools.Debt;
import tools.Request;
import users.Patron;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class BookingSystem {
    /**
     * Checks whether patron can take the following document.
     *
     * @param idDocument documents.Document ID to check.
     * @param database   tools.Database that stores the information.
     * @return {@code true} if this patron can get the document, otherwise {@code false}.
     */
    public boolean canRequestDocument(Patron patron, int idDocument, Database database) {
        try {
            database.getPatron(patron.getId());
        } catch (SQLException | NoSuchElementException e) {
            System.out.println("tools.Database <- users.Patron: No patron registered with ID=" + patron.getId());
            return false;
        }
        try {
            Document doc = database.getDocument(idDocument);
            if ((patron.getStatus().toLowerCase().equals("instructor") || (patron.getStatus().toLowerCase().equals("ta")) || (patron.getStatus().toLowerCase().equals("vp")) ||
                    (patron.getStatus().toLowerCase().equals("professor"))) && (doc.getNumberOfCopies() != 0) &&
                    !(doc.isReference()) && !patron.getListOfDocumentsPatron().contains(idDocument) && !findInRequests(patron, idDocument, database)) {
                return true;
            } else if (doc.isAllowedForStudents() &&
                    doc.getNumberOfCopies() != 0 &&
                    !(doc.isReference()) && !patron.getListOfDocumentsPatron().contains(idDocument) && !findInRequests(patron, idDocument, database)) {
                return true;
            } else {
                if (doc.isReference() && doc.getNumberOfCopies() == 0)
                    System.out.println("No available copies. Document ID: " + idDocument);
                if (patron.getListOfDocumentsPatron().contains(idDocument))
                    System.out.println("Patron already have a copy of this document. Patron ID: " + patron.getId() +
                            ", Document ID: " + idDocument);
                if (!doc.isAllowedForStudents())
                    System.out.println("This document is not allowed for students. Document ID:" + idDocument);
                if (doc.isReference())
                    System.out.println("Reference materials are not available for taking. Document ID: " + idDocument);
                return false;
            }
        } catch (SQLException | NoSuchElementException e) {
            System.out.println("tools.Database <- users.Patron: Incorrect ID. documents.Document with ID="
                    + idDocument + " may not be registered id database.");
            return false;
        }
    }

    /**
     * Take the following book ang give it to this patron.
     *
     * @param idBook   documents.Book to take.
     * @param database tools.Database that stores the information.
     */
    public void takeBook(Patron patron, int idBook, Database database) {
        try {

            patron.getListOfDocumentsPatron().add(idBook);
            database.getBook(idBook).deleteCopy();
            decreaseCountOfCopies(idBook, database);
            Date dateBook = new Date();
            Date dateExpire = new Date();
            if (database.getBook(idBook).isBestseller() && !patron.getStatus().toLowerCase().equals("vp"))
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
                        dateExpire.setTime(dateExpire.getTime() + 7 * 24 * 60 * 60 * 1000);
                        break;
                }
            }

            Debt debt = new Debt(patron.getId(), idBook, dateBook, dateExpire, 0, true);
            database.insertDebt(debt);

        } catch (SQLException | NoSuchElementException e) {
            System.out.println("Incorrect id=" + idBook);
        }
    }

    /**
     * Take the following audio/video ang give it to this patron.
     *
     * @param idAV     Audio/video to take.
     * @param database tools.Database that stores the information.
     */
    public void takeAV(Patron patron, int idAV, Database database) {
        try {
            if (canRequestDocument(patron, idAV, database)) {
                patron.getListOfDocumentsPatron().add(idAV);
                database.getAV(idAV).deleteCopy();
                decreaseCountOfCopies(idAV, database);
                Date date = new Date();
                Date date2 = new Date();
                if (patron.getStatus().toLowerCase().equals("vp"))
                    date2.setTime(date2.getTime() + 7 * 24 * 60 * 60 * 1000);
                else date2.setTime(date2.getTime() + 14 * 24 * 60 * 60 * 1000);
                Debt debt = new Debt(patron.getId(), idAV, date, date2, 0, true);
                database.insertDebt(debt);
            }
        } catch (SQLException | NoSuchElementException e) {
            System.out.println("Incorrect id" + idAV);
        }
    }

    /**
     * Take the following article ang give it to this patron.
     *
     * @param idArticle Article to take.
     * @param database  tools.Database that stores the information.
     */
    public void takeArticle(Patron patron, int idArticle, Database database) {
        try {
            if (canRequestDocument(patron, idArticle, database)) {
                patron.getListOfDocumentsPatron().add(idArticle);
                database.getArticle(idArticle).deleteCopy();
                decreaseCountOfCopies(idArticle, database);
                Date date = new Date();
                Date date2 = new Date();
                if (patron.getStatus().toLowerCase().equals("vp"))
                    date2.setTime(date2.getTime() + 7 * 24 * 60 * 60 * 1000);
                else date2.setTime(date2.getTime() + 14 * 60 * 60 * 1000 * 24);
                Debt debt = new Debt(patron.getId(), idArticle, date, date2, 0, true);

                database.insertDebt(debt);
            }
        } catch (SQLException | NoSuchElementException e) {
            System.out.println("Incorrect id" + idArticle);
        } catch (ParseException e) {
            System.out.println("Data parsing failed");
        }
    }

    /**
     * Decrease the number of copies of specified document by one.
     *
     * @param idDocument documents.Document ID.
     * @param database   tools.Database that stores the information.
     * @throws SQLException If passed the wrong document ID.
     */
    private void decreaseCountOfCopies(int idDocument, Database database) {
        try {
            int count = database.getDocument(idDocument).getNumberOfCopies();
            database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count - 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * Make request the following document
     *
     * @param idDocument document to request
     * @param database   Database that stores the information
     * @throws SQLException something went wrong in database
     */
    public void makeRequest(Patron patron, int idDocument, Database database) {
        try {

            Request request = new Request(patron, database.getDocument(idDocument), new Date(), false);
            if (!findInRequests(patron, idDocument, database))
                database.insertRequest(request);

        } catch (NoSuchElementException e) {
            System.out.println("Incorrect id" + idDocument);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean findInRequests(Patron patron, int docId, Database database) {
        try {
            List<Request> requests = database.getRequestsForPatron(patron.getId());
            for (Request i : requests) {
                if (i.getIdDocument() == docId && i.getIdPatron() == patron.getId()) return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * patron renews document after approving
     *
     * @param docID    documents.Document ID
     * @param database tools.Database stores the information
     */
    public void renewDocument(Patron patron, int docID, Database database) {

        try {
            Debt debt = database.getDebt(database.findDebtID(patron.getId(), docID));
            Date expDate = debt.getExpireDate();
            expDate.setTime(expDate.getTime() + 7 * 60 * 60 * 24 * 1000);
            if (!patron.getStatus().equals("vp")) {
                database.editDebtColumn(debt.getDebtId(), "can_renew", "false");
            }
            debt.setExpireDate(expDate);
            database.editDebtColumn(debt.getDebtId(), "expire_date",
                    (new SimpleDateFormat("yyyy-MM-dd")).format(debt.getExpireDate()));

        } catch (NoSuchElementException | SQLException e) {
            System.out.println("Incorrect id");
        } catch (ParseException e) {
            System.out.println("Incorrect data parsing");
        }
    }

    /**
     * patron sends request to renew document
     *
     * @param debtId   - id of debt patron wants to renew
     * @param database - information storage
     */
    public void sendRenewRequest(Patron patron, int debtId, Database database) {
        try {
            Debt debt = database.getDebt(debtId);
            Document doc = database.getDocument(debt.getDocumentId());
            Date date = new Date();
            Request request = new Request(patron, doc, date, true);

            if (debt.canRenew())
                database.insertRequest(request);
            else System.out.println("The document is already renewed, so you need to return it!");
        } catch (SQLException | ParseException e) {
            System.out.println("Incorrect id");
        }
    }

}
