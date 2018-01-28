import java.util.ArrayList;
import java.util.List;

public class Librarian extends User {

    private List<Patron> patrons;
    private List<Document> documents;

    Librarian(String name, String address, String phoneNumber, int id){
        super(name, address, phoneNumber, id);
        patrons = new ArrayList<>();
    }

    public void createPatron(String name, String address, String phoneNumber, String status, int id){
        Patron patron = new Patron(name, address, phoneNumber, status, id);
        patron.setDebts(0);
        patrons.add (patron);
        //TODO: add new patron to database (if database exists)
    }

    public void deletePatron(int patronId){
        patrons.remove(patronId);
        //TODO: remove from database (if database exists)
    }

    public void giveABook(Patron patron, Document document){
        //TODO: Patron's access to the document!!!
        patron.getListOfDocuments().add(document);
    }

    public void addCopy(Document document){
        //TODO: problem with list pf copies in each document object
    }

    public void setNamePatron(Patron patron, String name) {
        patron.setName(name);
    }
    public void setAddressPatron(Patron patron,String address) {
        patron.setAddress(address);
    }

    public void setPhoneNumberPatron(Patron patron, String phoneNumber) {
        patron.setPhoneNumber(phoneNumber);
    }

    public void setIdPatron(Patron patron, int id) {
        patron.setId(id);
    }

    public void setStatusPatron(Patron patron, String status){
        patron.setStatus(status);
    }

    public void printListOfPatrons(){
        for (int i = 0 ; i < getPatrons().size(); i++)
            System.out.println( "ID: " + getPatrons().get(i).getId() + "      Name: " + getPatrons().get(i).getName());
    }

    private List<Patron> getPatrons() {
        return patrons;
    }

    private List<Document> getDocuments() {
        return documents;
    }

    public void addDocumentInTheLibrary(Document document){
        documents.add(document);
    }

    public void deleteDocumentFromTheLibrary(Document document){
        documents.remove(document);
    }

    public void printLibrary(){
        for(int i = 0; i < getDocuments().size(); i++)
            System.out.println("ID: " + getDocuments().get(i).getDocID() + "      Authors" +  getDocuments().get(i).getAuthors()
                    + "        Title: " +  getDocuments().get(i).getTitle());
    }
}
