import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        Database db = new Database();
        db.connect();
        if (db.isConnected()) {
            /*db.insertArticle("testing data", "Lenar Gumerov", true, 0,
                    false, 0.5, "hate date","databases","somebody",
                    "dafsafs","asdfasdf","2017-01-01");
            */
            //////////testing/////////////
            /*
            ArrayList<String> shit = db.getDocumentList();
            for(String temp : shit) {
                System.out.println(temp);
            }
            */
            ArrayList<AudioVideoMaterial> ya = db.getAVList();
            ArrayList<JournalArticle> zaebalsya = db.getArticleList();
            db.close();
        }
    }
}
/*
db.execute("INSERT INTO users(login, password,status,firstname,lastname,phone,address)" +
                " VALUES('lenargumerov','1234','admin','lenar','gumerov','2281337','unversitetskaya')");
 */