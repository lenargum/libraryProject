import javax.print.Doc;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Librarian extends User {

	Librarian(String login, String password, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
	}

	/**
	 * @param : Document
	 */
	public void addBook(Book book, Database database) throws SQLException {
		try {
			database.insertBook(book);
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}

	public void addAV(AudioVideoMaterial AV, Database database) throws SQLException {
		try {
			database.insertAV(AV);
		} catch (SQLException e) {
			System.out.println("Incorrect document");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect document");
		}
	}

	public void addArticle(JournalArticle journalArticle, Database database) throws SQLException {
		try {
			database.insertArticle(journalArticle);
		} catch (SQLException e) {
			System.out.println("Incorrect document");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect document");
		}
	}


	/**
	 * @param : Patron
	 */
	public void RegisterPatron(Patron patron, Database database) throws SQLException {
		try {
			database.insertPatron(patron);
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}


	/**
	 * @param : id of document
	 */
	public void deleteDocument(int idDocument, Database database) throws SQLException {
		try {
			database.deleteDocument(idDocument);
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	/**
	 * @param idPatron
	 */
	public void deletePatron(int idPatron, Database database) throws SQLException {
		try {
			database.deleteUser(idPatron);
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}

	public void modifyDocumentPrice(int idDoicument, Database database, double price) throws SQLException {
		try {
			database.getDocument(idDoicument).setPrice(price);
			database.editDocumentColumn(idDoicument, "price", Double.toString(price));
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	public void modifyBookEdition(int idBook, Database database, int edition) throws SQLException {
		try {
			database.getBook(idBook).setEdition(edition);
			database.editDocumentColumn(idBook, "edition", Integer.toString(edition));
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	public void modifyDocumentAllowness(int idDocument, Database database, boolean isAllowedForStudents) throws SQLException {
		try {
			database.getDocument(idDocument).setAllowedForStudents(isAllowedForStudents);
			database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowedForStudents));
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		}
	}

	public void modifyDocumentCopies(int idDocument, Database database, int countOfCopies) throws SQLException {
		try {
			database.getDocument(idDocument).setNumberOfCopies(countOfCopies);
			database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(countOfCopies));
		} catch (SQLException e) {
			System.out.println("Incorrect id");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id");
		}
	}

	public void modifyBookBestseller(int idBook, Database database, boolean bestseller) throws SQLException {
		try {
			database.getBook(idBook).setBestseller(bestseller);
			database.editDocumentColumn(idBook, "bestseller", Boolean.toString(bestseller));
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	public void modifyPatronSurname(int idPatron, Database database, String surname) throws SQLException {
		try {
			database.getPatron(idPatron).setSurname(surname);
			database.editUserColumn(idPatron, "lastname", surname);
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	public void modifyPatronAddress(int idPatron, Database database, String address) throws SQLException {
		try {
			database.getPatron(idPatron).setAddress(address);
			database.editUserColumn(idPatron, "address", address);
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	public void modifyPatronPhoneNumber(int idPatron, Database database, String phoneNumber) throws SQLException {
		try {
			database.getPatron(idPatron).setPhoneNumber(phoneNumber);
			database.editUserColumn(idPatron, "phone", phoneNumber);
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect input");
		} catch (SQLException e) {
			System.out.println("Incorrect input");
		}
	}

	public void modifyPatronStatus(int idPatron, Database database, String status) throws SQLException {
		try {
			database.getPatron(idPatron).setStatus(status);
			database.editDocumentColumn(idPatron, "status", status);
		} catch (SQLException e) {
			System.out.println("Incorrect id");
		} catch (NoSuchElementException e) {
			System.out.println("Incorrect id");
		}
	}

	public int getNumberOfDocument(Database database) throws SQLException {
		int n = 0;
		for (Document i : database.getDocumentList()) {
			n += i.getNumberOfCopies();
		}
		return n;
	}

	public int getDocumentID (Document document, Database database) throws SQLException{
		for (Document i:database.getDocumentList()
			 ) {
			if(i.equals(document)) return i.getID();
		}
		return 0;
	}

}
