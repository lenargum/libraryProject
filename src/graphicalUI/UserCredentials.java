package graphicalUI;

public class UserCredentials {
	private char[] login;
	private char[] password;
	private boolean authorized;

	public UserCredentials(String login, String password) {
		this.login = login.toCharArray();
		this.password = password.toCharArray();
		this.authorized = false;
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
		return authorized;
	}

	public VirtualServer authorize() {
		//return new Patron("Name", "1, Universitetskaya", "+79871234567", "STUDENT", 2);
		VirtualServer server = new VirtualServer();
		authorized = server.authorize(String.copyValueOf(login));
		return server;
	}
}
