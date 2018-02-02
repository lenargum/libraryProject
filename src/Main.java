import materials.Document;
import users.Librarian;
import users.User;

public class Main {

	public static void main(String[] args) {
		User librarian = new Librarian("Maria", "Bulochnaya", "586", 0);
		Document book = new Document("It", "St.King", 1, true, 456);
	}
}
