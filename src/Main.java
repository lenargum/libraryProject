import materials.Document;
import users.Librarian;
import users.Patron;

public class Main {

    public static void main(String[] args) {
        Librarian librarian = new Librarian("Name", "Address", "589", 0);
        Document book = new Document("St.King", "It", 0, true, 456);
        librarian.addDocumentInLibrary(book);
        librarian.createPatron("Patron", "Address1", "5892", "student");
        boolean a = librarian.getListOfPatrons().get(0).getRequest(0, librarian);

        librarian.getListOfPatrons().get(0).takeDocument(0, librarian);
        System.out.println(a);

//        System.out.println(librarian.getListOfDocuments().get(0).getTitle());
    }
}
