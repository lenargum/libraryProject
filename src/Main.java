import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        //example of database usage
        Database db = new Database();
        db.connect();
        if (db.isConnected()) {
            //body of database interacti
            //db.getLibrarian(2).addBook(new Book("Delete", "Me", false, 8, false, 12, "meow", "meow", 5, false), db);
           // db.getPatron(4).takeDocument(6, db);
            //System.out.println(db.getPatron(4).getListOfDocumentsPatron());
           // db.getLibrarian(2).RegisterPatron(new Patron("login", "password", "student", "Name", "Surname", "224511", "Unuiversitetskaya"), db);
            db.soutDocs();
            Patron pidor = db.getPatron(5);
            pidor.takeBook(7, db);
            System.out.println(db.getDocument(7).isAllowedForStudents());
            db.soutDocs();
            db.close();
        }
    }
}