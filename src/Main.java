import materials.Book;
import materials.Document;
import users.Librarian;
import users.LibrarianInterface;

public class Main {

    public static void main(String[] args) {
        LibrarianInterface librarian = new Librarian("A", "Adrs", "8999", 0);
        librarian.registerMaterial(new Book("A", "tLoR", 123,  true, 134));
    }
}
