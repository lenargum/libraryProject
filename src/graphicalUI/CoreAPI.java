package graphicalUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CoreAPI {
	private Credentials credentials;
	private boolean loggedIn;

	public CoreAPI() {
		loggedIn = false;
	}

	public List<DocItem> getAllBooks() {
		List<DocItem> list = new LinkedList<>();
		for (int i = 0; i < 20; i++) {
			list.add(new DocItem("Introduction to Something " + (i + 1), "Author Authorovich", i + 1));
		}

		return list;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void authorize(Credentials credentials) {
		this.credentials = credentials;
		loggedIn = true;

		// to be continued
	}

	public void deauthorize() {
		assert loggedIn;
		credentials = null;
		loggedIn = false;
	}

	public ObservableList<UserDocs.MyDocsView> getUserBooks() {
		ObservableList<UserDocs.MyDocsView> list = FXCollections.observableArrayList();
		list.add(new UserDocs.MyDocsView("Boookee 1", 12, 1));
		list.add(new UserDocs.MyDocsView("AVVEE", 3, 2));
		list.add(new UserDocs.MyDocsView("Porn", -16, 3));
		list.add(new UserDocs.MyDocsView("Introduction to Something", 9, 4));
		list.add(new UserDocs.MyDocsView("Another book", 5, 5));
		list.add(new UserDocs.MyDocsView("MORE DOCS", 10, 6));
		list.add(new UserDocs.MyDocsView("Heeey", 2, 7));

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
		waitlist.add(new UserDocs.WaitlistView("Cormen!", 100500, 1));
		waitlist.add(new UserDocs.WaitlistView("Some Strange Book", 0, 2));

		return waitlist;
	}

	public ObservableList<ApprovalCell> getRenewRequests() {
		ObservableList<ApprovalCell> list = FXCollections.observableArrayList();
		list.addAll(new ApprovalCell("Sdbdjks", 1, "Succi", 1),
				new ApprovalCell("ubjksd", 2, "You", 2));

		return list;
	}

	public ObservableList<ApprovalCell> getTakeRequests() {
		ObservableList<ApprovalCell> list = FXCollections.observableArrayList();
		list.addAll(new ApprovalCell("hfiushfid", 1, "Succi", 1),
				new ApprovalCell("sidhvidhs", 2, "You", 2));

		return list;
	}

	public ObservableList<DebtsManager.DebtCell> getUserDebts() {
		ObservableList<DebtsManager.DebtCell> list = FXCollections.observableArrayList();
		list.add(new DebtsManager.DebtCell(1, "BIsufubsu", 1, "Rivera",
				1, "22/02/2018", "08/04/2018"));

		return list;
	}

	public UserType userType() {
		return UserType.LIBRARIAN;
	}

	public static enum UserType {
		LIBRARIAN, PATRON
	}
}
