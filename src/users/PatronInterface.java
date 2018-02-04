package users;

import materials.Document;

import java.util.LinkedList;

/**
 * The interface represents functions and opportunities of patrons
 */
public interface PatronInterface {

	/**
	 * returns status of current patron - student or faculty
	 *
	 * @return
	 */
	public String getStatus();

	/**
	 * sets status of patron - student or faculty
	 *
	 * @param status
	 */
	public void setStatus(String status);

	/**
	 * @return list of documents the patron is keeping
	 */
	public LinkedList<Document> getListOfDocumentsPatron();

	/**
	 * @return sum of patrons debts
	 */
	public int getDebts();

	/**
	 * sets patrons debts
	 *
	 * @param debts
	 */
	public void setDebts(int debts);

	/**
	 * adds document to list of documents patron is keeping
	 *
	 * @param document
	 */
	public void addDocumentInList(Document document);

	/**
	 * if patron wants to borrow some document he sends request to the library
	 *
	 * @param idDocument is id of the document patron wants to take
	 * @param librarian  is librarian who gets the request
	 * @return true if patron can borrow the book, false otherwise
	 */
	public boolean getRequest(int idDocument, Librarian librarian);

	/**
	 * if patron can take the document he does it!
	 *
	 * @param idDocument is ID of document patron takes
	 * @param librarian  is librarian that gives to patron this document
	 */
	public void takeDocument(int idDocument, Librarian librarian);

	/**
	 * receives documents in the library
	 *
	 * @param librarian is librarian that gives documents
	 * @return list of documents patron wants to take
	 */
	public LinkedList<Document> getDocumentsInLibrary(Librarian librarian);

}
