import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        //example of database usage
        Scanner in = new Scanner(System.in);
        Database db = new Database();
        db.connect();
        if (db.isConnected()) {
            //body of database interaction

            System.out.println("Hello! Enter your login:");
            String login = in.nextLine();
            System.out.println("Enter your password:");
            String password = in.nextLine();
            while(!db.login(login,password)) {
                System.out.println("Error!\nLogin or password are not correct.\nType \"quit\" to exit program or\n enter your login again:");
                login = in.nextLine();
                if(login.equals("quit")) System.exit(0);
                System.out.println("Enter your password:");
                password = in.nextLine();
            }
            int userId = db.loginId(login,password);

            if(db.loginStatus(userId).equals("LIBRARIAN")) {
                Librarian user = db.getLibrarian(userId);
            } else {
                Patron user = db.getPatron(userId);
            }
            //System.out.println("Hello, "+user.getName()+"!");



            //not necessarily, but desirable
            db.close();
        }
    }
}