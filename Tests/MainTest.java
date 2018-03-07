import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
	Database database = new Database();

	Book lookupInBooks(String author, String title, String keywords, Database db) throws SQLException {
		List<Book> bookList = db.getBookList();
		Book searchBook = new Book(title, author, false, 0,
				false, 0, keywords, "", 0, false);
		for (Book book : bookList) {
			if (book.compare(searchBook)){
				return book;
			}
		}

		return null;
	}

	@Test
	void TestCase1() throws SQLException {
		database.connect();
		if (database.isConnected()) {
			Librarian librarian = database.getLibrarian(1);
//			database.insertLibrarian(librarian);

			Book b1 = new Book("Book1", "Author1", true, 3, false,
					400, "Book1", "Publ", 2006, false);
			Book b2 = new Book("Book2", "Author2", true, 2, false,
					400, "Book2", "Publ", 2012, false);
			Book b3 = new Book("Book3", "Author3", true, 1, false,
					600, "Book3", "Publ", 2007, false);

			librarian.addBook(b1, database);
			librarian.addBook(b2, database);
			librarian.addBook(b3, database);

			AudioVideoMaterial av1 = new AudioVideoMaterial("Video1", "fessi", true,
					1, false, 100, "video1");
			AudioVideoMaterial av2 = new AudioVideoMaterial("Audio2", "fesso", true,
					1, false, 50, "audio2");

			librarian.addAV(av1, database);
			librarian.addAV(av2, database);

			assertTrue(librarian.getNumberOfDocument(database) == 8);

			Patron p1 = new Patron("pat1", "patpass", "faculty",
					" Sergey", " Afonso", "30001", "ViaMargutta, 3");
			Patron p2 = new Patron("pat2", "patpass", "faculty",
					"Victor", "Rivera", "6454251", "Centr1");
			Patron p3 = new Patron("pat3", "patpass", "student",
					"Elvira", "Espindola", "30003 ", "ViadelCorso, 22");

			database.insertPatron(p1);
			database.insertPatron(p2);
			database.insertPatron(p3);

			assertTrue(database.getLibrarianList().size() + database.getPatronList().size() == 4);

			database.close();
		}
	}

	@Test
	void TestCase2() throws SQLException {
		database.connect();
		if (database.isConnected()) {
			Librarian librarian = database.getLibrarian(1);



			librarian.modifyDocumentCopies(16, database,
					database.getBook(16).getNumberOfCopies() - 2);
			librarian.modifyDocumentCopies(18, database,
					database.getBook(18).getNumberOfCopies() - 1);

			librarian.deletePatron(7, database);

			database.close();
		}
	}

	@Test
	void TestCase3() throws SQLException, ParseException {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {
			Patron p1 = new Patron("gjdkg", "ghajdafgjk", "faculty", "Eugenii", "Zuev", "8932058391850398", "Inno");
			Patron p2 = database.getPatron(5);
			Librarian librarian = database.getLibrarian(2);
			librarian.RegisterPatron(p1, database);
			p1.takeBook(7, database);
			int a = database.getDebt(database.findDebtID(p1.getId(), 7)).daysLeft();
			assertTrue(a == 28);
			p1.returnBook(7, database);
			librarian.deletePatron(p1.getId(), database);
			database.close();
		}
	}

	@Test
	void TestCase4() throws SQLException, ParseException {
		database.connect();
		if (database.isConnected()) {
			Patron p1 = new Patron("gjdkg", "ghajdafgjk", "faculty", "Eugenii", "Zuev", "8932058391850398", "Inno");
			Librarian lib = database.getLibrarian(2);
			Book book = new Book("B", "A", true, 3, false, 1200, "fdhjkshkh", "fdahfd", 9, true);
			lib.RegisterPatron(p1, database);
			lib.addBook(book, database);
			p1.takeBook(book.getID(), database);
			int a = database.getDebt(database.findDebtID(p1.getId(), 7)).daysLeft();
			assertTrue(a == 14);
			p1.returnBook(book.getID(), database);
			lib.deletePatron(p1.getId(), database);
			lib.deleteDocument(book.getID(), database);
			database.close();
		}
	}

	@Test
	void TestCase5() {
		database.connect();
		if (database.isConnected()) {
			database.close();
		}
	}

	@Test
	void TestCase6() throws SQLException {
		database.connect();
		if (database.isConnected()) {
			Librarian lib = database.getLibrarian(2);
			Patron p1 = new Patron("gjdkg", "ghajdafgjk", "faculty", "Eugenii", "Zuev", "8932058391850398", "Inno");
			lib.RegisterPatron(p1, database);
			p1.takeBook(7, database);
			System.out.println(p1.canRequestBook(7, database));
			p1.takeBook(7, database);
			database.close();
		}
	}

	@Test
	void TestCase7() {
		database.connect();
		if (database.isConnected()) {

			database.close();
		}
	}

	@Test
	void TestCase8() {
		database.connect();
		if (database.isConnected()) {

			database.close();
		}
	}

	@Test
	void TestCase9() {
		database.connect();
		if (database.isConnected()) {

			database.close();
		}
	}

	@Test
	void TestCase10() {
		database.connect();
		if (database.isConnected()) {

			database.close();
		}
	}

}
