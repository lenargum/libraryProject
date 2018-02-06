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

        //assertTrue(p.getRequest(A.getDocID(), librarian));
        System.out.println("p can take the document!");
        p.takeDocument(A.getDocID(), librarian);
        assertTrue(p.getListOfDocumentsPatron().size() == 1);
        System.out.println("Number of documents p is keeping equals to 1");

    }

    @Test
    void TestCase02(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
        Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

        librarian.registerPatron(p);
        System.out.println("Test for delivery 4");

    }

    @Test
    void TestCase03(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
        Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

        librarian.registerPatron(p);

        System.out.println("Test for delivery 4");

    }

    @Test
    void TestCase04(){
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
    void TestCase05(){
        Librarian librarian = new Librarian("Tatyana", "Innopolis", "89185678909", 1);
        Patron f = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

        librarian.registerPatron(f);

        Patron s = new Patron("Madina", "Innopolis", "123456789000", "student", librarian.getListOfPatrons().size());

        librarian.registerPatron(s);

        Patron r = new Patron("Ruslan", "Innopolis", "543687435","student", librarian.getListOfPatrons().size());

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
        Patron p = new Patron("Giancarlo", "Innopolis", "123456789000", "faculty", librarian.getListOfPatrons().size());

        librarian.registerPatron(p);

    }

}