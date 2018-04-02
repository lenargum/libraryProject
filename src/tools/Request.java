package tools;

import documents.Document;
import users.Patron;
import users.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.PriorityQueue;

/**
 * This class describes the requests of booking system
 *
 * @author Madina Gafarova
 * @author Anastasiia Minakova
 * @author Lenar Gumerov
 */
public class Request {
    /**
     * tools.Request id
     */
    private int requestId;

    /**
     * Associated patron's ID.
     */
    private int idPatron;

    /**
     * Associated patron's name.
     */
    private String namePatron;

    /**
     * Associated patron's surname.
     */
    private String surnamePatron;

    /**
     * Associated document's ID.
     */
    private int idDocument;

    /**
     * Associated patron's priority.
     */
    private int priority;

    /**
     * Date of booking
     */
    private Date date;

    /**
     * Flag to check of type of request
     */
    private boolean isRenewRequest = false;

    /**
     * tools.Comparator to compare the priority
     */
    private ComparatorPriority comparator = new ComparatorPriority();

    /**
     * Priority queue
     */
    private PriorityQueue<Patron> queue = new PriorityQueue<>(comparator);

    /**
     * Initialize new request
     * @param patron Associated Patron.
     * @param document Associated Document
     * @param date Date of booking
     * @param isRenewRequest Flag
     */
    public Request(Patron patron, Document document, Date date, boolean isRenewRequest) {
        this.idPatron = patron.getId();
        this.namePatron = patron.getName();
        this.surnamePatron = patron.getSurname();
        this.idDocument = document.getID();
        this.date = date;
        this.priority = patron.getPriority();
        this.isRenewRequest = isRenewRequest;
    }

    /**
     * Add Patron to priority queue
     * @param idPatron Associated patron's ID.
     * @param database Database
     * @throws SQLException
     */
    public void addToQueue(int idPatron, Database database) throws SQLException {
        queue.add(database.getPatron(idPatron));
    }

    /**
     * Approve request (Patron can take this document)
     * @param idPatron Associated patron's ID.
     * @param idDocument Associated document's ID.
     * @param database Database
     * @throws SQLException
     */
    public void approveRequest(int idPatron, int idDocument, Database database) throws SQLException {
        database.getPatron(idPatron).takeDocument(idDocument, database);
        queue.remove(database.getPatron(idPatron));
        database.deleteRequest(idPatron, idDocument);
    }

    /**
     * Refuse request (Patron cannot take this document)
     * @param idPatron Associated patron's ID.
     * @param idDocument Associated document's ID.
     * @param database Database
     * @throws SQLException
     */
    public void refuseRequest(int idPatron, int idDocument, Database database) throws SQLException {
//        if (!database.getPatron(idPatron).canRequestDocument(idDocument, database)){
//            //queue.remove(database.getPatron(idPatron));
//	        System.out.println("Not for this patron");
//    }
        database.deleteRequest(idPatron, idDocument);
    }

    /**
     * Approve request to renew document.
     * @param database Database
     * @throws SQLException
     */
    public void approveRenew(Database database)throws SQLException{
        database.getPatron(idPatron).renewDocument(idDocument, database);
        database.deleteRequest(idPatron);
    }

    /**
     * Refuse request to renew document.
     */
    public void refuseRenew(){
        System.out.println("There is outstanding request for this document, so you cannot renew it :(");
    }

    /**
     * Get Patron's ID.
     * @return users.Patron Id
     */
    public int getIdPatron() {
        return idPatron;
    }

    /**
     * Set Patron's ID.
     * @param idPatron Associated patron's ID.
     */
    public void setIdPatron(int idPatron) {
        this.idPatron = idPatron;
    }

    /**
     * Get Patron's name.
     * @return users. Patron name
     */
    public String getNamePatron() {
        return namePatron;
    }

    /**
     * Set Patron's name
     * @param namePatron Associated patron's name.
     */
    public void setNamePatron(String namePatron) {
        this.namePatron = namePatron;
    }

    /**
     * Get Patron's surname.
     * @return users.Patron surname
     */
    public String getSurnamePatron() {
        return surnamePatron;
    }

    /**
     * Set Patron's username.
     * @param surnamePatron Associated patron's surname.
     */
    public void setSurnamePatron(String surnamePatron) {
        this.surnamePatron = surnamePatron;
    }

    /**
     * Get Document's ID.
     * @return documents.Document ID
     */
    public int getIdDocument() {
        return idDocument;
    }

    /**
     * Set Document's ID
     * @param idDocument Associated document's ID.
     */
    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    /**
     * Get Patron's priority.
     * @return users.Patron priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Set Patron's priority.
     * @param priority Associated patron's priority.
     */

    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Get the Date of Booking document.
     * @return Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Set the Date of Booking.
     * @param date Associated current date.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Get boolean value of type of request.
     * @return boolean
     */
    public boolean isRenewRequest() {
        return isRenewRequest;
    }

    /**
     * Set boolean value of type of request.
     * @param renewRequest Associated type of request.
     */
    public void setIsRenewRequest(boolean renewRequest) {
        isRenewRequest = renewRequest;
    }

    /**
     * Get ID of request.
     * @return tools.Request ID
     */
    public int getRequestId() {
        return requestId;
    }

    /**
     * Set ID of request.
     * @param requestId Associated request's ID.
     */
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
