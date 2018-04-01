package tools;

import documents.Document;
import users.Patron;

import java.sql.SQLException;
import java.util.Date;
import java.util.PriorityQueue;

public class Request {
    private int requestId;
    private int idPatron;
    private String namePatron;
    private String surnamePatron;
    private int idDocument;
    private int priority;
    private Date date;
    private boolean isRenewRequest = false;


    private boolean canTake; // users.Librarian can change this field //TODO: think about it
    //TODO: create getter and setter for canTake

    private ComparatorPriority comparator = new ComparatorPriority();
    private PriorityQueue<Patron> queue = new PriorityQueue<>(comparator);

    public Request(Patron patron, Document document, Date date, boolean isRenewRequest) {
        this.idPatron = patron.getId();
        this.namePatron = patron.getName();
        this.surnamePatron = patron.getSurname();
        this.idDocument = document.getID();
        this.date = date;
        this.priority = patron.getPriority();
        canTake = false;
        this.isRenewRequest = isRenewRequest;
    }

    public void addToQueue(int idPatron, Database database) throws SQLException {
        queue.add(database.getPatron(idPatron, database));

    }

    public void approveRequest(int idPatron, int idDocument, Database database) throws SQLException {
        queue.remove(database.getPatron(idPatron, database));
        canTake = true;
        database.getPatron(idPatron, database).takeDocument(idDocument, database);
    }

    public void refuseRequest(int idPatron, int idDocument, Database database) throws SQLException {
        if (!database.getPatron(idPatron, database).canRequestDocument(idDocument, database)){
            queue.remove(database.getPatron(idPatron, database));
        }
        if (database.getDocument(idDocument).getNumberOfCopies() == 0){
            System.out.println("Wait until the copies appear or Please, check your status!");
        }
    }

    public void approveRenew(Database database)throws SQLException{
        database.getPatron(idPatron, database).renewDocument(idDocument, database);
    }

    public void refuseRenew(){
        System.out.println("There is outstanding request for this document, so you cannot renew it :(");
    }

    public int getIdPatron() {
        return idPatron;
    }

    public void setIdPatron(int idPatron) {
        this.idPatron = idPatron;
    }

    public String getNamePatron() {
        return namePatron;
    }

    public void setNamePatron(String namePatron) {
        this.namePatron = namePatron;
    }

    public String getSurnamePatron() {
        return surnamePatron;
    }

    public void setSurnamePatron(String surnamePatron) {
        this.surnamePatron = surnamePatron;
    }

    public int getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(int idDocument) {
        this.idDocument = idDocument;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isRenewRequest() {
        return isRenewRequest;
    }

    public void setIsRenewRequest(boolean renewRequest) {
        isRenewRequest = renewRequest;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }
}
