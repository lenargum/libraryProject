package users;

import materials.Document;

import java.util.LinkedList;

/**
 * interface describing properties and functions of librarian
 */
public interface LibrarianInterface {

    /**
     * deletes patron from the library
     *
     * @param idPatron is id of patron that needs to be deleted
     */
    public void deletePatron(int idPatron);

    /**
     * adds document to the library
     *
     * @param document
     */
    public void addDocumentInLibrary(Document document);

    /**
     * deletes document from the library
     *
     * @param idDocument is ID of document we need to delete
     */
    public void deleteDocumentFromLibrary(int idDocument);

    /**
     * sets name of patron
     *
     * @param patron
     * @param namePatron
     */
    public void setNamePatron(Patron patron, String namePatron);

    /**
     * sets address of patron
     *
     * @param patron
     * @param address
     */
    public void setAddressPatron(Patron patron, String address);

    /**
     * sets phone number of patron
     *
     * @param patron
     * @param phoneNumber
     */
    public void setPhoneNumberPatron(Patron patron, String phoneNumber);

    /**
     * sets status of patron
     *
     * @param patron
     * @param status - student or faculty
     */
    public void setStatusPatron(Patron patron, String status);

    /**
     * @return list of patrons
     */
    public LinkedList<Patron> getListOfPatrons();
}
