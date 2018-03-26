import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

/**
 * This class describes patron in library system.
 *
 * @author Madina Gafarova
 * @see User
 */
public class Patron extends User {
	/**
	 * Patron type.
	 * Possible values: {@code "faculty"}, {@code "student"}
	 */
	private String status;

	/**
	 * List of patrons' documents IDs.
	 */
	private ArrayList<Integer> listOfDocumentsPatron = new ArrayList<>();

	/**
	 * Initialize new user.
	 *
	 * @param login    Login.
	 * @param password Password.
	 * @param status   Patron type. Possible values: {@code "faculty"}, {@code "student"}
	 * @param name     First name.
	 * @param surname  Last name.
	 * @param phone    Phone number.
	 * @param address  Living address.
	 */
	public Patron(String login, String password, String status, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
		this.status = status;
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
	 * Get the patron status. Possible values: {@code "faculty"}, {@code "student"}
	 *
	 * @return Patron status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the new patron status. Possible values: {@code "faculty"}, {@code "student"}
	 *
	 * @param status New patron status.
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Checks whether patron can take the following book.
	 *
	 * @param idBook   Book ID to check.
	 * @param database Database that stores the information.
	 * @return {@code true} if this patron can get the book, otherwise {@code false}.
	 */
	public boolean canRequestBook(int idBook, Database database) {
		try {
			database.getPatron(getId());
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Database <- Patron: No patron registered with ID=" + getId());
			return false;
		}
		try {
			Book book = database.getBook(idBook);
			if ((this.status.toLowerCase().equals("faculty")) && (book.getNumberOfCopies() != 0) &&
					!book.isReference() && !getListOfDocumentsPatron().contains(idBook)) {
				return true;
			} else if ((this.status.toLowerCase().equals("faculty"))) {
				if (book.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(idBook))
					System.out.println("You already have copy of this book");
				if (book.isReference())
					System.out.println("You can not borrow reference materials");
				return false;
			} else {
				if (book.isReference() && book.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(idBook))
					System.out.println("You already have copy of this book");
				if (!book.isAllowedForStudents())
					System.out.println("This document is not for students");
				if (book.isReference())
					System.out.println("You can not borrow reference materials");
				return book.isAllowedForStudents() && book.getNumberOfCopies() != 0 &&
						!book.isReference() && !getListOfDocumentsPatron().contains(idBook);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Database <- Patron: Incorrect ID. Book with ID="
					+ idBook + " may not be registered id database.");
			return false;
		}
	}

	/**
	 * Checks whether patron can take the following article.
	 *
	 * @param idArticle Article ID to check.
	 * @param database  Database that stores the information.
	 * @return {@code true} if this patron can get the article, otherwise {@code false}.
	 */
	public boolean canRequestArticle(int idArticle, Database database) {
		try {
			database.getPatron(getId());
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Database <- Patron: No patron registered with ID=" + getId());
			return false;
		}
		try {
			JournalArticle article = database.getArticle(idArticle);
			if ((this.status.toLowerCase().equals("faculty")) && (article.getNumberOfCopies() != 0) &&
					!article.isReference() && !getListOfDocumentsPatron().contains(idArticle)) {
				return true;
			} else if ((this.status.toLowerCase().equals("faculty"))) {
				if (article.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(idArticle))
					System.out.println("You already have copy of this journal article");
				if (article.isReference())
					System.out.println("You can not borrow reference materials");
				return false;
			} else {
				if (article.isReference() && article.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(idArticle))
					System.out.println("You already have copy of this journal article");
				if (!article.isAllowedForStudents())
					System.out.println("This document is not for students");
				if (article.isReference())
					System.out.println("You can not borrow reference materials");
				return article.isAllowedForStudents() &&
						article.getNumberOfCopies() != 0 &&
						!article.isReference() && !getListOfDocumentsPatron().contains(idArticle);
			}
		} catch (SQLException | ParseException | NoSuchElementException e) {
			System.out.println("Database <- Patron: Incorrect ID. Article with ID="
					+ idArticle + " may not be registered id database.");
			return false;
		}
	}

	/**
	 * Checks whether patron can take the following audio/video.
	 *
	 * @param idAV     Audio/video ID to check.
	 * @param database Database that stores the information.
	 * @return {@code true} if this patron can get the audio/video, otherwise {@code false}.
	 */
	public boolean canRequestAV(int idAV, Database database) {
		try {
			database.getPatron(getId());
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Database <- Patron: No patron registered with ID=" + getId());
			return false;
		}
		try {
			AudioVideoMaterial av = database.getAV(idAV);
			if ((this.status.toLowerCase().equals("faculty")) && (av.getNumberOfCopies() != 0) &&
					!av.isReference() && !getListOfDocumentsPatron().contains(idAV)) {
				return true;
			} else if ((this.status.toLowerCase().equals("faculty"))) {
				if (av.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(idAV))
					System.out.println("You already have copy of this audio/video");

				if (av.isReference())
					System.out.println("You can not borrow reference materials");
				return false;
			} else {
				if (av.isReference() && av.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(idAV))
					System.out.println("You already have copy of this audio/video");
				if (!av.isAllowedForStudents())
					System.out.println("This document is not for students");

				if (av.isReference())
					System.out.println("You can not borrow reference materials");
				return av.isAllowedForStudents() &&
						av.getNumberOfCopies() != 0 &&
						!av.isReference() && !getListOfDocumentsPatron().contains(idAV);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Database <- Patron: Incorrect ID. AV with ID="
					+ idAV + " may not be registered id database.");
			return false;
		}
	}

	/**
	 * Checks whether patron can take the following document.
	 *
	 * @param idDocument Document ID to check.
	 * @param database   Database that stores the information.
	 * @return {@code true} if this patron can get the document, otherwise {@code false}.
	 */
	public boolean canRequestDocument(int idDocument, Database database) {
		try {
			database.getPatron(getId());
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Database <- Patron: No patron registered with ID=" + getId());
			return false;
		}
		try {
			Document doc = database.getDocument(idDocument);
			if ((this.status.toLowerCase().equals("faculty")) && (doc.getNumberOfCopies() != 0) &&
					!(doc.isReference()) && !getListOfDocumentsPatron().contains(idDocument)) {
				return true;
			} else if (doc.isAllowedForStudents() &&
					doc.getNumberOfCopies() != 0 &&
					!(doc.isReference()) && !getListOfDocumentsPatron().contains(idDocument)) {
				return true;
			} else {
				if (doc.isReference() && doc.getNumberOfCopies() == 0)
					System.out.println("Not copies");
				if (getListOfDocumentsPatron().contains(idDocument))
					System.out.println("You already have copy of this document");
				if (!doc.isAllowedForStudents())
					System.out.println("This document is not for students");
				if (doc.isReference())
					System.out.println("You can not borrow reference materials");
				return false;
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Database <- Patron: Incorrect ID. Document with ID="
					+ idDocument + " may not be registered id database.");
			return false;
		}
	}

	/**
	 * Take the following book ang give it to this patron.
	 *
	 * @param idBook   Book to take.
	 * @param database Database that stores the information.
	 */
	public void takeBook(int idBook, Database database) {
		try {
			if (canRequestBook(idBook, database)) {
			    //send request to database
			}
		} catch ( NoSuchElementException e) {
			System.out.println("Incorrect id=" + idBook);
		}
	}

	/**
	 * Take the following audio/video ang give it to this patron.
	 *
	 * @param idAV     Audio/video to take.
	 * @param database Database that stores the information.
	 */
	public void takeAV(int idAV, Database database) {
		try {
			if (canRequestAV(idAV, database)) {
			    //send request  to database
			}
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + idAV);
		}
	}

	/**
	 * Take the following article ang give it to this patron.
	 *
	 * @param idArticle Article to take.
	 * @param database  Database that stores the information.
	 */
	public void takeArticle(int idArticle, Database database) throws ParseException {
		try {
			if (canRequestArticle(idArticle, database)) {
				//send request to database
			}
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + idArticle);
		}
	}

	/**
	 * Take the following document ang give it to this patron.
	 *
	 * @param idDocument document to take.
	 * @param database   Database that stores the information.
	 */
	public void takeDocument(int idDocument, Database database) {
		try {
			if (canRequestDocument(idDocument, database)) {
				//send request to the database
			}
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id" + idDocument);
		}
	}

	/**
	 * Return the book to the library.
	 *
	 * @param idBook   Book ID.
	 * @param database Database that stores the information.
	 */
	public void returnBook(int idBook, Database database) {
		try {
		        Debt debt = database.getDebt(database.findDebtID(this.getId(), idBook));
		        debt.countFee(database);
		        if(debt.getFee() > 0){
		            //send request to the library
                } else System.out.println("You need to pay!");
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		} catch (ParseException e){

        }
	}

	/**
	 * Return the article to the library.
	 *
	 * @param idArticle Article ID.
	 * @param database  Database that stores the information.
	 */
	public void returnArticle(int idArticle, Database database) throws ParseException {
		try {
            Debt debt = database.getDebt(database.findDebtID(this.getId(), idArticle));
            debt.countFee(database);
            if(debt.getFee() > 0){
                //send request to the library
            } else System.out.println("You need to pay!");
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
	 * @param database Database that stores the information.
	 */
	public void returnAV(int idAV, Database database) {
		try {
             Debt debt = database.getDebt(database.findDebtID(this.getId(), idAV));
             debt.countFee(database);
             if(debt.getFee() > 0){

            } else System.out.println("You need to pay!");
		    } catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		} catch (ParseException e){

        }
	}

	/**
	 * Return the document to the library.
	 *
	 * @param idDocument Document ID.
	 * @param database   Database that stores the information.
	 */
	public void returnDocument(int idDocument, Database database) {
		try {
		    Debt debt = database.getDebt(database.findDebtID(this.getId(), idDocument));
            debt.countFee(database);
            if(debt.getFee() > 0){
                //send request to the library
            } else System.out.println("You need to pay!");
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		} catch (ParseException e){

        }
	}

	/**
	 * Compare this patron with another.
	 *
	 * @param patron Another patron to compare.
	 * @return {@code true} if patrons are similar, {@code false} otherwise.
	 */
	boolean compare(Patron patron) {
		return this.getLogin().equals(patron.getLogin());
	}

	/**
	 * patron requests the document renew
	 * @param docID Document ID
	 * @param database Database stores the information
	 */
	public void renewDocument(int docID, int libID, Database database){
		try{
			int debtID = database.findDebtID(this.getId(), docID);
			Debt debt = database.getDebt(debtID);
			if(debt.canRenew()){
				database.renew(debtID);
			} else {
				System.out.println("The document is already renewed!");
			}
		} catch (NoSuchElementException | SQLException e){
			System.out.println("Incorrect id");
		} catch(ParseException e) {
			System.out.println("By default");
        }
	}

	/**
	 * patron wants to fine his debt
	 * @param debtID  - debt to fine
	 * @param database - information storage
	 */
	public void fine(int debtID, Database database){
		try{
			Debt debt = database.getDebt(debtID);
			debt.countFee(database);
			if(debt.getFee() > 0){
				database.fee(debtID);
			} else {
				System.out.println("You do not owe Library anything");
			}
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect ID");
		} catch (ParseException e) {
			System.out.println("By default");
		}
	}
}