package graphicalUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import materials.AudioVideoMaterial;
import materials.Book;
import materials.Document;
import users.Librarian;
import users.Patron;

public class VirtualServer {
	private Librarian librarian;
	private Patron patron;

	public VirtualServer() {
		librarian = new Librarian("MariaVanna", "1, Universitetskaya", "+79876543211", 1);
		librarian.getListOfPatrons().add(new Patron("Name", "1, Universitetskaya", "+79871234567", "STUDENT", 2));

		librarian.addDocumentInLibrary(new Book("Object-oriented Programming", "Bertrand Meyer", 1, true, 500));
		librarian.addDocumentInLibrary(new Book("Introduction to Algorithms", "Thomas Cormen", 2, true, 700));
		librarian.addDocumentInLibrary(new Book("50 shades of Gray", "E. L. James", 3, false, 100));
		librarian.addDocumentInLibrary(new AudioVideoMaterial("Audio 1", "Author", 4, 39.99f, true));
	}

	public boolean authorize(String login) {
		boolean found = false;
		for (Patron p : librarian.getListOfPatrons()) {
			if (p.getName().equals(login)) {
				found = true;
				patron = p;
				break;
			}
		}

		return found;
	}

	public Patron getPatron() {
		return patron;
	}

	public ObservableList<String> getBookTitles() {
		ObservableList<String> li = FXCollections.observableArrayList();
		for (Document doc : librarian.getListOfDocuments()) {
			li.add(doc.getTitle());
		}

		return li;
	}

	public boolean bookItem(String name, Patron pat) {
		boolean booked = false;
		Document doc = null;
		for (Document document : librarian.getListOfDocuments()) {
			if (document.getTitle().equals(name)) {
				doc = document;
			}
		}

		if (pat.getRequest(doc.getDocID(), librarian)) {
			booked = true;
		}
		if (booked) {
			pat.takeDocument(doc.getDocID(), librarian);
		}

		return booked;
	}
}
