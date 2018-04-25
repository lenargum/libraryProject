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
	}

	public void editLibrarianName(int idLibrarian, String newName, Database database) {
		this.modify.editLibrarianName(idLibrarian, newName, database);
	}

	public void editLibrarianSurname(int idLibrarian, String newSurname, Database database) {
		this.modify.editLibrarianSurname(idLibrarian, newSurname, database);
	}

	public void editLibrarianAddress(int idLibrarian, String newAddress, Database database) {
		this.modify.editLibrarianAddress(idLibrarian, newAddress, database);
	}

	public void editLibrarianPhone(int idLibrarian, String newPhone, Database database) {
		this.modify.editLibrarianPhone(idLibrarian, newPhone, database);
	}

	public void setModifyPrivilegeLibrarian(Librarian librarian, Database database) {
		this.modify.setModifyPrivilegeLibrarian(librarian, database);
	}

	public void setAddPrivilegeLibrarian(Librarian librarian, Database database) {
		this.modify.setAddPrivilegeLibrarian(librarian, database);
	}

	public void setBasicPrivilegeLibrarian(Librarian librarian, Database database) {
		this.modify.setBasicPrivilegeLibrarian(librarian, database);
	}

	public void setDeletePrivilegeLibrarian(Librarian librarian, Database database) {
		this.modify.setDeletePrivilegeLibrarian(librarian, database);
	}

	public void deleteLibrarian(int idLibrarian, Database database) {
		this.modifyLibrary.deleteLibrarian(idLibrarian, database);
	}


	public void editPatronName(int idPatron, String newName, Database database) {
		this.modify.editPatronName(idPatron, newName, database);
	}

	public void editPatronSurname(int idPatron, String newSurname, Database database) {
		this.modify.editPatronSurname(idPatron, newSurname, database);
	}

	public void editPatronAddress(int idPatron, String newAddress, Database database) {
		this.modify.editPatronAddress(idPatron, newAddress, database);
	}

	public void editPatronPhone(int idPatron, String newPhone, Database database) {
		this.modify.editPatronPhone(idPatron, newPhone, database);
	}

	public void editPatronStatus(int idPatron, String newStatus, Database database) {
		this.modify.editPatronStatus(idPatron, newStatus, database);
	}

	public void deletePatron(int idPatron, Database database) {
		this.modifyLibrary.deletePatron(idPatron, database);
	}
}
