import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.DeflaterOutputStream;

public class Patron extends User{
    private String status;
    private ArrayList<Document> listOfDocumentsPatron;
    private int debts;


    Patron(String name, String address, String phoneNumber, String status, int id){
        super(name, address, phoneNumber, id);
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.debts = 0;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Document> getListOfDocumentsPatron() {
        return listOfDocumentsPatron;
    }

    public void setDebts(int debts) {
        this.debts = debts;
    }

    public int getDebts() {
        return debts;
    }

    public void addBookInList(Document document){
        listOfDocumentsPatron.add(document);
    }

    public boolean getRequest(int idDocument, SetManager manager){
        //TODO: Request to take a book from the library
        if (this.getStatus() == "faculty"){
            return true;
        } else {
            if (manager.listOfDocuments.get(idDocument).isAllowedForStudents())
                return true;
            else
                return false;
        }
    }

    public void getDocument(int idDocument, Librarian librarian){
        if (getRequest(idDocument, librarian.manager)){
            addBookInList(librarian.manager.listOfDocuments.get(idDocument));
 //           librarian.manager.listOfDocuments.get(idDocument).
        } else {
            System.out.println("Access is not available");
        }
    }

    public List<Document> getDocumentsInLibrary(Librarian librarian){
        return librarian.manager.listOfDocuments;
    }

    public void bringBackDocument(int idDocument, Librarian librarian){
        this.getListOfDocumentsPatron().remove(librarian.manager.listOfDocuments.get(idDocument));
        //TODO: set/get who took document
    }
}
