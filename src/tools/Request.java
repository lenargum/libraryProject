package tools;

import documents.Document;
import users.Patron;

import java.sql.SQLException;
import java.util.Date;
import java.util.PriorityQueue;

public class Request {
    private int idPatron;
    private String namePatron;
    private String surnamePatron;
    private int idDocument;
    private int priority;
    private Date date;


    private boolean canTake; // users.Librarian can change this field //TODO: think about it
    //TODO: create getter and setter for canTake

    private ComparatorPriority comparator = new ComparatorPriority();
    private PriorityQueue<Patron> queue = new PriorityQueue<>(comparator);

    public Request(Patron patron, Document document, Date date) {
        this.idPatron = patron.getId();
        this.namePatron = patron.getName();
        this.surnamePatron = patron.getSurname();
        this.idDocument = document.getID();
        this.date = date;
        this.priority = patron.getPriority();
        canTake = false;
    }

    public void addToQueue(int idPatron, Database database) throws SQLException {
        queue.add(database.getPatron(idPatron));

    }

    public void approveRequest(int idPatron, int idDocument, Database database) throws SQLException {
        queue.remove(database.getPatron(idPatron));
        canTake = true;
        database.getPatron(idPatron).takeDocument(idDocument, database);
    }

    public void refuseRequest(int idPatron, int idDocument, Database database) throws SQLException {
        if (!database.getPatron(idPatron).canRequestDocument(idDocument, database)){
            queue.remove(database.getPatron(idPatron));
        }
        if (database.getDocument(idDocument).getNumberOfCopies() == 0){
            System.out.println("Wait until the copies appear or Please, check your status!");
        }
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
}
