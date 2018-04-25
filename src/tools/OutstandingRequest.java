package tools;

import documents.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutstandingRequest {
	public void makeOutstandingRequest(int librarianId, Request request, Database database) {
		if (Logic.canSetRequest(librarianId, database)) {
			sendNotificationsForOutstandingRequest(request, database);
			database.deleteRequestsForDocument(request.getIdDocument());
			database.log(Constants.placeOutstandingRequestMessage(database.getLibrarian(librarianId), database.getDocument(request.getIdDocument())));
		} else {

		}
	}

	public void makeDeletionRequest(int documentId, Database database) {
		sendNotificationDeletionDocument(documentId, database);
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
			//if (temp.getIdPatron() != request.getIdPatron()) {
			Document doc = db.getDocument(request.getIdDocument());
			db.insertNotification(temp.getRequestId(), temp.getIdPatron(),
					"Outstanding request for " + doc.getTitle(), new Date());
			db.log(Constants.patronNotifiedMessage(db.getPatron(temp.getIdPatron()), "Outstanding request for " + doc.getTitle()));
			//}
		}
		ArrayList<Debt> debts = db.getDebtsForDocument(request.getIdDocument());
		Document doc = db.getDocument(request.getIdDocument());
		for (Debt temp : debts) {
			db.insertNotification(temp.getDebtId(), temp.getPatronId(), "Outstanding request for " + doc.getTitle(), new Date());
			db.log(Constants.patronNotifiedMessage(db.getPatron(temp.getPatronId()), "Outstanding request for " + doc.getTitle()));
		}
	}

	private void sendNotificationsForAvailability(int docId, Database database) {
		List<Request> requests = null;
		requests = database.getRequestsForDocument(docId);
		int i = 0;
		while (i < database.getDocument(docId).getNumberOfCopies()) {
			Request temp = requests.get(i);
			database.insertNotification(temp.getRequestId(), temp.getIdPatron(), "Set available document", new Date());
			database.log(Constants.patronNotifiedMessage(database.getPatron(temp.getIdPatron() ), "Set available document " + temp.getIdDocument()));
			i++;
		}
	}

	private void sendNotificationDeletionDocument(int docaumentId, Database database) {
		List<Request> requests = database.getRequestsForDocument(docaumentId);
		int i = 0;
		int n = database.getRequestsForDocument(docaumentId).size();
		while (i < n) {
			Request temp = requests.get(i);
			database.insertNotification(temp.getRequestId(), temp.getIdPatron(), "This document is deleted", new Date());
			database.log(Constants.patronNotifiedMessage(database.getPatron(temp.getIdPatron() ), "This document is deleted " + temp.getIdDocument()));
		}
	}

}
