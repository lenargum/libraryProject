package tools;

import materials.AudioVideoMaterial;
import materials.Book;
import materials.JournalArticle;
import org.junit.jupiter.api.Test;
import users.Librarian;
import users.Patron;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TypetesterTest {
    @Test
    void TestGetType000() {
        Typetester t = new Typetester();
        Book a = new Book("gfdghsjkgh", "HJGH", 567, false, true, 1234);
        t.setType(a);
        assertEquals("book", t.getType());
        assertNotEquals("document", t.getType());
        System.out.println("Typtester for class 'Book' works!");
    }

    @Test
    void TestGetType001() {
        Typetester t = new Typetester();
        JournalArticle a = new JournalArticle("gfdghsjkgh", "HJGH", 567, 1234, false, true);
        t.setType(a);
        assertEquals("journal article", t.getType());
        assertNotEquals("document", t.getType());
        System.out.println("Typtester for class 'Journal Article' works!");
    }

    @Test
    void TestGetType002() {
        Typetester t = new Typetester();
        AudioVideoMaterial a = new AudioVideoMaterial("gfdghsjkgh", "HJGH", 567, 123, true, false);
        t.setType(a);
        assertEquals("audio-video material", t.getType());
        assertNotEquals("document", t.getType());
        System.out.println("Typtester for class 'Audio/Video material' works!");
    }

    @Test
    void TestGetType003() {
        Typetester t = new Typetester(), t2 = new Typetester();

        Patron a = new Patron("Madina", "Innopolis", "1234567879", "student", 67888);
        Patron b = new Patron("Giancarlo", "Innopolis", "1234567879", "faculty", 463786);
        t.setType(a);
        t2.setType(b);
        assertEquals("student", t.getType());
        assertEquals("faculty", t2.getType());

        assertNotEquals("faculty", t.getType());
        assertNotEquals("student", t2.getType());

        assertNotEquals(t.getType(), t2.getType());
        System.out.println("Typtester for class 'Patron' works!");
    }

    @Test
    void TestGetType004() {
        Typetester t = new Typetester(), t2 = new Typetester(), t3 = new Typetester();

        Librarian L = new Librarian("GreatLibrarian", "Innopolis", "89280666850", 66666);
        Patron a = new Patron("Madina", "Innopolis", "1234567879", "student", 67888);
        Patron b = new Patron("Giancarlo", "Innopolis", "1234567879", "faculty", 463786);
        t.setType(a);
        t2.setType(b);
        t3.setType(L);

        assertEquals("librarian", t3.getType());

        assertNotEquals("faculty", t3.getType());
        assertNotEquals("student", t3.getType());

        assertNotEquals(t.getType(), t2.getType());
        assertNotEquals(t.getType(), t3.getType());
        assertNotEquals(t2.getType(), t3.getType());

        System.out.println("Typtester for class 'Librarian' works!");

        System.out.println("5 tests are passed \n Class Typtester works!");
    }

}