package tools;

import documents.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutstandingRequest {
	public void makeOutstandingRequest(int librarianId, Request request, Database database) {
		if (Logic.canSetRequest(librarianId, database)) {
			database.log("Librarian " + librarianId + "id has made outstanding Request " + request.getRequestId() + "id.");
			database.editDocumentColumn(request.getIdDocument(), "is_under_outstanding_request", "true");
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
			database.editDocumentColumn(docID, "is_under_outstanding_request", "false");
			sendNotificationsForAvailability(docID, database);
		}
	}

	private void sendNotificationsForOutstandingRequest(Request request, Database db) {
		ArrayList<Request> requests = null;
		requests = db.getRequestsForDocument(request.getIdDocument());
		for (Request temp : requests) {
			Document doc = db.getDocument(request.getIdDocument());
			db.insertNotification(request.getIdDocument(), temp.getIdPatron(),
					"Outstanding request for " + doc.getTitle(), new Date());

			db.log("Notification of not availability for User " + temp.getIdPatron() + "id has been sent");
		}
		ArrayList<Debt> debts = db.getDebtsForDocument(request.getIdDocument());
		Document doc = db.getDocument(request.getIdDocument());
		for (Debt temp : debts) {
			db.insertNotification(temp.getDocumentId(), temp.getPatronId(),
					"You need to return " + doc.getTitle() + " because of outstanding request placed on it.", new Date());

			db.log("Notification of need to return document " + doc.getID() + "id for User " + temp.getPatronId() + "id has been sent");
		}
	}

	private void sendNotificationsForAvailability(int docId, Database database) {
		List<Request> requests;
		requests = database.getRequestsForDocument(docId);

		for (Request temp: requests) {
			database.insertNotification(temp.getIdDocument(), temp.getIdPatron(), "Set available document", new Date());

		}
	}

	private void sendNotificationsDeletion(Request request, Database db) {
		ArrayList<Request> requests;
		requests = db.getRequestsForDocument(request.getIdDocument());
		for (Request temp : requests) {
			Document doc = db.getDocument(request.getIdDocument());
			db.insertNotification(temp.getIdDocument(), temp.getIdPatron(),
					"Delete document " + doc.getTitle(), new Date());

		}
		ArrayList<Debt> debts = db.getDebtsForDocument(request.getIdDocument());
		Document doc = db.getDocument(request.getIdDocument());
		for (Debt temp : debts) {
			db.insertNotification(temp.getDocumentId(), temp.getPatronId(), "Delete document " + doc.getTitle(), new Date());
		}
	}
}
