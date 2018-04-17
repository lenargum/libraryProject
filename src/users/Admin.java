package users;

import admin_tools.Modify;
import admin_tools.ModifyLibrary;
import documents.Document;
import tools.Database;

import java.sql.SQLException;
import java.text.ParseException;

public class Admin {
	private String name;
	private String surname;
	private String login;
	private String password;

	private Admin(String name, String surname, String login, String password) {
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.password = password;
	}

	Modify modify = new Modify();
	ModifyLibrary modifyLibrary = new ModifyLibrary();

	public void addLibrarian(Librarian librarian, Database database) {
		this.modifyLibrary.addLibrarian(librarian, database);
	}

	public void editLibrarianName(int idLibrarian, String newName, Database database) throws SQLException {
		this.modify.editLibrarianName(idLibrarian, newName, database);
	}

	public void editLibrarianSurname(int idLibrarian, String newSurname, Database database) throws SQLException {
		this.modify.editLibrarianSurname(idLibrarian, newSurname, database);
	}

	public void editLibrarianAddress(int idLibrarian, String newAddress, Database database) throws SQLException {
		this.editPatronAddress(idLibrarian, newAddress, database);
	}

	public void editLibrarianPhone(int idLibrarian, String newPhone, Database database) throws SQLException {
		this.modify.editLibrarianPhone(idLibrarian, newPhone, database);
	}

	public void deleteLibrarian(int idLibrarian, Database database) {
		this.modifyLibrary.deleteLibrarian(idLibrarian, database);
	}

	public void editPatronName(int idPatron, String newName, Database database) throws SQLException {
		this.modify.editPatronName(idPatron, newName, database);
	}

	public void editPatronSurname(int idPatron, String newSurname, Database database) throws SQLException {
		this.modify.editPatronSurname(idPatron, newSurname, database);
	}

	public void editPatronAddress(int idPatron, String newAddress, Database database) throws SQLException {
		this.modify.editPatronAddress(idPatron, newAddress, database);
	}

	public void editPatronPhone(int idPatron, String newPhone, Database database) throws SQLException {
		this.modify.editPatronPhone(idPatron, newPhone, database);
	}

	public void editPatronStatus(int idPatron, String newStatus, Database database) throws SQLException {
		this.modify.editPatronStatus(idPatron, newStatus, database);
	}

	public void deletePatron(int idPatron, Database database) throws SQLException {
		this.modify.deletePatron(idPatron, database);
	}

	public void editDocumentNumberOdCopies(int idDocument, int newNumberOfCopies, Database database) throws SQLException {
		this.modify.editDocumentNumberOdCopies(idDocument, newNumberOfCopies, database);
	}

	public void editDocumentIsAllowedForStudents(int idDocument, boolean isAllowed, Database database) throws SQLException {
		this.modify.editDocumentIsAllowedForStudents(idDocument, isAllowed, database);
	}

	public void editDocumentPrice(int idDocument, double newPrice, Database database) throws SQLException {
		this.modify.editDocumentPrice(idDocument, newPrice, database);
	}

	public void editDocumentIsReference(int idDocument, boolean isReference, Database database) throws SQLException {
		this.modify.editDocumentIsReference(idDocument, isReference, database);
	}

	public void editBookEdition(int idBook, int newEdition, Database database) throws SQLException {
		database.editDocumentColumn(idBook, "edition", Integer.toString(newEdition));
		database.getBook(idBook).setEdition(newEdition);
	}

	public void editBookIsBestseller(int idBook, boolean isBestseller, Database database) throws SQLException {
		this.modify.editBookIsBestseller(idBook, isBestseller, database);
	}

	public void deleteDocument(int idDocument, Database database) throws SQLException, ParseException {
		this.modifyLibrary.deleteDocument(idDocument, database);
	}
}
