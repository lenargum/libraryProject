package patronTools;

import documents.Document;
import tools.Database;
import tools.Debt;
import tools.Logic;
import tools.Request;
import users.Patron;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

public class BookingSystem {


	/**
	 * Take the following book ang give it to this patron.
	 *
	 * @param idBook   documents.Book to take.
	 * @param database tools.Database that stores the information.
	 */
	public void takeBook(Patron patron, int idBook, Database database) {
		try {

			patron.getListOfDocumentsPatron().add(idBook);
			database.getBook(idBook).deleteCopy();
			decreaseCountOfCopies(idBook, database);
			Date dateBook = new Date();
			Date dateExpire = Logic.expireDate(patron.getId(), idBook, database);

			Debt debt = new Debt(patron.getId(), idBook, dateBook, dateExpire, 0, true);
			database.insertDebt(debt);
			database.log("Patron with " + patron.getId() + " id has taken the book with id " + idBook + ".");

		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id=" + idBook);
		}
	}

	/**
	 * Take the following audio/video ang give it to this patron.
	 *
	 * @param idAV     Audio/video to take.
	 * @param database tools.Database that stores the information.
	 */
	public void takeAV(Patron patron, int idAV, Database database) {
		try {
			patron.getListOfDocumentsPatron().add(idAV);
			database.getAV(idAV).deleteCopy();
			decreaseCountOfCopies(idAV, database);
			Date date = new Date();
			Date date2 = Logic.expireDate(patron.getId(), idAV, database);
			Debt debt = new Debt(patron.getId(), idAV, date, date2, 0, true);
			database.insertDebt(debt);
			database.log("Patron with " + patron.getId() + " id has taken the audio/video material with id " + idAV + ".");

		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + idAV);
		}
	}

	/**
	 * Take the following article ang give it to this patron.
	 *
	 * @param idArticle Article to take.
	 * @param database  tools.Database that stores the information.
	 */
	public void takeArticle(Patron patron, int idArticle, Database database) {
		try {
			patron.getListOfDocumentsPatron().add(idArticle);
			database.getArticle(idArticle).deleteCopy();
			decreaseCountOfCopies(idArticle, database);
			Date date = new Date();
			Date date2 = Logic.expireDate(patron.getId(), idArticle, database);
			Debt debt = new Debt(patron.getId(), idArticle, date, date2, 0, true);
			database.log("Patron with " + patron.getId() + " id has taken the Journal article with id " + idArticle + ".");

			database.insertDebt(debt);
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + idArticle);
		}
	}

	/**
	 * Decrease the number of copies of specified document by one.
	 *
	 * @param idDocument documents.Document ID.
	 * @param database   tools.Database that stores the information.
	 * @throws SQLException If passed the wrong document ID.
	 */
	private void decreaseCountOfCopies(int idDocument, Database database) {
		int count = database.getDocument(idDocument).getNumberOfCopies();
		database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count - 1));


	}


	/**
	 * Make request the following document
	 *
	 * @param idDocument document to request
	 * @param database   Database that stores the information
	 * @throws SQLException something went wrong in database
	 */
	public void makeRequest(Patron patron, int idDocument, Database database) {
		try {
			if (Logic.canRequestDocument(idDocument, patron.getId(), database)) {
				Request request = new Request(patron, database.getDocument(idDocument), new Date(), false);
				database.insertRequest(request);
				database.log("Patron with id " + patron.getId() + " has requested document with id " + idDocument + ".");
			}
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + idDocument);
		}
	}


	/**
	 * patron renews document after approving
	 *
	 * @param docID    documents.Document ID
	 * @param database tools.Database stores the information
	 */
	public void renewDocument(Patron patron, int docID, Database database) {

		Debt debt = database.getDebt(database.findDebtID(patron.getId(), docID));
		Date expDate = Logic.renewExpireDate(debt.getDebtId(), database);
		if (!patron.getStatus().equals("vp")) {
			database.editDebtColumn(debt.getDebtId(), "can_renew", "false");
		}
		debt.setExpireDate(expDate);
		database.editDebtColumn(debt.getDebtId(), "expire_date",
				(new SimpleDateFormat("yyyy-MM-dd")).format(debt.getExpireDate()));
		database.log("Patron with id " + patron.getId() + " has renewed document with id " + docID + ".");

	}

	/**
	 * patron sends request to renew document
	 *
	 * @param debtId   - id of debt patron wants to renew
	 * @param database - information storage
	 */
	public void sendRenewRequest(Patron patron, int debtId, Database database) {
		Debt debt = database.getDebt(debtId);
		if (Logic.canRenewDocument(debt.getDocumentId(), patron.getId(), database)) {

			Document doc = database.getDocument(debt.getDocumentId());
			Date date = new Date();
			Request request = new Request(patron, doc, date, true);
			database.insertRequest(request);
			database.log("Patron with id " + patron.getId() + " has tried to renew document with id " + debt.getDocumentId() + ".");
		}

	}

}
