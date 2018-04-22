package librarianTools;

import tools.Database;
import tools.Request;

import java.sql.SQLException;

public class BookingSystem {
	/**
	 * taking document request confirmation
	 *
	 * @param request  - request librarian confirms
	 * @param database - information storage
	 */
	public void submitRequest(Request request, Database database) {
		System.out.println("users.Librarian <- submitting request " + request.getRequestId() + " . . .");
		request.approveRequest(request.getIdPatron(), request.getIdDocument(), database);
	}

	/**
	 * delete taking document request
	 *
	 * @param request  - request the librarian refuses
	 * @param database - information storage
	 */
	public void deleteRequest(Request request, Database database) {
		request.refuseRequest(request.getIdPatron(), request.getIdDocument(), database);
	}


}

