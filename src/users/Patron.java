package users;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import tools.Database;
import tools.Debt;
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
	 * Checks whether patron can take the following book.
	 *
	 * @param bookID   documents.Book ID to check.
	 * @param database tools.Database that stores the information.
	 * @return {@code true} if this patron can get the book, otherwise {@code false}.
	 */
	public boolean canRequestBook(int bookID, Database database) {
		try {
			database.getPatron(getId());
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("tools.Database <- users.Patron: There is no registered patron with ID=" + getId());
			return false;
		}
		try {
			Book book = database.getBook(bookID);
			if ((this.status.toLowerCase().equals("instructor") || (this.status.toLowerCase().equals("ta")) || (this.status.toLowerCase().equals("vp")) ||
					(this.status.toLowerCase().equals("professor")))
					&& (book.getNumberOfCopies() != 0) &&
					!book.isReference() && !getListOfDocumentsPatron().contains(bookID)) {
				return true;
			} else if ((this.status.toLowerCase().equals("instructor")) || (this.status.toLowerCase().equals("ta")) || (this.status.toLowerCase().equals("vp")) ||
					(this.status.toLowerCase().equals("professor"))) {
				if (book.getNumberOfCopies() == 0)
					System.out.println("No copies");
				if (getListOfDocumentsPatron().contains(bookID))
					System.out.println("You already have copy of this book");
				if (book.isReference())
					System.out.println("You can not borrow reference materials");
				return false;
			} else {
				if (book.isReference() && book.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(bookID))
					System.out.println("You already have copy of this book");
				if (!book.isAllowedForStudents())
					System.out.println("This document is not for students");
				if (book.isReference())
					System.out.println("You can not borrow reference materials");
				return book.isAllowedForStudents() && book.getNumberOfCopies() != 0 &&
						!book.isReference() && !getListOfDocumentsPatron().contains(bookID);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("tools.Database <- users.Patron: Incorrect ID. documents.Book with ID="
					+ bookID + " may not be registered id database.");
			return false;
		}
	}

	/**
	 * Checks whether patron can take the following article.
	 *
	 * @param articleID Article ID to check.
	 * @param database  tools.Database that stores the information.
	 * @return {@code true} if this patron can get the article, otherwise {@code false}.
	 */
	private boolean canRequestArticle(int articleID, Database database) {
		try {
			database.getPatron(getId());
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("tools.Database <- users.Patron: There is no registered patron with ID=" + getId());
			return false;
		}
		try {
			JournalArticle article = database.getArticle(articleID);
			if ((this.status.toLowerCase().equals("instructor") || (this.status.toLowerCase().equals("ta")) || (this.status.toLowerCase().equals("vp")) ||
					(this.status.toLowerCase().equals("professor"))) && (article.getNumberOfCopies() != 0) &&
					!article.isReference() && !getListOfDocumentsPatron().contains(articleID)) {
				return true;
			} else if ((this.status.toLowerCase().equals("instructor")) || (this.status.toLowerCase().equals("ta")) || (this.status.toLowerCase().equals("vp")) ||
					(this.status.toLowerCase().equals("professor"))) {
				if (article.getNumberOfCopies() == 0)
					System.out.println("No copies");
				if (getListOfDocumentsPatron().contains(articleID))
					System.out.println("You already have copy of this journal article");
				if (article.isReference())
					System.out.println("You can not borrow reference materials");
				return false;
			} else {
				if (article.isReference() && article.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(articleID))
					System.out.println("You already have copy of this journal article");
				if (!article.isAllowedForStudents())
					System.out.println("This document is not for students");
				if (article.isReference())
					System.out.println("You can not borrow reference materials");
				return article.isAllowedForStudents() &&
						article.getNumberOfCopies() != 0 &&
						!article.isReference() && !getListOfDocumentsPatron().contains(articleID);
			}
		} catch (SQLException | ParseException | NoSuchElementException e) {
			System.out.println("tools.Database <- users.Patron: Incorrect ID. Article with ID="
					+ articleID + " may not be registered id database.");
			return false;
		}
	}

	/**
	 * Checks whether patron can take the following audio/video.
	 *
	 * @param avID     Audio/video ID to check.
	 * @param database tools.Database that stores the information.
	 * @return {@code true} if this patron can get the audio/video, otherwise {@code false}.
	 */
	private boolean canRequestAV(int avID, Database database) {
		try {
			database.getPatron(getId());
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("tools.Database <- users.Patron: There is no registered patron with ID=" + getId());
			return false;
		}
		try {
			AudioVideoMaterial av = database.getAV(avID);
			if ((this.status.toLowerCase().equals("instructor") || (this.status.toLowerCase().equals("ta")) || (this.status.toLowerCase().equals("vp")) ||
					(this.status.toLowerCase().equals("professor"))) && (av.getNumberOfCopies() != 0) &&
					!av.isReference() && !getListOfDocumentsPatron().contains(avID)) {
				return true;
			} else if ((this.status.toLowerCase().equals("instructor")) || (this.status.toLowerCase().equals("ta")) || (this.status.toLowerCase().equals("vp")) ||
					(this.status.toLowerCase().equals("professor"))) {
				if (av.getNumberOfCopies() == 0)
					System.out.println("No copies");
				if (getListOfDocumentsPatron().contains(avID))
					System.out.println("You already have copy of this audio/video");

				if (av.isReference())
					System.out.println("You can not borrow reference materials");
				return false;
			} else {
				if (av.isReference() && av.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(avID))
					System.out.println("You already have copy of this audio/video");
				if (!av.isAllowedForStudents())
					System.out.println("This document is not for students");

				if (av.isReference())
					System.out.println("You can not borrow reference materials");
				return av.isAllowedForStudents() &&
						av.getNumberOfCopies() != 0 &&
						!av.isReference() && !getListOfDocumentsPatron().contains(avID);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("tools.Database <- users.Patron: Incorrect ID. AV with ID="
					+ avID + " may not be registered id database.");
			return false;
		}
	}

	/**
	 * Checks whether patron can take the following document.
	 *
	 * @param documentID documents.Document ID to check.
	 * @param database   tools.Database that stores the information.
	 * @return {@code true} if this patron can get the document, otherwise {@code false}.
	 */
	public boolean canRequestDocument(int documentID, Database database) {
		try {
			database.getPatron(getId());
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("tools.Database <- users.Patron: There is no registered patron with ID=" + getId());
			return false;
		}
		try {
			Document doc = database.getDocument(documentID);
			if ((this.status.toLowerCase().equals("instructor") || (this.status.toLowerCase().equals("ta")) || (this.status.toLowerCase().equals("vp")) ||
					(this.status.toLowerCase().equals("professor"))) && (doc.getNumberOfCopies() != 0) &&
					!(doc.isReference()) && !getListOfDocumentsPatron().contains(documentID) && !findInRequests(documentID, database)) {
				return true;
			} else if (doc.isAllowedForStudents() &&
					doc.getNumberOfCopies() != 0 &&
					!(doc.isReference()) && !getListOfDocumentsPatron().contains(documentID) && !findInRequests(documentID, database)) {
				return true;
			} else {
				if (doc.isReference() && doc.getNumberOfCopies() == 0)
					System.out.println("No available copies. Document ID: " + documentID);
				if (getListOfDocumentsPatron().contains(documentID))
					System.out.println("Patron already have a copy of this document. Patron ID: " + getId() +
							", Document ID: " + documentID);
				if (!doc.isAllowedForStudents())
					System.out.println("This document is not allowed for students. Document ID:" + documentID);
				if (doc.isReference())
					System.out.println("Reference materials are not available for taking. Document ID: " + documentID);
				return false;
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("tools.Database <- users.Patron: Incorrect ID. documents.Document with ID="
					+ documentID + " may not be registered id database.");
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Take the following book ang give it to this patron.
	 *
	 * @param bookID   documents.Book to take.
	 * @param database tools.Database that stores the information.
	 */
	public void takeBook(int bookID, Database database) {
		try {

			this.listOfDocumentsPatron.add(bookID);
			database.getBook(bookID).deleteCopy();
			decreaseCountOfCopies(bookID, database);
			Date dateBook = new Date();
			Date dateExpire = new Date();
			if (database.getBook(bookID).isBestseller() && !getStatus().toLowerCase().equals("vp"))
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

			Debt debt = new Debt(getId(), bookID, dateBook, dateExpire, 0, true);
			database.insertDebt(debt);

		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id=" + bookID);
		}
	}

	/**
	 * Take the following audio/video ang give it to this patron.
	 *
	 * @param avID     Audio/video to take.
	 * @param database tools.Database that stores the information.
	 */
	public void takeAV(int avID, Database database) {
		try {
			if (canRequestAV(avID, database)) {
				this.listOfDocumentsPatron.add(avID);
				database.getAV(avID).deleteCopy();
				decreaseCountOfCopies(avID, database);
				Date dateBook = new Date();
				Date dateReturn = new Date();
				if (status.toLowerCase().equals("vp"))
					dateReturn.setTime(dateReturn.getTime() + 7 * 24 * 60 * 60 * 1000);
				else dateReturn.setTime(dateReturn.getTime() + 14 * 24 * 60 * 60 * 1000);
				Debt debt = new Debt(getId(), avID, dateBook, dateReturn, 0, true);
				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + avID);
		}
	}

	/**
	 * Take the following article ang give it to this patron.
	 *
	 * @param articleID Article to take.
	 * @param database  tools.Database that stores the information.
	 */
	public void takeArticle(int articleID, Database database) {
		try {
			if (canRequestArticle(articleID, database)) {
				this.listOfDocumentsPatron.add(articleID);
				database.getArticle(articleID).deleteCopy();
				decreaseCountOfCopies(articleID, database);
				Date dateBook = new Date();
				Date dateReturn = new Date();
				if (status.toLowerCase().equals("vp"))
					dateReturn.setTime(dateReturn.getTime() + 7 * 24 * 60 * 60 * 1000);
				else dateReturn.setTime(dateReturn.getTime() + 14 * 60 * 60 * 1000 * 24);
				Debt debt = new Debt(getId(), articleID, dateBook, dateReturn, 0, true);

				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + articleID);
		} catch (ParseException e) {
			System.out.println("Data parsing failed");
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
			if (!findInRequests(documentID, database))
				database.insertRequest(request);
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + documentID);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public boolean findInRequests(int documentID, Database database) throws SQLException, ParseException {
		List<Request> requests = database.getRequestsForPatron(this.getId());
		for (Request i : requests) {
			if (i.getIdDocument() == documentID && i.getIdPatron() == this.getId()) return true;
		}
		return false;
	}

	/**
	 * Decrease the number of copies of specified document by one.
	 *
	 * @param documentID documents.Document ID.
	 * @param database   tools.Database that stores the information.
	 * @throws SQLException If passed the wrong document ID.
	 */
	private void decreaseCountOfCopies(int documentID, Database database) throws SQLException {
		int count = database.getDocument(documentID).getNumberOfCopies();
		database.editDocumentColumn(documentID, "num_of_copies", Integer.toString(count - 1));
	}

	/**
	 * Increase the number of copies of specified document by one.
	 *
	 * @param documentID documents.Document ID.
	 * @param database   tools.Database that stores the information.
	 * @throws SQLException If passed the wrong document ID.
	 */
	private void increaseCountOfCopies(int documentID, Database database) throws SQLException {
		int count = database.getDocument(documentID).getNumberOfCopies();
		database.editDocumentColumn(documentID, "num_of_copies", Integer.toString(count + 1));
	}

	/**
	 * Return the book to the library.
	 *
	 * @param bookID   documents.Book ID.
	 * @param database tools.Database that stores the information.
	 */
	public void returnBook(int bookID, Database database) {
		try {
			for (int i = 0; i < listOfDocumentsPatron.size(); i++) {
				if (getListOfDocumentsPatron().get(i).equals(bookID)) {
					getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getBook(bookID).addCopy();
			increaseCountOfCopies(bookID, database);
			int debtID = database.findDebtID(this.getId(), bookID);
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
	 * @param articleID Article ID.
	 * @param database  tools.Database that stores the information.
	 */
	public void returnArticle(int articleID, Database database) throws ParseException {
		try {
			for (int i = 0; i < listOfDocumentsPatron.size(); i++) {
				if (getListOfDocumentsPatron().get(i).equals(articleID)) {
					getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getArticle(articleID).addCopy();
			increaseCountOfCopies(articleID, database);
			int debtID = database.findDebtID(this.getId(), articleID);
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
	 * @param avID     Audio/video ID.
	 * @param database tools.Database that stores the information.
	 */
	public void returnAV(int avID, Database database) {
		try {
			for (int i = 0; i < listOfDocumentsPatron.size(); i++) {
				if (getListOfDocumentsPatron().get(i).equals(avID)) {
					getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getAV(avID).addCopy();
			increaseCountOfCopies(avID, database);
			int debtID = database.findDebtID(this.getId(), avID);
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
	 * @param documentID documents.Document ID.
	 * @param database   tools.Database that stores the information.
	 */
	public void returnDocument(int documentID, Database database) {
		try {
			for (int i = 0; i < listOfDocumentsPatron.size(); i++) {
				if (getListOfDocumentsPatron().get(i).equals(documentID)) {
					getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getDocument(documentID).addCopy();
			increaseCountOfCopies(documentID, database);
			System.out.println("Return confirmed!");
			int debtID = database.findDebtID(this.getId(), documentID);
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
	 * @param documentID documents.Document ID
	 * @param database   tools.Database stores the information
	 */
	public void renewDocument(int documentID, Database database) {

		try {
			Debt debt = database.getDebt(database.findDebtID(this.getId(), documentID));
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

			if (debt.canRenew())
				database.insertRequest(request);
			else System.out.println("The document is already renewed, so you need to return it!");
		} catch (SQLException | ParseException e) {
			System.out.println("Incorrect id");
		}
	}

	public enum PatronType {
		STUDENT, INSTRUCTOR, TA, VP, PROFESSOR
	}
}