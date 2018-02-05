package tools;

import org.junit.jupiter.api.Test;

import materials.AudioVideoMaterial;
import materials.JournalArticle;
import materials.Book;

import users.Librarian;
import users.Patron;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Here were detected troubles(not in the booking system)
 */
class SetManagerTest {
    SetManager manager = new SetManager();

    @Test
    void Test001_addPatron() {

        Patron a = new Patron("Madina", "Innopolis", "45789972398", "student", 12345);
        Patron b = new Patron("Giancarlo", "Italia", "6574320975", "faculty", 789012);

        manager.addPatron(a);
        assertTrue(manager.listOfUsers.contains(a));
        System.out.println("Addeed Madina!");
        manager.addPatron(b);
        assertTrue(manager.listOfUsers.contains(b));
        System.out.println("Added Giancarlo!");

        System.out.println("The 'add Patron' function works in SetManager class for 'Patron' class");
    }

    @Test
    void Test002_deletePatron() {
        Patron a = new Patron("Madina", "Innopolis", "45789972398", "student", 1);
        Patron b = new Patron("Giancarlo", "Italia", "6574320975", "faculty", 2);

        manager.addPatron(a);

        manager.addPatron(b);


        manager.deletePatron(a.getId());
        assertFalse(manager.listOfUsers.contains(a));
        System.out.println("Deleted Madina!");
        manager.deletePatron(b.getId());
        assertFalse(manager.listOfUsers.contains(b));
        System.out.println("Deleted Giancarlo!");

        System.out.println("The 'Delete patron' function works in SetManager class for both 'Student' and 'Faculty' instances of 'Patron' class");
    }

    @Test
    void Test003_addDocumentInLibrary() {

        Book a = new Book("ABC0", "a, b, c, d", 1, true, 45);
        JournalArticle b = new JournalArticle("ABC1", "a, b, c, d", 2, 45, true);
        AudioVideoMaterial c = new AudioVideoMaterial("ABC2", "a, b, c, d", 3, 45, true);

        manager.addDocumentInLibrary(a);
        assertTrue(manager.listOfDocuments.contains(a));
        System.out.println("Book added to the Library!");
        manager.addDocumentInLibrary(b);
        assertTrue(manager.listOfDocuments.contains(b));
        System.out.println("Article added to the Library!");
        manager.addDocumentInLibrary(c);
        assertTrue(manager.listOfDocuments.contains(c));
        System.out.println("Material added to the Library!");

        manager.listOfDocuments.clear();
        System.out.println("'addDocumentTolibrary' function works in SetManager class for all the instances of 'Document' class");
    }

    /**
     * Troubles are detected!!!
     */
    @Test
    void Test_004deleteDocumentFromLibrary() {
        Book a = new Book("ABC0", "a, b, c, d", 1, true, 45);
        JournalArticle b = new JournalArticle("ABC1", "a, b, c, d", 2, 45, true);
        AudioVideoMaterial c = new AudioVideoMaterial("ABC2", "a, b, c, d", 3, 45, true);

        manager.addDocumentInLibrary(a);
        manager.addDocumentInLibrary(b);
        manager.addDocumentInLibrary(c);

        manager.deleteDocumentFromLibrary(a.getDocID() - 1);
        assertFalse(manager.listOfDocuments.contains(a));
        System.out.println("Book is deleted from the Library!");
        manager.deleteDocumentFromLibrary(b.getDocID() - 1);
        assertTrue(manager.listOfDocuments.contains(b));
        System.out.println("Article is deleted from the Library!");
        manager.deleteDocumentFromLibrary(c.getDocID() - 1);
        assertFalse(manager.listOfDocuments.contains(c));
        System.out.println("Material is deleted from the Library!");

        assertTrue(manager.listOfDocuments.size() == 0);

        System.out.println("deleteDocumentFromLibrary' function works in SetManager class for all the instances of 'Document' class");
    }

}