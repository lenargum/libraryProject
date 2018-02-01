import java.util.LinkedList;
import java.util.List;

/**
 * The difference between Id of Objects and positions in the Lists is 1!!!
 * Id of first user is 1. In the list this User has position 0.
 */
public class SetManager {
    public List<Patron> listOfUsers = new LinkedList<>();
    public List<Document> listOfDocuments = new LinkedList<>();

    public int vacantIdPatron = 1;
    public int vacantIdDocument = 1;
   // public int vacantIdAV = 1;

    public void addPatron(Patron patron){
        listOfUsers.add(patron);
        vacantIdPatron++;
        patron.setDebts(0);
    }

    public void deletePatron(int idPatron){
        if(listOfUsers.isEmpty()){
            System.out.println("List of Users is empty");
        } else {
            listOfUsers.remove(idPatron - 1);
            vacantIdPatron--;
        }
    }

    public void addDocumentInLibrary(Document document){
        listOfDocuments.add(document);
        vacantIdDocument++;
    }

    public void deleteDocumentFromLibrary(int idDocument){
        if(listOfDocuments.isEmpty()){
            System.out.println("Library already empty");
        } else {
            listOfDocuments.remove(idDocument - 1);
            vacantIdDocument--;
            for(int i = 0 ; i < listOfUsers.size(); i++){
                if (listOfUsers.get(i).getListOfDocumentsPatron().contains(listOfDocuments.get(idDocument)))
                    listOfUsers.get(i).getListOfDocumentsPatron().contains(listOfDocuments.remove(idDocument));
            }
        }
    }

}
