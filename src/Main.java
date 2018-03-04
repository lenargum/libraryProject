import java.sql.SQLException;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        //example of database usage
        Database db = new Database();
        Scanner in = new Scanner(System.in);
        db.connect();
        if (db.isConnected()) {
            //body of database
            System.out.println("Hello! Enter your login:");
            String login = in.nextLine();
            System.out.println("Enter your password:");
            String password = in.nextLine();
            while (!db.login(login, password)) {
                System.out.println("Error!\nLogin or password are not correct.\nType \"quit\" to exit program or\n enter your login again:");
                login = in.nextLine();
                if (login.equals("quit")) System.exit(0);
                System.out.println("Enter your password:");
                password = in.nextLine();
            }
            int userId = db.loginId(login, password);
            Patron user = db.getPatron(userId);

            while (true) {
                clearScreen();
                System.out.println("1 » Help \n" +
                        "2 » Borrowed book manager \n" +
                        "3 » Library\n" +
                        "0 » Exit\n");
                int input = enterNum(in, 3,"Enter option number: ");
                switch(input) {
                    case 0: exit(db); break;
                    case 1: System.out.println("\nHelp yourself\n"); break;
                    case 2: bookManagerMenu(in, user,db); break;
                    case 3: libraryMenu(in,user,db); break;
                }
            }
        }
    }



    private static void exit(Database db) {
        db.close();
        System.exit(0);
    }

    private static int enterNum(Scanner in, int upperBound,String message) {
        boolean inputIsCorrect = false;
        int input = -1;
        while (!inputIsCorrect) {
            System.out.print(message);
            String inputLine = in.nextLine();
            System.out.println("\n");
            try {
                input = Integer.parseInt(inputLine);
                if(upperBound != 0) {
                    if (0 <= input && input <= upperBound) {
                        inputIsCorrect = true;
                    }
                } else {
                    inputIsCorrect = true;
                }

            } catch (NumberFormatException e) {
                System.out.println("Input string is incorrect");
                inputIsCorrect = false;
            }
        }
        return input;
    }

    private static void bookManagerMenu(Scanner in, Patron user, Database db) throws SQLException, ParseException {
        clearScreen();
        db.getDebtsForUser(user.getId());
        System.out.println("0 » Back\n" +
                "1 » Return book");
        int input = enterNum(in, 1,"Enter option number: ");
        switch(input) {
            case 0: break;
            case 1: user.returnDocument(enterNum(in,0,"Enter document id: "),db); break;
        }
    }

    private static void libraryMenu(Scanner in, Patron user, Database db) throws SQLException, ParseException {
        clearScreen();
        System.out.println("0 » Back\n" +
                "1 » Books\n" +
                "2 » AVs\n" +
                "3 » Articles");
        int input = enterNum(in, 3,"Enter option number: ");
        switch(input) {
            case 0: break;
            case 1: books(in,user,db); break;
            case 2: avs(in,user,db); break;
            case 3: articles(in,user,db); break;
        }

    }

    private static void books(Scanner in, Patron user, Database db) throws SQLException {
        clearScreen();
        try {
            System.out.println("All books in library: ");
            System.out.println();
            for(Book temp:db.getBookList()) {
                System.out.println(temp.toString());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Sorry! There is no books in library");
        }
        System.out.println("0 » Back\n" +
                "1 » Take book");
        int input = enterNum(in,1,"Enter option number: ");
        switch(input) {
            case 0: break;
            case 1:
                user.takeBook(enterNum(in,0,"Enter book id: "),db); break;
        }
    }

    private static void avs(Scanner in, Patron user, Database db) throws SQLException {
        clearScreen();
        try {
            System.out.println("All audio/video materials in library: ");
            System.out.println();
            for(AudioVideoMaterial temp:db.getAVList()) {
                System.out.println(temp.toString());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Sorry! There is no audio/video materials in library");
        }
        System.out.println("0 » Back\n" +
                "1 » Take audio/video");
        int input = enterNum(in,1, "Enter option number: ");
        switch(input) {
            case 0: break;
            case 1:
                user.takeAV(enterNum(in,0,"Enter audio/video id: "),db); break;
        }
    }

    private static void articles(Scanner in, Patron user, Database db) throws SQLException, ParseException {
        clearScreen();
        try {
            System.out.println("All journal articles in library: ");
            System.out.println();
            for(JournalArticle temp:db.getArticleList()) {
                System.out.println(temp.toString());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Sorry! There is no journal articles in library");
        }
        System.out.println("0 » Back\n" +
                "1 » Take article");
        int input = enterNum(in,1,"Enter option number: ");
        switch(input) {
            case 0: break;
            case 1:
                user.takeArticle(enterNum(in,0,"Enter article id: "),db); break;
        }
    }

    private static void clearScreen() {
        /*for(int clear = 0; clear < 1000; clear++)
        {
            System.out.println("\b") ;
        }*/
    }
}