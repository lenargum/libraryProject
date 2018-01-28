public class Main {

    public static void main(String[] args) {
        Librarian librarian = new Librarian("Maria", "Bulochnaya", "586", 0 );
        librarian.createPatron("Vasiliy", "Naberezhnaya", "58963", "Student", 1);
        System.out.println(librarian.getPatrons());
    }
}
