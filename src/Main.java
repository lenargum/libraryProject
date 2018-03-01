import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
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