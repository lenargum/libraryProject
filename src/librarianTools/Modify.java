package librarianTools;

import tools.Database;
import tools.Logic;

public class Modify {

	/**
	 * Modify the price of document stored in database.
	 *
	 * @param idDocument ID of document which is going to be modified.
	 * @param database   tools.Database that stores the information.
	 * @param price      New price.
	 */
	public void modifyDocumentPrice(int librarianId, int idDocument, Database database, double price) {
		if (Logic.canModify(librarianId, database)) {
			database.getDocument(idDocument).setPrice(price);
			database.editDocumentColumn(idDocument, "price", Double.toString(price));
			database.log("Librarian " + librarianId + "id has edited Price to " + price + " of Document " + idDocument + "id.");
		}
	}


	/**
	 * Modify the edition year of book stored in database.
	 *
	 * @param idBook   ID of book which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param edition  New edition year.
	 */
	public void modifyBookEdition(int librarianId, int idBook, Database database, int edition) {
		if (Logic.canModify(librarianId, database)) {
			database.getBook(idBook).setEdition(edition);
			database.editDocumentColumn(idBook, "edition", Integer.toString(edition));

			database.log("Librarian " + librarianId + "id has edited Edition to " + edition + " of Book " + idBook + "id.");
		}
	}


	/**
	 * Modify the student allowance status of document stored in database.
	 *
	 * @param idDocument           ID of document which is going to be modified.
	 * @param database             tools.Database that stores the information.
	 * @param isAllowedForStudents New status.
	 */
	public void modifyDocumentAllowance(int librarianId, int idDocument, Database database, boolean isAllowedForStudents) {
		if (Logic.canModify(librarianId, database)) {
			database.getDocument(idDocument).setAllowedForStudents(isAllowedForStudents);
			database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowedForStudents));

			database.log("Librarian " + librarianId + "id has edited Allowance to " + isAllowedForStudents + " of Document " + idDocument + "id.");
		}
	}

	/**
	 * Modify the count of copies of document stored in database.
	 *
	 * @param idDocument    ID of document which is going to be modified.
	 * @param database      tools.Database that stores the information.
	 * @param countOfCopies New number.
	 */
	public void modifyDocumentCopies(int librarianId, int idDocument, Database database, int countOfCopies) {
		if (Logic.canAdd(librarianId, database)) {
			database.getDocument(idDocument).setNumberOfCopies(countOfCopies);
			database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(countOfCopies));

			database.log("Librarian " + librarianId + "id has edited number of copies to " + countOfCopies + " of Document " + idDocument + "id.");
		}
	}

	/**
	 * Modify the bestseller status of book stored in database.
	 *
	 * @param idBook     ID of book which is going to be modified.
	 * @param database   tools.Database that stores the information.
	 * @param bestseller New status.
	 */
	public void modifyBookBestseller(int librarianId, int idBook, Database database, boolean bestseller) {
		if (Logic.canModify(librarianId, database)) {
			database.getBook(idBook).setBestseller(bestseller);
			database.editDocumentColumn(idBook, "bestseller", Boolean.toString(bestseller));

			database.log("Librarian " + librarianId + "id has edited isBestSeller to " + bestseller + " of Book " + idBook + "id.");
		}
	}

	/**
	 * Modify the last name of patron stored in database.
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param name     New name.
	 */
	public void modifyPatronName(int librarianId, int idPatron, Database database, String name) {
		if (Logic.canModify(librarianId, database)) {
			database.getPatron(idPatron).setName(name);
			database.editUserColumn(idPatron, "name", name);

			database.log("Librarian " + librarianId + "id has edited Name to " + name + " of Patron " + idPatron + "id.");
		}
	}

	/**
	 * Modify the last name of patron stored in database.
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param surname  New last name.
	 */
	public void modifyPatronSurname(int librarianId, int idPatron, Database database, String surname) {
		if (Logic.canModify(librarianId, database)) {
			database.getPatron(idPatron).setSurname(surname);
			database.editUserColumn(idPatron, "lastname", surname);
			database.log("Librarian " + librarianId + "id has edited Surname to " + surname + " of Patron " + idPatron + "id.");
		}
	}

	/**
	 * Modify the living address of patron stored in database.
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param address  New living address.
	 */
	public void modifyPatronAddress(int librarianId, int idPatron, Database database, String address) {
		if (Logic.canModify(librarianId, database)) {
			database.getPatron(idPatron).setAddress(address);
			database.editUserColumn(idPatron, "address", address);
			database.log("Librarian " + librarianId + "id has edited Address to " + address + " of Patron " + idPatron + "id.");
		}
	}

	/**
	 * Modify the phone number of patron stored in database.
	 *
	 * @param idPatron    ID of patron which is going to be modified.
	 * @param database    tools.Database that stores the information.
	 * @param phoneNumber New phone number.
	 */
	public void modifyPatronPhoneNumber(int librarianId, int idPatron, Database database, String phoneNumber) {
		if (Logic.canModify(librarianId, database)) {
			database.getPatron(idPatron).setPhoneNumber(phoneNumber);
			database.editUserColumn(idPatron, "phone", phoneNumber);
			database.log("Librarian " + librarianId + "id has edited PhoneNumber to " + phoneNumber + " of Patron " + idPatron + "id.");
		}
	}

	/**
	 * Modify the status of patron stored in database.
	 * Possible values: {@code "instructor"}, {@code "student"}, {@code "ta"}, {@code "professor"}
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param status   New status.
	 */
	public void modifyPatronStatus(int librarianId, int idPatron, Database database, String status) {
		if (Logic.canModify(librarianId, database)) {
			database.getPatron(idPatron).setStatus(status);
			database.editDocumentColumn(idPatron, "status", status);
			database.log("Librarian " + librarianId + "id has edited Status to " + status + " of Patron " + idPatron + "id.");
		}
	}
}
