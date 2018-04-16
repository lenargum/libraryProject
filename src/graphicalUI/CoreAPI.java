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

/**
 * Abstraction layer over core.
 *
 * @author Ruslan Shakirov
 */
public class CoreAPI {
	private boolean loggedIn;
	private User user;
	private Database db;

	/**
	 * Create new API.
	 */
	public CoreAPI() {
		db = new Database();
		loggedIn = false;
	}

	/**
	 * Get currently authorized user.
	 *
	 * @return User.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Get user from database by ID.
	 *
	 * @param userID User ID.
	 * @return User.
	 */
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

	/**
	 * Get all books from database.
	 *
	 * @return List of all documents.
	 */
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

	/**
	 * Get all users from database.
	 *
	 * @return List of all users.
	 */
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

	/**
	 * Get all documents from database.
	 *
	 * @return All documents form database.
	 */
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

	/**
	 * Checks if current user logged in.
	 *
	 * @return <code>true</code> if logged in, <code>false</code> otherwise.
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Authorize in system.
	 *
	 * @param credentials User credentials.
	 * @return <code>true</code> if authorized successfully, <code>false</code> otherwise.
	 */
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

	/**
	 * Deauthorize current user.
	 */
	public void deauthorize() {
		assert loggedIn;
		user = null;
		loggedIn = false;
	}

	/**
	 * Get documents for user.
	 *
	 * @return List of user documents.
	 */
	public ObservableList<UserDocs.MyDocsView> getUserDocs() {
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

	/**
	 * Get recent.
	 *
	 * @return List of recent books.
	 */
	public ObservableList<DocListItem> getRecent() {
		ObservableList<DocListItem> recent = FXCollections.observableArrayList();
		int count = getUserDocs().size() > 5 ? 5 : getUserDocs().size();
		ObservableList<UserDocs.MyDocsView> sorted = getUserDocs().sorted(Comparator.comparing(o -> o.daysLeft.getValue()));

		for (int i = 0; i < count; i++) {
			recent.add(new DocListItem(sorted.get(i).docTitle.getValue(), sorted.get(i).daysLeft.intValue(), i));
		}

		return recent;
	}

	/**
	 * Get waitlist for user.
	 *
	 * @return Waitlist.
	 */
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

	/**
	 * Get renew requests for user.
	 *
	 * @return Renew requests for users.
	 */
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

	/**
	 * Add new user to database.
	 *
	 * @param newUser New user.
	 */
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

	/**
	 * Edit user record in database.
	 *
	 * @param userID   User ID.
	 * @param column   Record column.
	 * @param newValue New value.
	 */
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

	/**
	 * Get take requests.
	 *
	 * @return Take requests.
	 */
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

	/**
	 * Get debts for current user.
	 *
	 * @return List of debts.
	 */
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

	public ObservableList<DebtsManager.DebtCell> getAllDebts() {
		ObservableList<DebtsManager.DebtCell> list = FXCollections.observableArrayList();

		db.connect();
		try {
			for (Debt debt : db.getDebtsList()) {
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

	/**
	 * Make outstanding request.
	 *
	 * @param requestID Request ID.
	 */
	public void makeOutstandingRequest(int requestID) {
		db.connect();
		try {
			Request request = db.getRequest(requestID);
			((Librarian) user).makeOutstandingRequest(request, db);
		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}

	/**
	 * Get notifications for current user.
	 *
	 * @return List of notifications.
	 */
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

	/**
	 * Check whether current user can take document.
	 *
	 * @param docID Document ID.
	 * @return <code>true</code> if user can take document, <code>false</code> otherwise.
	 */
	public boolean canTakeDocument(int docID) {
		if (user == null) return false;
		if (user instanceof Librarian) {
			return false;
		} else {
			db.connect();
			boolean respond = ((Patron) user).canRequestDocument(docID, db);
			db.close();
			return respond;
		}
	}

	/**
	 * Request document.
	 *
	 * @param docID Document ID.
	 */
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

	/**
	 * Accept booking request.
	 *
	 * @param requestID Request ID.
	 */
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

	/**
	 * Reject booking request.
	 *
	 * @param requestID Request ID.
	 */
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

	/**
	 * Accept renew request.
	 *
	 * @param requestID Request ID.
	 */
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

	/**
	 * Reject renew request.
	 *
	 * @param requestID Request ID.
	 */
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

	/**
	 * Get current user type.
	 *
	 * @return Current user type.
	 */
	public UserType userType() {
		return determineUserType(user);
	}

	/**
	 * Determines user type.
	 *
	 * @param usr User to check.
	 * @return User type.
	 */
	public UserType determineUserType(User usr) {
		if (usr instanceof Librarian) {
			return UserType.LIBRARIAN;
		} else {
			return determinePatronType((Patron) usr);
		}
	}

	/**
	 * Determines patron type.
	 *
	 * @param patron Patron to check.
	 * @return Patron type.
	 */
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

	/**
	 * Possible user types.
	 */
	public enum UserType {
		LIBRARIAN, PATRON,
		STUDENT, INSTRUCTOR, TA, VP, PROFESSOR
	}
}
