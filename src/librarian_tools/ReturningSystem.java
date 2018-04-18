package librarian_tools;

import tools.Database;
import tools.Debt;
import tools.Logic;
import tools.Request;
import users.Patron;

import java.sql.SQLException;
import java.text.ParseException;

public class ReturningSystem {

    /**
     * Return confirmation
     *
     * @param debtID   - id of debt patron wants to close
     * @param database - information storage
     * @throws SQLException   Something went wrong in database.
     * @throws ParseException Something wrong with input.
     */
    public void confirmReturn(int debtID, Database database)  {
        try {
            Debt debt = database.getDebt(debtID);
            if(Logic.canReturnDocument(debt.getDocumentId(), debt.getPatronId(), database)){
                Patron patron = database.getPatron(debt.getPatronId());
                patron.returnDocument(debt.getDocumentId(), database);
            }
        } catch (SQLException e){
            System.out.println("Incorrect id");
        } catch (ParseException e){
            System.out.println("Incorrect id");
        }
    }

    /**
     * renew document confirmation
     *
     * @param request  - request that patron sent to renew document
     * @param database - information storage
     */
    public void confirmRenew(Request request, Database database) {
        try {
            request.approveRenew(database);
            database.deleteRequest(request.getRequestId());
        } catch (SQLException ignored) {

        }
    }

    /**
     * confirmation of getting fee
     *
     * @param debtID   - id of debt patron wants to close
     * @param database - information storage
     */
    public void getFee(int debtID, Database database)  {
        try {
            if(Logic.canFine(debtID, database)) {
                Debt debt = database.getDebt(debtID);
                debt.setFee(0);
                System.out.println("Payout confirmed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
