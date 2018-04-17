package users;


import patron_tools.BookingSystem;
import patron_tools.ReturningSystem;
import tools.Database;
import tools.Debt;
import tools.Logic;
import tools.Request;
import tools.WrongUserTypeException;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This class describes patron in library system.
 *
 * @author Madina Gafarova
 * @see User
 */
public class Patron extends User {
	/**
	 * users.Patron type.
	 * Possible values: {@code "instructor"}, {@code "student"}
	 */
	private String status;

	/**
	 * Priority of users.Patron.
	 * Levels of priority: student, instructor(instructions), TA, VP, professor
	 */
	protected int priority;

	/**
	 * List of patrons' documents IDs.
	 */
	private ArrayList<Integer> listOfDocumentsPatron = new ArrayList<>();

	/**
	 * Initialize new user.
	 *
	 * @param login    Login.
	 * @param password Password.
	 *  users.Patron type. Possible values: {@code "instructor"}, {@code "student"}
	 * @param name     First name.
	 * @param surname  Last name.
	 * @param phone    Phone number.
	 * @param address  Living address.
	 */
	public Patron(String login, String password, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
	}

	private BookingSystem bookingSystem = new BookingSystem();
	private ReturningSystem returningSystem = new ReturningSystem();

	/**
	 * Get the patrons' documents IDs.
	 *
	 * @return List of patrons' documents IDs.
	 */
	public ArrayList<Integer> getListOfDocumentsPatron() {
		return listOfDocumentsPatron;
	}

	/**
	 * Get the patron status. Possible values: {@code "instructor"}, {@code "student"}
	 *
	 * @return users.Patron status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the new patron status. Possible values: {@code "instructor"}, {@code "student"}
	 *
	 * @param status New patron status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public int getPriority() {
		return priority;
	}

	protected void setPriority() {
	}

	/**
	 * Checks whether patron can take the following document.
	 *
	 * @param documentID documents.Document ID to check.
	 * @param database   tools.Database that stores the information.
	 * @return {@code true} if this patron can get the document, otherwise {@code false}.
	 */
	public boolean canRequestDocument(int documentID, Database database) {
		return Logic.canRequestDocument(documentID, getId(), database);
	}

	/**
	 * Take the following book ang give it to this patron.
	 *
	 * @param bookID   documents.Book to take.
	 * @param database tools.Database that stores the information.
	 */
	public void takeBook(int bookID, Database database) {
		this.bookingSystem.takeBook(this, bookID, database);
	}

	/**
	 * Take the following audio/video ang give it to this patron.
	 *
	 * @param avID     Audio/video to take.
	 * @param database tools.Database that stores the information.
	 */
	public void takeAV(int avID, Database database) {
		this.bookingSystem.takeAV(this, avID, database);
	}

	/**
	 * Take the following article ang give it to this patron.
	 *
	 * @param articleID Article to take.
	 * @param database  tools.Database that stores the information.
	 */
	public void takeArticle(int articleID, Database database) {
		this.bookingSystem.takeArticle(this, articleID, database);
	}

	/**
	 * Make request the following document
	 *
	 * @param documentID document to request
	 * @param database   Database that stores the information
	 */
	public void makeRequest(int documentID, Database database) {
		this.bookingSystem.makeRequest(this, documentID, database);
	}


	/**
	 * Return the document to the library.
	 *
	 * @param documentID documents.Document ID.
	 * @param database   tools.Database that stores the information.
	 */
	public void returnDocument(int documentID, Database database) {
		this.returningSystem.returnDocument(this, documentID, database);
	}

	/**
	 * Compare this patron with another.
	 *
	 * @param patron Another patron to compare.
	 * @return {@code true} if patrons are similar, {@code false} otherwise.
	 */
	public boolean compare(Patron patron) {
		return this.getLogin().equals(patron.getLogin());
	}

	/**
	 * patron renews document after approving
	 *
	 * @param documentID documents.Document ID
	 * @param database   tools.Database stores the information
	 */
	public void renewDocument(int documentID, Database database) {
		this.bookingSystem.renewDocument(this, documentID, database);
	}

	/**
	 * patron sends request to renew document
	 *
	 * @param debtId   - id of debt patron wants to renew
	 * @param database - information storage
	 */
	public void sendRenewRequest(int debtId, Database database) {
		this.bookingSystem.sendRenewRequest(this, debtId, database);
	}

}