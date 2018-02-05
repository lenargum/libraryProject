package users;

import materials.Document;
import tools.SetManager;

import java.util.LinkedList;

/**
 * class represents properties and functions of librarian
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
	 * registers patron in the library
	 *
	 * @param patron
	 */
	public void registerPatron(Patron patron) {
		manager.listOfUsers.add(patron);
	}

	/**
	 * deletes patron from the library
	 *
	 * @param patronId is id of patron that needs to be deleted
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
	public void addDocumentInLibrary(Document document) {
		manager.addDocumentInLibrary(document);
	}

	/**
	 * deletes document from the library
	 *
	 * @param idDocument is ID of document we need to delete
	 */
	@Override
	public void deleteDocumentFromLibrary(int idDocument) {
		manager.deleteDocumentFromLibrary(idDocument);
	}

	/**
	 * sets name of patron
	 *
	 * @param patron
	 * @param name
	 */
	@Override
	public void setNamePatron(Patron patron, String name) {
		patron.setName(name);
	}

	/**
	 * sets address of patron
	 *
	 * @param patron
	 * @param address
	 */
	@Override
	public void setAddressPatron(Patron patron, String address) {
		patron.setAddress(address);
	}

	/**
	 * sets phone number of patron
	 *
	 * @param patron
	 * @param phoneNumber
	 */
	@Override
	public void setPhoneNumberPatron(Patron patron, String phoneNumber) {
		patron.setPhoneNumber(phoneNumber);
	}

	/**
	 * sets status of patron
	 *
	 * @param patron
	 * @param status - student or faculty
	 */
	public void setStatusPatron(Patron patron, String status) {
		patron.setStatus(status);
	}

	/**
	 * @return list of patrons
	 */
	public LinkedList<Patron> getListOfPatrons() {
		return manager.listOfUsers;
	}

	/**
	 * @return list of documents that are kept n library
	 */
	public LinkedList<Document> getListOfDocuments() {
		return manager.listOfDocuments;
	}

	/**
	 * checks whether one librarian is copy of another - it can be done with repeated registration
	 *
	 * @param user
	 * @return true if current user is copy of other
	 */
	public boolean equals(Librarian user) {
		if(this == user) return false;
		return this.getAddress().equals(user.getAddress()) &&
				this.getName().equals(user.getName()) &&
				this.getPhoneNumber().equals(user.getPhoneNumber());
	}
}
