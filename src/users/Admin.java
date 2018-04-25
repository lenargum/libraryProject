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

	public void setModifyPrivilegeLibrarian(Librarian librarian, Database database) {
		this.modify.setModifyPrivilegeLibrarian(librarian, database);
		database.log("ADMIN has edited Privilege to level \"Modify\" of Librarian "+librarian.getId()+"id");

	}

	public void setAddPrivilegeLibrarian(Librarian librarian, Database database) {
		this.modify.setAddPrivilegeLibrarian(librarian, database);
		database.log("ADMIN has edited Privilege to level \"Add\" of Librarian "+librarian.getId()+"id");

	}

	public void setDeletePrivilegeLibrarian(Librarian librarian, Database database) {
		this.modify.setDeletePrivilegeLibrarian(librarian, database);
		database.log("ADMIN has edited Privilege to level \"Delete\" of Librarian "+librarian.getId()+"id");

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
}
