package librarian_tools;

import documents.Document;
import tools.Database;
import tools.Logic;
import tools.Request;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutstandingRequest {
    public void makeOutstandingRequest(int librarianId, Request request, Database database) {
        if(Logic.canSetRequest(librarianId, database)) {
            sendNotificationsForOutstandingRequest(request, database);
            database.deleteRequestsForDocument(request.getIdDocument());
        } else {

        }

    }

    public void setAvailability(int docID, Database database)  {
            if (database.getDocument(docID).getNumberOfCopies() > 0) {
                sendNotificationsForAvailability(docID, database);
            }

    }

    private void sendNotificationsForOutstandingRequest(Request request, Database db) {
        ArrayList<Request> requests = null;
            requests = db.getRequestsForDocument(request.getIdDocument());
            for (Request temp : requests) {
                if (temp.getIdPatron() == request.getIdPatron()) {
                    Document doc = db.getDocument(request.getIdDocument());
                    db.insertNotification(temp.getRequestId(), temp.getIdPatron(),
                            "Outstanding request for " + doc.getTitle(), new Date());
                }
            }

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
}
