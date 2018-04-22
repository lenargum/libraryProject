package adminTools;

import tools.OutstandingRequest;
import tools.Database;
import users.Librarian;

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

		OutstandingRequest deletionDocument = new OutstandingRequest();
		deletionDocument.makeDeletionRequest(idDocument, database);
		database.deleteDocument(idDocument);

	}


}
