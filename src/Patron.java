import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class Patron extends User {
    private String status;
    public ArrayList listOfDocumentsPatron;

    Patron(String login, String password, String status, String name, String surname,String phone, String address) {
        super(login,password,name,surname,phone,address);
        this.status = status;
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
        if ((this.status == "faculty") && (database.getBook(idBook).getNumberOfCopies() != 0) &&
                (!database.getBook(idBook).isBestseller())) {
            return true;
        } else {
            return database.getBook(idBook).isAllowedForStudents() & database.getBook(idBook).getNumberOfCopies() != 0 &&
                    !(database.getBook(idBook).isBestseller()) &&
                    !database.getBook(idBook).isReference();
        }
    }

    public boolean canRequestArticle(int idArticle, Database database) throws SQLException, ParseException {
        if ((this.status == "faculty")&& (database.getArticle(idArticle).getNumberOfCopies() != 0)){
            return true;
        } else {
            return database.getArticle(idArticle).isAllowedForStudents() &&
                    database.getArticle(idArticle).getNumberOfCopies() != 0 &&
                    !database.getArticle(idArticle).isReference();
        }
    }

    public boolean canRequestAV(int idAV, Database database) throws SQLException {
        if ((this.status == "faculty") && (database.getAV(idAV).getNumberOfCopies() != 0)) {
            return true;
        } else {
            return database.getAV(idAV).isAllowedForStudents() &&
                    database.getAV(idAV).getNumberOfCopies() != 0 &&
                    !database.getAV(idAV).isReference();
        }
    }

    /**
     * MAIN FUNCTION OF REQUESTING DOCUMENTS
     */
    public boolean canRequestDocument(int idDocument, Database database) throws SQLException {
        if((this.status == "faculty") && (database.getDocument(idDocument).getNumberOfCopies() != 0)){
            return true;
        } else{
          return database.getDocument(idDocument).isAllowedForStudents() &&
                  database.getDocument(idDocument).getNumberOfCopies() != 0 &&
                  !database.getDocument(idDocument).isReference();
        }
    }

    /**
     * @param : id of Document, Database
     */
    public void takeBook(int idBook, Database database) throws SQLException {
        if (canRequestBook(idBook, database)) {
            this.getListOfDocumentsPatron().add(idBook);
            database.getBook(idBook).deleteCopy();
            //TODO: set date of reservation
        }
    }

    public void takeAV(int idAV, Database database) throws SQLException {
        if (canRequestAV(idAV, database)) {
            this.getListOfDocumentsPatron().add(idAV);
            database.getAV(idAV).deleteCopy();
            //TODO: set date of reservation
        }
    }

    public void takeArticle(int idArticle, Database database) throws SQLException, ParseException {
        if(canRequestArticle(idArticle, database)){
            this.getListOfDocumentsPatron().add(idArticle);
            database.getArticle(idArticle).deleteCopy();
        }
    }

    /**
     * MAIN FUNCTIOM OF BOOKING SYSTEM
     */

    public void takeDocument(int idDocument, Database database) throws SQLException {
        if(canRequestDocument(idDocument, database)){
            listOfDocumentsPatron.add(idDocument);
            database.getDocument(idDocument).deleteCopy();
        }

    }

    /**
     * @param : id of Document, Database
     */
    public void returnBook(int idBook, Database database) throws SQLException {
        listOfDocumentsPatron.remove(idBook);
        database.getBook(idBook).addCopy();
    }

    public void returnArticle(int idArticle, Database database) throws SQLException, ParseException {
        listOfDocumentsPatron.remove(idArticle);
        database.getArticle(idArticle).addCopy();
    }

    public void returnAV(int idAV, Database database) throws SQLException {
        listOfDocumentsPatron.remove(idAV);
        database.getAV(idAV).addCopy();
    }

    /**
     * MAIN FUNCTION OF RETURNING SYSTEM
     */

    public void returnDocument(int idDocument, Database database) throws SQLException {
        listOfDocumentsPatron.remove(idDocument);
        database.getDocument(idDocument).addCopy();
    }

    /**
     * @param : id of document
     */
    public void renewDocument() {
        //TODO: Connect with date of reservation
    }
}
