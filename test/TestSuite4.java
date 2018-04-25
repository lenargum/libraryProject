import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import org.junit.jupiter.api.Test;
import tools.Database;
import users.*;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestSuite4 {
	private Book b1, b2, b3;
	private AudioVideoMaterial d3;
	private Patron p1, p2, p3, s, v;
	private Librarian l1, l2, l3;
	private Admin admin;
	private Database database = new Database();

	private void initialState() {
		database.connect();
		database.clear();

		admin = new Admin("login", "password", "Ruslan", "Gumerov", "4357398758", "Universitetskaya 1");

		p1 = new Professor("patron1", "patpass", "Sergey", "Afonso", "30001", "Via Margutta, 3");
		p2 = new Professor("patron2", "patpass", "Nadia", "Teixeira", "30002", "Via Scara, 13");
		p3 = new Professor("patron3", "patpass", "Elvira", "Espindola", "30003", "Via del Corso, 22");
		s = new Student("patron4", "patpass", "Andrey", "Velo", "30004", "Avenida Mazatlan, 350");
		v = new VisitingProfessor("patron5", "patpass", "Veronika", "Rama", "30005", "Stret Atocha, 27");

		b1 = new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein", true, 0, false, 5000, "Algorithms, Data Structures, Complexity, Computational Theory", "MIT Press", 2009, true);
		b2 = new Book("Algorithms + Data Structures = Programs", "Niklaus Wirth", true, 0, false, 5000, "Algorithms, Data Structures, Search Algorithms, Pascal", "Prentice Hall PTR", 1978, true);
		b3 = new Book("The Art of Computer Programming", "Donald E. Knuth", true, 0, false, 5000, "Algorithms, Combinatorial Algorithms, Recursion", "Addison Wesley Longman Publishing Co., Inc", 1997, true);


		database.insertAdmin(admin);

		//database.insertPatron(p1);
		//database.insertPatron(p2);
		//database.insertPatron(p3);
		//database.insertPatron(s);
		//database.insertPatron(v);

		database.insertBook(b1);
		database.insertBook(b2);
		database.insertBook(b3);

		database.close();
	}

	@Test
	void testCase01() {
		initialState();
		database.connect();

		Admin falseAdmin = new Admin("false", "false", "NOT", "ADMIN", "00000000000000", "MyImaginarium");
		database.insertAdmin(falseAdmin);
		assertEquals(1, database.getUsers().size());
		database.close();
	}

	@Test
	void testCase02() {
		initialState();
		database.connect();

		l1 = new Librarian("loglib1", "passlib1", "Eugenia", "Rama", "27987328791", "Universitetskaya, 1");
		l2 = new Librarian("loglib2", "passlib2", "Luie", "Ramos", "57835937905", "Universitetskaya, 1");
		l3 = new Librarian("loglib3", "passlib3", "Ramon", "Valdez", "23049320849", "Universitetskaya, 1");


		admin.addLibrarian(l1, database);
		admin.addLibrarian(l2, database);
		admin.addLibrarian(l3, database);

		admin.setModifyPrivilegeLibrarian(l1, database);
		admin.setAddPrivilegeLibrarian(l2, database);
		admin.setDeletePrivilegeLibrarian(l3, database);

		assertEquals(3, database.getLibrarianList().size());

		database.close();
	}

	@Test
	void testCase03() {
		testCase02();
		database.connect();

		l1.modifyDocumentCopies(b1.getID(), database, 3);
		assertEquals(0, b1.getNumberOfCopies());

		l1.modifyDocumentCopies(b2.getID(), database, 3);
		assertEquals(0, b2.getNumberOfCopies());

		l1.modifyDocumentCopies(b3.getID(), database, 3);
		assertEquals(0, b3.getNumberOfCopies());

		database.close();

	}

	@Test
	void testCase04() {
		testCase02();
		database.connect();

		l2.modifyDocumentCopies(b1.getID(), database, 3);
		b1.setNumberOfCopies(database.getDocument(b1.getID()).getNumberOfCopies());
		assertEquals(3, b1.getNumberOfCopies());


		l2.modifyDocumentCopies(b2.getID(), database, 3);
		b2.setNumberOfCopies(database.getDocument(b2.getID()).getNumberOfCopies());
		assertEquals(3, b2.getNumberOfCopies());


		l2.modifyDocumentCopies(b3.getID(), database, 3);
		b3.setNumberOfCopies(database.getDocument(b3.getID()).getNumberOfCopies());
		assertEquals(3, b3.getNumberOfCopies());


		l2.registerPatron(p1, database);
		l2.registerPatron(p2, database);
		l2.registerPatron(p3, database);
		l2.registerPatron(s, database);
		l2.registerPatron(v, database);

		assertEquals(5, database.getPatronList().size());

		database.close();

	}

	@Test
	void testCase05() {
		testCase04();
		database.connect();

		l3.modifyDocumentCopies(b1.getID(), database, b1.getNumberOfCopies() - 1);
		b1.setNumberOfCopies(database.getDocument(b1.getID()).getNumberOfCopies());
		assertEquals(2, b1.getNumberOfCopies());
		database.close();
	}

	@Test
	void testCase06() {
		testCase04();
		database.connect();

		p1.makeRequest(b3.getID(), database);
		p2.makeRequest(b3.getID(), database);
		p3.makeRequest(b3.getID(), database);
		s.makeRequest(b3.getID(), database);
		v.makeRequest(b3.getID(), database);

		l1.makeOutstandingRequest(database.getRequest(p1.getId(), b3.getID()), database);

		assertEquals(0, database.getNotificationsList().size());
		assertEquals(5, database.getRequests().size());

		database.close();
	}

	@Test
	void testCase07() {
		testCase04();
		database.connect();

		p1.makeRequest(b3.getID(), database);
		p2.makeRequest(b3.getID(), database);
		p3.makeRequest(b3.getID(), database);
		s.makeRequest(b3.getID(), database);
		v.makeRequest(b3.getID(), database);

		l3.makeOutstandingRequest(database.getRequest(p1.getId(), b3.getID()), database);

		assertEquals(5, database.getNotificationsList().size());
		assertEquals(0, database.getRequests().size());

		database.close();
	}

	@Test
	void testCase08() {
		testCase06();
		database.connect();

		for(String i: database.getLog()){
			System.out.println(i);
		}

		database.close();
	}

	@Test
	void testCase09() {
		testCase07();
		database.connect();

		for(String i: database.getLog()){
			System.out.println(i);
		}

		database.close();
	}

	private HashSet<String> search(String... keywords) {
		HashSet<String> resultSet = new HashSet<>();

		database.connect();
		for (Document doc : database.getDocumentList()) {
			for (String keyword : keywords) {
				String docKeywords = (doc.getKeyWords() +
						doc.getTitle() + doc.getAuthors()).toLowerCase();
				if (docKeywords.contains(keyword.toLowerCase())) {
					resultSet.add(doc.getTitle());
				}
			}
		}
		database.close();

		return resultSet;
	}

	private HashSet<String> searchByTitle(String... keywords) {
		HashSet<String> resultSet = new HashSet<>();

		database.connect();
		for (Document doc : database.getDocumentList()) {
			for (String keyword : keywords) {
				String docKeywords = doc.getTitle().toLowerCase();
				if (docKeywords.contains(keyword.toLowerCase())) {
					resultSet.add(doc.getTitle());
				}
			}
		}
		database.close();

		return resultSet;
	}

	@Test
	void testCase10() {
		testCase04();
		HashSet<String> searchResult =
				search("to algorithms");

		assertEquals(1, searchResult.size());
		assertTrue(searchResult.contains("Introduction to Algorithms"));
	}

	@Test
	void testCase11() {
		testCase04();

		HashSet<String> searchResult =
				searchByTitle("algorithms");

		assertEquals(2, searchResult.size());
		assertTrue(searchResult.contains("Introduction to Algorithms"));
		assertTrue(searchResult.contains("Algorithms + Data Structures = Programs"));
	}

	@Test
	void testCase12() {
		testCase04();
		HashSet<String> searchResult =
				search("algorithms");

		assertEquals(3, searchResult.size());
		assertTrue(searchResult.contains("Introduction to Algorithms"));
		assertTrue(searchResult.contains("Algorithms + Data Structures = Programs"));
		assertTrue(searchResult.contains("The Art of Computer Programming"));
	}

	@Test
	void testCase13() {
		testCase04();
		HashSet<String> searchResult =
				search("algorithms programming");

		assertEquals(0, searchResult.size());
	}

	@Test
	void testCase14() {
		testCase04();
		HashSet<String> searchResult =
				search("algorithms", "programming");

		assertEquals(3, searchResult.size());
		assertTrue(searchResult.contains("Introduction to Algorithms"));
		assertTrue(searchResult.contains("Algorithms + Data Structures = Programs"));
		assertTrue(searchResult.contains("The Art of Computer Programming"));
	}
}