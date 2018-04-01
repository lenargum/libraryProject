import java.sql.SQLException;

public class RenewRequest {

    int requestID;
    int debtID;
    int patronID;
    int documentID;
    String patronName;
    String patronSurname;
    String documentName;

    public RenewRequest(int debtId, int patronId, int documentId, String patronName, String patronSurname, String documentName){
        this.debtID = debtId;
        this.patronID = patronId;
        this.documentID = documentId;
        this.patronName = patronName;
        this.patronSurname = patronSurname;
        this.documentName = documentName;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getRequestID() {
        return requestID;
    }

    public void setDebtID(int debtId){
        this.debtID = debtId;
    }

    public int getDebtID(){
        return debtID;
    }

    public void setPatronID(int patronId){
        this.patronID = patronId;
    }

    public int getPatronID(){
        return patronID;
    }

    public void setDocumentID(int docId){
        this.documentID = docId;
    }

    public int getDocumentID() {
        return documentID;
    }

    public void setPatronName(String patronName){
        this.patronName = patronName;
    }

    public String getPatronName() {
        return patronName;
    }

    public void setPatronSurname(String patronSurname){
        this.patronSurname = patronSurname;
    }

    public String getPatronSurname() {
        return patronSurname;
    }

    public void approve(Database database)throws SQLException{
        database.getPatron(patronID).returnDocument(documentID, database);
    }

    public void refuse(){
        System.out.println("There is outstanding request for this document, so you cannot renew it :(");
    }
}
