package graphicalUI;

import documents.Document;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tools.Database;
import tools.Debt;
import tools.Notification;
import tools.Request;
import users.Librarian;
import users.Patron;
import users.User;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;

public class CoreAPI {
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

	public User getUserByID(int userID) {
		db.connect();
		User result = null;
		try {
			result = db.getLibrarian(userID);
		} catch (SQLException | NoSuchElementException e) {
			try {
				result = db.getPatron(userID);
			} catch (SQLException e1) {
				System.out.println("Cannot find exact user. User ID: " + userID);
			}
		} finally {
			db.close();
		}

		return result;
	}

	public List<DocItem> getAllBooks() {
		List<DocItem> list = new LinkedList<>();
		List<Document> documents = new LinkedList<>();
		try {
			db.connect();
			documents = db.getDocumentList();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		for (Document doc : documents) {
			list.add(new DocItem(doc.getTitle(), doc.getAuthors(), doc.getID()));
		}

		return list;
	}

	public ObservableList<UserManager.UserCell> getAllUsers() {
		ObservableList<UserManager.UserCell> list = FXCollections.observableArrayList();

		db.connect();

		try {
			for (User usr : db.getUsers()) {
				list.add(new UserManager.UserCell(usr.getId(), usr.getName(), usr.getSurname(),
						usr.getAddress(), usr.getPhoneNumber(), determineUserType(usr).name()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

		return list;
	}

	public ObservableList<DocumentManager.DocCell> getAllDocs() {
		ObservableList<DocumentManager.DocCell> list = FXCollections.observableArrayList();

		db.connect();
		try {
			for (Document doc : db.getDocumentList()) {
				list.add(new DocumentManager.DocCell(doc.getID(), doc.getTitle(), doc.getAuthors(),
						doc.getType(), doc.getNumberOfCopies(),
						doc.isAllowedForStudents(), doc.isReference()));
			}
		} catch (SQLException e) {
			System.out.println("Failed to fetch document list.");
		} finally {
			db.close();
		}

		return list;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public boolean authorize(Credentials credentials) {
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

			assert doc != null;
			assert debt != null;
			list.add(new UserDocs.MyDocsView(doc.getTitle(), debt.daysLeft(), id));
		}
		db.close();

		return list;
	}

	public ObservableList<DocListItem> getRecent() {
		ObservableList<DocListItem> recent = FXCollections.observableArrayList();
		int count = getUserBooks().size() > 5 ? 5 : getUserBooks().size();
		ObservableList<UserDocs.MyDocsView> sorted = getUserBooks().sorted(Comparator.comparing(o -> o.daysLeft.getValue()));

		for (int i = 0; i < count; i++) {
			recent.add(new DocListItem(sorted.get(i).docTitle.getValue(), sorted.get(i).daysLeft.intValue(), i));
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
			for (Request request : db.getRenewRequests()) {
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

	public void addNewUser(User newUser) {
		db.connect();

		try {
			if (newUser instanceof Librarian) {
				db.insertLibrarian((Librarian) newUser);
				newUser.setId(db.getLibrarianID((Librarian) newUser));
			} else {
				db.insertPatron((Patron) newUser);
				newUser.setId(db.getPatronID((Patron) newUser));
			}
		} catch (SQLException e) {
			System.out.println("Cannot add user to database.");
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	public void editUser(int userID, String column, String newValue) {
		db.connect();

		try {
			db.editUserColumn(userID, column, newValue);
		} catch (SQLException e) {
			System.out.println("Unable to mdify user. User ID: " + userID);
			e.printStackTrace();
		} finally {
			db.close();
		}
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

	public void makeOutstandingRequest(int requestID) {

	}

	public ObservableList<Notification> getUserNotifications() {
		ObservableList<Notification> list = FXCollections.observableArrayList();

		db.connect();
		try {
			list.addAll(db.getNotificationsForUser(user.getId()));
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
			System.out.println("Current user is librarian, asking database to reject...");
			db.connect();

			try {
				Request request = db.getRequest(requestID);
				System.out.println("Deleting request " + request.getRequestId());
				((Librarian) user).deleteRequest(request, db);
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
		} else {
			System.out.println("Current user is not a librarian.");
		}
	}

	public void acceptRenewRequest(int requestID) {
		if (user instanceof Librarian) {
			System.out.println("Current user is librarian, asking database to accept...");
			db.connect();

			try {
				Request request = db.getRequest(requestID);
				System.out.println("Submitting request " + request.getRequestId());
				((Librarian) user).confirmRenew(request, db);
			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
		} else {
			System.out.println("Current user is not librarian.");
		}
	}

	public void rejectRenewRequest(int requestID) {
		if (user instanceof Librarian) {
			System.out.println("Current user is librarian, asking database to accept...");
			db.connect();

			try {
				Request request = db.getRequest(requestID);
				System.out.println("Submitting request " + request.getRequestId());

			} catch (SQLException | ParseException e) {
				e.printStackTrace();
			} finally {
				db.close();
			}
		} else {
			System.out.println("Current user is not librarian.");
		}
	}

	public UserType userType() {
		return determineUserType(user);
	}

	public UserType determineUserType(User usr) {
		if (usr instanceof Librarian) {
			return UserType.LIBRARIAN;
		} else {
			return determinePatronType((Patron) usr);
		}
	}

	private UserType determinePatronType(Patron patron) {
		switch (patron.getPriority()) {
			case 0:
				return UserType.STUDENT;
			case 1:
				return UserType.INSTRUCTOR;
			case 2:
				return UserType.TA;
			case 3:
				return UserType.VP;
			case 4:
				return UserType.PROFESSOR;
			default:
				return UserType.PATRON;
		}
	}

	public enum UserType {
		LIBRARIAN, PATRON,
		STUDENT, INSTRUCTOR, TA, VP, PROFESSOR
	}
}
