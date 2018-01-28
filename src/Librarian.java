import java.util.ArrayList;
import java.util.List;

public class Librarian extends User {

    private List<Patron> patrons;

    Librarian(String name, String address, String phoneNumber, int id){
        super(name, address, phoneNumber, id);
        patrons = new ArrayList<>();
    }

    public void createPatron(String name, String address, String phoneNumber, String status, int id){
        Patron patron = new Patron(name, address, phoneNumber, status, id);
        patrons.add(id, patron);
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

}
