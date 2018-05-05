package graphicalUI;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tools.Database;
import tools.Debt;
import tools.Notification;
import tools.Request;
import users.Admin;
import users.Librarian;
import users.Patron;
import users.User;

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
	 * Opens database connection
	 * if it is not connected.
	 */
	private void connectToDatabase() {
		if (!db.isConnected()) {
			db.connect();
		}
	}

	/**
	 * Closes database connection
	 * if it is connected.
	 */
	private void closeDatabaseConnection() {
		if (db.isConnected()) {
			db.close();
		}
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
	User getUserByID(int userID) {
		connectToDatabase();
		User result;
		try {
			result = db.getLibrarian(userID);
		} catch (NoSuchElementException e) {
			result = db.getPatron(userID);
		}

		return result;
	}

	/**
	 * Get document from database by ID.
	 *
	 * @param docID Document ID.
	 * @return Document.
	 */
	Document getDocumentByID(int docID) {
		connectToDatabase();

		return db.getDocument(docID);
	}

	/**
	 * Get all books from database.
	 *
	 * @return List of all documents.
	 */
	List<DocItem> getAllBooks() {
		List<DocItem> list = new LinkedList<>();
		List<Document> documents;

		connectToDatabase();
		documents = db.getDocumentList();


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
	ObservableList<UserManager.UserCell> getAllUsers() {
		ObservableList<UserManager.UserCell> list = FXCollections.observableArrayList();

		connectToDatabase();
		if (user instanceof Admin) {
			for (User usr : db.getUsers()) {
				if (usr instanceof Admin) continue;
				list.add(new UserManager.UserCell(usr.getId(), usr.getName(), usr.getSurname(),
						usr.getAddress(), usr.getPhoneNumber(), usr.getClass().getSimpleName()));
			}
		} else if (user instanceof Librarian) {
			for (User usr : db.getUsers()) {
				if (usr instanceof Librarian || usr instanceof Admin) continue;
				list.add(new UserManager.UserCell(usr.getId(), usr.getName(), usr.getSurname(),
						usr.getAddress(), usr.getPhoneNumber(), usr.getClass().getSimpleName()));
			}
		}

		return list;
	}

	/**
	 * Get all documents from database.
	 *
	 * @return All documents form database.
	 */
	ObservableList<DocumentManager.DocCell> getAllDocs() {
		ObservableList<DocumentManager.DocCell> list = FXCollections.observableArrayList();

		connectToDatabase();
		for (Document doc : db.getDocumentList()) {
			list.add(new DocumentManager.DocCell(doc.getID(), doc.getTitle(), doc.getAuthors(),
					doc.getClass().getSimpleName(), doc.getNumberOfCopies(),
					doc.isAllowedForStudents(), doc.isReference()));
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
	boolean authorize(Credentials credentials) {
		connectToDatabase();
		try {
			user = db.authorise(credentials.getLogin(), credentials.getPassword());
			loggedIn = true;
		} catch (NoSuchElementException e) {
			loggedIn = false;
		}

		Runtime.getRuntime().addShutdownHook(new Thread(this::deauthorize));

		return loggedIn;
	}

	/**
	 * Deauthorize current user.
	 */
	void deauthorize() {
		assert loggedIn;
		user = null;
		loggedIn = false;
		closeDatabaseConnection();
	}

	public ObservableList<DocItem> search(List<String> keywords) {
		HashSet<Document> searchResult = new HashSet<>();

		connectToDatabase();
		for (Document doc : db.getDocumentList()) {
			for (String keyword : keywords) {
				String docKeywords = (doc.getKeyWords() +
						doc.getTitle() + doc.getAuthors()).toLowerCase();
				if (docKeywords.contains(keyword.toLowerCase())) {
					searchResult.add(doc);
				}
			}
		}


		ObservableList<DocItem> resultItems = FXCollections.observableArrayList();

		for (Document doc : searchResult) {
			resultItems.add(new DocItem(doc.getTitle(), doc.getAuthors(), doc.getID()));
		}

		return resultItems;
	}

	/**
	 * Get documents for user.
	 *
	 * @return List of user documents.
	 */
	ObservableList<UserDocs.MyDocsView> getUserDocs() {
		ObservableList<UserDocs.MyDocsView> list = FXCollections.observableArrayList();
		List<Debt> debts = new ArrayList<>();

		connectToDatabase();
		if (user instanceof Patron) {
			debts = db.getDebtsForUser(user.getId());
		}

		for (Debt debt : debts) {
			Document doc = db.getDocument(debt.getDocumentId());

			assert doc != null;
			list.add(new UserDocs.MyDocsView(doc.getTitle(), debt.daysLeft(), debt.getDebtId()));
		}


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
	ObservableList<UserDocs.WaitlistView> getWaitList() {
		ObservableList<UserDocs.WaitlistView> waitlist = FXCollections.observableArrayList();
		List<Request> requests;
		connectToDatabase();
		requests = db.getRequestsForPatron(user.getId());
		for (Request request : requests) {
			Document doc = db.getDocument(request.getIdDocument());
			waitlist.add(new UserDocs.WaitlistView(doc.getTitle(), 0,
					request.getRequestId()));
		}


		return waitlist;
	}

	/**
	 * Get renew requests for user.
	 *
	 * @return Renew requests for users.
	 */
	ObservableList<ApprovalCell> getRenewRequests() {
		ObservableList<ApprovalCell> list = FXCollections.observableArrayList();

		connectToDatabase();
		for (Request request : db.getRenewRequests()) {
			Document doc = db.getDocument(request.getIdDocument());
			Patron pat = db.getPatron(request.getIdPatron());
			list.add(new ApprovalCell(request.getRequestId(),
					doc.getTitle(), doc.getID(),
					pat.getName() + " " + pat.getSurname(), pat.getId()));
		}


		return list;
	}

	/**
	 * Add new user to database.
	 *
	 * @param newUser New user.
	 */
	void addNewUser(User newUser) {
		connectToDatabase();
		if (user instanceof Admin) {
			((Admin) user).addLibrarian((Librarian) newUser, db);
		} else {
			((Librarian) user).registerPatron((Patron) newUser, db);
		}

	}

	/**
	 * Add new document to database.
	 *
	 * @param document New document.
	 */
	void addNewDocument(Document document) {
		connectToDatabase();
		if (user instanceof Librarian) {
			if (document instanceof Book) {
				((Librarian) user).addBook((Book) document, db);
			}
			if (document instanceof JournalArticle) {
				((Librarian) user).addArticle((JournalArticle) document, db);
			}
			if (document instanceof AudioVideoMaterial) {
				((Librarian) user).addAV((AudioVideoMaterial) document, db);
			}
		}

	}

	/**
	 * Edit user record in database.
	 *
	 * @param userID   User ID.
	 * @param column   Record column.
	 * @param newValue New value.
	 */
	void editUser(int userID, String column, String newValue) {
		connectToDatabase();
		db.editUserColumn(userID, column, newValue);

	}

	/**
	 * Edit document record in database.
	 *
	 * @param docID    Document ID.
	 * @param column   Record column.
	 * @param newValue New value.
	 */
	void editDocument(int docID, String column, String newValue) {
		connectToDatabase();
		db.editDocumentColumn(docID, column, newValue);

	}

	void deleteDocument(int docID) {
		connectToDatabase();
		if (user instanceof Librarian) {
			((Librarian) user).deleteDocument(docID, db);
		}


	}

	/**
	 * Get take requests.
	 *
	 * @return Take requests.
	 */
	ObservableList<ApprovalCell> getTakeRequests() {
		ObservableList<ApprovalCell> list = FXCollections.observableArrayList();

		connectToDatabase();
		for (Request request : db.getRequests()) {
			Document doc = db.getDocument(request.getIdDocument());
			Patron pat = db.getPatron(request.getIdPatron());
			list.add(new ApprovalCell(request.getRequestId(),
					doc.getTitle(), doc.getID(),
					pat.getName() + " " + pat.getSurname(), pat.getId()));
		}


		return list;
	}

	/**
	 * Get debts for current user.
	 *
	 * @return List of debts.
	 */
	public ObservableList<DebtsManager.DebtCell> getUserDebts() {
		ObservableList<DebtsManager.DebtCell> list = FXCollections.observableArrayList();

		connectToDatabase();
		for (Debt debt : db.getDebtsForUser(user.getId())) {
			Patron pat = db.getPatron(debt.getPatronId());
			Document doc = db.getDocument(debt.getDocumentId());
			list.add(new DebtsManager.DebtCell(debt.getDebtId(),
					pat.getName() + " " + pat.getSurname(), pat.getId(),
					doc.getTitle(), doc.getID(),
					debt.getBookingDate().toString(),
					debt.getExpireDate().toString()));
		}


		return list;
	}

	/**
	 * All debts.
	 *
	 * @return Get debts.
	 */
	ObservableList<DebtsManager.DebtCell> getAllDebts() {
		ObservableList<DebtsManager.DebtCell> list = FXCollections.observableArrayList();

		connectToDatabase();
		for (Debt debt : db.getDebtsList()) {
			Patron pat = db.getPatron(debt.getPatronId());
			Document doc = db.getDocument(debt.getDocumentId());
			list.add(new DebtsManager.DebtCell(debt.getDebtId(),
					pat.getName() + " " + pat.getSurname(), pat.getId(),
					doc.getTitle(), doc.getID(),
					debt.getBookingDate().toString(),
					debt.getExpireDate().toString()));
		}


		return list;
	}

	/**
	 * Make outstanding request.
	 *
	 * @param patronID   Patron's ID.
	 * @param documentID Document ID.
	 */
	void makeOutstandingRequest(int patronID, int documentID) {
		if (user instanceof Librarian) {
			connectToDatabase();
			Patron patron = db.getPatron(patronID);
			Document document = db.getDocument(documentID);
			Request request = new Request(patron, document, new Date(), false);
			((Librarian) user).makeOutstandingRequest(request, db);

		}
	}

	void makeOutstandingRequest(int requestID) {
		if (user instanceof Librarian) {
			connectToDatabase();
			Request request = db.getRequest(requestID);
			((Librarian) user).makeOutstandingRequest(request, db);

		}
	}

	void confirmReturn(int debtID) {
		if (user instanceof Librarian) {
			connectToDatabase();
			((Librarian) user).confirmReturn(debtID, db);

		}
	}

	/**
	 * Get notifications for current user.
	 *
	 * @return List of notifications.
	 */
	ObservableList<Notification> getUserNotifications() {
		ObservableList<Notification> list = FXCollections.observableArrayList();

		connectToDatabase();
		list.addAll(db.getNotificationsForUser(user.getId()));


		return list;
	}

	/**
	 * Get list of logs.
	 *
	 * @return List of logs.
	 */
	ObservableList<String> getLogs() {
		ObservableList<String> logs = FXCollections.observableArrayList();
		connectToDatabase();
		logs.addAll(db.getLog());


		return logs;
	}

	void deleteUser(int id) {
		connectToDatabase();
		if (user instanceof Admin) {
			((Admin) user).deleteLibrarian(id, db);
		}
		if (user instanceof Librarian) {
			((Librarian) user).deletePatron(id, db);
		}

	}

	/**
	 * Check whether current user can take document.
	 *
	 * @param docID Document ID.
	 * @return <code>true</code> if user can take document, <code>false</code> otherwise.
	 */
	boolean canTakeDocument(int docID) {
		if (user == null) return false;
		if (user instanceof Librarian || user instanceof Admin) {
			return false;
		} else {
			connectToDatabase();
			return ((Patron) user).canRequestDocument(docID, db);
		}
	}

	/**
	 * Request document.
	 *
	 * @param docID Document ID.
	 */
	void bookOrRequest(int docID) {
		if (canTakeDocument(docID)) {
			connectToDatabase();
			((Patron) user).makeRequest(docID, db);

		}
	}

	/**
	 * Make renew request.
	 *
	 * @param debtID Debt to renew.
	 */
	void makeRenewRequest(int debtID) {
		if (user instanceof Patron) {
			connectToDatabase();
			((Patron) user).sendRenewRequest(debtID, db);

		}
	}

	void makeReturnRequest(int debtID) {
		if (user instanceof Patron) {
			connectToDatabase();
			Debt debt = db.getDebt(debtID);
			((Patron) user).returnDocument(debt.getDocumentId(), db);

		}
	}

	/**
	 * Accept booking request.
	 *
	 * @param requestID Request ID.
	 */
	void acceptBookRequest(int requestID) {
		if (user instanceof Librarian) {
			System.out.println("Current user is librarian, asking database to accept...");
			connectToDatabase();
			Request request = db.getRequest(requestID);
			System.out.println("Submitting request " + request.getRequestId());
			((Librarian) user).submitRequest(request, db);

		} else {
			System.out.println("Current user is not librarian.");
		}
	}

	/**
	 * Reject booking request.
	 *
	 * @param requestID Request ID.
	 */
	void rejectBookRequest(int requestID) {
		if (user instanceof Librarian) {
			System.out.println("Current user is librarian, asking database to reject...");
			connectToDatabase();
			Request request = db.getRequest(requestID);
			System.out.println("Deleting request " + request.getRequestId());
			((Librarian) user).deleteRequest(request, db);

		} else {
			System.out.println("Current user is not a librarian.");
		}
	}

	/**
	 * Accept renew request.
	 *
	 * @param requestID Request ID.
	 */
	void acceptRenewRequest(int requestID) {
		if (user instanceof Librarian) {
			System.out.println("Current user is librarian, asking database to accept...");
			connectToDatabase();
			Request request = db.getRequest(requestID);
			System.out.println("Submitting request " + request.getRequestId());
			((Librarian) user).confirmRenew(request, db);

		} else {
			System.out.println("Current user is not librarian.");
		}
	}

	/**
	 * Reject renew request.
	 *
	 * @param requestID Request ID.
	 */
	void rejectRenewRequest(int requestID) {
		if (user instanceof Librarian) {
			System.out.println("Current user is librarian, asking database to accept...");
			connectToDatabase();
			Request request = db.getRequest(requestID);
			System.out.println("Submitting request " + request.getRequestId());
			((Librarian) user).deleteRequest(request, db);

		} else {
			System.out.println("Current user is not librarian.");
		}
	}

	/**
	 * Determines user type.
	 *
	 * @param usr User to check.
	 * @return User type.
	 */
	@Deprecated
	UserType determineUserType(User usr) {
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
	@Deprecated
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
	@Deprecated
	public enum UserType {
		LIBRARIAN, PATRON,
		STUDENT, INSTRUCTOR, TA, VP, PROFESSOR
	}
}
