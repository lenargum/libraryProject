import materials.Document;
import users.Librarian;

public class Main {

    public static void main(String[] args) {
        Librarian librarian = new Librarian("Name", "Address", "589", 0);
        Document book = new Document("St.King", "It", 0, true, 456);
        Document book1 = new Document("Cormen", "Algorithm", 1, true, 589);
        librarian.addDocumentInLibrary(book);
        librarian.addDocumentInLibrary(book1);
        librarian.createPatron("Patron", "Address1", "5892", "student");
        librarian.getListOfPatrons().get(0).takeDocument(0, librarian);
        librarian.getListOfPatrons().get(0).takeDocument(1, librarian);
        librarian.deleteDocumentFromLibrary(0);

        System.out.println(librarian.getListOfDocuments().get(1).isChecked());
        System.out.println(librarian.getListOfPatrons().get(0).getListOfDocumentsPatron());

//        System.out.println(librarian.getListOfDocuments().get(0).getTitle());
    }
}
