import java.util.ArrayList;

public class Patron extends User{
    private String status;
    private ArrayList<Document> listOfDocuments;
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

    public ArrayList<Document> getListOfDocuments() {
        return listOfDocuments;
    }

    public void printListOfDocuments(){
        for(int i = 0; i < getListOfDocuments().size(); i++)
            System.out.println("ID: " + getListOfDocuments().get(i).getDocID() + "      Authors" +  getListOfDocuments().get(i).getAuthors()
                    + "        Title: " +  getListOfDocuments().get(i).getTitle());
    }
    public void setDebts(int debts) {
        this.debts = debts;
    }

    public int getDebts() {
        return debts;
    }

}
