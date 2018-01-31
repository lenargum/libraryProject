import java.util.ArrayList;

public class Patron extends User{
    private String status;
    private ArrayList<Book> listOfBooksPatron;
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

    public ArrayList<Book> getListOfBooksPatron() {
        return listOfBooksPatron;
    }

    public void setDebts(int debts) {
        this.debts = debts;
    }

    public int getDebts() {
        return debts;
    }

    public boolean checkCapability(Document document){
        if (this.getStatus() == "faculty"){
            return true;
        } else {
            return document.isAllowedForStudents();
        }
    }

    public void addBookInList(Book book){
        listOfBooksPatron.add(book);
    }

    public void getRequest(Book book, Librarian librarian){
        //TODO: Request to take a book from the library
        if (this.getStatus() == "faculty"){

        }
    }

}
