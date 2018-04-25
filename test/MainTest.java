import documents.AudioVideoMaterial;
import documents.Book;
import org.junit.jupiter.api.Test;
import tools.Database;
import tools.Debt;
import users.*;

import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
	private Book d1, d2, b1, b2, b3;
	private AudioVideoMaterial d3;
	private Patron p1, p2, p3, s, v;
	private Librarian librarian, l1, l2, l3;
	private Admin admin;
	private Database database = new Database();


	void initialState() {
		database.connect();
		database.clear();
		admin = new Admin("admin", "admin-pass", "Amy", "Pond", "12334567788", "Imaginarium");
		librarian = new Librarian("librarian", "lib-pass", "Maria", "Nikolaeva", "81234567890", "Universitetskaya street, 1");
		d1 = new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein", true, 3, false, 5000, "algorithms, programming", "MIT Press", 3, false);
		d2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm", true, 3, false, 1700, "OOP, programming", "Addison-Wesley Professional", 1, false);
		d3 = new AudioVideoMaterial("Null References: The Billion Dollar Mistake", "Tony Hoare", true, 2, false, 700, "programming");
		p1 = new Professor("patron1", "patpass", "Sergey", "Afonso", "30001", "Via Margutta, 3");
		p2 = new Professor("patron2", "patpass", "Nadia", "Teixeira", "30002", "Via Scara, 13");
		p3 = new Professor("patron3", "patpass", "Elvira", "Espindola", "30003", "Via del Corso, 22");
		s = new Student("patron4", "patpass", "Andrey", "Velo", "30004", "Avenida Mazatlan, 350");
		v = new VisitingProfessor("patron5", "patpass", "Veronika", "Rama", "30005", "Stret Atocha, 27");

		database.insertAdmin(admin);

		admin.addLibrarian(librarian, database);
		admin.setDeletePrivilegeLibrarian(librarian, database);


		//System.out.println(librarian.getPrivilege());
		librarian.addBook(d1, database);
		//System.out.println(librarian.getPrivilege());
		librarian.addBook(d2, database);
		//System.out.println(librarian.getPrivilege());
		librarian.addAV(d3, database);
		//System.out.println(librarian.getPrivilege());
		librarian.registerPatron(p1, database);
		//System.out.println(librarian.getPrivilege());
		librarian.registerPatron(p2, database);
		//System.out.println(librarian.getPrivilege());
		librarian.registerPatron(p3, database);
		//System.out.println(librarian.getPrivilege());
		librarian.registerPatron(s, database);
		//System.out.println(librarian.getPrivilege());
		librarian.registerPatron(v, database);
		//System.out.println(librarian.getPrivilege());


		database.close();
	}

	void initialStateTC1() {
		database.connect();
		p1.makeRequest(d1.getID(), database);
		librarian.submitRequest(database.getRequest(p1.getId(), d1.getID()), database);
		p1.makeRequest(d2.getID(), database);
		librarian.submitRequest(database.getRequest(p1.getId(), d2.getID()), database);
		database.close();
	}

	void initialStateTC2() {
		database.connect();
		p1.makeRequest(d1.getID(), database);
		librarian.submitRequest(database.getRequest(p1.getId(), d1.getID()), database);
		p1.makeRequest(d2.getID(), database);
		librarian.submitRequest(database.getRequest(p1.getId(), d2.getID()), database);

		s.makeRequest(d1.getID(), database);
		librarian.submitRequest(database.getRequest(s.getId(), d1.getID()), database);
		s.makeRequest(d2.getID(), database);
		librarian.submitRequest(database.getRequest(s.getId(), d2.getID()), database);

		v.makeRequest(d1.getID(), database);
		librarian.submitRequest(database.getRequest(v.getId(), d1.getID()), database);
		v.makeRequest(d2.getID(), database);
		librarian.submitRequest(database.getRequest(v.getId(), d2.getID()), database);

		database.close();
	}

	void initialStateTC34() {
		database.connect();
		p1.makeRequest(d1.getID(), database);
		librarian.submitRequest(database.getRequest(p1.getId(), d1.getID()), database);

		s.makeRequest(d3.getID(), database);
		librarian.submitRequest(database.getRequest(s.getId(), d3.getID()), database);

		v.makeRequest(d3.getID(), database);
		librarian.submitRequest(database.getRequest(v.getId(), d3.getID()), database);
		database.close();
	}

	void initialStateTC10() {
		database.connect();
		database.connect();
		p1.makeRequest(d1.getID(), database);
		librarian.submitRequest(database.getRequest(p1.getId(), d1.getID()), database);
		v.makeRequest(d1.getID(), database);
		librarian.submitRequest(database.getRequest(v.getId(), d1.getID()), database);

		p1.sendRenewRequest(database.findDebtID(p1.getId(), d1.getID()), database);
		librarian.confirmRenew(database.getRequest(p1.getId(), d1.getID()), database);
		v.sendRenewRequest(database.findDebtID(v.getId(), d1.getID()), database);
		librarian.confirmRenew(database.getRequest(v.getId(), d1.getID()), database);
		database.close();
		database.close();
	}

	@Test
	void TestCase01() {
		System.out.println("Test Case 1");
		initialState();
		initialStateTC1();
		database.connect();


		int debtId = database.findDebtID(p1.getId(), d2.getID());

		librarian.confirmReturn(debtId, database);

		debtId = database.findDebtID(p1.getId(), d1.getID());
		Debt debt = database.getDebt(debtId);
		debt.countFee(database);

		System.out.println(database.getDebtsForUser(p1.getId()));

		assertEquals(1, database.getDebtsForUser(p1.getId()).size()); //we check that p1 has only one document
		assertTrue(debt.daysLeft() > 0);//we check if there is no overdue
		assertEquals(0, debt.getFee());  //so patron don't need to pay

		database.close();
	}

	@Test
	void TestCase02() {
		System.out.println("Test Case 2");
		initialState();
		initialStateTC2();
		database.connect();

		System.out.println(database.getDebtsList().toString());
		assertEquals(2, database.getDebtsForUser(p1.getId()).size());
		Debt debt1 = database.getDebt(database.findDebtID(p1.getId(), d1.getID()));
		Debt debt2 = database.getDebt(database.findDebtID(p1.getId(), d2.getID()));
		debt1.countFee(database);
		debt2.countFee(database);
		assertEquals(27, debt1.daysLeft());
		assertEquals(27, debt2.daysLeft());
		assertEquals(0, debt1.getFee());
		assertEquals(0, debt2.getFee());


		assertEquals(2, database.getDebtsForUser(s.getId()).size());
		debt1 = database.getDebt(database.findDebtID(s.getId(), d1.getID()));
		debt2 = database.getDebt(database.findDebtID(s.getId(), d2.getID()));
		debt1.countFee(database);
		debt2.countFee(database);
		assertEquals(20, debt1.daysLeft());
		assertEquals(20, debt2.daysLeft());
		assertEquals(0, debt1.getFee());
		assertEquals(0, debt2.getFee());


		assertEquals(2, database.getDebtsForUser(v.getId()).size());
		debt1 = database.getDebt(database.findDebtID(v.getId(), d1.getID()));
		debt2 = database.getDebt(database.findDebtID(v.getId(), d2.getID()));
		debt1.countFee(database);
		debt2.countFee(database);
		assertEquals(6, debt1.daysLeft());
		assertEquals(6, debt2.daysLeft());
		assertEquals(0, debt1.getFee());
		assertEquals(0, debt2.getFee());

		database.close();
	}

	@Test
	void TestCase03() throws SQLException, ParseException {
		System.out.println("Test Case 3");
		initialState();
		initialStateTC34();
		database.connect();

		p1.sendRenewRequest(database.findDebtID(p1.getId(), d1.getID()), database);
		librarian.confirmRenew(database.getRequest(p1.getId(), d1.getID()), database);

		s.sendRenewRequest(database.findDebtID(s.getId(), d3.getID()), database);
		librarian.confirmRenew(database.getRequest(s.getId(), d3.getID()), database);

		v.sendRenewRequest(database.findDebtID(v.getId(), d3.getID()), database);
		librarian.confirmRenew(database.getRequest(v.getId(), d3.getID()), database);

		System.out.println(database.getDebtsForUser(p1.getId()));
		assertEquals(1, database.getDebtsForUser(p1.getId()).size());
		Debt debt1 = database.getDebt(database.findDebtID(p1.getId(), d1.getID()));
		debt1.countFee(database);
		assertEquals(27, debt1.daysLeft());
		assertEquals(0, debt1.getFee());

		System.out.println(database.getDebtsForUser(s.getId()));
		assertEquals(1, database.getDebtsForUser(s.getId()).size());
		debt1 = database.getDebt(database.findDebtID(s.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());

		System.out.println(database.getDebtsForUser(v.getId()));
		assertEquals(1, database.getDebtsForUser(v.getId()).size());
		debt1 = database.getDebt(database.findDebtID(v.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(6, debt1.daysLeft());
		assertEquals(0, debt1.getFee());


		database.close();
	}

	@Test
	void TestCase04() throws SQLException, ParseException {
		System.out.println("Test Case 4");
		initialState();
		initialStateTC34();
		database.connect();

		p1.sendRenewRequest(database.findDebtID(p1.getId(), d1.getID()), database);
		librarian.confirmRenew(database.getRequest(p1.getId(), d1.getID()), database);

		p3.makeRequest(d3.getID(), database);
		s.sendRenewRequest(database.findDebtID(s.getId(), d3.getID()), database);

		v.sendRenewRequest(database.findDebtID(v.getId(), d3.getID()), database);

		System.out.println(database.getDebtsForUser(p1.getId()));
		assertEquals(1, database.getDebtsForUser(p1.getId()).size());
		Debt debt1 = database.getDebt(database.findDebtID(p1.getId(), d1.getID()));
		debt1.countFee(database);
		assertEquals(27, debt1.daysLeft());
		assertEquals(0, debt1.getFee());

		System.out.println(database.getDebtsForUser(s.getId()));
		assertEquals(1, database.getDebtsForUser(s.getId()).size());
		debt1 = database.getDebt(database.findDebtID(s.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());

		System.out.println(database.getDebtsForUser(v.getId()));
		assertEquals(1, database.getDebtsForUser(v.getId()).size());
		debt1 = database.getDebt(database.findDebtID(v.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(6, debt1.daysLeft());
		assertEquals(0, debt1.getFee());

		database.close();
	}

	@Test
	void TestCase05() throws SQLException, ParseException {
		System.out.println("Test Case 5");
		initialState();
		database.connect();

		p1.makeRequest(d3.getID(), database);
		s.makeRequest(d3.getID(), database);
		v.makeRequest(d3.getID(), database);

		librarian.submitRequest(database.getRequest(s.getId(), d3.getID()), database);
		librarian.submitRequest(database.getRequest(p1.getId(), d3.getID()), database);

		System.out.println(database.getDebtsForUser(p1.getId()));
		assertEquals(1, database.getDebtsForUser(p1.getId()).size());
		Debt debt1 = database.getDebt(database.findDebtID(p1.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());

		System.out.println(database.getDebtsForUser(s.getId()));
		assertEquals(1, database.getDebtsForUser(s.getId()).size());
		debt1 = database.getDebt(database.findDebtID(s.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());

		System.out.println(database.getDebtsForUser(v.getId()));
		assertEquals(0, database.getDebtsForUser(v.getId()).size());

		assertEquals(1, database.getRequests().size());

		database.close();
	}

	@Test
	void TestCase06() throws SQLException, ParseException {
		System.out.println("Test Case 6");
		initialState();
		database.connect();

		p1.makeRequest(d3.getID(), database);
		p2.makeRequest(d3.getID(), database);
		s.makeRequest(d3.getID(), database);
		v.makeRequest(d3.getID(), database);
		p3.makeRequest(d3.getID(), database);

		librarian.submitRequest(database.getRequest(p1.getId(), d3.getID()), database);
		librarian.submitRequest(database.getRequest(p2.getId(), d3.getID()), database);

		System.out.println(database.getRequests());
		assertEquals(2, database.getDebtsList().size());
		assertEquals(3, database.getRequests().size());
		assertEquals(1, database.getDebtsForUser(p1.getId()).size());
		Debt debt1 = database.getDebt(database.findDebtID(p1.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());
		assertEquals(1, database.getDebtsForUser(p2.getId()).size());
		debt1 = database.getDebt(database.findDebtID(p2.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());

		assertEquals(3, database.getRequests().size());

		database.close();
	}

	@Test
	void TestCase07() throws SQLException, ParseException {
		System.out.println("Test Case 7");
		initialState();
		TestCase06();
		database.connect();

		librarian.makeOutstandingRequest(database.getRequest(s.getId(), d3.getID()), database);
		assertEquals(database.getRequestsForDocument(d3.getID()).size(), 0);

		assertEquals(5, database.getNotificationsList().size());

		database.close();
	}

	@Test
	void TestCase08() throws SQLException, ParseException {
		System.out.println("Test Case 8");
		initialState();
		TestCase06();
		database.connect();

		librarian.confirmReturn(database.findDebtID(p2.getId(), d3.getID()), database);
		assertEquals(1, database.getDebtsList().size());
		assertEquals(3, database.getRequests().size());
		assertEquals(1, database.getDebtsForUser(p1.getId()).size());
		Debt debt1 = database.getDebt(database.findDebtID(p1.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());
		assertEquals(0, database.getDebtsForUser(p2.getId()).size());

		librarian.setAvailability(d3.getID(), database);
		assertEquals(1, database.getNotificationsList().size());
		database.close();
	}

	@Test
	void TestCase09() throws SQLException, ParseException {
		System.out.println("Test Case 9");
		initialState();
		TestCase06();
		database.connect();

		p1.sendRenewRequest(database.findDebtID(p1.getId(), d3.getID()), database);
		assertEquals(2, database.getDebtsList().size());
		assertEquals(3, database.getRequests().size());
		assertEquals(1, database.getDebtsForUser(p1.getId()).size());
		Debt debt1 = database.getDebt(database.findDebtID(p1.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());
		assertEquals(1, database.getDebtsForUser(p2.getId()).size());
		debt1 = database.getDebt(database.findDebtID(p2.getId(), d3.getID()));
		debt1.countFee(database);
		assertEquals(13, debt1.daysLeft());
		assertEquals(0, debt1.getFee());
		database.close();
	}

	@Test
	void TestCase10() throws SQLException, ParseException {
		System.out.println("Test Case 10");
		initialState();
		initialStateTC10();
		database.connect();
		p1.sendRenewRequest(database.findDebtID(p1.getId(), d1.getID()), database);

		v.sendRenewRequest(database.findDebtID(v.getId(), d1.getID()), database);
		librarian.confirmRenew(database.getRequest(v.getId(), d1.getID()), database);

		assertEquals(2, database.getDebtsList().size());
		assertEquals(1, database.getDebtsForUser(p1.getId()).size());
		Debt debt1 = database.getDebt(database.findDebtID(p1.getId(), d1.getID()));
		debt1.countFee(database);
		assertEquals(27, debt1.daysLeft());
		assertEquals(0, debt1.getFee());
		assertEquals(1, database.getDebtsForUser(v.getId()).size());
		debt1 = database.getDebt(database.findDebtID(v.getId(), d1.getID()));
		debt1.countFee(database);
		assertEquals(6, debt1.daysLeft());
		assertEquals(0, debt1.getFee());
		database.close();
	}


	void initialStateDelFour() {
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
	void TestCase201() {
		initialStateDelFour();
		database.connect();

		Admin falseAdmin = new Admin("false", "false", "NOT", "ADMIN", "00000000000000", "MyImaginarium");
		database.insertAdmin(falseAdmin);
		assertEquals(1, database.getUsers().size());
		database.close();
	}

	@Test
	void TestCase202() {
		initialStateDelFour();
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
	void TestCase203() {
		initialStateDelFour();
		TestCase202();
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
	void TestCase204() {
		initialStateDelFour();
		TestCase202();
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
	void TestCase205() {
		initialStateDelFour();
		TestCase204();
		database.connect();

		l3.modifyDocumentCopies(b1.getID(), database, b1.getNumberOfCopies() - 1);
		b1.setNumberOfCopies(database.getDocument(b1.getID()).getNumberOfCopies());
		assertEquals(2, b1.getNumberOfCopies());
		database.close();
	}

	@Test
	void TestCase206() {
		initialStateDelFour();
		TestCase204();
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
	void TestCase207() {
		initialStateDelFour();
		TestCase204();
		database.connect();

		p1.makeRequest(b3.getID(), database);
		p2.makeRequest(b3.getID(), database);
		p3.makeRequest(b3.getID(), database);
		s.makeRequest(b3.getID(), database);
		v.makeRequest(b3.getID(), database);

		l3.makeOutstandingRequest(database.getRequest(p1.getId(), b3.getID()), database);

		database.getLog();
		assertEquals(5, database.getNotificationsList().size());
		assertEquals(0, database.getRequests().size());

		database.close();
	}

	@Test
	void TestCase208() {
		initialStateDelFour();
		TestCase206();
		database.connect();


		database.close();
	}

	@Test
	void TestCase209() {
		initialStateDelFour();
		TestCase207();
		database.connect();


		database.close();
	}

	@Test
	void TestCase210() {
		initialStateDelFour();
		TestCase204();
		database.connect();


		database.close();
	}

	@Test
	void TestCase211() {
		initialStateDelFour();
		TestCase204();
		database.connect();


		database.close();
	}

	@Test
	void TestCase212() {
		initialStateDelFour();
		TestCase204();
		database.connect();


		database.close();
	}

	@Test
	void TestCase213() {
		initialStateDelFour();
		TestCase204();
		database.connect();


		database.close();
	}

	@Test
	void TestCase214() {
		initialStateDelFour();
		TestCase204();
		database.connect();


		database.close();
	}
}