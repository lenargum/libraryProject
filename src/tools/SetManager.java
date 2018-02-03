package tools;

import materials.Document;
import users.Patron;

import java.util.LinkedList;

/**
 * The difference between Id of Objects and positions in the Lists is 1!!!
 * Id of first user is 1. In the list this users.User has position 0.
 */
public class SetManager implements SetManagerInterface {

    public LinkedList<Patron> listOfUsers = new LinkedList<>();
    public LinkedList<Document> listOfDocuments = new LinkedList<>();

    public int vacantIdPatron = 0;
    public int vacantIdDocument = 0;

    @Override
    public void addPatron(Patron patron) {
        listOfUsers.add(patron);
        vacantIdPatron++;
        patron.setDebts(0);
    }

    @Override
    public void deletePatron(int idPatron) {
        if (listOfUsers.isEmpty()) {
            System.out.println("List of Users is empty");
        } else {
            listOfUsers.remove(idPatron);
            vacantIdPatron--;
        }
    }

    @Override
    public void addDocumentInLibrary(Document document) {
        listOfDocuments.add(document);
        vacantIdDocument++;
    }

    @Override
    public void deleteDocumentFromLibrary(int idDocument) {
        if (listOfDocuments.isEmpty()) {
            System.out.println("Library already empty");
        } else {
            listOfDocuments.remove(idDocument );
            vacantIdDocument--;
            for (int i = 0; i < listOfUsers.size(); i++) {
                for (int j = 0; j < listOfUsers.get(i).getListOfDocumentsPatron().size(); j++){
                    if (listOfUsers.get(i).getListOfDocumentsPatron().get(j).getDocID() == idDocument)
                        listOfUsers.get(i).getListOfDocumentsPatron().remove(j);
                }
            }
        }
    }

}
