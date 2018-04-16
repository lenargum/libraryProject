package users;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import tools.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

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
	private int priority;

	/**
	 * List of patrons' documents IDs.
	 */
	private ArrayList<Integer> listOfDocumentsPatron = new ArrayList<>();

	/**
	 * Initialize new user.
	 *
	 * @param login    Login.
	 * @param password Password.
	 * @param status   users.Patron type. Possible values: {@code "instructor"}, {@code "student"}
	 * @param name     First name.
	 * @param surname  Last name.
	 * @param phone    Phone number.
	 * @param address  Living address.
	 */
	public Patron(String login, String password, String status, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
		this.status = status.toLowerCase();
		this.setPriority(this.status);
	}

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

	private void setPriority(String status) {
		switch (status.toLowerCase()) {
			case "student":
				priority = 0;
				break;
			case "instructor":
				priority = 1;
				break;
			case "ta":
				priority = 2;
				break;
			case "vp":
				priority = 3;
				break;
			case "professor":
				priority = 4;
				break;
			default:
				priority = -1;
				break;
		}
	}


	/**
	 * Make request the following document
	 *
	 * @param documentID document to request
	 * @param database   Database that stores the information
	 * @throws SQLException something went wrong in database
	 */
	public void makeRequest(int documentID, Database database) throws SQLException {
		try {
			Request request = new Request(this, database.getDocument(documentID), new Date(), false);
			if (!Booking.findInRequests(documentID, getId(), database))
				database.insertRequest(request);
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + documentID);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
	 * patron sends request to renew document
	 *
	 * @param debtId   - id of debt patron wants to renew
	 * @param database - information storage
	 */
	public void sendRenewRequest(int debtId, Database database) {
		try {
			Debt debt = database.getDebt(debtId);
			Document doc = database.getDocument(debt.getDocumentId());
			Date date = new Date();
			Request request = new Request(this, doc, date, true);

			if (debt.canRenew())
				database.insertRequest(request);
			else System.out.println("The document is already renewed, so you need to return it!");
		} catch (SQLException | ParseException e) {
			System.out.println("Incorrect id");
		}
	}
}