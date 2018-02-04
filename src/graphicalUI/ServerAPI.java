package graphicalUI;

import materials.AudioVideoMaterial;
import materials.Book;
import materials.Document;
import materials.JournalArticle;
import users.Librarian;
import users.Patron;

import java.util.LinkedList;
import java.util.List;

/**
 * This class used to interact with server.
 */
public class ServerAPI {
	/**
	 * Current librarian used to manage users and documents.
	 */
	private Librarian librarian;
	/**
	 * Current patron used to identify user.
	 */
	private Patron patron;

	/**
	 * Main constructor.
	 */
	public ServerAPI() {
		// TEMPORARY CODE - WILL BE REPLACED
		librarian = new Librarian("MariaVanna", "1, Universitetskaya", "+79876543211", -1);
		librarian.getListOfPatrons().add(new Patron("Name", "1, Universitetskaya", "+79871234567", "STUDENT", librarian.getListOfPatrons().size()));

		librarian.addDocumentInLibrary(new Book("Object-oriented Programming", "Bertrand Meyer", librarian.getListOfDocuments().size(), true, 500));
		librarian.addDocumentInLibrary(new Book("Introduction to Algorithms", "Thomas Cormen", librarian.getListOfDocuments().size(), true, 700));
		librarian.addDocumentInLibrary(new Book("50 shades of Gray", "E. L. James", librarian.getListOfDocuments().size(), false, 100));
		librarian.addDocumentInLibrary(new AudioVideoMaterial("Audio 1", "Author", librarian.getListOfDocuments().size(), 39.99f, true));
	}

	/**
	 * Authorize using credentials.
	 *
	 * @param login    User login.
	 * @param password User password.
	 * @return Is authorization successful.
	 */
	public boolean authorize(char[] login, char[] password) {
		// TEMPORARY CODE - WILL BE REMOVED
		boolean found = false;
		String loginString = String.copyValueOf(login);
		for (Patron pat : librarian.getListOfPatrons()) {
			if (pat.getName().equals(loginString)) {
				found = true;
				patron = pat;
				break;
			}
		}

		return found;
	}

	/**
	 * Get current patron.
	 *
	 * @return Current patron.
	 */
	public Patron getPatron() {
		return patron;
	}

	/**
	 * Get list of all documents.
	 *
	 * @return List of all documents.
	 */
	public List<Document> getDocuments() {
		return librarian.getListOfDocuments();
	}

	public List<Document> getBooks() {
		LinkedList<Document> books = new LinkedList<>();
		for (Document doc : librarian.getListOfDocuments()) {
			if (doc instanceof Book) {
				books.add(doc);
			}
		}

		return books;
	}

	public List<Document> getJournalArticles() {
		LinkedList<Document> articles = new LinkedList<>();
		for (Document doc : librarian.getListOfDocuments()) {
			if (doc instanceof JournalArticle) {
				articles.add(doc);
			}
		}

		return articles;
	}

	public List<Document> getAVs() {
		LinkedList<Document> AVs = new LinkedList<>();
		for (Document doc : librarian.getListOfDocuments()) {
			if (doc instanceof AudioVideoMaterial) {
				AVs.add(doc);
			}
		}

		return AVs;
	}

	/**
	 * Book an item.
	 *
	 * @param name Document name (TEMPORARY)
	 * @param pat  Patron which wants to book a document.
	 * @return Is booking successful.
	 */
	public boolean bookItem(String name, Patron pat) {
		boolean booked = false;
		Document doc = null;
		for (Document document : librarian.getListOfDocuments()) {
			if (document.getTitle().equals(name)) {
				doc = document;
			}
		}

		if (pat.getRequest(doc.getDocID(), librarian)) {
			booked = true;
		}
		if (booked) {
			pat.takeDocument(doc.getDocID(), librarian);
		}

		return booked;
	}

	public List<Document> getMyDocs() {
		return librarian.getListOfPatrons().get(patron.getId()).getListOfDocumentsPatron();
	}
}
