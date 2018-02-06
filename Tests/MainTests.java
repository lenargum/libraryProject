import static org.junit.jupiter.api.Assertions.*;
import materials.AudioVideoMaterial;
import materials.Book;
import materials.JournalArticle;
import org.junit.jupiter.api.Test;
import users.Librarian;
import users.Patron;
import tools.SetManager;

class MainTests {

    @Test
    void TestCase01(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
        Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

        librarian.registerPatron(p);

        Book A = new Book("Touch of Class", "B. Meyer", librarian.getListOfDocuments().size(), true,  1000);
        librarian.addDocumentInLibrary(A);
        Book B = new Book("Touch of Class", "B. Meyer", librarian.getListOfDocuments().size(), true,  1000);
        librarian.addDocumentInLibrary(B);

        assertTrue(p.getRequest(A.getDocID(), librarian));
        p.takeDocument(A.getDocID(), librarian);


    }

    @Test
    void TestCase02(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

    @Test
    void TestCase03(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

    @Test
    void TestCase04(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

    @Test
    void TestCase05(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

    @Test
    void TestCase06(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

    @Test
    void TestCase07(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

    @Test
    void TestCase08(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

    @Test
    void TestCase09(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

    @Test
    void TestCase10(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);

    }

}