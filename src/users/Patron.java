package users;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import tools.Database;
import tools.Debt;
import tools.Logic;
import tools.Request;

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
	 * Take the following book ang give it to this patron.
	 *
	 * @param idBook   documents.Book to take.
	 * @param database tools.Database that stores the information.
	 */
	public void takeBook(int idBook, Database database) {
		try {

			this.listOfDocumentsPatron.add(idBook);
			database.getBook(idBook).deleteCopy();
			decreaseCountOfCopies(idBook, database);
			Date dateBook = new Date();
			Date dateExpire = new Date();
			if (database.getBook(idBook).isBestseller() && !getStatus().toLowerCase().equals("vp"))
				dateExpire.setTime(dateExpire.getTime() + 14 * 24 * 60 * 60 * 1000);
			else {
				switch (status.toLowerCase()) {
					case "student":
						dateExpire.setTime(dateExpire.getTime() + 21 * 24 * 60 * 60 * 1000);
						break;
					case "instructor":
					case "ta":
					case "professor":
						dateExpire.setTime(dateExpire.getTime() + 28L * 24 * 60 * 60 * 1000);
						break;
					default:
						dateExpire.setTime(dateExpire.getTime() + 7 * 24 * 60 * 60 * 1000);
						break;
				}
			}

			Debt debt = new Debt(getId(), idBook, dateBook, dateExpire, 0, true);
			database.insertDebt(debt);

		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id=" + idBook);
		}
	}

	/**
	 * Take the following audio/video ang give it to this patron.
	 *
	 * @param idAV     Audio/video to take.
	 * @param database tools.Database that stores the information.
	 */
	public void takeAV(int idAV, Database database) {
		try {
			if (Logic.canRequestDocument(idAV, this.getId(), database)) {
				this.listOfDocumentsPatron.add(idAV);
				database.getAV(idAV).deleteCopy();
				decreaseCountOfCopies(idAV, database);
				Date date = new Date();
				Date date2 = new Date();
				if (status.toLowerCase().equals("vp"))
					date2.setTime(date2.getTime() + 7 * 24 * 60 * 60 * 1000);
				else date2.setTime(date2.getTime() + 14 * 24 * 60 * 60 * 1000);
				Debt debt = new Debt(getId(), idAV, date, date2, 0, true);
				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + idAV);
		}
	}

	/**
	 * Take the following article ang give it to this patron.
	 *
	 * @param idArticle Article to take.
	 * @param database  tools.Database that stores the information.
	 */
	public void takeArticle(int idArticle, Database database) {
		try {
			if (Logic.canRequestDocument(idArticle, this.getId(), database)) {
				this.listOfDocumentsPatron.add(idArticle);
				database.getArticle(idArticle).deleteCopy();
				decreaseCountOfCopies(idArticle, database);
				Date date = new Date();
				Date date2 = new Date();
				if (status.toLowerCase().equals("vp"))
					date2.setTime(date2.getTime() + 7 * 24 * 60 * 60 * 1000);
				else date2.setTime(date2.getTime() + 14 * 60 * 60 * 1000 * 24);
				Debt debt = new Debt(getId(), idArticle, date, date2, 0, true);

				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + idArticle);
		} catch (ParseException e) {
			System.out.println("Data parsing failed");
		}
	}

	/**
	 * Make request the following document
	 *
	 * @param idDocument document to request
	 * @param database   Database that stores the information
	 * @throws SQLException something went wrong in database
	 */
	public void makeRequest(int idDocument, Database database) throws SQLException {
		try {
			Request request = new Request(this, database.getDocument(idDocument), new Date(), false);
			if (!findInRequests(idDocument, database))
				database.insertRequest(request);
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + idDocument);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public boolean findInRequests(int docId, Database database) throws SQLException, ParseException {
		List<Request> requests = database.getRequestsForPatron(this.getId());
		for (Request i : requests) {
			if (i.getIdDocument() == docId && i.getIdPatron() == this.getId()) return true;
		}
		return false;
	}

	/**
	 * Decrease the number of copies of specified document by one.
	 *
	 * @param idDocument documents.Document ID.
	 * @param database   tools.Database that stores the information.
	 * @throws SQLException If passed the wrong document ID.
	 */
	private void decreaseCountOfCopies(int idDocument, Database database) throws SQLException {
		int count = database.getDocument(idDocument).getNumberOfCopies();
		database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count - 1));
	}

	/**
	 * Increase the number of copies of specified document by one.
	 *
	 * @param idDocument documents.Document ID.
	 * @param database   tools.Database that stores the information.
	 * @throws SQLException If passed the wrong document ID.
	 */
	private void increaseCountOfCopies(int idDocument, Database database) throws SQLException {
		int count = database.getDocument(idDocument).getNumberOfCopies();
		database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count + 1));
	}

	/**
	 * Return the book to the library.
	 *
	 * @param idBook   documents.Book ID.
	 * @param database tools.Database that stores the information.
	 */
	public void returnBook(int idBook, Database database) {
		try {
			for (int i = 0; i < listOfDocumentsPatron.size(); i++) {
				if (getListOfDocumentsPatron().get(i).equals(idBook)) {
					getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getBook(idBook).addCopy();
			increaseCountOfCopies(idBook, database);
			int debtID = database.findDebtID(this.getId(), idBook);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Return the article to the library.
	 *
	 * @param idArticle Article ID.
	 * @param database  tools.Database that stores the information.
	 */
	public void returnArticle(int idArticle, Database database) throws ParseException {
		try {
			for (int i = 0; i < listOfDocumentsPatron.size(); i++) {
				if (getListOfDocumentsPatron().get(i).equals(idArticle)) {
					getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getArticle(idArticle).addCopy();
			increaseCountOfCopies(idArticle, database);
			int debtID = database.findDebtID(this.getId(), idArticle);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Return the audio/video to the library.
	 *
	 * @param idAV     Audio/video ID.
	 * @param database tools.Database that stores the information.
	 */
	public void returnAV(int idAV, Database database) {
		try {
			for (int i = 0; i < listOfDocumentsPatron.size(); i++) {
				if (getListOfDocumentsPatron().get(i).equals(idAV)) {
					getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getAV(idAV).addCopy();
			increaseCountOfCopies(idAV, database);
			int debtID = database.findDebtID(this.getId(), idAV);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Return the document to the library.
	 *
	 * @param idDocument documents.Document ID.
	 * @param database   tools.Database that stores the information.
	 */
	public void returnDocument(int idDocument, Database database) {
		try {
			for (int i = 0; i < listOfDocumentsPatron.size(); i++) {
				if (getListOfDocumentsPatron().get(i).equals(idDocument)) {
					getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getDocument(idDocument).addCopy();
			increaseCountOfCopies(idDocument, database);
			System.out.println("Return confirmed!");
			int debtID = database.findDebtID(this.getId(), idDocument);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
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
	 * patron renews document after approving
	 *
	 * @param docID    documents.Document ID
	 * @param database tools.Database stores the information
	 */
	public void renewDocument(int docID, Database database) {

		try {
			Debt debt = database.getDebt(database.findDebtID(this.getId(), docID));
			Date expDate = debt.getExpireDate();
			expDate.setTime(expDate.getTime() + 7 * 60 * 60 * 24 * 1000);
			if (!this.getStatus().equals("vp")) {
				database.editDebtColumn(debt.getDebtId(), "can_renew", "false");
			}
			debt.setExpireDate(expDate);
			database.editDebtColumn(debt.getDebtId(), "expire_date",
					(new SimpleDateFormat("yyyy-MM-dd")).format(debt.getExpireDate()));

		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (ParseException e) {
			System.out.println("Incorrect data parsing");
		}
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
			if (Logic.canRenewDocument(debt.getDocumentId(), debt.getPatronId(), database))
				database.insertRequest(request);
		} catch (SQLException | ParseException e) {
			System.out.println("Incorrect id");
		}
	}
}