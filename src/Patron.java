//import javax.xml.crypto.Data;
import com.sun.deploy.config.AutoUpdater;
import jdk.nashorn.internal.scripts.JO;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Patron extends User {
    private String status;
    private ArrayList<Integer> listOfDocumentsPatron;

    Patron(String login, String password, String status, String name, String surname,String phone, String address) {
        super(login,password,name,surname,phone,address);
        this.status = status;
        listOfDocumentsPatron = new ArrayList();
    }

    public ArrayList getListOfDocumentsPatron() {
        return listOfDocumentsPatron;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    /**
     * @return true: if Patron can get the document, otherwise false
     */
    public boolean canRequestBook(int idBook, Database database) throws SQLException {
        Book book = database.getBook(idBook);
        if ((this.status.toLowerCase().equals("faculty")) && (book.getNumberOfCopies() != 0) &&
                !book.isReference() && !getListOfDocumentsPatron().contains(idBook)) {
            return true;
        } else {
            return book.isAllowedForStudents() && book.getNumberOfCopies() != 0 &&
                    !book.isReference()  && !getListOfDocumentsPatron().contains(idBook);
        }
    }

    public boolean canRequestArticle(int idArticle, Database database) throws SQLException, ParseException {
        JournalArticle article = database.getArticle(idArticle);
        if ((this.status.toLowerCase().equals("faculty"))&& (article.getNumberOfCopies() != 0)&&
                !article.isReference() && !getListOfDocumentsPatron().contains(idArticle)){
            return true;
        } else {
            return article.isAllowedForStudents() &&
                    article.getNumberOfCopies() != 0 &&
                    !article.isReference() && !getListOfDocumentsPatron().contains(idArticle);
        }
    }

    public boolean canRequestAV(int idAV, Database database) throws SQLException {
        AudioVideoMaterial av = database.getAV(idAV);
        if ((this.status.toLowerCase().equals("faculty")) && (av.getNumberOfCopies() != 0) &&
                !av.isReference() && !getListOfDocumentsPatron().contains(idAV)) {
            return true;
        } else {
            return av.isAllowedForStudents() &&
                    av.getNumberOfCopies() != 0 &&
                    !av.isReference() && !getListOfDocumentsPatron().contains(idAV);
        }
    }

    /**
     * MAIN FUNCTION OF REQUESTING DOCUMENTS
     */
    public boolean canRequestDocument(int idDocument, Database database) throws SQLException {
        Document doc = database.getDocument(idDocument);
        if((this.status.toLowerCase().equals("faculty")) && (doc.getNumberOfCopies() != 0) &&
                !(doc.isReference()) && !getListOfDocumentsPatron().contains(idDocument)){
            return true;
        }
        else if (doc.isAllowedForStudents() &&
                  doc.getNumberOfCopies() != 0 &&
                  !(doc.isReference()) && !getListOfDocumentsPatron().contains(idDocument)){
            return true;
        } else{
            if(doc.isReference() && doc.getNumberOfCopies() == 0)
                System.out.println("Not copies");
            if(getListOfDocumentsPatron().contains(idDocument))
                System.out.println("You already have copy of this document");
            System.out.println("This Document is not for you");
            return false;
        }
    }

    /**
     * @param : id of Document, Database
     */
    public void takeBook(int idBook, Database database) throws SQLException {
        if (canRequestBook(idBook, database)) {
            this.getListOfDocumentsPatron().add(idBook);
            database.getBook(idBook).deleteCopy();
            Date date = new Date();
            Debt debt = new Debt(getId(), idBook, date, date, 0, true);
            database.insertDebt(debt);
        }
    }

    public void takeAV(int idAV, Database database) throws SQLException {
        if (canRequestAV(idAV, database)) {
            this.getListOfDocumentsPatron().add(idAV);
            database.getAV(idAV).deleteCopy();
            Date date = new Date();
            Debt debt = new Debt(getId(), idAV, date, date, 0, true);
            database.insertDebt(debt);
        }
    }

    public void takeArticle(int idArticle, Database database) throws SQLException, ParseException {
        if(canRequestArticle(idArticle, database)){
            this.getListOfDocumentsPatron().add(idArticle);
            database.getArticle(idArticle).deleteCopy();
            Date date = new Date();
            Debt debt = new Debt(getId(), idArticle, date, date, 0, true);
            database.insertDebt(debt);
        }
    }

    /**
     * MAIN FUNCTIOM OF BOOKING SYSTEM
     */

    public void takeDocument(int idDocument, Database database) throws SQLException {
        if(canRequestDocument(idDocument, database)){
            getListOfDocumentsPatron().add(idDocument);
            database.getDocument(idDocument).deleteCopy();
            Date date = new Date();
            Debt debt = new Debt(getId(), idDocument, date, date, 0, true);
            database.insertDebt(debt);
        }

    }

    /**
     * @param : id of Document, Database
     */
    public void returnBook(int idBook, Database database) throws SQLException {
        for(int i = 0; i < listOfDocumentsPatron.size(); i++){
            if (getListOfDocumentsPatron().get(i).equals(Integer.toString(idBook))){
                getListOfDocumentsPatron().remove(i);
                break;
            }
        }
        database.getBook(idBook).addCopy();
        int debtID = database.findDebtID(this.getId(), idBook);
        database.deleteDebt(debtID);
    }

    public void returnArticle(int idArticle, Database database) throws SQLException, ParseException {
        for(int i = 0; i < listOfDocumentsPatron.size(); i++){
            if (getListOfDocumentsPatron().get(i).equals(Integer.toString(idArticle))){
                getListOfDocumentsPatron().remove(i);
                break;
            }
        }
        database.getArticle(idArticle).addCopy();
        int debtID = database.findDebtID(this.getId(), idArticle);
        database.deleteDebt(debtID);
    }

    public void returnAV(int idAV, Database database) throws SQLException {
        for(int i = 0; i < listOfDocumentsPatron.size(); i++){
            if (getListOfDocumentsPatron().get(i).equals(Integer.toString(idAV))){
                getListOfDocumentsPatron().remove(i);
                break;
            }
        }
        database.getAV(idAV).addCopy();
        int debtID = database.findDebtID(this.getId(), idAV);
        database.deleteDebt(debtID);
    }

    /**
     * MAIN FUNCTION OF RETURNING SYSTEM
     */

    public void returnDocument(int idDocument, Database database) throws SQLException {
        for(int i = 0; i < listOfDocumentsPatron.size(); i++){
            if (getListOfDocumentsPatron().get(i).equals(Integer.toString(idDocument))){
                getListOfDocumentsPatron().remove(i);
                break;
            }
        }
        database.getDocument(idDocument).addCopy();
        int debtID = database.findDebtID(this.getId(), idDocument);
        database.deleteDebt(debtID);
    }

    /**
     * @param : id of document
     */
    public void renewDocument() {
        //TODO: Connect with date of reservation
    }
}
