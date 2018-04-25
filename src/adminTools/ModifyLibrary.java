package adminTools;

import tools.Database;
import tools.OutstandingRequest;
import tools.Request;
import users.Librarian;

import java.util.List;

public class ModifyLibrary {
	public void addLibrarian(Librarian librarian, Database database) {

		database.insertLibrarian(librarian);

	}

	public void deleteLibrarian(int idLibrarian, Database database) {
		//TODO: This is necessary?
//        Librarian librarian = database.getLibrarian(idLibrarian);
//        librarian.setName(null);
//        librarian.setSurname(null);
//        librarian.setAddress(null);
//        librarian.setPhoneNumber(null);
//        librarian.setLogin(null);
//        librarian.setPassword(null);
		database.deleteUser(idLibrarian);

	}

	public void deletePatron(int idPatron, Database database) {

		if (database.getPatron(idPatron).getListOfDocumentsPatron().isEmpty())
			database.deleteUser(idPatron);
		else
			System.out.println("This user did not return documents!");
	}


	public void deleteDocument(int idDocument, Database database) {
		OutstandingRequest deletionNotification = new OutstandingRequest();
		if (database.getDebtsForDocument(idDocument).size() != 0 && database.getRequestsForDocument(idDocument).size()!= 0) {

			List<Request> requests = database.getRequestsForDocument(idDocument);
			for (int i = 0; i < requests.size(); i++) {
				deletionNotification.makeDeletionRequest(requests.get(i), database);
				database.deleteRequestsForDocument(idDocument);
			}
		}
		else{
			database.deleteDocument(idDocument);
		}
	}


}
