
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


    private boolean canTake; // Librarian can change this field //TODO: think about it
    //TODO: create getter and setter for canTake

    private ComparatorPriority comparator = new ComparatorPriority();
    private PriorityQueue<Patron> queue = new PriorityQueue<>(comparator);

    public Request(int idPatron, int idDocument,Date date, Database database) throws SQLException {
        Patron temp = database.getPatron(idPatron);
        this.idPatron = idPatron;
        this.namePatron = temp.getName();
        this.surnamePatron = temp.getSurname();
        this.idDocument = idDocument;
        this.date = date;
        this.priority = temp.getPriority();
        canTake = temp.canRequestDocument(idDocument, database);
    }

//    public void abilityToTake(boolean canTake){
//        this.canTake = canTake;
//    }

    public void addToQueue(int idPatron, Database database) throws SQLException {
        queue.add(database.getPatron(idPatron));

    }

    public void approveRequest(int idPatron, int idDocument, Database database) throws SQLException {
        queue.remove(database.getPatron(idPatron));
        database.getPatron(idPatron).takeDocument(idDocument, database);
    }

    public void refuseRequest(int idPatron, int idDocument, Database database) throws SQLException {
        if (!canTake){
            queue.remove(database.getPatron(idPatron));
        } else if (database.getDocument(idDocument).getNumberOfCopies() == 0){
            System.out.println("Wait until the copies appear");
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
