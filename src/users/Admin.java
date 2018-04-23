package users;

import adminTools.Modify;
import adminTools.ModifyLibrary;
import tools.Database;

public class Admin extends User {
	private Modify modify;
	private ModifyLibrary modifyLibrary;

	public Admin(String login, String password, String name, String surname, String phone, String address) {
		super(login, password, name, surname, "", "");
		modify = new Modify();
		modifyLibrary = new ModifyLibrary();
	}

	public void addLibrarian(Librarian librarian, Database database) {
		this.modifyLibrary.addLibrarian(librarian, database);
		database.log("ADMIN has added Librarian "+librarian.getId()+"id");
	}

	public void editLibrarianName(int idLibrarian, String newName, Database database) {
		this.modify.editLibrarianName(idLibrarian, newName, database);
		database.log("ADMIN has edited Name to \""+newName+"\" of Librarian "+idLibrarian+"id");

	}

	public void editLibrarianSurname(int idLibrarian, String newSurname, Database database) {
		this.modify.editLibrarianSurname(idLibrarian, newSurname, database);
		database.log("ADMIN has edited Surname to \""+newSurname+"\" of Librarian "+idLibrarian+"id");
	}

	public void editLibrarianAddress(int idLibrarian, String newAddress, Database database) {
		this.modify.editLibrarianAddress(idLibrarian, newAddress, database);
		database.log("ADMIN has edited Address to \""+newAddress+"\" of Librarian "+idLibrarian+"id");
	}

	public void editLibrarianPhone(int idLibrarian, String newPhone, Database database) {
		this.modify.editLibrarianPhone(idLibrarian, newPhone, database);
		database.log("ADMIN has edited Phone to \""+newPhone+"\" of Librarian "+idLibrarian+"id");
	}

	public void setModifyPrivilegeLibrarian(int idLibrarian, Database database) {
		this.modify.setModifyPrivilegeLibrarian(idLibrarian, database);
		database.log("ADMIN has edited Privilege to level \"Modify\" of Librarian "+idLibrarian+"id");
	}

	public void setAddPrivilegeLibrarian(int idLibrarian, Database database) {
		this.modify.setAddPrivilegeLibrarian(idLibrarian, database);
		database.log("ADMIN has edited Privilege to level \"Add\" of Librarian "+idLibrarian+"id");
	}

	public void setDeletePrivilegeLibrarian(int idLibrarian, Database database) {
		this.modify.setDeletePrivilegeLibrarian(idLibrarian, database);
		database.log("ADMIN has edited Privilege to level \"Delete\" of Librarian "+idLibrarian+"id");
	}

	public void deleteLibrarian(int idLibrarian, Database database) {
		this.modifyLibrary.deleteLibrarian(idLibrarian, database);
		database.log("ADMIN has deleted Librarian "+idLibrarian+"id");
	}


	public void editPatronName(int idPatron, String newName, Database database) {
		this.modify.editPatronName(idPatron, newName, database);
		database.log("ADMIN has edited Name to \""+newName+"\" of Patron "+idPatron+"id");
	}

	public void editPatronSurname(int idPatron, String newSurname, Database database) {
		this.modify.editPatronSurname(idPatron, newSurname, database);
		database.log("ADMIN has edited Surname to \""+newSurname+"\" of Patron "+idPatron+"id");
	}

	public void editPatronAddress(int idPatron, String newAddress, Database database) {
		this.modify.editPatronAddress(idPatron, newAddress, database);
		database.log("ADMIN has edited Address to \""+newAddress+"\" of Patron "+idPatron+"id");
	}

	public void editPatronPhone(int idPatron, String newPhone, Database database) {
		this.modify.editPatronPhone(idPatron, newPhone, database);
		database.log("ADMIN has edited Phone to \""+newPhone+"\" of Patron "+idPatron+"id");
	}

	public void editPatronStatus(int idPatron, String newStatus, Database database) {
		this.modify.editPatronStatus(idPatron, newStatus, database);
		database.log("ADMIN has edited Status to \""+newStatus+"\" of Patron "+idPatron+"id");
	}

	public void deletePatron(int idPatron, Database database) {
		this.modifyLibrary.deletePatron(idPatron, database);
		database.log("ADMIN has deleted Patron "+idPatron+"id");
	}

	public void editDocumentNumberOfCopies(int idDocument, int newNumberOfCopies, Database database) {
		this.modify.editDocumentNumberOfCopies(idDocument, newNumberOfCopies, database);
		database.log("");
		database.log("ADMIN has edited Number_of_Copies to \""+newNumberOfCopies+"\" of Document "+idDocument+"id");
	}

	public void editDocumentIsAllowedForStudents(int idDocument, boolean isAllowed, Database database) {
		this.modify.editDocumentIsAllowedForStudents(idDocument, isAllowed, database);
		database.log("ADMIN has edited Is_Allowed_For_Students to \""+isAllowed+"\" of Document "+idDocument+"id");
	}

	public void editDocumentPrice(int idDocument, double newPrice, Database database) {
		this.modify.editDocumentPrice(idDocument, newPrice, database);
		database.log("ADMIN has edited Price to \""+newPrice+"\" of Document "+idDocument+"id");
	}

	public void editDocumentIsReference(int idDocument, boolean isReference, Database database) {
		this.modify.editDocumentIsReference(idDocument, isReference, database);
		database.log("ADMIN has edited Is_Reference to \""+isReference+"\" of Document "+idDocument+"id");
	}

	public void editBookEdition(int idBook, int newEdition, Database database) {
		this.modify.editBookEdition(idBook, newEdition, database);
		database.log("ADMIN has edited Edition to \""+newEdition+"\" of Book "+idBook+"id");
	}

	public void editBookIsBestseller(int idBook, boolean isBestseller, Database database) {
		this.modify.editBookIsBestseller(idBook, isBestseller, database);
		database.log("ADMIN has edited Is_Bestseller to \""+isBestseller+"\" of Book "+idBook+"id");
	}

	public void deleteDocument(int idDocument, Database database) {
		this.modifyLibrary.deleteDocument(idDocument, database);
		database.log("ADMIN has deleted Document "+idDocument+"id");
	}
}
