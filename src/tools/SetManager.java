package tools;

import materials.Document;
import users.Patron;

import java.util.LinkedList;

/**
 * The difference between Id of Objects and positions in the Lists is 1!!!
 * Id of first user is 1. In the list this users.User has position 0.
 */
public class SetManager implements SetManagerInterface {

    public LinkedList<Patron> listOfUsers = new LinkedList<>(); //list of the libraries users
    public LinkedList<Document> listOfDocuments = new LinkedList<>(); //list of documents that are kept in the library

    public int vacantIdPatron = 0; //minimal vacant ID for a Patron
    public int vacantIdDocument = 0; //minimal vacant ID for a document

    /**
     * adds patron to the library
     * @param patron is patron need to be added
     */
    @Override
    public void addPatron(Patron patron) {
        listOfUsers.add(patron);
        patron.setDebts(0);
    }

    /**
     * deletes patron from the library
     * @param idPatron is ID of patron that need to be deleted
     */
    @Override
    public void deletePatron(int idPatron) {
        if (listOfUsers.isEmpty()) {
            System.out.println("List of Users is empty");
        } else {
            listOfUsers.remove(idPatron);
        }
    }

    /**
     * adds document to the library
     * @param document is document that need to be added
     */
    @Override
    public void addDocumentInLibrary(Document document) {
        listOfDocuments.add(document);
    }

    /**
     * deletes document from the library
     * @param idDocument is ID of the document that need to be deleted
     */
    @Override
    public void deleteDocumentFromLibrary(int idDocument) {
        if (listOfDocuments.isEmpty()) {
            System.out.println("Library already empty");
        } else {
            listOfDocuments.remove(idDocument );
            for (int i = 0; i < listOfUsers.size(); i++) {
                for (int j = 0; j < listOfUsers.get(i).getListOfDocumentsPatron().size(); j++){
                    if (listOfUsers.get(i).getListOfDocumentsPatron().get(j).getDocID() == idDocument)
                        listOfUsers.get(i).getListOfDocumentsPatron().remove(j);
                }
            }
        }
    }

}
