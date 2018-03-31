import java.sql.SQLException;
import java.text.ParseException;

public class ReturnRequest {
    int debtID;
    int patronID;
    int docID;
    String patName;
    String patSurname;

    public ReturnRequest(int debtId, Database database) throws ParseException, SQLException{
        Debt debt = database.getDebt(debtId);
        this.debtID = debtId;
        this.patronID = debt.getPatronId();
        this.docID = debt.getDocumentId();
        this.patName = database.getPatron(patronID).getName();
        this.patSurname = database.getPatron(patronID).getSurname();
    }

    public void approveReturn(){

    }

    public void refuseReturn(){

    }
}
