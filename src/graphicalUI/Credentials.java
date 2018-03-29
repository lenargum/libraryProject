package graphicalUI;

/**
 * Contains user credentials.
 *
 * @author Ruslan Shakirov
 */
public class Credentials {
	/**
	 * User login.
	 */
	private String login;

	/**
	 * User password.
	 */
	private char[] password;

	/**
	 * Create new credentials.
	 *
	 * @param login    User login.
	 * @param password User password.
	 */
	public Credentials(String login, String password) {
		this.login = login;
		this.password = password.toCharArray();
	}

	/**
	 * Get user login.
	 *
	 * @return User login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Get user password.
	 *
	 * @return User password.
	 */
	public String getPassword() {
		return new String(password);
	}
}
