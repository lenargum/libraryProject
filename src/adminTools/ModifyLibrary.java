package adminTools;

import tools.Database;
import tools.Logic;
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
		List <Librarian> l = database.getLibrarianList();
		for(Librarian i: l){
			if(i.getId() == idLibrarian){
				database.deleteUser(idLibrarian);
				return;
			}
		}
		System.out.println("Librarian with such id " + idLibrarian + "does not exist.");

	}

	public void deletePatron(int idPatron, Database database) {

		if (database.getPatron(idPatron).getListOfDocumentsPatron().isEmpty())
			database.deleteUser(idPatron);
		else
			System.out.println("This user did not return documents!");
	}

}
