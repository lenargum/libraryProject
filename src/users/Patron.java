package users;

import materials.Document;

import java.util.LinkedList;

/**
 * class represents "Patron" type in users.Patron
 */
public class Patron extends User implements PatronInterface {
    private String status; // status of the patron - student or faculty
    private LinkedList<Document> listOfDocumentsPatron = new LinkedList<>(); // list of documents patron is keeping
    private int debts; // debts of the patron


    /**
     * constructor
     * @param name
     * @param address
     * @param phoneNumber
     * @param status
     * @param id
     */
    Patron(String name, String address, String phoneNumber, String status, int id) {
        super(name, address, phoneNumber, id);
        this.status = status;
    }

    /**
     * sets status of patron - student or faculty
     * @param status
     */
    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * returns status of current patron - student or faculty
     * @return
     */
    @Override
    public String getStatus() {
        return status;
    }

    /**
     *
     * @return list of documents the patron is keeping
     */
    @Override
    public LinkedList<Document> getListOfDocumentsPatron() {
        return listOfDocumentsPatron;
    }

    /**
     * sets patrons debts
     * @param debts
     */
    @Override
    public void setDebts(int debts) {
        this.debts = debts;
    }

    /**
     *
     * @return sum of patrons debts
     */
    @Override
    public int getDebts() {
        return debts;
    }

    /**
     * adds document to list of documents patron is keeping
     * @param document
     */
    @Override
    public void addDocumentInList(Document document) {
        listOfDocumentsPatron.add(document);
    }

    /**
     * if patron wants to borrow some document he sends request to the library
     * @param idDocument is id of the document patron wants to take
     * @param librarian is librarian who gets the request
     * @return true if patron can borrow the book, false otherwise
     */
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

    /**
     * if patron can take the document he does it!
     * @param idDocument is ID of document patron takes
     * @param librarian is librarian that gives to patron this document
     */
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

    /**
     * receives documents in the library
     * @param librarian is librarian that gives documents
     * @return list of documents patron wants to take
     */
    @Override
    public LinkedList<Document> getDocumentsInLibrary(Librarian librarian) {
        return librarian.manager.listOfDocuments;
    }


    /**
     * if patron already used some documents he needs to return them to the library
     * @param idDocument is ID of document patron wants to return
     * @param librarian is librarian that receives documents
     */
    public void bringBackDocument(int idDocument, Librarian librarian) {
        this.getListOfDocumentsPatron().remove(librarian.manager.listOfDocuments.get(idDocument));
        librarian.manager.listOfDocuments.get(idDocument).setChecked(false);
        librarian.manager.listOfDocuments.get(idDocument).setUserID(-1);
        //TODO: set/get who took document
    }
}
