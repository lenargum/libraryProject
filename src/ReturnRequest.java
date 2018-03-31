import java.sql.SQLException;
import java.text.ParseException;

public class ReturnRequest {
    int debtID;
    int patronID;
    int docID;
    String patName;
    String patSurname;

    public ReturnRequest(int debtID, int docID, int patronID, String name, String surname){
        this.debtID = debtID;
        this.patronID = patronID;
        this.docID = docID;
        this.patName = name;
        this.patSurname = surname;
    }

    public void approveReturn(Database database) throws SQLException{
        database.getPatron(patronID).returnDocument(docID, database);
    }

}
