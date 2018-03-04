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
            Patron mainPidor  = new Patron("a.naumchev", "12345678", "faculty", "Alexandr", "Naumchev", "12345678", "Innopolis, Universitetskaya");
            Librarian librarian = db.getLibrarian(2);
            //librarian.RegisterPatron(mainPidor, db);
            System.out.println(mainPidor.getId());
            Patron pidor = db.getPatron(5);
            System.out.println(mainPidor.canRequestBook(7, db));
            mainPidor.takeBook(7, db);
            pidor.takeBook(7, db);
            //System.out.println(db.getDocument(7).isAllowedForStudents());
            mainPidor.returnBook(7, db);
            db.soutDocs();
            db.close();
        }
    }
}