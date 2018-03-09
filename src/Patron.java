import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

public class Patron extends User {
	private String status;
	private ArrayList<Integer> listOfDocumentsPatron = new ArrayList<>();

	Patron(String login, String password, String status, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
		this.status = status;
	}

	public ArrayList getListOfDocumentsPatron() {
		return listOfDocumentsPatron;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/* @return true if Patron can get the document, otherwise false
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

	/* MAIN FUNCTION OF REQUESTING DOCUMENTS
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

	/* @param : id of Document, Database
	 */
	public void takeBook(int idBook, Database database) {
		try {
			if (canRequestBook(idBook, database)) {
				this.getListOfDocumentsPatron().add(idBook);
				database.getBook(idBook).deleteCopy();
				decreaseCountOdCopies(idBook, database);
				Date date = new Date();
				Date date2 = new Date();
				if (database.getBook(idBook).isBestseller())
					date2.setTime(date2.getTime() + 14 * 24 * 60 * 60 * 1000);
				else {
					if (status.toLowerCase().equals("student"))
						date2.setTime(date2.getTime() + 21 * 24 * 60 * 60 * 1000);
					else date2.setTime(date2.getTime() + 28 * 24 * 60 * 60 * 1000);
				}
				Debt debt = new Debt(getId(), idBook, date, date2, 0, true);
				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id=" + idBook);
		}
	}

	public void takeAV(int idAV, Database database) {
		try {
			if (canRequestAV(idAV, database)) {
				this.getListOfDocumentsPatron().add(idAV);
				database.getAV(idAV).deleteCopy();
				decreaseCountOdCopies(idAV, database);
				Date date = new Date();
				Date date2 = new Date();
				date2.setTime(date2.getTime() + 14 * 24 * 60 * 60 * 1000);
				Debt debt = new Debt(getId(), idAV, date, date2, 0, true);
				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + idAV);
		}
	}

	public void takeArticle(int idArticle, Database database) throws ParseException {
		try {
			if (canRequestArticle(idArticle, database)) {
				this.getListOfDocumentsPatron().add(idArticle);
				database.getArticle(idArticle).deleteCopy();
				decreaseCountOdCopies(idArticle, database);
				Date date = new Date();
				Date date2 = new Date();
				date2.setTime(date2.getTime() + 14 * 60 * 60 * 1000 * 24);
				Debt debt = new Debt(getId(), idArticle, date, date, 0, true);

				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + idArticle);
		}
	}

	/* MAIN FUNCTIOM OF BOOKING SYSTEM
	 */

	public void takeDocument(int idDocument, Database database) {
		try {
			if (canRequestDocument(idDocument, database)) {
				getListOfDocumentsPatron().add(idDocument);
				database.getDocument(idDocument).deleteCopy();
				decreaseCountOdCopies(idDocument, database);
				Date date = new Date();
				Debt debt = new Debt(getId(), idDocument, date, date, 0, true);
				database.insertDebt(debt);
			}
		} catch (SQLException | NoSuchElementException e) {
			System.out.println("Incorrect id" + idDocument);
		}
	}


	public void decreaseCountOdCopies(int idDocument, Database database) throws SQLException {
		int count = database.getDocument(idDocument).getNumberOfCopies();
		database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count - 1));
	}

	public void increaseCountOfCopies(int idDocument, Database database) throws SQLException {
		int count = database.getDocument(idDocument).getNumberOfCopies();
		database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(count + 1));
	}


	/* @param : id of Document, Database
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

	/* MAIN FUNCTION OF RETURNING SYSTEM
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
			int debtID = database.findDebtID(this.getId(), idDocument);
			database.deleteDebt(debtID);
		} catch (NoSuchElementException | SQLException e) {
			System.out.println("Incorrect id");
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Incorrect input");
		}
	}

	boolean compare(Patron patron) {
		return this.getLogin().equals(patron.getLogin());
	}
}