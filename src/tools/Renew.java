package tools;

import users.Patron;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

public class Renew {

    /**
     * patron renews document after approving
     *
     * @param documentID documents.Document ID
     * @param database   tools.Database stores the information
     */
    public void renewDocument(int documentID, int patronId, Database database) {

        try {
            Patron patron = database.getPatron(patronId);
            Debt debt = database.getDebt(database.findDebtID(patronId, documentID));
            Date expDate = debt.getExpireDate();
            expDate.setTime(expDate.getTime() + 7 * 60 * 60 * 24 * 1000);
            if (patron.getStatus().equals("vp")) {
                database.editDebtColumn(debt.getDebtId(), "can_renew", "false");
            }
            debt.setExpireDate(expDate);
            database.editDebtColumn(debt.getDebtId(), "expire_date",
                    (new SimpleDateFormat("yyyy-MM-dd")).format(debt.getExpireDate()));

        } catch (NoSuchElementException | SQLException e) {
            System.out.println("Incorrect id");
        } catch (ParseException e) {
            System.out.println("Incorrect data parsing");
        }
    }
}
