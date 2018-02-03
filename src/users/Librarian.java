package users;

import materials.Document;
import tools.SetManager;

import java.util.LinkedList;

/**
 * class implements librarian
 */
public class Librarian extends User implements LibrarianInterface {

	SetManager manager = new SetManager();

	/**
	 * constructor
	 *
	 * @param name
	 * @param address
	 * @param phoneNumber
	 * @param id
	 */
	public Librarian(String name, String address, String phoneNumber, int id) {
		super(name, address, phoneNumber, id);
	}

	/**
	 * creates new patron
	 *
	 * @param name        name of the patron need to be created
	 * @param address     address of this patron
	 * @param phoneNumber his phone number
	 * @param status      and status
	 */
	@Override
	public void createPatron(String name, String address, String phoneNumber, String status) {
		Patron patron = new Patron(name, address, phoneNumber, status, manager.listOfUsers.size());
		patron.setDebts(0);
		manager.addPatron(patron);
		//TODO: add new patron to database (if database exists)
	}

	/**
	 * creates new document
	 *
	 * @param author               is author of created document
	 * @param tittle               is tittle of created document
	 * @param isAllowedForStudents shows possibility  of taking this document by students
	 * @param price                is price of document
	 */
	@Override
	public void createDocumentInLibrary(String author, String tittle, boolean isAllowedForStudents, float price) {
		Document document = new Document(author, tittle, getListOfDocuments().size(), isAllowedForStudents, price);
		addDocumentInLibrary(document);
		document.setUserID(-1);
		document.setChecked(false);
	}

	/**
	 * deletes Patron using his ID
	 *
	 * @param patronId
	 */
	@Override
	public void deletePatron(int patronId) {
		manager.deletePatron(patronId);
		//TODO: remove from database (if database exists)
	}

	/**
	 * adds document to the library
	 *
	 * @param document
	 */
	@Override
	public void addDocumentInLibrary(Document document) {
		manager.addDocumentInLibrary(document);
	}

	/**
	 * deletes document from the library using its ID
	 *
	 * @param idDocument
	 */
	@Override
	public void deleteDocumentFromLibrary(int idDocument) {
		manager.deleteDocumentFromLibrary(idDocument);
	}

	/**
	 * editing profile of the patron: sets his name
	 *
	 * @param patron
	 * @param name
	 */
	@Override
	public void setNamePatron(Patron patron, String name) {
		patron.setName(name);
	}

	/**
	 * editing profile of the patron: sets his address
	 *
	 * @param patron
	 * @param address
	 */
	@Override
	public void setAddressPatron(Patron patron, String address) {
		patron.setAddress(address);
	}

	/**
	 * editing profile of the patron: sets his phoneNumber
	 *
	 * @param patron
	 * @param phoneNumber
	 */
	@Override
	public void setPhoneNumberPatron(Patron patron, String phoneNumber) {
		patron.setPhoneNumber(phoneNumber);
	}

	/**
	 * editing profile of the patron: sets his status - student or faculty
	 *
	 * @param patron
	 * @param status
	 */
	public void setStatusPatron(Patron patron, String status) {
		patron.setStatus(status);
	}

	/**
	 * @return list of patrons of the library
	 */
	public LinkedList<Patron> getListOfPatrons() {
		return manager.listOfUsers;
	}

	/**
	 * @return list of documents that are kept in the library
	 */
	public LinkedList<Document> getListOfDocuments() {
		return manager.listOfDocuments;
	}
}
