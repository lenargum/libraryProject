package tools;

import materials.Document;
import users.Patron;

import java.util.LinkedList;

/**
 * class implements "SetManager" in tools.SetManager
 * The difference between Id of Objects and positions in the Lists is 1!!!
 * Id of first user is 1. In the list this users.User has position 0.
 */
public class SetManager implements SetManagerInterface {

	public LinkedList<Patron> listOfUsers = new LinkedList<>(); //list of the libraries users
	public LinkedList<Document> listOfDocuments = new LinkedList<>(); //list of documents that are kept in the library

	/**
	 * adds patron to the library
	 *
	 * @param patron is patron need to be added
	 */
	@Override
	public void addPatron(Patron patron) {
		listOfUsers.add(patron);
		patron.setDebts(0);
	}

	/**
	 * deletes patron from the library
	 *
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
	 *
	 * @param document is document that need to be added
	 */
	@Override
	public void addDocumentInLibrary(Document document) {
		if (isFirst(document)) {
			document.setCopy(false);
			document.setOriginID(document.getDocID());
		} else {
			document.setCopy(true);
			document.setOriginID(findFirst(document));
			listOfDocuments.get(findFirst(document)).addCopy(document.getDocID());
		}
		listOfDocuments.add(document);
	}

	/**
	 * deletes document from the library
	 *
	 * @param idDocument is ID of the document that need to be deleted
	 */
	@Override
	public void deleteDocumentFromLibrary(int idDocument) {
		if (listOfDocuments.isEmpty()) {
			System.out.println("Library already empty");
		} else {
			listOfDocuments.remove(idDocument);
			for (int i = 0; i < listOfUsers.size(); i++) {
				for (int j = 0; j < listOfUsers.get(i).getListOfDocumentsPatron().size(); j++) {
					if (listOfUsers.get(i).getListOfDocumentsPatron().get(j).getDocID() == idDocument)
						listOfUsers.get(i).getListOfDocumentsPatron().remove(j);
				}
			}
		}
	}

	public boolean isFirst(Document document) {
		int i = 0;
		while (i < listOfDocuments.size() && !listOfDocuments.get(i).equals(document)) i++;
		if (i == listOfDocuments.size()) return true;
		return false;
	}

	public int findFirst(Document document) {
		int i = 0;
		while (i < listOfDocuments.size() && !listOfDocuments.get(i).equals(document)) i++;
		return i;
	}

	public int findFree(int id) {
		Document document = listOfDocuments.get(id);
		int ind = id;
		if (document.isCopy()) {
			ind = findFirst(document);
		}
		int i = 0;
		//System.out.println("ind = " + ind);
		//System.out.println("size = " + listOfDocuments.size());
		while (i < document.getCopiesIDs().size() && listOfDocuments.get(ind).isChecked()) {
			ind = document.getCopiesIDs().get(i);
			i++;
			//System.out.println("ind = " + ind);
		}
		return ind;
	}

}
