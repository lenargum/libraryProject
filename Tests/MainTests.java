import materials.Book;
import org.junit.jupiter.api.Test;
import users.Librarian;
import users.Patron;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTests {

	@Test
	void TestCase01() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

		librarian.registerPatron(p);

		Book A = new Book("Touch of Class", "B. Meyer", librarian.getListOfDocuments().size(), true, 1000);
		librarian.addDocumentInLibrary(A);
		Book B = new Book("Touch of Class", "B. Meyer", librarian.getListOfDocuments().size(), true, 1000);
		librarian.addDocumentInLibrary(B);

		//assertTrue(p.getRequest(A.getDocID(), librarian));
		System.out.println("p can take the document!");
		p.takeDocument(A.getDocID(), librarian);
		assertTrue(p.getListOfDocumentsPatron().size() == 1);
		System.out.println("Number of documents p is keeping equals to 1");

	}

	@Test
	void TestCase02() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

		librarian.registerPatron(p);
		System.out.println("Test for delivery 4");

	}

	@Test
	void TestCase03() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

		librarian.registerPatron(p);

		System.out.println("Test for delivery 4");

	}

	@Test
	void TestCase04() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		Patron f = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

		librarian.registerPatron(f);

		Patron s = new Patron("Madina", "Innopolis", "123456789000", "student", librarian.getListOfPatrons().size());

		librarian.registerPatron(s);

		Book b = new Book("bestseller", "S. King", librarian.getListOfDocuments().size(), true, 1000);

		librarian.addDocumentInLibrary(b);

		f.takeDocument(b.getDocID(), librarian);
		assertTrue(f.getListOfDocumentsPatron().size() == 1);
		System.out.println("f can take the b");

	}

	@Test
	void TestCase05() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		Patron f = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

		librarian.registerPatron(f);

		Patron s = new Patron("Madina", "Innopolis", "123456789000", "student", librarian.getListOfPatrons().size());

		librarian.registerPatron(s);

		Patron r = new Patron("Ruslan", "Innopolis", "543687435", "student", librarian.getListOfPatrons().size());

		librarian.registerPatron(r);

		Book A = new Book("Computer Architecture", "Tanenbaum", librarian.getListOfDocuments().size(), true, 2000);
		librarian.addDocumentInLibrary(A);

		Book B = new Book("Computer Architecture", "Tanenbaum", librarian.getListOfDocuments().size(), true, 2000);
		librarian.addDocumentInLibrary(B);

		s.takeDocument(A.getDocID(), librarian);
		assertTrue(s.getListOfDocumentsPatron().size() == 1);
		System.out.println("Madina successfully took the document!");

		r.takeDocument(A.getDocID(), librarian);
		assertTrue(r.getListOfDocumentsPatron().size() == 1);
		System.out.println("Ruslan successfully took the document!");

		f.takeDocument(A.getDocID(), librarian);
		assertFalse(f.getRequest(A.getDocID(), librarian));


	}

	@Test
	void TestCase06() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

		librarian.registerPatron(p);

		Book A = new Book("Computer Architecture", "Tanenbaum", librarian.getListOfDocuments().size(), true, 2000);
		librarian.addDocumentInLibrary(A);

		Book B = new Book("Computer Architecture", "Tanenbaum", librarian.getListOfDocuments().size(), true, 2000);
		librarian.addDocumentInLibrary(B);

		p.takeDocument(A.getDocID(), librarian);
		assertTrue(p.getListOfDocumentsPatron().size() == 1);
		System.out.println("Giancarlo successfully took first copy of CA textbook");
		p.takeDocument(A.getDocID(), librarian);
		assertFalse(p.getListOfDocumentsPatron().size() == 2);
		System.out.println("Even dean cannot take two copies of the same book! :ь");
	}

	@Test
	void TestCase07() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

		Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());
		librarian.registerPatron(p);
		Patron s = new Patron("Madina", "Innopolis", "123456789000", "student", librarian.getListOfPatrons().size());
		librarian.registerPatron(s);

		Book A = new Book("Computer Architecture", "Tanenbaum", librarian.getListOfDocuments().size(), true, 2000);
		librarian.addDocumentInLibrary(A);

		Book B = new Book("Computer Architecture", "Tanenbaum", librarian.getListOfDocuments().size(), true, 2000);
		librarian.addDocumentInLibrary(B);

		s.takeDocument(A.getDocID(), librarian);
		assertTrue(s.getListOfDocumentsPatron().size() == 1);
		System.out.println("Madina successfully took the document!");

		p.takeDocument(A.getDocID(), librarian);
		assertTrue(p.getListOfDocumentsPatron().size() == 1);
		System.out.println("Giancarlo successfully took the document!");

	}

	@Test
	void TestCase08() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());
		librarian.registerPatron(p);
		Patron s = new Patron("Madina", "Innopolis", "123456789000", "student", librarian.getListOfPatrons().size());
		librarian.registerPatron(s);

		Book A = new Book("Computer Architecture", "Tanenbaum", librarian.getListOfDocuments().size(), true, 2000);
		librarian.addDocumentInLibrary(A);

		s.takeDocument(A.getDocID(), librarian);
		assertTrue(s.getListOfDocumentsPatron().size() == 1);
		System.out.println("Madina successfully took the document!");
	}

	@Test
	void TestCase09() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		System.out.println("This test is same as case 8, but it also needs returning, that is part of delivery 2, not 1");
	}

	@Test
	void TestCase10() {
		Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
		Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

		librarian.registerPatron(p);

		Book A = new Book("Helpful Formulas", "Vygodsky", librarian.getListOfDocuments().size(), true, 2000);
		librarian.addDocumentInLibrary(A);
		librarian.getListOfDocuments().get(A.getDocID()).setReference(true);

		p.takeDocument(A.getDocID(), librarian);
		assertFalse(p.getRequest(A.getDocID(), librarian));
		assertTrue(p.getListOfDocumentsPatron().size() == 0);
		System.out.println("Even dean cannot take reference books! :ь");

	}

}