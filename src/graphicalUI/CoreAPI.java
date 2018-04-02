package graphicalUI;

import documents.Document;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tools.Database;
import tools.Debt;
import tools.Request;
import users.Librarian;
import users.Patron;
import users.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CoreAPI {
	private Credentials credentials;
	private boolean loggedIn;
	private User user;
	private Database db;

	public CoreAPI() {
		db = new Database();
		loggedIn = false;
	}

	public User getUser() {
		return user;
	}

	public List<DocItem> getAllBooks() {
		List<DocItem> list = new LinkedList<>();
		List<Document> documents = new LinkedList<>();
		try {
			db.connect();
			documents = db.getDocumentList();
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (Document doc : documents) {
			list.add(new DocItem(doc.getTitle(), doc.getAuthors(), doc.getID()));
		}

		return list;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public boolean authorize(Credentials credentials) {
		this.credentials = credentials;
		boolean respond = false;
		db.connect();
		try {
			respond = db.login(credentials.getLogin(), credentials.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (respond) {
			try {
				for (Librarian librarian : db.getLibrarianList()) {
					if (librarian.getLogin().equals(credentials.getLogin())) {
						user = librarian;
						db.close();
						break;
					}
				}

				if (user == null) {
					for (Patron patron : db.getPatronList()) {
						if (patron.getLogin().equals(credentials.getLogin())) {
							user = patron;
							db.close();
							break;
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (user != null) {
			loggedIn = true;
		}

		return loggedIn;
	}

	public void deauthorize() {
		assert loggedIn;
		credentials = null;
		user = null;
		loggedIn = false;
	}

	public ObservableList<UserDocs.MyDocsView> getUserBooks() {
		ObservableList<UserDocs.MyDocsView> list = FXCollections.observableArrayList();
		List<Integer> documents = new ArrayList<>();
		if (user instanceof Patron) {
			documents = ((Patron) user).getListOfDocumentsPatron();
		}

		db.connect();
		for (Integer id : documents) {
			Document doc = null;
			Debt debt = null;
			try {
				doc = db.getDocument(id);
				debt = db.getDebt(db.findDebtID(user.getId(), id));
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			}

			list.add(new UserDocs.MyDocsView(doc.getTitle(), debt.daysLeft(), id));
		}
		db.close();

		return list;
	}

	public ObservableList<DocCell> getRecent() {
		ObservableList<DocCell> recent = FXCollections.observableArrayList();
		int count = getUserBooks().size() > 5 ? 5 : getUserBooks().size();
		ObservableList<UserDocs.MyDocsView> sorted = getUserBooks().sorted(Comparator.comparing(o -> o.daysLeft.getValue()));

		for (int i = 0; i < count; i++) {
			recent.add(new DocCell(sorted.get(i).docTitle.getValue(), sorted.get(i).daysLeft.intValue(), i));
		}

		return recent;
	}

	public ObservableList<UserDocs.WaitlistView> getWaitList() {
		ObservableList<UserDocs.WaitlistView> waitlist = FXCollections.observableArrayList();
		List<Request> requests = new ArrayList<>();
		db.connect();
		try {
			requests = db.getRequestsForPatron(user.getId());
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		}

		for (Request request : requests) {
			try {
				Document doc = db.getDocument(request.getIdDocument());
				waitlist.add(new UserDocs.WaitlistView(doc.getTitle(), 0,
						request.getRequestId()));
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		db.close();

		return waitlist;
	}

	public ObservableList<ApprovalCell> getRenewRequests() {
		ObservableList<ApprovalCell> list = FXCollections.observableArrayList();

		db.connect();
		try {
			for (Request request : db.getRequests()) {
				Document doc = db.getDocument(request.getIdDocument());
				Patron pat = db.getPatron(request.getIdPatron());
				list.add(new ApprovalCell(request.getRequestId(),
						doc.getTitle(), doc.getID(),
						pat.getName() + " " + pat.getSurname(), pat.getId()));
			}
		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		}
		db.close();

		return list;
	}

	public ObservableList<ApprovalCell> getTakeRequests() {
		ObservableList<ApprovalCell> list = FXCollections.observableArrayList();

		db.connect();
		try {
			for (Request request : db.getRequests()) {
				Document doc = db.getDocument(request.getIdDocument());
				Patron pat = db.getPatron(request.getIdPatron());
				list.add(new ApprovalCell(request.getRequestId(),
						doc.getTitle(), doc.getID(),
						pat.getName() + " " + pat.getSurname(), pat.getId()));
			}
		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		}
		db.close();

		return list;
	}

	public ObservableList<DebtsManager.DebtCell> getUserDebts() {
		ObservableList<DebtsManager.DebtCell> list = FXCollections.observableArrayList();

		db.connect();
		try {
			for (Debt debt : db.getDebtsForUser(user.getId())) {
				Patron pat = db.getPatron(debt.getPatronId());
				Document doc = db.getDocument(debt.getDocumentId());
				list.add(new DebtsManager.DebtCell(debt.getDebtId(),
						pat.getName() + " " + pat.getSurname(), pat.getId(),
						doc.getTitle(), doc.getID(),
						debt.getBookingDate().toString(),
						debt.getExpireDate().toString()));
			}
		} catch (SQLException | ParseException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		return list;
	}

	public boolean canTakeDocument(int docID) {
		if (user instanceof Librarian) {
			return false;
		} else {
			db.connect();
			boolean respond = ((Patron) user).canRequestDocument(docID, db);
			db.close();
			return respond;
		}
	}

	public void bookOrRequest(int docID) {
		if (canTakeDocument(docID)) {
			try {
				db.connect();
				((Patron) user).makeRequest(docID, db);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
		}
	}

	public void acceptBookRequest(int requestID) {
		if (user instanceof Librarian) {
			System.out.println("Current user is librarian, asking database to accept...");
			db.connect();

			try {
				Request request = db.getRequest(requestID);
				System.out.println("Submitting request " + request.getRequestId());
				((Librarian) user).submitRequest(request, db);
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
		} else {
			System.out.println("Current user is not librarian.");
		}
	}

	public void rejectBookRequest(int requestID) {
		if (user instanceof Librarian) {
			db.connect();

			try {
				Request request = db.getRequest(requestID);
				((Librarian) user).deleteRequest(request, db);
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
		}
	}

	public UserType userType() {
		if (user instanceof Librarian) {
			return UserType.LIBRARIAN;
		} else {
			return UserType.PATRON;
		}
	}

	public static enum UserType {
		LIBRARIAN, PATRON
	}
}
