import materials.Document;
import users.Librarian;

public class Main {

    public static void main(String[] args) {
        Librarian librarian = new Librarian("Name", "Address", "589", 0);
        Document book = new Document("It", "St.King", 1, true, 456);

        librarian.createPatron("Patron", "Address1", "5892", "student");

    }
}
