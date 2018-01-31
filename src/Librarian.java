import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Librarian extends User {
    //TODO make interfaces
    SetManager manager = new SetManager();

    Librarian(String name, String address, String phoneNumber, int id){
        super(name, address, phoneNumber, id);
    }

    public void createPatron(String name, String address, String phoneNumber, String status){
        Patron patron = new Patron(name, address, phoneNumber, status, manager.vacantIdPatron);
        patron.setDebts(0);
        manager.addPatron(patron);
        //TODO: add new patron to database (if database exists)
    }

    public void deletePatron(int patronId){
        manager.deletePatron(patronId);
        //TODO: remove from database (if database exists)
    }

    public void addBookInLibrary(Book book){
        manager.addBookInLibrary(book);
    }

    public void deleteBookFromLibrary(int idBook){
        manager.deleteBookFromLibrary(idBook);
    }

    public void giveABook(int idPatron, int idBook){
        //TODO: Patron's access to the document!!!
        manager.listOfUsers.get(idPatron).addBookInList(manager.listOfBooks.get(idBook));
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

    public void setStatusPatron(Patron patron, String status){
        patron.setStatus(status);
    }


}
