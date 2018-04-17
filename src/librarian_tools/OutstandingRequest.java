package librarian_tools;

import documents.Document;
import tools.Database;
import tools.Request;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutstandingRequest {
    public void makeOutstandingRequest(Request request, Database database) {
        try {
            sendNotificationsForOutstandingRequest(request, database);
            database.deleteRequestsForDocument(request.getIdDocument());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAvailability(int docID, Database database)  {
        try {
            if (database.getDocument(docID).getNumberOfCopies() > 0) {
                sendNotificationsForAvailability(docID, database);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sendNotificationsForOutstandingRequest(Request request, Database db) {
        ArrayList<Request> requests = null;
        try {
            requests = db.getRequests(request.getIdDocument());
            for (Request temp : requests) {
                if (temp.getIdPatron() == request.getIdPatron()) {
                    Document doc = db.getDocument(request.getIdDocument());
                    db.insertNotification(temp.getRequestId(), temp.getIdPatron(),
                            "Outstanding request for " + doc.getTitle(), new Date());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void sendNotificationsForAvailability(int docId, Database database) {
        List<Request> requests = null;
        try {
            requests = database.getRequests(docId);
            int i = 0;
            while (i < database.getDocument(docId).getNumberOfCopies()) {
                Request temp = requests.get(i);
                database.insertNotification(temp.getRequestId(), temp.getIdPatron(), "Set available document", new Date());
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
