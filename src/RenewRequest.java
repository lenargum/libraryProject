import java.sql.SQLException;

public class RenewRequest {

    int debtID;
    int patronID;
    int documentID;
    String patronName;
    String patronSurname;

    public RenewRequest(int debtId, int patronId, int documentId, String patronName, String patronSurname){
        this.debtID = debtId;
        this.patronID = patronId;
        this.documentID = documentId;
        this.patronName = patronName;
        this.patronSurname = patronSurname;
    }

    public void approve(Database database)throws SQLException{
        database.getPatron(patronID).returnDocument(documentID, database);
    }

    public void refuse(){
        System.out.println("There is outstanding request for this document, so you cannot renew it :(");
    }
}
