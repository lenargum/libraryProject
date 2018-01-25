import java.util.ArrayList;

public class Patron extends User{
    private String status;
    private ArrayList<Document> listOfDocuments;
    private int debts;

    Patron(String name, int id){
        super(name, id);
    }

    public void setStatus(String status) {
        this.status = status;
        this.debts = 0;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Document> getListOfDocuments() {
        return listOfDocuments;
    }

    public void setDebts(int debts) {
        this.debts = debts;
    }

    public int getDebts() {
        return debts;
    }

    public void getRequest(Document document){

    }

}
