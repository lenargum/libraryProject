package users;

import materials.Document;

import java.util.LinkedList;

/**
 * The interface represents all functions of the librarian
 */
public interface LibrarianInterface {

    /**
     * creates new patron
     * @param name name of the patron need to be created
     * @param address address of this patron
     * @param phoneNumber his phone number
     * @param status and status
     */
    public void createPatron(String name, String address, String phoneNumber, String status);

    /**
     * creates new document
     * @param author is author of created document
     * @param tittle is tittle of created document
     * @param isAllowedForStudents shows possibility  of taking this document by students
     * @param price is price of document
     */
    public void createDocumentInLibrary(String author, String tittle, boolean isAllowedForStudents, float price);

    /**
     * deletes Patron using his ID
     * @param idPatron
     */
    public void deletePatron(int idPatron);

    /**
     * adds document to the library
     * @param document
     */
    public void addDocumentInLibrary(Document document);

    /**
     * deletes document from the library using its ID
     * @param idDocument
     */
    public void deleteDocumentFromLibrary(int idDocument);

    /**
     * editing profile of the patron: sets his name
     * @param patron
     * @param namePatron
     */
    public void setNamePatron(Patron patron, String namePatron);

    /**
     * editing profile of the patron: sets his address
     * @param patron
     * @param address
     */
    public void setAddressPatron(Patron patron, String address);

    /**
     * editing profile of the patron: sets his phoneNumber
     * @param patron
     * @param phoneNumber
     */
    public void setPhoneNumberPatron(Patron patron, String phoneNumber);

    /**
     * editing profile of the patron: sets his status - student or faculty
     * @param patron
     * @param status
     */
    public void setStatusPatron(Patron patron, String status);

    /**
     *
     * @return list of patrons of the library
     */
    public LinkedList<Patron> getListOfPatrons();

    /**
     *
     * @return list of documents that are kept in the library
     */
    public LinkedList<Document> getListOfDocuments();
}
