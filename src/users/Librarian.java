package users;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import tools.Database;
import tools.Debt;
import tools.Request;
import tools.Return;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class describes librarian in library system.
 *
 * @author Madina Gafarova
 * @see User
 */
public class Librarian extends User {
	/**
	 * Initialize new librarian.
	 *
	 * @param login    Login.
	 * @param password Password.
	 * @param name     First name.
	 * @param surname  Last name.
	 * @param phone    Phone number.
	 * @param address  Living address.
	 */
	public Librarian(String login, String password, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
	}

	/**
	 * Add new book to the database.
	 *
	 * @param book     documents.Book to add.
	 * @param database tools.Database that stores the information.
	 */
	public void addBook(Book book, Database database) {
		try {
			database.insertBook(book);
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Add new audio/video to the database.
	 *
	 * @param AV       Audio/video to add.
	 * @param database tools.Database that stores the information.
	 */
	public void addAV(AudioVideoMaterial AV, Database database) {
		try {
			database.insertAV(AV);
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect document");
		}
	}

	/**
	 * Add new article to the database.
	 *
	 * @param journalArticle Article to add.
	 * @param database       tools.Database that stores the information.
	 */
	public void addArticle(JournalArticle journalArticle, Database database) {
		try {
			database.insertArticle(journalArticle);
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect document");
		}
	}

	/**
	 * Add new patron to the database.
	 *
	 * @param patron   users.Patron to add.
	 * @param database tools.Database that stores the information.
	 */
	public void registerPatron(Patron patron, Database database) {
		try {
			database.insertPatron(patron);
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Delete the document from the database.
	 *
	 * @param idDocument ID of document which is going to be deleted.
	 * @param database   tools.Database that stores the information.
	 */
	public void deleteDocument(int idDocument, Database database) {
		try {
			database.deleteDocument(idDocument);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Delete the patron from the database.
	 *
	 * @param idPatron ID of patron which is going to be deleted.
	 * @param database tools.Database that stores the information.
	 */
	public void deletePatron(int idPatron, Database database) {
		try {
			database.deleteUser(idPatron);
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Modify the price of document stored in database.
	 *
	 * @param idDocument ID of document which is going to be modified.
	 * @param database   tools.Database that stores the information.
	 * @param price      New price.
	 */
	public void modifyDocumentPrice(int idDocument, Database database, double price) {
		try {
			database.getDocument(idDocument).setPrice(price);
			database.editDocumentColumn(idDocument, "price", Double.toString(price));
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Modify the edition year of book stored in database.
	 *
	 * @param idBook   ID of book which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param edition  New edition year.
	 */
	public void modifyBookEdition(int idBook, Database database, int edition) {
		try {
			database.getBook(idBook).setEdition(edition);
			database.editDocumentColumn(idBook, "edition", Integer.toString(edition));
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Modify the student allowance status of document stored in database.
	 *
	 * @param idDocument           ID of document which is going to be modified.
	 * @param database             tools.Database that stores the information.
	 * @param isAllowedForStudents New status.
	 */
	public void modifyDocumentAllowance(int idDocument, Database database, boolean isAllowedForStudents) {
		try {
			database.getDocument(idDocument).setAllowedForStudents(isAllowedForStudents);
			database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowedForStudents));
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Modify the count of copies of document stored in database.
	 *
	 * @param idDocument    ID of document which is going to be modified.
	 * @param database      tools.Database that stores the information.
	 * @param countOfCopies New number.
	 */
	public void modifyDocumentCopies(int idDocument, Database database, int countOfCopies) {
		try {
			database.getDocument(idDocument).setNumberOfCopies(countOfCopies);
			database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(countOfCopies));
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id");
		}
	}

	/**
	 * Modify the bestseller status of book stored in database.
	 *
	 * @param idBook     ID of book which is going to be modified.
	 * @param database   tools.Database that stores the information.
	 * @param bestseller New status.
	 */
	public void modifyBookBestseller(int idBook, Database database, boolean bestseller) {
		try {
			database.getBook(idBook).setBestseller(bestseller);
			database.editDocumentColumn(idBook, "bestseller", Boolean.toString(bestseller));
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Modify the last name of patron stored in database.
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param surname  New last name.
	 */
	public void modifyPatronSurname(int idPatron, Database database, String surname) {
		try {
			database.getPatron(idPatron).setSurname(surname);
			database.editUserColumn(idPatron, "lastname", surname);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Modify the living address of patron stored in database.
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param address  New living address.
	 */
	public void modifyPatronAddress(int idPatron, Database database, String address) {
		try {
			database.getPatron(idPatron).setAddress(address);
			database.editUserColumn(idPatron, "address", address);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Modify the phone number of patron stored in database.
	 *
	 * @param idPatron    ID of patron which is going to be modified.
	 * @param database    tools.Database that stores the information.
	 * @param phoneNumber New phone number.
	 */
	public void modifyPatronPhoneNumber(int idPatron, Database database, String phoneNumber) {
		try {
			database.getPatron(idPatron).setPhoneNumber(phoneNumber);
			database.editUserColumn(idPatron, "phone", phoneNumber);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect input");
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
	public void modifyPatronStatus(int idPatron, Database database, String status) {
		try {
			database.getPatron(idPatron).setStatus(status);
			database.editDocumentColumn(idPatron, "status", status);
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id");
		}
	}

	/**
	 * Get count of all possible copies of documents in the library.
	 *
	 * @param database tools.Database that stores the information.
	 * @return Count of all copies.
	 * @throws SQLException If database is busy.
	 */
	public int getNumberOfDocument(Database database) throws SQLException {
		int n = 0;
		for (Document i : database.getDocumentList()) {
			n += i.getNumberOfCopies();
		}
		return n;
	}

	/**
	 * Compare this librarian with another.
	 *
	 * @param librarian Another librarian to compare.
	 * @return {@code true} if librarians are similar, {@code false} otherwise.
	 */
	public boolean compare(Librarian librarian) {
		return this.getLogin().equals(librarian.getLogin());
	}


	/**
	 * Return confirmation
	 *
	 * @param debtID   - id of debt patron wants to close
	 * @param database - information storage
	 * @throws SQLException   Something went wrong in database.
	 * @throws ParseException Something wrong with input.
	 */
	public void confirmReturn(int debtID, Database database) throws SQLException, ParseException {
		Debt debt = database.getDebt(debtID);
		debt.countFee(database);
		if (debt.getFee() == 0) {
			Return.returnDocument(debt.getDocumentId(), debt.getPatronId(), database);
		} else {
			System.out.println("You need to pay for delay");
		}
	}

	/**
	 * renew document confirmation
	 *
	 * @param request  - request that patron sent to renew document
	 * @param database - information storage
	 */
	public void confirmRenew(Request request, Database database) {
		try {
			request.approveRenew(database);
			database.deleteRequest(request.getRequestId());
		} catch (SQLException ignored) {

		}
	}


	/**
	 * confirmation of getting fee
	 *
	 * @param debtID   - id of debt patron wants to close
	 * @param database - information storage
	 */
	public void getFee(int debtID, Database database) {
		try {
			Debt debt = database.getDebt(debtID);
			debt.setFee(0);
			System.out.println("Payout confirmed!");
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect ID");
		} catch (ParseException e) {
			System.out.println("By default");
		}
	}

	/**
	 * taking document request confirmation
	 *
	 * @param request  - request librarian confirms
	 * @param database - information storage
	 * @throws SQLException Something went wrong in database.
	 */
	public void submitRequest(Request request, Database database) throws SQLException {
		System.out.println("users.Librarian <- submitting request " + request.getRequestId() + " . . .");
		request.approveRequest(request.getIdPatron(), request.getIdDocument(), database);
	}

	/**
	 * delete taking document request
	 *
	 * @param request  - request the librarian refuses
	 * @param database - information storage
	 * @throws SQLException Something went wrong in database.
	 */
	public void deleteRequest(Request request, Database database) throws SQLException {
		request.refuseRequest(request.getIdPatron(), request.getIdDocument(), database);
	}

	public void makeOutstandingRequest(Request request, Database database) throws SQLException, ParseException {
		sendNotificationsForOutstandingRequest(request, database);
		database.deleteRequestsForDocument(request.getIdDocument());
	}

	public void setAvailability(int docID, Database database) throws SQLException, ParseException {
		if (database.getDocument(docID).getNumberOfCopies() > 0) {
			sendNotificationsForAvailability(docID, database);
		}

	}

	private void sendNotificationsForOutstandingRequest(Request request, Database db) throws SQLException, ParseException {
		ArrayList<Request> requests = db.getRequests(request.getIdDocument());
		for (Request temp : requests) {
			if (temp.getIdPatron() == request.getIdPatron()) {
				Document doc = db.getDocument(request.getIdDocument());
				db.insertNotification(temp.getRequestId(), temp.getIdPatron(),
						"Outstanding request for " + doc.getTitle(), new Date());
			}
		}
	}

	private void sendNotificationsForAvailability(int docId, Database database) throws SQLException, ParseException {
		List<Request> requests = database.getRequests(docId);
		int i = 0;
		while (i < database.getDocument(docId).getNumberOfCopies()) {
			Request temp = requests.get(i);
			database.insertNotification(temp.getRequestId(), temp.getIdPatron(), "Set available document", new Date());
			i++;
		}
	}

}
