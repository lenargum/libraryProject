package users;

import materials.Document;
import tools.SetManager;

import java.util.LinkedList;

public class Librarian extends User implements LibrarianInterface {

    SetManager manager = new SetManager();

    public Librarian(String name, String address, String phoneNumber, int id) {
        super(name, address, phoneNumber, id);
    }

    @Override
    public void createPatron(String name, String address, String phoneNumber, String status) {
        Patron patron = new Patron(name, address, phoneNumber, status, manager.listOfUsers.size());
        patron.setDebts(0);
        manager.addPatron(patron);
        //TODO: add new patron to database (if database exists)
    }

    @Override
    public void createDocumentInLibrary(String author, String tittle, boolean isAllowedForStudents, float price){
        Document document = new Document(author, tittle, getListOfDocuments().size(), isAllowedForStudents, price );
        addDocumentInLibrary(document);
        document.setUserID(-1);
        document.setChecked(false);
    }

    @Override
    public void deletePatron(int patronId) {
        manager.deletePatron(patronId);
        //TODO: remove from database (if database exists)
    }

    @Override
    public void addDocumentInLibrary(Document document) {
        manager.addDocumentInLibrary(document);
    }

    @Override
    public void deleteDocumentFromLibrary(int idDocument) {
        manager.deleteDocumentFromLibrary(idDocument);
    }

    @Override
    public void setNamePatron(Patron patron, String name) {
        patron.setName(name);
    }

    @Override
    public void setAddressPatron(Patron patron, String address) {
        patron.setAddress(address);
    }

    @Override
    public void setPhoneNumberPatron(Patron patron, String phoneNumber) {
        patron.setPhoneNumber(phoneNumber);
    }


    public void setStatusPatron(Patron patron, String status) {
        patron.setStatus(status);
    }

    public LinkedList<Patron> getListOfPatrons(){
        return manager.listOfUsers;
    }

    public LinkedList<Document> getListOfDocuments(){
        return manager.listOfDocuments;
    }
}