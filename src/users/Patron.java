package users;

import materials.Document;

import java.util.LinkedList;

/**
 * class represents "Patron" type in user.Patron
 */
public class Patron extends User implements PatronInterface {
	private String status; //status of patron - faculty or student
	private LinkedList<Document> listOfDocumentsPatron = new LinkedList<>(); //list of documents patron is keeping
	private int debts; //sum of patrons debts


	/**
	 * constructor
	 *
	 * @param name
	 * @param address
	 * @param phoneNumber
	 * @param status
	 * @param id
	 */
	public Patron(String name, String address, String phoneNumber, String status, int id) {
		super(name, address, phoneNumber, id);
		this.status = status;
	}

	/**
	 * returns status of current patron - student or faculty
	 *
	 * @return
	 */
	@Override
	public String getStatus() {
		return status;
	}

	/**
	 * sets status of patron - student or faculty
	 *
	 * @param status
	 */
	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return list of documents the patron is keeping
	 */
	@Override
	public LinkedList<Document> getListOfDocumentsPatron() {
		return listOfDocumentsPatron;
	}

	/**
	 * @return sum of patrons debts
	 */
	@Override
	public int getDebts() {
		return debts;
	}

	/**
	 * sets patrons debts
	 *
	 * @param debts
	 */
	@Override
	public void setDebts(int debts) {
		this.debts = debts;
	}

	/**
	 * adds document to list of documents patron is keeping
	 *
	 * @param document
	 */
	@Override
	public void addDocumentInList(Document document) {
		listOfDocumentsPatron.add(document);
	}

	/**
	 * if patron wants to borrow some document he sends request to the library
	 *
	 * @param idDocument is id of the document patron wants to take
	 * @param librarian  is librarian who gets the request
	 * @return true if patron can borrow the book, false otherwise
	 */
	@Override
	public boolean getRequest(int idDocument, Librarian librarian) {
		//TODO: Request to take a book from the library
		if ((this.getStatus() == "faculty") && !(librarian.getListOfDocuments().get(idDocument).isChecked())) {
			return true;
		} else {
			if ((librarian.manager.listOfDocuments.get(idDocument).isAllowedForStudents() && !(librarian.getListOfDocuments().get(idDocument).isChecked())))
				return true;
			else
				return false;
		}
	}

	/**
	 * if patron can take the document he does it!
	 *
	 * @param idDocument is ID of document patron takes
	 * @param librarian  is librarian that gives to patron this document
	 */
	@Override
	public void takeDocument(int idDocument, Librarian librarian) {
		if (getRequest(idDocument, librarian)) {
			this.addDocumentInList(librarian.manager.listOfDocuments.get(idDocument));
			librarian.manager.listOfDocuments.get(idDocument).setChecked(true);
			librarian.manager.listOfDocuments.get(idDocument).setUserID(this.getId());
		} else {
			System.out.println("Access is not available");
		}
	}

	/**
	 * receives documents in the library
	 *
	 * @param librarian is librarian that gives documents
	 * @return list of documents patron wants to take
	 */
	@Override
	public LinkedList<Document> getDocumentsInLibrary(Librarian librarian) {
		return librarian.manager.listOfDocuments;
	}


	/**
	 * When patron used documents he need to return them to the library
	 *
	 * @param idDocument is ID of the document user needs to return
	 * @param librarian  is librarian that will receive the document
	 */
	public void bringBackDocument(int idDocument, Librarian librarian) {
		this.getListOfDocumentsPatron().remove(librarian.manager.listOfDocuments.get(idDocument));
		librarian.manager.listOfDocuments.get(idDocument).setChecked(false);
		librarian.manager.listOfDocuments.get(idDocument).setUserID(-1);
		//TODO: set/get who took document
	}

	/**
	 * checks whether one user is copy of another - it can be done with repeated registration
	 *
	 * @param user is patron that could do a mistake
	 * @return true if one user is copy of another, false otherwise
	 */
	public boolean equals(Patron user) {
		return this.getId() != user.getId() && this.getAddress().equals(user.getAddress()) && this.getName().equals(user.getName()) && this.getPhoneNumber() == user.getPhoneNumber() && this.getStatus().equals(user.getStatus());
	}
}
