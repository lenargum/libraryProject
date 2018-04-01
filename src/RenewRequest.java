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

    public void approveRequest(){

    }

    public void refuseRequest(){

    }
}
