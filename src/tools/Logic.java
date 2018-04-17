package tools;

import documents.Document;
import users.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.NoSuchElementException;

public class Logic {
    /**
     * Checks whether patron can take the following book.
     *
     * @param idDocument  documents.Book ID to check.
     * @param database tools.Database that stores the information.
     * @return {@code true} if this patron can get the book, otherwise {@code false}.
     */
    public static boolean canRequestDocument(int idDocument, int patronId, Database database) {
        try {
            database.getPatron(patronId);
        } catch (SQLException | NoSuchElementException e) {
            System.out.println("tools.Database <- users.Patron: No patron registered with ID=" + patronId);
            return false;
        }
        try {
            Patron patron = database.getPatron(patronId);
            Document doc = database.getDocument(idDocument);
            if(patron.findInRequests(idDocument, database)){
                System.out.println("You already request this document");
                return false;
            }
            if (!(patron instanceof Student) && (doc.getNumberOfCopies() != 0) &&
                    !(doc.isReference()) && !patron.getListOfDocumentsPatron().contains(idDocument) && !patron.findInRequests(idDocument, database)) {
                return true;
            } else if (doc.isAllowedForStudents() &&
                    doc.getNumberOfCopies() != 0 &&
                    !(doc.isReference()) && !patron.getListOfDocumentsPatron().contains(idDocument) && !patron.findInRequests(idDocument, database)) {
                return true;
            } else {
                if (doc.isReference() && doc.getNumberOfCopies() == 0)
                    System.out.println("No available copies. Document ID: " + idDocument);
                if (patron.getListOfDocumentsPatron().contains(idDocument))
                    System.out.println("Patron already have a copy of this document. Patron ID: " + patronId +
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
        } catch (ParseException e) {
            e.printStackTrace();
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

    public static boolean canFine(int documentId, int patronId, Database database) throws SQLException, ParseException {
        Debt debt = database.getDebt(database.findDebtID(patronId, documentId));
        debt.countFee(database);
        if(debt.getFee() == 0) return true;
        System.out.println("The patron with id " + patronId + " does not owe the library anything");
        return false;
    }

    public static boolean canModify(int privilege){
        return privilege > 0;
    }

    public static boolean canAdd(int privilege){
        return privilege > 1;
    }

    public static boolean canDelete(int privilege){
        return privilege == 3;
    }

}
