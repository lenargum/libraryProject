package users;

import materials.Document;

import java.util.LinkedList;

public class Patron extends User implements PatronInterface {
    private String status;
    private LinkedList<Document> listOfDocumentsPatron = new LinkedList<>();
    private int debts;


    Patron(String name, String address, String phoneNumber, String status, int id) {
        super(name, address, phoneNumber, id);
        this.status = status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public LinkedList<Document> getListOfDocumentsPatron() {
        return listOfDocumentsPatron;
    }

    @Override
    public void setDebts(int debts) {
        this.debts = debts;
    }

    @Override
    public int getDebts() {
        return debts;
    }

    @Override
    public void addDocumentInList(Document document) {
        listOfDocumentsPatron.add(document);
    }

    @Override
    public boolean getRequest(int idDocument, Librarian librarian) {
        //TODO: Request to take a book from the library
        if (this.getStatus() == "faculty") {
            return true;
        } else {
            if (librarian.manager.listOfDocuments.get(idDocument).isAllowedForStudents())
                return true;
            else
                return false;
        }
    }

    @Override
    public void takeDocument(int idDocument, Librarian librarian) {
        if (getRequest(idDocument, librarian)) {
            this.addDocumentInList(librarian.manager.listOfDocuments.get(idDocument));
            librarian.manager.listOfDocuments.get(idDocument).setChecked(true);
            librarian.manager.listOfDocuments.get(idDocument).setUserID(this.getId());
        } else {
            System.out.println("Access is not available");
        }
    }

    @Override
    public LinkedList<Document> getDocumentsInLibrary(Librarian librarian) {
        return librarian.manager.listOfDocuments;
    }


    public void bringBackDocument(int idDocument, Librarian librarian) {
        this.getListOfDocumentsPatron().remove(librarian.manager.listOfDocuments.get(idDocument));
        librarian.manager.listOfDocuments.get(idDocument).setChecked(false);
        librarian.manager.listOfDocuments.get(idDocument).setUserID(-1);
        //TODO: set/get who took document
    }
}
