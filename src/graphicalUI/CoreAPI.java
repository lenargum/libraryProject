package graphicalUI;

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

	public void deauthorize(){
		assert loggedIn;
		credentials = null;
		loggedIn = false;
	}

	public UserType userType() {
		return UserType.LIBRARIAN;
	}

	public static enum UserType {
		LIBRARIAN, PATRON
	}
}
