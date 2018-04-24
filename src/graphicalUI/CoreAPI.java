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
		db.connect();
		User result;
		try {
			result = db.getLibrarian(userID);
		} catch (NoSuchElementException e) {
			result = db.getPatron(userID);
		} finally {
			db.close();
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
		db.connect();
		Document result = db.getDocument(docID);
		db.close();
		return result;
	}

	/**
	 * Get all books from database.
	 *
	 * @return List of all documents.
	 */
	List<DocItem> getAllBooks() {
		List<DocItem> list = new LinkedList<>();
		List<Document> documents;

		db.connect();
		documents = db.getDocumentList();
		db.close();

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

		db.connect();
		if (user instanceof Admin) {
			for (User usr : db.getUsers()) {
				if (usr instanceof Admin) continue;
				list.add(new UserManager.UserCell(usr.getId(), usr.getName(), usr.getSurname(),
						usr.getAddress(), usr.getPhoneNumber(), usr.getClass().getSimpleName()));
			}
		} else {
			for (User usr : db.getUsers()) {
				if (usr instanceof Librarian || usr instanceof Admin) continue;
				list.add(new UserManager.UserCell(usr.getId(), usr.getName(), usr.getSurname(),
						usr.getAddress(), usr.getPhoneNumber(), usr.getClass().getSimpleName()));
			}
			for (User usr : db.getUsers()) {
				list.add(new UserManager.UserCell(usr.getId(), usr.getName(), usr.getSurname(),
						usr.getAddress(), usr.getPhoneNumber(), usr.getClass().getSimpleName()));
			}
			db.close();

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

		db.connect();
		for (Document doc : db.getDocumentList()) {
			list.add(new DocumentManager.DocCell(doc.getID(), doc.getTitle(), doc.getAuthors(),
					doc.getClass().getSimpleName(), doc.getNumberOfCopies(),
					doc.isAllowedForStudents(), doc.isReference()));
		}
		db.close();

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
		db.connect();
		try {
			user = db.authorise(credentials.getLogin(), credentials.getPassword());
			loggedIn = true;
		} catch (NoSuchElementException e) {
			loggedIn = false;
		} finally {
			db.close();
		}

		return loggedIn;
	}

	/**
	 * Deauthorize current user.
	 */
	void deauthorize() {
		assert loggedIn;
		user = null;
		loggedIn = false;
	}

	ObservableList<DocItem> search(List<String> keywords) {
		HashSet<Document> searchResult = new HashSet<>();

		db.connect();
		for (Document doc : db.getDocumentList()) {
			for (String keyword : keywords) {
				String docKeywords = (doc.getKeyWords() +
						doc.getTitle() + doc.getAuthors()).toLowerCase();
				if (docKeywords.contains(keyword.toLowerCase())) {
					searchResult.add(doc);
				}
			}
		}
		db.close();

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
		List<Integer> documents = new ArrayList<>();
		if (user instanceof Patron) {
			documents = ((Patron) user).getListOfDocumentsPatron();
		}

		db.connect();
		for (Integer id : documents) {
			Document doc;
			Debt debt;
			doc = db.getDocument(id);
			debt = db.getDebt(db.findDebtID(user.getId(), id));

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
	ObservableList<UserDocs.WaitlistView> getWaitList() {
		ObservableList<UserDocs.WaitlistView> waitlist = FXCollections.observableArrayList();
		List<Request> requests;
		db.connect();
		requests = db.getRequestsForPatron(user.getId());
		for (Request request : requests) {
			Document doc = db.getDocument(request.getIdDocument());
			waitlist.add(new UserDocs.WaitlistView(doc.getTitle(), 0,
					request.getRequestId()));
		}
		db.close();

		return waitlist;
	}

	/**
	 * Get renew requests for user.
	 *
	 * @return Renew requests for users.
	 */
	ObservableList<ApprovalCell> getRenewRequests() {
		ObservableList<ApprovalCell> list = FXCollections.observableArrayList();

		db.connect();
		for (Request request : db.getRenewRequests()) {
			Document doc = db.getDocument(request.getIdDocument());
			Patron pat = db.getPatron(request.getIdPatron());
			list.add(new ApprovalCell(request.getRequestId(),
					doc.getTitle(), doc.getID(),
					pat.getName() + " " + pat.getSurname(), pat.getId()));
		}
		db.close();

		return list;
	}

	/**
	 * Add new user to database.
	 *
	 * @param newUser New user.
	 */
	void addNewUser(User newUser) {
		db.connect();
		if (newUser instanceof Librarian) {
			db.insertLibrarian((Librarian) newUser);
			newUser.setId(db.getLibrarianID((Librarian) newUser));
		} else {
			db.insertPatron((Patron) newUser);
			newUser.setId(db.getPatronID((Patron) newUser));
		}
		db.close();
	}

	/**
	 * Add new document to database.
	 *
	 * @param document New document.
	 */
	void addNewDocument(Document document) {
		db.connect();
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
		db.close();
	}

	/**
	 * Edit user record in database.
	 *
	 * @param userID   User ID.
	 * @param column   Record column.
	 * @param newValue New value.
	 */
	void editUser(int userID, String column, String newValue) {
		db.connect();
		db.editUserColumn(userID, column, newValue);
		db.close();
	}

	/**
	 * Edit document record in database.
	 *
	 * @param docID    Document ID.
	 * @param column   Record column.
	 * @param newValue New value.
	 */
	void editDocument(int docID, String column, String newValue) {
		db.connect();
		db.editDocumentColumn(docID, column, newValue);
		db.close();
	}

	void deleteDocument(int docID) {
		db.connect();
		if (user instanceof Librarian) {
			((Librarian) user).deleteDocument(docID, db);
		}
		if (user instanceof Admin) {
			((Admin) user).deleteDocument(docID, db);
		}
		db.close();
	}

	/**
	 * Get take requests.
	 *
	 * @return Take requests.
	 */
	ObservableList<ApprovalCell> getTakeRequests() {
		ObservableList<ApprovalCell> list = FXCollections.observableArrayList();

		db.connect();
		for (Request request : db.getRequests()) {
			Document doc = db.getDocument(request.getIdDocument());
			Patron pat = db.getPatron(request.getIdPatron());
			list.add(new ApprovalCell(request.getRequestId(),
					doc.getTitle(), doc.getID(),
					pat.getName() + " " + pat.getSurname(), pat.getId()));
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
		for (Debt debt : db.getDebtsForUser(user.getId())) {
			Patron pat = db.getPatron(debt.getPatronId());
			Document doc = db.getDocument(debt.getDocumentId());
			list.add(new DebtsManager.DebtCell(debt.getDebtId(),
					pat.getName() + " " + pat.getSurname(), pat.getId(),
					doc.getTitle(), doc.getID(),
					debt.getBookingDate().toString(),
					debt.getExpireDate().toString()));
		}
		db.close();

		return list;
	}

	/**
	 * @return
	 */
	ObservableList<DebtsManager.DebtCell> getAllDebts() {
		ObservableList<DebtsManager.DebtCell> list = FXCollections.observableArrayList();

		db.connect();
		for (Debt debt : db.getDebtsList()) {
			Patron pat = db.getPatron(debt.getPatronId());
			Document doc = db.getDocument(debt.getDocumentId());
			list.add(new DebtsManager.DebtCell(debt.getDebtId(),
					pat.getName() + " " + pat.getSurname(), pat.getId(),
					doc.getTitle(), doc.getID(),
					debt.getBookingDate().toString(),
					debt.getExpireDate().toString()));
		}
		db.close();

		return list;
	}

	/**
	 * Make outstanding request.
	 *
	 * @param requestID Request ID.
	 */
	void makeOutstandingRequest(int requestID) {
		db.connect();
		Request request = db.getRequest(requestID);
		((Librarian) user).makeOutstandingRequest(request, db);
		db.close();
	}

	/**
	 * Get notifications for current user.
	 *
	 * @return List of notifications.
	 */
	ObservableList<Notification> getUserNotifications() {
		ObservableList<Notification> list = FXCollections.observableArrayList();

		db.connect();
		list.addAll(db.getNotificationsForUser(user.getId()));
		db.close();

		return list;
	}

	/**
	 * Check whether current user can take document.
	 *
	 * @param docID Document ID.
	 * @return <code>true</code> if user can take document, <code>false</code> otherwise.
	 */
	boolean canTakeDocument(int docID) {
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
	void bookOrRequest(int docID) {
		if (canTakeDocument(docID)) {
			db.connect();
			((Patron) user).makeRequest(docID, db);
			db.close();
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
			db.connect();
			Request request = db.getRequest(requestID);
			System.out.println("Submitting request " + request.getRequestId());
			((Librarian) user).submitRequest(request, db);
			db.close();
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
			db.connect();
			Request request = db.getRequest(requestID);
			System.out.println("Deleting request " + request.getRequestId());
			((Librarian) user).deleteRequest(request, db);
			db.close();
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
			db.connect();
			Request request = db.getRequest(requestID);
			System.out.println("Submitting request " + request.getRequestId());
			((Librarian) user).confirmRenew(request, db);
			db.close();
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
			Request request = db.getRequest(requestID);
			System.out.println("Submitting request " + request.getRequestId());
			db.close();
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
