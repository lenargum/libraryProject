public class UserCredentials {
	private char[] login;
	private char[] password;

	public UserCredentials(String login, String password) {
		this.login = login.toCharArray();
		this.password = password.toCharArray();
	}

	public char[] getLogin() {
		return login;
	}

	public String getLoginString() {
		return String.copyValueOf(login);
	}

	public char[] getPassword() {
		return password;
	}

	public boolean isAuthorized() {
		//TODO Implement isAuthorized
		return true;
	}
}
