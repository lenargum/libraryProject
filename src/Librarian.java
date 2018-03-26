import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Date;

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
	 * @param book     Book to add.
	 * @param database Database that stores the information.
	 */
	public void addBook(Book book, Database database) {
		try {
			database.insertBook(book);
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Add new audio/video to the database.
	 *
	 * @param AV       Audio/video to add.
	 * @param database Database that stores the information.
	 */
	public void addAV(AudioVideoMaterial AV, Database database) {
		try {
			database.insertAV(AV);
		} catch (SQLException e) {
			System.out.println("Incorrect document");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect document");
		}
	}

	/**
	 * Add new article to the database.
	 *
	 * @param journalArticle Article to add.
	 * @param database       Database that stores the information.
	 */
	public void addArticle(JournalArticle journalArticle, Database database) {
		try {
			database.insertArticle(journalArticle);
		} catch (SQLException e) {
			System.out.println("Incorrect document");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect document");
		}
	}

	/**
	 * Add new patron to the database.
	 *
	 * @param patron   Patron to add.
	 * @param database Database that stores the information.
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
	 * @param database   Database that stores the information.
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
	 * @param database Database that stores the information.
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
	 * @param database   Database that stores the information.
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
	 * @param database Database that stores the information.
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
	 * @param database             Database that stores the information.
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
	 * @param database      Database that stores the information.
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
	 * @param database   Database that stores the information.
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
	 * @param database Database that stores the information.
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
	 * @param database Database that stores the information.
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
	 * @param database    Database that stores the information.
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
	 * Possible values: {@code "faculty"}, {@code "student"}
	 *
	 * @param idPatron ID of patron which is going to be modified.
	 * @param database Database that stores the information.
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
	 * @param database Database that stores the information.
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
	boolean compare(Librarian librarian) {
		return this.getLogin().equals(librarian.getLogin());
	}

	public void articleRequestConfirmation(int idPatron, int idAV, Database database){
		try {
				Patron pat = database.getPatron(idPatron);
				pat.getListOfDocumentsPatron().add(idAV);
				database.getAV(idAV).deleteCopy();
				decreaseCountOfCopies(idAV, database);
				Date date = new Date();
				Date date2 = new Date();
				if(pat.getStatus().toLowerCase().equals("visiting professor"))
					date2.setTime(date2.getTime() + 7 * 24 * 60 * 60 * 1000);
				else date2.setTime(date2.getTime() + 14 * 24 * 60 * 60 * 1000);
				Debt debt = new Debt(pat.getId(), idAV, date, date2, 0, true);
				database.insertDebt(debt);
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + idAV);
		}
	}

	public void avRequestConfirmation(int idPatron, int idBook, Database database){
		try {
			Patron pat = database.getPatron(idPatron);
				pat.getListOfDocumentsPatron().add(idBook);
				database.getBook(idBook).deleteCopy();
				decreaseCountOfCopies(idBook, database);
				Date dateBook = new Date();
				Date dateExpire = new Date();
				if (database.getBook(idBook).isBestseller())
					dateExpire.setTime(dateExpire.getTime() + 14 * 24 * 60 * 60 * 1000);
				else {
					if (pat.getStatus().toLowerCase().equals("student"))
						dateExpire.setTime(dateExpire.getTime() + 21 * 24 * 60 * 60 * 1000);
					else if (pat.getStatus().toLowerCase().equals("faculty")){
						dateExpire.setTime(dateExpire.getTime() + 28L * 24 * 60 * 60 * 1000);
					} else {
						dateExpire.setTime(dateExpire.getTime() + 7 * 24 * 60 * 60 * 1000);
					}


				Debt debt = new Debt(pat.getId(), idBook, dateBook, dateExpire, 0, true);
				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id=" + idBook);
		}
	}

	public void bookRequestConfirmation(int idPatron, int idArticle, Database database){
		try {
				Patron pat = database.getPatron(idPatron);
				pat.getListOfDocumentsPatron().add(idArticle);
				database.getArticle(idArticle).deleteCopy();
				decreaseCountOfCopies(idArticle, database);
				Date date = new Date();
				Date date2 = new Date();
				if(pat.getStatus().toLowerCase().equals("visiting professor"))
					date2.setTime(date2.getTime() + 7 * 24 * 60 * 60 * 1000);
				else date2.setTime(date2.getTime() + 14 * 60 * 60 * 1000 * 24);
				Debt debt = new Debt(pat.getId(), idArticle, date, date, 0, true);

				database.insertDebt(debt);
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + idArticle);
		} catch(ParseException e){

		}
	}

	public void documentRequestConfrmation(int idPatron, int idDocument, Database database){
		try{
			Patron pat = database.getPatron(idPatron);
			Document doc= database.getDocument(idDocument);
			pat.getListOfDocumentsPatron().add(idDocument);
			doc.deleteCopy();
			decreaseCountOfCopies(idDocument, database);
			Date date = new Date();
			Debt debt = new Debt(pat.getId(), idDocument, date, date, 0, true);
			database.insertDebt(debt);
		} catch(SQLException | NoSuchElementException e){
			System.out.println("Incorrect ID!");
		}

	}


	public void articleReturnConfirmation(int idPatron, int idArticle, Database database){
		try {
			Patron pat = database.getPatron(idPatron);
			JournalArticle article = database.getArticle(idArticle);
			for (int i = 0; i < pat.getListOfDocumentsPatron().size(); i++) {
				if (pat.getListOfDocumentsPatron().get(i).equals(idArticle)) {
					pat.getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			article.addCopy();
			increaseCountOfCopies(idArticle, database);
			int debtID = database.findDebtID(pat.getId(), idArticle);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		} catch (ParseException e){

		}
	}

	public void avReturnConfirmation(int idPatron, int idAV, Database database){
		try {
			Patron pat = database.getPatron(idPatron);
			for (int i = 0; i < pat.getListOfDocumentsPatron().size(); i++) {
				if (pat.getListOfDocumentsPatron().get(i).equals(idAV)) {
					pat.getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getAV(idAV).addCopy();
			increaseCountOfCopies(idAV, database);
			int debtID = database.findDebtID(pat.getId(), idAV);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		}
	}

	public void bookReturnConfirmation(int idPatron, int idBook, Database database){
		try {
			Book book = database.getBook(idBook);
			Patron pat = database.getPatron(idPatron);
			for (int i = 0; i < pat.getListOfDocumentsPatron().size(); i++) {
				if (pat.getListOfDocumentsPatron().get(i).equals(idBook)) {
					pat.getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			book.addCopy();
			increaseCountOfCopies(idBook, database);
			int debtID = database.findDebtID(pat.getId(), idBook);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		}
	}

	public void documentReturnConfirmation(int idPatron, int idDocument, Database database){
		try {
			Patron pat = database.getPatron(idPatron);

			for (int i = 0; i < pat.getListOfDocumentsPatron().size(); i++) {
				if (pat.getListOfDocumentsPatron().get(i).equals(idDocument)) {
					pat.getListOfDocumentsPatron().remove(i);
					break;
				}
			}
			database.getDocument(idDocument).addCopy();
			increaseCountOfCopies(idDocument, database);
			int debtID = database.findDebtID(pat.getId(), idDocument);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * Document renew confirmation
	 *
	 * @param debtID is debt we want to renew
	 * @param database Database storing the information
	 */
	public void renewDocument(int debtID, Database database){
		try{
			Debt debt = database.getDebt(debtID);
			if(false){//in the condition we check if there is outstanding request for document
				System.out.println("You can not renew this document!");
			} else {
				Date expDate = debt.getExpireDate();
				expDate.setTime(expDate.getTime() + 7 * 60 * 60 * 24 * 1000);
				debt.setCanRenew(false || database.getPatron(debt.getPatronId()).getStatus().toLowerCase().equals("vp"));
				debt.setExpireDate(expDate);
                System.out.println("Document was renewed!");
			}
		} catch (SQLException | NoSuchElementException e){
			System.out.println("Incorrect ID");
		} catch (ParseException e){
			System.out.println("By default");
		}
	}

	public void getFee(int debtID, Database database){
		try{
			Debt debt = database.getDebt(debtID);
			debt.setFee(0);
			System.out.println("Payout confirmed!");
		} catch (SQLException | NoSuchElementException e){
			System.out.println("Incorrect ID");
		} catch (ParseException e){
			System.out.println("By default");
		}
	}

	/**
	 * Decrease the number of copies of specified document by one.
	 *
	 * @param idDocument Document ID.
	 * @param database   Database that stores the information.
	 * @throws SQLException If passed the wrong document ID.
	 */
	private void decreaseCountOfCopies(int idDocument, Database database) throws SQLException {
		int count = database.getDocument(idDocument).getNumberOfCopies();
		database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count - 1));
	}

	/**
	 * Increase the number of copies of specified document by one.
	 *
	 * @param idDocument Document ID.
	 * @param database   Database that stores the information.
	 * @throws SQLException If passed the wrong document ID.
	 */
	private void increaseCountOfCopies(int idDocument, Database database) throws SQLException {
		int count = database.getDocument(idDocument).getNumberOfCopies();
		database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count + 1));
	}
	
}
