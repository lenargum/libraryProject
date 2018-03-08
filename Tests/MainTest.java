import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
	static Book b1, b2, b3;
	static AudioVideoMaterial av1, av2;
	static Patron p1, p2, p3;
	static Librarian librarian;
	Database database = new Database();

	Book lookupInBooks(String author, String title, String keywords, Database db) throws SQLException {
		List<Book> bookList = db.getBookList();
		Book searchBook = new Book(title, author, false, 0,
				false, 0, keywords, "", 0, false);
		for (Book book : bookList) {
			if (book.compare(searchBook)) {
				return book;
			}
		}

		return null;
	}

	@Test
	void cleanBeforeTest() {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {
			database.clear();
			database.close();
		}
	}

	@Test
		// Test case 1
	void test1_TC1() throws SQLException {
		database.connect();
		if (database.isConnected()) {
			librarian = new Librarian("librarian", "pass", "Name", "Surname", "123545687", "Innopolis");
			database.insertLibrarian(librarian);
			librarian.setId(database.getLibrarianID(librarian));

			b1 = new Book("Book1", "Author1", true, 3, false,
					400, "Book1", "Publ", 2006, false);
			b2 = new Book("Book2", "Author2", true, 2, false,
					400, "Book2", "Publ", 2012, false);
			b3 = new Book("Book3", "Author3", true, 1, false,
					600, "Book3", "Publ", 2007, false);

			librarian.addBook(b1, database);
			b1.setID(database.getDocumentID(b1));
			librarian.addBook(b2, database);
			b2.setID(database.getDocumentID(b2));
			librarian.addBook(b3, database);
			b3.setID(database.getDocumentID(b3));

			av1 = new AudioVideoMaterial("Video1", "fessi", true,
					1, false, 100, "video1");
			av2 = new AudioVideoMaterial("Audio2", "fesso", true,
					1, false, 50, "audio2");

			librarian.addAV(av1, database);
			av1.setID(database.getDocumentID(av1));
			librarian.addAV(av2, database);
			av2.setID(database.getDocumentID(av2));

			assertTrue(librarian.getNumberOfDocument(database) == 8);

			p1 = new Patron("pat1", "patpass", "faculty",
					"Sergey", "Afonso", "30001", "Via Margutta, 3");
			p2 = new Patron("pat2", "patpass", "student",
					"Nadia", "Teixeira", "30002", "Via Sacra, 13");
			p3 = new Patron("pat3", "patpass", "student",
					"Elvira", "Espindola", "30003 ", "Viadel Corso, 22");

			database.insertPatron(p1);
			p1.setId(database.getPatronID(p1));
			database.insertPatron(p2);
			p2.setId(database.getPatronID(p2));
			database.insertPatron(p3);
			p3.setId(database.getPatronID(p3));

			assertTrue(database.getLibrarianList().size() + database.getPatronList().size() == 4);

			database.close();
		}
	}

	@Test
		// Test case 2
	void test2_TC2() throws SQLException {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {
			Librarian librarian = database.getLibrarian(1);

			librarian.modifyDocumentCopies(b1.getID(), database,
					database.getBook(b1.getID()).getNumberOfCopies() - 2);
			librarian.modifyDocumentCopies(b3.getID(), database,
					database.getBook(b3.getID()).getNumberOfCopies() - 1);

			assertTrue(librarian.getNumberOfDocument(database) == 5);

			librarian.deletePatron(p2.getId(), database);

			assertTrue(database.getLibrarianList().size() + database.getPatronList().size() == 3);

			database.close();
		}
	}

	@Test
		// Test case 3
	void test3_TC3() throws SQLException, ParseException {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {
			assertEquals(p1.getName(), "Sergey");
			assertEquals(p1.getSurname(), "Afonso");
			assertEquals(p1.getAddress(), "Via Margutta, 3");
			assertEquals(p1.getPhoneNumber(), "30001");
			assertEquals(p1.getStatus(), "faculty");

			assertEquals(p3.getName(), "Elvira");
			assertEquals(p3.getSurname(), "Espindola");
			assertEquals(p3.getAddress(), "Viadel Corso, 22");
			assertEquals(p3.getPhoneNumber(), "30003 ");
			assertEquals(p3.getStatus(), "student");

			database.close();
		}
	}

	@Test
		// Test case 7
	void test4_TC7() throws SQLException, ParseException {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {
			p1.takeBook(b1.getID(), database);
			p1.takeBook(b2.getID(), database);
			p1.takeBook(b3.getID(), database);
			p1.takeAV(av1.getID(), database);

			p2.takeBook(b1.getID(), database);
			p2.takeBook(b2.getID(), database);
			p2.takeAV(av2.getID(), database);

			assertEquals(p1.getName(), "Sergey");
			assertEquals(p1.getSurname(), "Afonso");
			assertEquals(p1.getAddress(), "Via Margutta, 3");
			assertEquals(p1.getPhoneNumber(), "30001");
			assertEquals(p1.getStatus(), "faculty");

			assertEquals(p2.getName(), "Nadia");
			assertEquals(p2.getSurname(), "Teixeira");
			assertEquals(p2.getAddress(), "Via Sacra, 13");
			assertEquals(p2.getPhoneNumber(), "30002");
			assertEquals(p2.getStatus(), "student");

			List<Debt> p1Debts = database.getDebtsForUser(p1.getId());
			List<Debt> p2Debts = database.getDebtsForUser(p2.getId());

			assertEquals(p1Debts.get(0).getDocumentId(), b1.getID());
			assertEquals(p1Debts.get(0).daysLeft(), 28);
			assertEquals(p1Debts.get(1).getDocumentId(), b2.getID());
			assertEquals(p1Debts.get(1).daysLeft(), 28);
			assertEquals(p1Debts.get(2).getDocumentId(), b3.getID());
			assertEquals(p1Debts.get(2).daysLeft(), 28);
			assertEquals(p1Debts.get(3).getDocumentId(), av1.getID());
			assertEquals(p1Debts.get(3).daysLeft(), 14);

			assertEquals(p2Debts.get(0).getDocumentId(), b1.getID());
			assertEquals(p2Debts.get(0).daysLeft(), 21);
			assertEquals(p2Debts.get(1).getDocumentId(), b2.getID());
			assertEquals(p2Debts.get(1).daysLeft(), 21);
			assertEquals(p2Debts.get(2).getDocumentId(), av2.getID());
			assertEquals(p2Debts.get(2).daysLeft(), 14);

			database.close();
		}
	}

	@Test
		// Test case 4
	void test5_TC4() throws SQLException, ParseException {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {
			try {
				database.getPatron(p2.getId());
			} catch (NoSuchElementException e) {
				System.out.println("No information for p2.");
			}

			assertEquals(p3.getName(), "Elvira");
			assertEquals(p3.getSurname(), "Espindola");
			assertEquals(p3.getAddress(), "Viadel Corso, 22");
			assertEquals(p3.getPhoneNumber(), "30003 ");
			assertEquals(p3.getStatus(), "student");

			database.close();
		}
	}

	@Test
		// Test case 5
	void test6_TC5() {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {
			assertFalse(p2.canRequestBook(b1.getID(), database));
			database.close();
		}
	}

	@Test
		// Test case 6
	void test7_TC6() throws SQLException, ParseException {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {
			p1.takeBook(b1.getID(), database);
			p3.takeBook(b1.getID(), database);
			p1.takeBook(b2.getID(), database);

			assertEquals(p1.getName(), "Sergey");
			assertEquals(p1.getSurname(), "Afonso");
			assertEquals(p1.getAddress(), "Via Margutta, 3");
			assertEquals(p1.getPhoneNumber(), "30001");
			assertEquals(p1.getStatus(), "faculty");

			assertEquals(p3.getName(), "Elvira");
			assertEquals(p3.getSurname(), "Espindola");
			assertEquals(p3.getAddress(), "Viadel Corso, 22");
			assertEquals(p3.getPhoneNumber(), "30003 ");
			assertEquals(p3.getStatus(), "student");

			List<Debt> p1Debts = database.getDebtsForUser(p1.getId());
			List<Debt> p3Debts = database.getDebtsForUser(p3.getId());

			assertEquals(p1Debts.get(0).getDocumentId(), b1.getID());
			assertEquals(p1Debts.get(0).daysLeft(), 28);

			assertEquals(p3Debts.get(0).getDocumentId(), b2.getID());
			assertEquals(p1Debts.get(0).daysLeft(), 21);

			database.close();
		}
	}

	@Test
		// Test case 8
	void test8_TC8() {
		if (!database.isConnected()) database.connect();
		if (database.isConnected()) {

			database.close();
		}
	}
}
