package tools;

import materials.Document;

/**
 * The interface represents managing functions of librarian
 */
public interface SetManagerInterface {

	/**
	 * adds patron to the library
	 *
	 * @param patron is patron need to be added
	 */
	public void addPatron(Patron patron);

	/**
	 * deletes patron from the library
	 *
	 * @param idPatron is ID of patron that need to be deleted
	 */
	public void deletePatron(int idPatron);

	/**
	 * adds document to the library
	 *
	 * @param document is document that need to be added
	 */
	public void addDocumentInLibrary(Document document);

	/**
	 * deletes document from the library
	 *
	 * @param idDocument is ID of the document that need to be deleted
	 */
	public void deleteDocumentFromLibrary(int idDocument);

}
