public class Main {

    public static void main(String[] args) {
        Librarian librarian = new Librarian("Maria", "Bulochnaya", "586", 0 );
        Document book=  new Document ("It", "St.King", 1);
        librarian.createPatron("Vasiliy", "Naberezhnaya", "58963", "Student", 1);
       // librarian.printListOfPatrons();
        librarian.addDocumentInTheLibrary(book);
        librarian.printLibrary();
    }
}
