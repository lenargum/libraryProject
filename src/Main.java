import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);


        //example of database usage
        Database db = new Database();
        db.connect();
        if (db.isConnected()) {
            //body of database interaction



            //not necessarily, but desirable
            db.close();
        }
    }
}