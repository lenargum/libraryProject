package users;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.JournalArticle;
import tools.Database;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * This class describes user in library system.
 *
 * @author Madina Gafarova
 */
public abstract class User {
	/**
	 * users.User ID.
	 */
	private int id;

	/**
	 * users' first name.
	 */
	private String name;

	/**
	 * users' last name.
	 */
	private String surname;

	/**
	 * users' living address.
	 */
	private String address;

	/**
	 * users' phone number.
	 */
	private String phoneNumber;

	/**
	 * users.User login.
	 */
	private String login;

	/**
	 * users.User password.
	 */
	private String password;

	/**
	 * Initialize new user.
	 *
	 * @param login       Login.
	 * @param password    Password.
	 * @param name        First name.
	 * @param surname     Last name.
	 * @param phoneNumber Phone number.
	 * @param address     Living address.
	 */
	public User(String login, String password, String name, String surname, String phoneNumber, String address) {
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.login = login;
		this.password = password;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Get user ID.
	 *
	 * @return users.User ID.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set new user ID.
	 *
	 * @param id New ID.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the users' first name.
	 *
	 * @return First name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the new users' first name.
	 *
	 * @param name New first name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the users' last name.
	 *
	 * @return Last name.
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Set the new users' last name.
	 *
	 * @param surname New last name.
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Get the users' living address.
	 *
	 * @return Living address.
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the users' living address.
	 *
	 * @param address New living address.
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the users' phone number.
	 *
	 * @return Phone number,
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Set the new users' phone number.
	 *
	 * @param phoneNumber New phone number.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Get the user login.
	 *
	 * @return users.User login.
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Set the new user login.
	 *
	 * @param login New login.
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Get the user password.
	 *
	 * @return users.User password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the new user password.
	 *
	 * @param password New password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get all the documents stored in the library in string notation.
	 *
	 * @param database tools.Database that stores the document information.
	 * @return List of strings describes documents in library.
	 * @see java.util.List
	 * @see ArrayList
	 */
	public ArrayList<String> getLibrary(Database database)  {
		return database.getDocumentStringList();
	}

	/**
	 * Get all the books stored in the library.
	 *
	 * @param database tools.Database that stores the document information.
	 * @return List of books in library.
	 * @see java.util.List
	 * @see ArrayList
	 */
	public ArrayList<Book> getListOfBooks(Database database) throws SQLException {
		return database.getBookList();
	}

	/**
	 * Get all the audios/videos stored in the library.
	 *
	 * @param database tools.Database that stores the document information.
	 * @return List of audios/videos in the library.
	 * @see java.util.List
	 * @see ArrayList
	 */
	public ArrayList<AudioVideoMaterial> getListOfAVs(Database database) throws SQLException {
		return database.getAVList();
	}

	/**
	 * Get all the articles stored in the library.
	 *
	 * @param database tools.Database that stores the document information.
	 * @return List of articles in the library.
	 * @see java.util.List
	 * @see ArrayList
	 */
	public ArrayList<JournalArticle> getListOfArticles(Database database) throws SQLException, ParseException {
		return database.getArticleList();
	}
}
