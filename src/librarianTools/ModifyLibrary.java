package librarianTools;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.JournalArticle;
import tools.Database;
import tools.Logic;
import tools.OutstandingRequest;
import tools.Request;
import users.Patron;

import java.util.List;


public class ModifyLibrary {
	/**
	 * Add new book to the database.
	 *
	 * @param book     documents.Book to add.
	 * @param database tools.Database that stores the information.
	 */
	public void addBook(int librarianId, Book book, Database database) {
		if (Logic.canAdd(librarianId, database)) {
			database.insertBook(book);
			database.log("Librarian " + librarianId + "id has added Book " + book.getAuthors() + " \"" + book.getTitle() + "\".");
		}
	}

	/**
	 * Add new audio/video to the database.
	 *
	 * @param AV       Audio/video to add.
	 * @param database tools.Database that stores the information.
	 */
	public void addAV(int librarianId, AudioVideoMaterial AV, Database database) {
		if (Logic.canAdd(librarianId, database)) {
			database.insertAV(AV);
			database.log("Librarian " + librarianId + "id has added AV " + AV.getAuthors() + " \"" + AV.getTitle() + "\".");
		}
	}

	/**
	 * Add new article to the database.
	 *
	 * @param journalArticle Article to add.
	 * @param database       tools.Database that stores the information.
	 */
	public void addArticle(int librarianId, JournalArticle journalArticle, Database database) {
		if (Logic.canAdd(librarianId, database)) {
			database.insertArticle(journalArticle);
			database.log("Librarian " + librarianId + "id has added Article " + journalArticle.getAuthors() + " \"" + journalArticle.getTitle() + "\".");
		}
	}


	/**
	 * Add new patron to the database.
	 *
	 * @param patron   users.Patron to add.
	 * @param database tools.Database that stores the information.
	 */
	public void registerPatron(int librarianId, Patron patron, Database database) {
		if (Logic.canAdd(librarianId, database)) {
			database.insertPatron(patron);
			database.log("Librarian " + librarianId + "id has registered Patron " + patron.getId() + "id.");
		}
	}

	/**
	 * Delete the document from the database.
	 *
	 * @param idDocument ID of document which is going to be deleted.
	 * @param database   tools.Database that stores the information.
	 */
	public void deleteDocument(int librarianId, int idDocument, Database database) {
		OutstandingRequest deletionNotification = new OutstandingRequest();
		if (Logic.canDelete(librarianId, database) &&
				database.getRequestsForDocument(idDocument).size() != 0) {
			List<Request> requests = database.getRequestsForDocument(idDocument);
			for (int i = 0; i < requests.size(); i++) {
				deletionNotification.makeDeletionRequest(requests.get(i), database);
			}
			database.deleteRequestsForDocument(idDocument);
			database.log("Librarian " + librarianId + "id has deleted Document " + idDocument + "id.");
		}
		else {
			if (database.getDebtsForDocument(idDocument).size() == 0)
				database.deleteDocument(idDocument);
		}
	}

	/**
	 * Delete the patron from the database.
	 *
	 * @param idPatron ID of patron which is going to be deleted.
	 * @param database tools.Database that stores the information.
	 */
	public void deletePatron(int librarianId, int idPatron, Database database) {
		if (Logic.canDelete(librarianId, database) &&
				database.getDebtsForUser(idPatron).size() == 0) {
			if (database.getRequestsForPatron(idPatron).size() == 0)
				database.deleteUser(idPatron);
			else{
				List<Request> requests = database.getRequestsForPatron(idPatron);
				for(int i = 0; i < requests.size(); i++)
					database.deleteRequest(idPatron, requests.get(i).getIdDocument());
				database.deleteUser(idPatron);
				database.log("Librarian " + librarianId + "id has deleted Patron " + idPatron + "id.");

			}
		}
	}

}
