public class Main {

    public static void main(String[] args) {
        Librarian librarian = new Librarian("Maria", "Bulochnaya", "586", 0 );
        Document book=  new Document ("It", "St.King", 1, true);
        librarian.createPatron("Vasiliy", "Naberezhnaya", "58963", "student", 1);
        librarian.createPatron("Anton", "Bulochnaya", "58933", "faculty", 2);
        librarian.addDocumentInTheLibrary(book);
       // librarian.deleteDocumentFromTheLibrary(1);
      //  librarian.deletePatron(1);
        librarian.printListOfPatrons();
        librarian.printLibrary();
    }
}
