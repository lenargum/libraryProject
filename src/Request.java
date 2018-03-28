
import java.sql.SQLException;
import java.util.Date;
import java.util.PriorityQueue;

public class Request {

    int idPatron;
    String namePatron;
    String surnamePatron;
    int idDocument;
    int priority;
    Date date;

    boolean canTake; // Librarian can change this field //TODO: think about it

    ComparatorPriority comparator = new ComparatorPriority();
    PriorityQueue queue = new PriorityQueue(comparator);

    Request(int idPatron, int idDocument,Date date, Database database) throws SQLException {
        this.idPatron = idPatron;
        this.namePatron = database.getPatron(idPatron).getName();
        this.surnamePatron = database.getPatron(idPatron).getSurname();
        this.idDocument = idDocument;
        this.date = date;
        this.priority = database.getPatron(idPatron).getPriority();
        canTake = database.getPatron(idPatron).canRequestDocument(idDocument, database);
    }

//    public void abilityToTake(boolean canTake){
//        this.canTake = canTake;
//    }

    public void addToQueue(int id, int idPatron, Database database) throws SQLException {
        queue.add(database.getPatron(idPatron));

    }

    public void approveRequest(int idPatron, int idDocument, Database database) throws SQLException {
        queue.remove(database.getPatron(idPatron));
        database.getPatron(idPatron).takeDocument(idDocument, database);
    }

    public void refuseRequest(int idPatron, int idDocument, Database database) throws SQLException {
        if (canTake == false){
            queue.remove(database.getPatron(idPatron));
        } else if (database.getDocument(idDocument).getNumberOfCopies() == 0){
            System.out.println("Wait until the copies appear");
        }
    }
}
