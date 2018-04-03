import documents.*;
import org.junit.jupiter.api.Test;
import tools.*;
import users.*;


import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private Book d1, d2;
    private AudioVideoMaterial av1, av2, d3;
    private Patron p1, p2, p3, s, v;
    private Librarian librarian;
    private Database database = new Database();

    void initialState() throws SQLException {
        database.connect();
        database.clear();
        librarian = new Librarian("librarian", "lib-pass", "Maria", "Nikolaeva", "81234567890", "Universitetskaya street, 1");
        d1 = new Book("Introduction to Algorithms", "Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein", true, 3, false, 5000, "algorithms, programming", "MIT Press", 3, true);
        d2 = new Book("Design Patterns: Elements of Reusable Object-Oriented Software", "Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm", true, 3, false, 1700, "OOP, programming", "Addison-Wesley Professional", 1, true);
        d3 = new AudioVideoMaterial("Null References: The Billion Dollar Mistake", "Tony Hoare", true, 2, false, 700, "programming");
        p1 = new Patron("patron1", "patpass", "professor", "Sergey", "Afonso", "30001", "Via Margutta, 3");
        p2 = new Patron("patron2", "patpass", "professor", "Nadia", "Teixeira", "30002", "Via Scara, 13");
        p3 = new Patron("patron3", "patpass", "professor", "Elvira", "Espindola", "30003", "Via del Corso, 22");
        s = new Patron("patron4", "patpass", "student", "Andrey", "Velo", "30004", "Avenida Mazatlan, 350");
        v = new Patron("patron5", "patpass", "vp", "Veronika", "Rama", "30005", "Stret Atocha, 27");
        database.insertLibrarian(librarian);
        database.insertAV(d3);
        database.insertBook(d1);
        database.insertBook(d2);
        database.insertPatron(p1);
        database.insertPatron(p2);
        database.insertPatron(p3);
        database.insertPatron(s);
        database.insertPatron(v);

        database.close();
    }

    void initialStateTC1()throws SQLException{
        database.connect();
        p1.makeRequest(d1.getID(), database);
        database.close();
    }

    void initialStateTC2()throws SQLException{
        database.connect();
        p1.makeRequest(d1.getID(), database);
        p1.makeRequest(d2.getID(), database);

        s.makeRequest(d1.getID(), database);
        s.makeRequest(d2.getID(), database);

        v.makeRequest(d1.getID(), database);
        v.makeRequest(d2.getID(), database);
        database.close();
    }

    void initialStateTC34()throws SQLException{
        database.connect();
        p1.makeRequest(d1.getID(), database);

        s.makeRequest(d2.getID(), database);

        v.makeRequest(d2.getID(), database);
        database.close();
    }

    void initialStateTC10()throws SQLException{
        database.connect();
        database.connect();
        p1.makeRequest(d1.getID(), database);

        v.makeRequest(d1.getID(), database);
        database.close();
        database.close();
    }

    @Test
    void TestCase01() throws SQLException{
        System.out.println("Test Case 1");
        initialState();
        initialStateTC1();
        database.connect();


        database.close();
    }

    @Test
    void TestCase02()throws SQLException{
        System.out.println("Test Case 2");
        initialState();
        initialStateTC2();
        database.connect();


        database.close();
    }

    @Test
    void TestCase03()throws SQLException{
        System.out.println("Test Case 3");
        initialState();
        initialStateTC34();
        database.connect();


        database.close();
    }

    @Test
    void TestCase04()throws SQLException{
        System.out.println("Test Case 4");
        initialState();
        initialStateTC34();
        database.connect();


        database.close();
    }

    @Test
    void TestCase05()throws SQLException{
        System.out.println("Test Case 5");
        initialState();
        database.connect();


        database.close();
    }

    @Test
    void TestCase06() throws SQLException, ParseException {
        System.out.println("Test Case 6");
        initialState();
        database.connect();

        p1.makeRequest(d3.getID(), database);
        p2.makeRequest(d3.getID(), database);
        p3.makeRequest(d3.getID(), database);
        s.makeRequest(d3.getID(), database);
        v.makeRequest(d3.getID(), database);

        System.out.println(database.getRequests());

        database.close();
    }

    @Test
    void TestCase07()throws SQLException, ParseException{
        System.out.println("Test Case 7");
        initialState();
        TestCase06();
        database.connect();


        database.close();
    }

    @Test
    void TestCase08()throws SQLException, ParseException {
        System.out.println("Test Case 8");
        initialState();
        TestCase06();
        database.connect();


        database.close();
    }

    @Test
    void TestCase09()throws SQLException, ParseException{
        System.out.println("Test Case 9");
        initialState();
        TestCase06();
        database.connect();


        database.close();
    }

    @Test
    void TestCase10()throws SQLException{
        System.out.println("Test Case 10");
        initialState();
        initialStateTC10();
        database.connect();


        database.close();
    }
}