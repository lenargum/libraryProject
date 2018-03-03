import materials.Book;
import materials.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneralTest {
	@Test
	void testCase1() {
		Librarian librarian = new Librarian("MariaVanna", "Innopolis", "+79871234567", -1);
		Patron patron = new Patron("Name", "Innopolis",
				"+79871234567", "student", librarian.getListOfPatrons().size());
		librarian.registerPatron(patron);
		librarian.addDocumentInLibrary(new Book("Object-oriented Programming", "Bertrand Meyer",
				librarian.getListOfDocuments().size(), true, 500));
		librarian.addDocumentInLibrary(new Book("Object-oriented Programming", "Bertrand Meyer",
				librarian.getListOfDocuments().size(), true, 500));


		checkout("Object-oriented Programming", patron, librarian);
		assertTrue(librarian.getListOfDocuments().size() == 2);
		assertTrue(patron.getListOfDocumentsPatron().size() == 1);
	}

	private void checkout(String title, Patron patron, Librarian librarian) {
		Document doc = findDoc(title, librarian);
		if (patron.getRequest(doc.getDocID(), librarian)) {
			patron.takeDocument(doc.getDocID(), librarian);
		}
	}

	Document findDoc(String title, Librarian librarian) {
		Document found = null;
		for (Document doc : librarian.getListOfDocuments()) {
			if (doc.getTitle().equals(title)) {
				found = doc;
				break;
			}
		}

		return found;
	}
}
