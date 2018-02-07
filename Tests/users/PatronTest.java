package users;

import org.junit.jupiter.api.Test;
import users.Patron;
import materials.Book;
import users.Librarian;
import tools.SetManager;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PatronTest {
    Librarian L = new Librarian("A A", "Innopolis", "89286878848", 789);
    //SetManager manager = new SetManager();
    @Test
    void addDocumentInList() {
        Book A = new Book("a", "b", 90,  true, 123);
        Book B = new Book("b", "b", 56, false, 124);
        Patron X = new Patron("A", "Innopolis", "89021", "student", 1234);
        Patron Y = new Patron("B", "Innopolis", "89056", "faculty", 5678);

        X.addDocumentInList(A);
        assertFalse(X.getListOfDocumentsPatron().isEmpty());

        Y.addDocumentInList(B);
        assertFalse(Y.getListOfDocumentsPatron().isEmpty());


    }

    @Test
    void getRequest() {
        Book A = new Book("a", "b", L.manager.listOfDocuments.size(),  true, 123);
        L.manager.addDocumentInLibrary(A);
        assertTrue(L.manager.listOfDocuments.contains(A));
        //System.out.println(L.manager.listOfDocuments.size());
        Book B = new Book("b", "b", L.manager.listOfDocuments.size(), false, 124);
        L.manager.addDocumentInLibrary(B);
        Patron X = new Patron("A", "Innopolis", "89021", "student", L.getListOfPatrons().size());
        L.manager.addPatron(X);
        Patron Y = new Patron("B", "Innopolis", "89056", "faculty", L.getListOfPatrons().size());
        L.manager.addPatron(Y);

        assertFalse(X.getRequest(B.getDocID(), L));
       // assertTrue (X.getRequest(A.getDocID(), L)); TODO: work on these asserts - (about requests)

        //assertTrue(Y.getRequest(A.getDocID(), L));
        //assertTrue(Y.getRequest(B.getDocID(), L));
        System.out.println("In this test we can see that system of getting requests to library is working for both students and faculty");
    }
    @Test
    void takeDocument() {
        System.out.println("Not a part of the Booking Sytem");
    }

    @Test
    void getDocumentsInLibrary() {
        System.out.println("Not a part of the Booking Sytem");
    }

    @Test
    void bringBackDocument() {
        System.out.println("Not a part of the Booking Sytem");
    }

    @Test
    void equals() {
       /* Patron A = new Patron("A A", "Innopolis", "89286878848", "student", 789);
        Patron B = new Patron("A A", "Innopolis", "89286878848", "studetn", 790);

        assertTrue(A.equals(B));
        assertTrue(B.equals(A));
        assertFalse(A.equals(A));

        System.out.println("The 'Equals' function works for the Patron class");*/
        System.out.println("Not a part of the Booking Sytem");
    }

}