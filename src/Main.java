public class Main {

    public static void main(String[] args) throws Exception {
        Database db = new Database();
        db.connect();
        if (db.isConnected()) {
            db.insertAV("how to be the best in your team", "Lenar Gumerov", true, 0, false, 0, "bestbookintheworld");
            db.close();
        }
    }
}
/*
db.execute("INSERT INTO users(login, password,status,firstname,lastname,phone,address)" +
                " VALUES('lenargumerov','1234','admin','lenar','gumerov','2281337','unversitetskaya')");
 */