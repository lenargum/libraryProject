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

	public ServerAPI authorize() {
		ServerAPI server = new ServerAPI();
		authorized = server.authorize(login, password);
		return server;
	}
}
