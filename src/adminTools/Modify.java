package adminTools;

import tools.Constants;
import tools.Database;
import users.Librarian;

public class Modify {

	public void editLibrarianName(int idLibrarian, String newName, Database database) {
		database.editUserColumn(idLibrarian, "firstname", newName);
		database.getLibrarian(idLibrarian).setName(newName);
		database.log("ADMIN has edited Name to \"" + newName + "\" of Librarian " + idLibrarian + "id");
	}

	public void editLibrarianSurname(int idLibrarian, String newSurname, Database database) {
		database.editUserColumn(idLibrarian, "lastname", newSurname);
		database.getLibrarian(idLibrarian).setSurname(newSurname);
		database.log("ADMIN has edited Surname to \"" + newSurname + "\" of Librarian " + idLibrarian + "id");
	}

	public void editLibrarianAddress(int idLibrarian, String newAddress, Database database) {
		database.editUserColumn(idLibrarian, "address", newAddress);
		database.getLibrarian(idLibrarian).setAddress(newAddress);
		database.log("ADMIN has edited Address to \"" + newAddress + "\" of Librarian " + idLibrarian + "id");
	}

	public void editLibrarianPhone(int idLibrarian, String newPhone, Database database) {
		database.editUserColumn(idLibrarian, "phone", newPhone);
		database.getLibrarian(idLibrarian).setPhoneNumber(newPhone);
		database.log("ADMIN has edited Phone to \"" + newPhone + "\" of Librarian " + idLibrarian + "id");
	}

	public void setModifyPrivilegeLibrarian(Librarian librarian, Database database) {
		librarian.setPrivilege(Constants.modifyPrivilege);
		editPrivilegeLibrarian(librarian, Constants.modifyPrivilege, database);
		database.log("ADMIN has edited Privilege to level \"Modify\" of Librarian " + librarian.getId() + "id");
	}

	public void setAddPrivilegeLibrarian(Librarian librarian, Database database) {
		librarian.setPrivilege(Constants.addPrivilege);
		editPrivilegeLibrarian(librarian, Constants.addPrivilege, database);
		database.log("ADMIN has edited Privilege to level \"Add\" of Librarian " + librarian.getId() + "id");
	}

	public void setDeletePrivilegeLibrarian(Librarian librarian, Database database) {
		librarian.setPrivilege(Constants.deletePrivilege);
		editPrivilegeLibrarian(librarian, Constants.deletePrivilege, database);
		database.log("ADMIN has edited Privilege to level \"Delete\" of Librarian " + librarian.getId() + "id");
	}

	public void setBasicPrivilegeLibrarian(Librarian librarian, Database database) {
		librarian.setPrivilege(Constants.deletePrivilege);
		editPrivilegeLibrarian(librarian, Constants.basicPrivilege, database);
		database.log("ADMIN has edited Privilege to level \"Basic\" of Librarian " + librarian.getId() + "id");
	}

	private void editPrivilegeLibrarian(Librarian librarian, int newPrivilege, Database database) {
		database.editUserColumn(librarian.getId(), "privileges", Integer.toString(newPrivilege));
	}

	public void editPatronName(int idPatron, String newName, Database database) {
		database.editUserColumn(idPatron, "firstname", newName);
		database.getPatron(idPatron).setName(newName);
		database.log("ADMIN has edited Name to \"" + newName + "\" of Patron " + idPatron + "id");
	}

	public void editPatronSurname(int idPatron, String newSurname, Database database) {
		database.editUserColumn(idPatron, "lastname", newSurname);
		database.getPatron(idPatron).setSurname(newSurname);
		database.log("ADMIN has edited Surname to \"" + newSurname + "\" of Patron " + idPatron + "id");
	}

	public void editPatronAddress(int idPatron, String newAddress, Database database) {
		database.editUserColumn(idPatron, "address", newAddress);
		database.getPatron(idPatron).setAddress(newAddress);
		database.log("ADMIN has edited Address to \"" + newAddress + "\" of Patron " + idPatron + "id");
	}

	public void editPatronPhone(int idPatron, String newPhone, Database database) {
		database.editUserColumn(idPatron, "phone", newPhone);
		database.getPatron(idPatron).setPhoneNumber(newPhone);
		database.log("ADMIN has edited Phone to \"" + newPhone + "\" of Patron " + idPatron + "id");
	}

	public void editPatronStatus(int idPatron, String newStatus, Database database) {
		database.editUserColumn(idPatron, "status", newStatus);
		database.getPatron(idPatron).setStatus(newStatus);
		database.log("ADMIN has edited Status to \"" + newStatus + "\" of Patron " + idPatron + "id");
	}
}
