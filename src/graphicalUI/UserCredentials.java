package graphicalUI;

import users.Patron;
import users.User;

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

	public User authorize() {
		authorized = true;
		return new Patron("Evdakia", "1, Universitetskaya st., Innopolis", "+79871234567", "student", 2);
	}
}
