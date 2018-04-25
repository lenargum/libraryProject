package users;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import librarianTools.BookingSystem;
import librarianTools.Modify;
import librarianTools.ModifyLibrary;
import librarianTools.ReturningSystem;
import tools.Constants;
import tools.Database;
import tools.OutstandingRequest;
import tools.Request;


/**
 * This class describes librarian in library system.
 *
 * @author Madina Gafarova
 * @see User
 */
public class Librarian extends User {

	private int privilege;
	/**
	 * Tools for Librarian
	 */
	private BookingSystem bookingSystem;
	private Modify modify;
	private ModifyLibrary modifyLibrary;
	private OutstandingRequest outstandingRequest;
	private ReturningSystem returningSystem;

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
		setPrivilege(Constants.basicPrivilege);
		bookingSystem = new BookingSystem();
		modify = new Modify();
		modifyLibrary = new ModifyLibrary();
		outstandingRequest = new OutstandingRequest();
		returningSystem = new ReturningSystem();
	}

	/**
	 * Initialize new librarian.
	 *
	 * @param login     Login.
	 * @param password  Password.
	 * @param name      First name.
	 * @param surname   Last name.
	 * @param phone     Phone number.
	 * @param address   Living address.
	 * @param privilege Privilege level
	 */
	public Librarian(String login, String password, String name, String surname, String phone, String address, int privilege) {
		super(login, password, name, surname, phone, address);
		setPrivilege(privilege);
		bookingSystem = new BookingSystem();
		modify = new Modify();
		modifyLibrary = new ModifyLibrary();
		outstandingRequest = new OutstandingRequest();
		returningSystem = new ReturningSystem();
	}


	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int p) {
		this.privilege = p;
	}

	/**
	 * Add new book to the database.
	 *
	 * @param book     documents.Book to add.
	 * @param database tools.Database that stores the information.
	 */
	public void addBook(Book book, Database database) {
		this.modifyLibrary.addBook(this.getId(), book, database);
		database.log("Librarian " + this.getId() + "id has added Book " + book.getAuthors() + " \"" + book.getTitle() + "\".");
	}

	/**
	 * Add new audio/video to the database.
	 *
	 * @param AV       Audio/video to add.
	 * @param database tools.Database that stores the information.
	 */
	public void addAV(AudioVideoMaterial AV, Database database) {
		this.modifyLibrary.addAV(this.getId(), AV, database);
		database.log("Librarian " + this.getId() + "id has added AV " + AV.getAuthors() + " \"" + AV.getTitle() + "\".");
	}

	/**
	 * Add new article to the database.
	 *
	 * @param journalArticle Article to add.
	 * @param database       tools.Database that stores the information.
	 */
	public void addArticle(JournalArticle journalArticle, Database database) {
		this.modifyLibrary.addArticle(this.getId(), journalArticle, database);
		database.log("Librarian " + this.getId() + "id has added Article " + journalArticle.getAuthors() + " \"" + journalArticle.getTitle() + "\".");
	}

	/**
	 * Add new patron to the database.
	 *
	 * @param patron   users.Patron to add.
	 * @param database tools.Database that stores the information.
	 */
	public void registerPatron(Patron patron, Database database) {
		this.modifyLibrary.registerPatron(this.getId(), patron, database);
		database.log("Librarian " + this.getId() + "id has registered Patron " + patron.getId() + "id.");
	}

	/**
	 * Delete the document from the database.
	 *
	 * @param idDocument ID of document which is going to be deleted.
	 * @param database   tools.Database that stores the information.
	 */
	public void deleteDocument(int idDocument, Database database) {
		this.modifyLibrary.deleteDocument(this.getId(), idDocument, database);
		database.log("Librarian " + this.getId() + "id has deleted Document " + idDocument + "id.");

	}

	/**
	 * Delete the patron from the database.
	 *
	 * @param idPatron ID of patron which is going to be deleted.
	 * @param database tools.Database that stores the information.
	 */
	public void deletePatron(int idPatron, Database database) {
		this.modifyLibrary.deletePatron(this.getId(), idPatron, database);
		database.log("Librarian " + this.getId() + "id has deleted Patron " + idPatron + "id.");
	}

	/**
	 * Modify the price of document stored in database.
	 *
	 * @param idDocument ID of document which is going to be modified.
	 * @param database   tools.Database that stores the information.
	 * @param price      New price.
	 */
	public void modifyDocumentPrice(int idDocument, Database database, double price) {
		this.modify.modifyDocumentPrice(this.getId(), idDocument, database, price);
		database.log("Librarian " + this.getId() + "id has edited Price to " + price + " of Document " + idDocument + "id.");
	}

	/**
	 * Modify the edition year of book stored in database.
	 *
	 * @param idBook   ID of book which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param edition  New edition year.
	 */
	public void modifyBookEdition(int idBook, Database database, int edition) {
		this.modify.modifyBookEdition(this.getId(), idBook, database, edition);
		database.log("Librarian " + this.getId() + "id has edited Edition to " + edition + " of Book " + idBook + "id.");
	}

	/**
	 * Modify the student allowance status of document stored in database.
	 *
	 * @param idDocument           ID of document which is going to be modified.
	 * @param database             tools.Database that stores the information.
	 * @param isAllowedForStudents New status.
	 */
	public void modifyDocumentAllowance(int idDocument, Database database, boolean isAllowedForStudents) {
		this.modify.modifyDocumentAllowance(this.getId(), idDocument, database, isAllowedForStudents);
		database.log("Librarian " + this.getId() + "id has edited Allowance to " + isAllowedForStudents + " of Document " + idDocument + "id.");
	}

	/**
	 * Modify the count of copies of document stored in database.
	 *
	 * @param idDocument    ID of document which is going to be modified.
	 * @param database      tools.Database that stores the information.
	 * @param countOfCopies New number.
	 */
	public void modifyDocumentCopies(int idDocument, Database database, int countOfCopies) {
		this.modify.modifyDocumentCopies(this.getId(), idDocument, database, countOfCopies);
		database.log("Librarian " + this.getId() + "id has edited number of copies to " + countOfCopies + " of Document " + idDocument + "id.");
	}

	/**
	 * Modify the bestseller status of book stored in database.
	 *
	 * @param idBook     ID of book which is going to be modified.
	 * @param database   tools.Database that stores the information.
	 * @param bestseller New status.
	 */
	public void modifyBookBestseller(int idBook, Database database, boolean bestseller) {
		this.modify.modifyBookBestseller(this.getId(), idBook, database, bestseller);
		database.log("Librarian " + this.getId() + "id has edited isBestSeller to " + bestseller + " of Book " + idBook + "id.");
	}

	/**
	 * Modify the last name of patron stored in database.
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param name     New Name.
	 */
	public void modifyPatronName(int idPatron, Database database, String name) {
		this.modify.modifyPatronName(this.getId(), idPatron, database, name);
		database.log("Librarian " + this.getId() + "id has edited Name to " + name + " of Patron " + idPatron + "id.");
	}

	/**
	 * Modify the last name of patron stored in database.
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param surname  New last name.
	 */
	public void modifyPatronSurname(int idPatron, Database database, String surname) {
		this.modify.modifyPatronSurname(this.getId(), idPatron, database, surname);
		database.log("Librarian " + this.getId() + "id has edited Surname to " + surname + " of Patron " + idPatron + "id.");
	}

	/**
	 * Modify the living address of patron stored in database.
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database tools.Database that stores the information.
	 * @param address  New living address.
	 */
	public void modifyPatronAddress(int idPatron, Database database, String address) {
		this.modify.modifyPatronAddress(this.getId(), idPatron, database, address);
		database.log("Librarian " + this.getId() + "id has edited Address to " + address + " of Patron " + idPatron + "id.");
	}

	/**
	 * Modify the phone number of patron stored in database.
	 *
	 * @param idPatron    ID of patron which is going to be modified.
	 * @param database    tools.Database that stores the information.
	 * @param phoneNumber New phone number.
	 */
	public void modifyPatronPhoneNumber(int idPatron, Database database, String phoneNumber) {
		this.modify.modifyPatronPhoneNumber(this.getId(), idPatron, database, phoneNumber);
		database.log("Librarian " + this.getId() + "id has edited PhoneNumber to " + phoneNumber + " of Patron " + idPatron + "id.");
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
		this.modify.modifyPatronStatus(this.getId(), idPatron, database, status);
		database.log("Librarian " + this.getId() + "id has edited Status to " + status + " of Patron " + idPatron + "id.");
	}

	/**
	 * Get count of all possible copies of documents in the library.
	 *
	 * @param database tools.Database that stores the information.
	 * @return Count of all copies.
	 */
	public int getNumberOfDocument(Database database) {
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
	 */
	public void confirmReturn(int debtID, Database database) {
		this.returningSystem.confirmReturn(debtID, database);
		database.log("Librarian " + this.getId() + "id has confirmed return of Debt " + debtID + "id.");
	}

	/**
	 * renew document confirmation
	 *
	 * @param request  - request that patron sent to renew document
	 * @param database - information storage
	 */
	public void confirmRenew(Request request, Database database) {
		this.returningSystem.confirmRenew(request, database);
		database.log("Librarian " + this.getId() + "id has confirmed renew of Request " + request.getRequestId() + "id.");
	}


	/**
	 * confirmation of getting fee
	 *
	 * @param debtID   - id of debt patron wants to close
	 * @param database - information storage
	 */
	public void getFee(int debtID, Database database) {
		this.returningSystem.getFee(debtID, database);
	}

	/**
	 * taking document request confirmation
	 *
	 * @param request  - request librarian confirms
	 * @param database - information storage
	 */
	public void submitRequest(Request request, Database database) {
		this.bookingSystem.submitRequest(request, database);
		database.log("Librarian " + this.getId() + "id has submitted Request " + request.getRequestId() + "id.");
	}

	/**
	 * delete taking document request
	 *
	 * @param request  - request the librarian refuses
	 * @param database - information storage
	 */
	public void deleteRequest(Request request, Database database) {
		this.bookingSystem.deleteRequest(request, database);
		database.log("Librarian " + this.getId() + "id has deleted Request " + request.getRequestId() + "id.");
	}

	public void makeOutstandingRequest(Request request, Database database) {
		this.outstandingRequest.makeOutstandingRequest(this.getId(), request, database);
		database.log("Librarian " + this.getId() + "id has made outstanding Request " + request.getRequestId() + "id.");
	}

	public void setAvailability(int docID, Database database) {
		this.outstandingRequest.setAvailability(docID, database);
		database.log("Librarian " + this.getId() + "id has set availability of Document " + docID + "id.");
	}

}
