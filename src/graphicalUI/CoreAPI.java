package graphicalUI;

public class CoreAPI {
	private Credentials credentials;
	private boolean loggedIn;

	public CoreAPI() {
		loggedIn = false;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void authorize(Credentials credentials) {
		this.credentials = credentials;
		loggedIn = true;
	}

	public UserType userType() {
		return UserType.LIBRARIAN;
	}

	public static enum UserType {
		LIBRARIAN, PATRON
	}


}
