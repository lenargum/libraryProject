import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class Patron extends User {
    private String status;
    public ArrayList<Document> listOfDocumentsPatron;

    Patron(String name, String surname, String address, String status, int id, String login, String password){
        super(name, surname, address, id,login, password);
        this.status = status;
    }

    public ArrayList<Document> getListOfDocumentsPatron() {
        return listOfDocumentsPatron;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    /**
     *
     * @return true: if Patron can get the document, otherwise false
     */
    public boolean canRequestBook(int idBook, Database database) throws SQLException {
        if ((this.status == "faculty")& (database.getBook(idBook).getNumberOfCopies() != 0) &
                (!database.getBook(idBook).isBestseller())) {
            return true;
        } else{
            return database.getBook(idBook).isAllowedForStudents() & database.getBook(idBook).getNumberOfCopies()!= 0 &
                    !(database.getBook(idBook).isBestseller());
        }
    }

    public boolean canRequestArticle(int idArticle, Database database) throws SQLException, ParseException {
        if ((this.status == "faculty")& (database.getArticle(idArticle).getNumberOfCopies() != 0)) {
            return true;
        } else{
            return database.getArticle(idArticle).isAllowedForStudents() & database.getArticle(idArticle).getNumberOfCopies() != 0;
        }
    }

    public boolean canRequestAV(int idAV, Database database) throws SQLException {
        if ((this.status == "faculty")& (database.getAV(idAV).getNumberOfCopies() != 0)) {
            return true;
        } else{
            return database.getAV(idAV).isAllowedForStudents() & database.getAV(idAV).getNumberOfCopies() != 0;
        }
    }

    /**
     * @param : id of Document, Database
     */
    public void takeBook(int idBook, Database database) throws SQLException {
        if (canRequestBook(idBook, database)){
            this.getListOfDocumentsPatron().add(idBook, database.getBook(idBook));
            //this.getListOfDocumentsPatron().get(idBook).setRenewable(true);
            database.getBook(idBook).setNumberOfCopies(database.getBook(idBook).getNumberOfCopies() - 1);
            database.getBook(idBook).setRenewable(true);
            //TODO: set date of reservation
        }
    }

    public void takeArticle(int idArticle, Database database) throws SQLException, ParseException {
        if (canRequestArticle(idArticle, database)){
            this.getListOfDocumentsPatron().add(idArticle, database.getArticle(idArticle));
            database.getArticle(idArticle).setNumberOfCopies(database.getArticle(idArticle).getNumberOfCopies() - 1);
            //TODO: set date of reservation
        }
    }

    public void takeAV(int idAV, Database database) throws SQLException {
        if(canRequestAV(idAV, database)){
            this.getListOfDocumentsPatron().add(idAV, database.getAV(idAV));
            database.getAV(idAV).setNumberOfCopies(database.getAV(idAV).getNumberOfCopies() - 1);
            //TODO: set date of reservation
        }
    }

    /**
     * @param : id of Document, Database
     */
    public void returnDocument(int idDocument){

    }

    /**
     *
     * @return true: if Patron can extend document, otherwise false
     */
    public boolean canRenewBook(int idDocument, Database database) throws SQLException {
        return database.getBook(idDocument).getRenewable();
    }

    /**
     * @param  : id of document
     */
    public void renewDocument(){
        //TODO: Connect with date of reservation
    }
}
