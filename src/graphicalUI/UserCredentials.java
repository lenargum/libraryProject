package graphicalUI;

/**
 * Contains user credentials.
 * Authorizes current user.
 */
public class UserCredentials {
    private char[] login;
    private char[] password;
    private boolean authorized;

    /**
     * Main constructor.
     *
     * @param login    User login.
     * @param password User password.
     */
    public UserCredentials(String login, String password) {
        this.login = login.toCharArray();
        this.password = password.toCharArray();
        this.authorized = false;
    }

    /**
     * Get login.
     *
     * @return User login.
     */
    public char[] getLogin() {
        return login;
    }

    /**
     * Get login in string.
     *
     * @return User login string.
     */
    public String getLoginString() {
        return String.copyValueOf(login);
    }

    /**
     * Checks is current user authorized.
     *
     * @return {@code True} if current user authorized, {@code false} otherwise.
     */
    public boolean isAuthorized() {
        return authorized;
    }

    /**
     * Authorizes current user.
     *
     * @return Authorized server API.
     */
    public ServerAPI authorize() {
        ServerAPI server = new ServerAPI();
        authorized = server.authorize(login, password);
        return server;
    }
}
