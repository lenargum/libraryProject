import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class MainTest{
    Database database = new Database();

    @Test
    void TestCase1() throws SQLException{
        database.connect();
        if(database.isConnected()) {
            Patron pat = new Patron("fghdsjk", "hglkjrhgkljdhfgjkhadl", "student", "AAAAAAA", "Kozlov", "47397027890", "1/2-133");
            Librarian lib = database.getLibrarian(2);
            int num = database.getBook(7).getNumberOfCopies();
            pat.takeBook(7, database);
            assertTrue(num == database.getBook(7).getNumberOfCopies());
            pat.returnBook(7, database);
            assertTrue(num == database.getBook(7).getNumberOfCopies());
            int id = pat.getId();
            lib.deletePatron(id, database);

            ///database.close();
        }
    }

    @Test
    void TestCase2()throws SQLException{
        database.connect();
        if(database.isConnected()) {
            Librarian librarian = database.getLibrarian(2);
            Patron patron = database.getPatron(5);

            //database.close();
        }
    }

   @Test
    void TestCase3()throws SQLException, ParseException{
        //database.connect();
        if(database.isConnected()) {
            Patron p1 = new Patron("gjdkg", "ghajdafgjk", "faculty", "Eugenii", "Zuev", "8932058391850398", "Inno");
            Patron p2 = database.getPatron(5);
            Librarian librarian = database.getLibrarian(2);
            librarian.RegisterPatron(p1, database);
            p1.takeBook(7, database);
            int a = database.getDebt(database.findDebtID(0, 7)).daysLeft();
            assertTrue(a == 28);
            p1.returnBook(7, database);
            librarian.deletePatron(p1.getId(), database);
           // database.close();
        }
    }

    @Test
    void TestCase4(){
        //database.connect();
        if(database.isConnected()) {

            //database.close();
        }
    }

    @Test
    void TestCase5(){
       // database.connect();
        if(database.isConnected()) {

            //database.close();
        }
    }

    @Test
    void TestCase6(){
       // database.connect();
        if(database.isConnected()) {

           // database.close();
        }
    }

    @Test
    void TestCase7(){
       // database.connect();
        if(database.isConnected()) {

            //database.close();
        }
    }
    @Test
    void TestCase8(){
        //database.connect();
        if(database.isConnected()) {

           // database.close();
        }
    }

    @Test
    void TestCase9(){
      //  database.connect();
        if(database.isConnected()) {

           // database.close();
        }
    }

    @Test
    void TestCase10(){
       // database.connect();
        if(database.isConnected()) {

            database.close();
        }
    }

}
