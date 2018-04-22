package adminTools;

import tools.Constants;
import tools.Database;
import users.Librarian;

public class Modify {

	public void editLibrarianName(int idLibrarian, String newName, Database database) {
		database.editUserColumn(idLibrarian, "firstname", newName);
		database.getLibrarian(idLibrarian).setName(newName);

	}

	public void editLibrarianSurname(int idLibrarian, String newSurname, Database database) {
		database.editUserColumn(idLibrarian, "lastname", newSurname);
		database.getLibrarian(idLibrarian).setSurname(newSurname);

	}

	public void editLibrarianAddress(int idLibrarian, String newAddress, Database database) {
		database.editUserColumn(idLibrarian, "address", newAddress);
		database.getLibrarian(idLibrarian).setAddress(newAddress);

	}

	public void editLibrarianPhone(int idLibrarian, String newPhone, Database database) {
		database.editUserColumn(idLibrarian, "phone", newPhone);
		database.getLibrarian(idLibrarian).setPhoneNumber(newPhone);
	}

	public void setModifyPrivilegeLibrarian(Librarian librarian) {
		librarian.setPrivilege(Constants.modifyPrivilege);
	}

	public void setAddPrivilegeLibrarian(Librarian librarian) {
		librarian.setPrivilege(Constants.addPrivilege);
	}

	public void setDeletePrivilegeLibrarian(Librarian librarian) {
		librarian.setPrivilege(Constants.deletePrivilege);
	}

	public void editPatronName(int idPatron, String newName, Database database) {
		database.editUserColumn(idPatron, "firstname", newName);
		database.getPatron(idPatron).setName(newName);

	}

	public void editPatronSurname(int idPatron, String newSurname, Database database) {
		database.editUserColumn(idPatron, "lastname", newSurname);
		database.getPatron(idPatron).setSurname(newSurname);

	}

	public void editPatronAddress(int idPatron, String newAddress, Database database) {
		database.editUserColumn(idPatron, "address", newAddress);
		database.getPatron(idPatron).setAddress(newAddress);


	}

	public void editPatronPhone(int idPatron, String newPhone, Database database) {
		database.editUserColumn(idPatron, "phone", newPhone);
		database.getPatron(idPatron).setPhoneNumber(newPhone);
	}

	public void editPatronStatus(int idPatron, String newStatus, Database database) {
		database.editUserColumn(idPatron, "status", newStatus);
		database.getPatron(idPatron).setStatus(newStatus);
	}


	public void editDocumentNumberOdCopies(int idDocument, int newNumberOfCopies, Database database) {
		database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(newNumberOfCopies));
		database.getDocument(idDocument).setNumberOfCopies(newNumberOfCopies);

	}

	public void editDocumentIsAllowedForStudents(int idDocument, boolean isAllowed, Database database) {

		database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowed));
		database.getDocument(idDocument).setAllowedForStudents(isAllowed);

	}

	public void editDocumentPrice(int idDocument, double newPrice, Database database) {

		database.editDocumentColumn(idDocument, "price", Double.toString(newPrice));
		database.getDocument(idDocument).setPrice(newPrice);

	}

	public void editDocumentIsReference(int idDocument, boolean isReference, Database database) {

		database.editDocumentColumn(idDocument, "is_reference", Boolean.toString(isReference));
		database.getDocument(idDocument).setReference(isReference);
	}

	public void editBookEdition(int idBook, int newEdition, Database database) {
		database.editDocumentColumn(idBook, "edition", Integer.toString(newEdition));
		database.getBook(idBook).setEdition(newEdition);
	}

	public void editBookIsBestseller(int idBook, boolean isBestseller, Database database) {

		database.editDocumentColumn(idBook, "bestseller", Boolean.toString(isBestseller));
		database.getBook(idBook).setBestseller(isBestseller);

	}

}
