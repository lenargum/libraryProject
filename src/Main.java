import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        //example of database usage
        Database db = new Database();
        db.connect();
        if (db.isConnected()) {
            //body of database interaction
            db.soutDocs();
            System.out.println(db.getPatronList());
            System.out.println(db.getLibrarianList());
            //db.insertLibrarian(librarian);

            Librarian librarian = db.getLibrarian(0);
            Patron patron = db.getPatron(1);

            //
            // patron.takeBook(0, db);
            //not necessarily, but desirable
            System.out.println(librarian.getName() + librarian.getSurname());
            db.close();
        }
    }
}