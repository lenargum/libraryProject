public class Main {

    public static void main(String[] args) throws Exception {
        Database db = new Database();
        db.connect();

    }
}
/*
db.execute("INSERT INTO users(login, password,status,firstname,lastname,phone,address)" +
                " VALUES('lenargumerov','1234','admin','lenar','gumerov','2281337','unversitetskaya')");
 */