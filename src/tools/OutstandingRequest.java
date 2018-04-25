package tools;

import documents.Document;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutstandingRequest {
	public void makeOutstandingRequest(int librarianId, Request request, Database database) {
		if (Logic.canSetRequest(librarianId, database)) {
			database.log("Librarian " + librarianId + "id has made outstanding Request " + request.getRequestId() + "id.");
			sendNotificationsForOutstandingRequest(request, database);
			database.deleteRequestsForDocument(request.getIdDocument());
		}
	}

	public void makeDeletionRequest(Request request, Database database) {
		sendNotificationsDeletion(request, database);
		database.log("Librarian " + request.getIdPatron() + "id has made deletion Request " + request.getRequestId() + "id.");

	}

	public void setAvailability(int docID, Database database) {
		if (database.getDocument(docID).getNumberOfCopies() > 0) {
			sendNotificationsForAvailability(docID, database);
		}
	}

	private void sendNotificationsForOutstandingRequest(Request request, Database db) {
		ArrayList<Request> requests = null;
		requests = db.getRequestsForDocument(request.getIdDocument());
		for (Request temp : requests) {
			Document doc = db.getDocument(request.getIdDocument());
			db.insertNotification(temp.getRequestId(), temp.getIdPatron(),
					"Outstanding request for " + doc.getTitle(), new Date());
			db.log("Notification of not availability for User " + temp.getIdPatron() + "id has sent");
		}
		ArrayList<Debt> debts = db.getDebtsForDocument(request.getIdDocument());
		Document doc = db.getDocument(request.getIdDocument());
	}

	private void sendNotificationsForAvailability(int docId, Database database) {
		List<Request> requests = null;
		requests = database.getRequestsForDocument(docId);
		int i = 0;
		while (i < database.getDocument(docId).getNumberOfCopies()) {
			Request temp = requests.get(i);
			database.insertNotification(temp.getRequestId(), temp.getIdPatron(), "Set available document", new Date());
			i++;
		}
	}

	private void sendNotificationsDeletion(Request request, Database db) {
		ArrayList<Request> requests = null;
		requests = db.getRequestsForDocument(request.getIdDocument());
		for (Request temp : requests) {
			Document doc = db.getDocument(request.getIdDocument());
			db.insertNotification(temp.getRequestId(), temp.getIdPatron(),
					"Delete document " + doc.getTitle(), new Date());

		}
		ArrayList<Debt> debts = db.getDebtsForDocument(request.getIdDocument());
		Document doc = db.getDocument(request.getIdDocument());
		for (Debt temp : debts) {
			db.insertNotification(temp.getDebtId(), temp.getPatronId(), "Delete document " + doc.getTitle(), new Date());
		}
	}
}
