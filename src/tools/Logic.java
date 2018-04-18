package tools;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import users.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

public class Logic {

    //CHECKING POSSIBILITIES OF PATRONS
    /**
     * Checks whether patron can take the following book.
     *
     * @param idDocument  documents.Book ID to check.
     * @param database tools.Database that stores the information.
     * @return {@code true} if this patron can get the book, otherwise {@code false}.
     */
    public static boolean canRequestDocument(int idDocument, int patronId, Database database) {
        try{
            database.getPatron(patronId);
        } catch (NoSuchElementException e){
            System.out.println("tools.Database <- users.Patron: Incorrect ID. users.Patron with ID="
                    + patronId + " may not be registered id database.");
        }
        try {
            Patron patron = database.getPatron(patronId);
            Document doc = database.getDocument(idDocument);
            if(findInRequests(patron, idDocument, database)){
                System.out.println("You already request this document");
                return false;
            }
            if(doc.getNumberOfCopies() == 0){
                System.out.println("No available copies. Document ID: " + idDocument);
                return false;
            }
            if(doc.isReference()){
                System.out.println("Reference materials are not available for taking. Document ID: " + idDocument);
                return false;
            }
            if (patron.getListOfDocumentsPatron().contains(idDocument)) {
                System.out.println("Patron already have a copy of this document. Patron ID: " + patronId +
                        ", Document ID: " + idDocument);
                return false;
            }
            if(patron instanceof Student && !doc.isAllowedForStudents()){
                System.out.println("This document is not allowed for students. Document ID:" + idDocument);
                return false;
            }

            return true;
        } catch ( NoSuchElementException e) {
            System.out.println("tools.Database <- users.Patron: Incorrect ID. documents.Document with ID="
                    + idDocument + " may not be registered id database.");
            return false;
        }
    }

    private static boolean findInRequests(Patron patron, int docId, Database database) {
            List<Request> requests = database.getRequestsForPatron(patron.getId());
            for (Request i : requests) {
                if (i.getIdDocument() == docId && i.getIdPatron() == patron.getId()) return true;
            }
            return false;
    }

    public static boolean canRenewDocument(int documentId, int patronId, Database database) throws SQLException, ParseException {
        Debt debt = database.getDebt(database.findDebtID(patronId, documentId));
        if(debt.canRenew()) return true;
        System.out.println("The document is already renewed, so you need to return it!");
        return false;
    }

    public static boolean canReturnDocument(int documentId, int patronId, Database database) throws SQLException, ParseException {
        Debt debt = database.getDebt(database.findDebtID(patronId, documentId));
        debt.countFee(database);
        if(debt.getFee() == 0) return true;
        //notify patron he has to fine the debt
        return false;
    }

    public static boolean canFine(int debtId, Database database) throws SQLException, ParseException {
        Debt debt = database.getDebt(debtId);
        debt.countFee(database);
        if(debt.getFee() == 0) return true;
        System.out.println("The patron with id " + debt.getPatronId() + " does not owe the library anything");
        return false;
    }

    //CHECKING POSSIBILITIES OF LIBRARIANS

    public static boolean canModify(int librarianId, Database database) throws SQLException {
        return database.getLibrarian(librarianId).getPrivilege() > 0;
    }

    public static boolean canAdd(int librarianId, Database database) throws SQLException {
        return database.getLibrarian(librarianId).getPrivilege() > 1;
    }

    public static boolean canDelete(int librarianId, Database database) throws SQLException{
        return database.getLibrarian(librarianId).getPrivilege() == 3;
    }

    //LOGIC OF SETTING DATES

    public static Date expireDate(int patronId, int docId, Database database) throws SQLException {
        Document document = database.getDocument(docId);
        Date date;
        if(document instanceof Book){
            date = dateForBooks(patronId, docId, database);
        } else if(document instanceof JournalArticle || document instanceof AudioVideoMaterial){
            date = dateForArticlesAndAVs(patronId, database);
        } else {
            throw new NoSuchElementException();
        }

        return date;
    }

    private static Date dateForBooks(int patronId, int bookId, Database database) throws SQLException {
        Book book = database.getBook(bookId);
        Patron patron = database.getPatron(patronId);
        Date date ;
        if(patron instanceof Student){
            if(book.isBestseller()) date = Constants.setTwoWeeks();
            else date = Constants.setThreeWeeks();
        } else if (patron instanceof VisitingProfessor){
            date = Constants.setWeek();
        } else if(patron instanceof Instructor || patron instanceof Professor || patron instanceof TeachingAsistent){
            if(book.isBestseller()) date = Constants.setTwoWeeks();
            else date = Constants.setFourWeeks();
        } else {
            throw new WrongUserTypeException();
        }

        return date;
    }

    private static Date dateForArticlesAndAVs(int patronId, Database database) throws SQLException {
        Patron patron = database.getPatron(patronId);
        Date date;
        if(patron instanceof Student){
            date = Constants.setTwoWeeks();
        } else if (patron instanceof VisitingProfessor){
            date = Constants.setWeek();
        } else if(patron instanceof Instructor || patron instanceof Professor || patron instanceof TeachingAsistent){
            date = Constants.setTwoWeeks();
        } else {
            throw new WrongUserTypeException();
        }

        return date;

    }

    public static Date renewExpireDate(int debtId, Database database) throws SQLException, ParseException {
        Debt debt = database.getDebt(debtId);
        Document document = database.getDocument(debt.getDocumentId());
        if(document instanceof  Book){

            return renewDateBooks(debt, database);
        } else if(document instanceof JournalArticle || document instanceof AudioVideoMaterial){

            return renewDateArticlesAndAVs(debt, database);
        } else {
            throw new NoSuchElementException();
        }

    }

    private static Date renewDateBooks(Debt debt, Database database) throws SQLException {
        Book book = database.getBook(debt.getDocumentId());
        Patron patron = database.getPatron(debt.getPatronId());
        if(patron instanceof Student){
            if(book.isBestseller()){
                return Constants.setTwoWeeks(debt.getExpireDate());
            } else {
                return Constants.setThreeWeeks(debt.getExpireDate());
            }
        } else if (patron instanceof VisitingProfessor){
            return Constants.setWeek(debt.getExpireDate());
        } else if (patron instanceof Instructor || patron instanceof Professor || patron instanceof TeachingAsistent){
            if(book.isBestseller()){
                return Constants.setTwoWeeks(debt.getExpireDate());
            } else {
                return Constants.setFourWeeks(debt.getExpireDate());
            }
        } else {
            throw new WrongUserTypeException();
        }
    }

    private static Date renewDateArticlesAndAVs(Debt debt, Database database) throws SQLException {
        Patron patron= database.getPatron(debt.getPatronId());
        if(patron instanceof VisitingProfessor){
            return Constants.setWeek(debt.getExpireDate());
        } else if (patron instanceof Instructor || patron instanceof Professor || patron instanceof TeachingAsistent || patron instanceof Student){
            return Constants.setTwoWeeks(debt.getExpireDate());
        } else {
            throw new WrongUserTypeException();
        }
    }

}
