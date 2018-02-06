package users;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibrarianTest {
    @Test
    void registerPatron() {
        System.out.println("Not a part of the Booking Sytem");
    }

    @Test
    void addDocumentInLibrary() {
        System.out.println("Not a part of the Booking Sytem");
    }

    @Test
    void getListOfPatrons() {
        System.out.println("Not a part of the Booking Sytem");
    }

    @Test
    void getListOfDocuments() {
        System.out.println("Not a part of the Booking Sytem");
    }

    @Test
    void equals() {
        Librarian A = new Librarian("A A", "Innopolis", "89286878848", 789);
        Librarian B = new Librarian("A A", "Innopolis", "89286878848", 790);

        assertTrue(A.equals(B));
        assertTrue(B.equals(A));
        assertFalse(A.equals(A));

        System.out.println("The 'Equals' function works for the Librarian class");
    }

}