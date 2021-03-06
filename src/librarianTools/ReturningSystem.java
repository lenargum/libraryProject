package librarianTools;

import tools.Database;
import tools.Debt;
import tools.Logic;
import tools.Request;
import users.Patron;


public class ReturningSystem {

	/**
	 * Return confirmation
	 *
	 * @param debtID   - id of debt patron wants to close
	 * @param database - information storage
	 */
	public void confirmReturn(int debtID, Database database) {
		Debt debt = database.getDebt(debtID);
		if (Logic.canReturnDocument(debt.getDocumentId(), debt.getPatronId(), database)) {
			Patron patron = database.getPatron(debt.getPatronId());
			patron.returnDocument(debt.getDocumentId(), database);
		}
	}

	/**
	 * renew document confirmation
	 *
	 * @param request  - request that patron sent to renew document
	 * @param database - information storage
	 */
	public void confirmRenew(Request request, Database database) {
		request.approveRenew(database);
		database.deleteRequest(request.getRequestId());
	}

	/**
	 * confirmation of getting fee
	 *
	 * @param debtID   - id of debt patron wants to close
	 * @param database - information storage
	 */
	public void getFee(int debtID, Database database) {
		if (Logic.canFine(debtID, database)) {
			Debt debt = database.getDebt(debtID);
			debt.setFee(0);
			System.out.println("Payout confirmed!");
		}
	}
}
