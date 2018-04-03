package tools;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import users.Librarian;
import users.Patron;
import users.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * tools.Database API for library system.
 * Used for accessing, reading and writing data into database.
 *
 * @author Lenar Gumerov
 */
public class Database {
	/**
	 * Current database connection session.
	 */
	private Connection connection;

	/**
	 * Stores current connection state.
	 */
	private boolean connected;

	/**
	 * Initialize current session, connect to database.
	 * <p>
	 * IMPORTANT: Please, close the connection when done, see <code>{@link Database}.close()</code>
	 */
	public void connect() {
		try {
			if (isConnected()) {
				System.out.println("tools.Database: Already connected");
				return;
			}

			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				throw new Exception("tools.Database: tools.Database driver not found");
			}

			String connectionURL = "jdbc:sqlite:library.db";

			connection = DriverManager.getConnection(connectionURL);
			connected = true;
			System.out.println("tools.Database: Connection successful");
		} catch (Exception e) {

			System.out.println("tools.Database: Connection failed");
		}
	}

	/**
	 * Get the current connection state.
	 *
	 * @return {@code true} if connection established, {@code false} otherwise.
	 */
	private boolean isConnected() {
		return this.connected;
	}

	/**
	 * Stop the current session, disconnect from database.
	 */
	public void close() {
		if (connection != null) {
			try {
				this.connection.close();
				System.out.println("tools.Database: Connection closed");
				this.connected = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("tools.Database: Connection was not opened, already closed.");
		}
	}

	/**
	 * @param MySQLStatement SQL command.
	 * @throws SQLException syntax or command error.
	 */
	private void executeUpdate(String MySQLStatement) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(MySQLStatement);
		statement.close();
	}

	/**
	 * @param MySQLStatement SQL command.
	 * @throws SQLException syntax or command error.
	 */
	private void execute(String MySQLStatement) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(MySQLStatement);
		statement.close();
	}

	/**
	 * @param MySQLStatement SQL query.
	 * @return ResultSet queried table.
	 * @throws SQLException syntax or query error.
	 */
	private ResultSet executeQuery(String MySQLStatement) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(MySQLStatement);
	}

	/**
	 * Insert the user with following parameters into database.
	 *
	 * @param login     users.User login.
	 * @param password  users.User password.
	 * @param status    users.User status.
	 * @param firstName First name.
	 * @param lastName  Last name.
	 * @param phone     Phone number.
	 * @param address   Living address.
	 * @throws SQLException user with such login already exists or inserted user with incorrect type.
	 */
	private void insertUser(String login, String password, String status,
	                        String firstName, String lastName, String phone, String address) throws SQLException {
		this.execute("INSERT INTO users(login, password, status, firstname, lastname, phone, address)" +
				" VALUES('" + login + "','" + password + "','"
				+ status + "','" + firstName + "','" + lastName + "','" +
				phone + "','" + address + "')");
	}

	/**
	 * Insert provided patron into database.
	 *
	 * @param patron users.Patron to insert.
	 * @throws SQLException user with such login exists.
	 * @see Patron
	 */
	public void insertPatron(Patron patron) throws SQLException {
		String status = "STUDENT";
		if (!patron.getStatus().toLowerCase().equals("librarian")) {
			status = patron.getStatus().toUpperCase();
		}
		insertUser(patron.getLogin(), patron.getPassword(), status, patron.getName(), patron.getSurname(), patron.getPhoneNumber(), patron.getAddress());
	}

	/**
	 * Insert provided librarian into database.
	 *
	 * @param librarian users.Librarian to insert.
	 * @throws SQLException user with such login exists.
	 * @see Librarian
	 */
	public void insertLibrarian(Librarian librarian) throws SQLException {
		insertUser(librarian.getLogin(), librarian.getPassword(), "LIBRARIAN", librarian.getName(), librarian.getSurname(), librarian.getPhoneNumber(), librarian.getAddress());
	}

	/**
	 * Insert document with following properties into database.
	 *
	 * @param name                 documents.Document title.
	 * @param authors              documents.Document authors.
	 * @param isAllowedForStudents Student allowance.
	 * @param numOfCopies          Count of copies.
	 * @param isReference          documents.Document reference status.
	 * @param price                documents.Document price.
	 * @param keywords             Search keywords.
	 * @param type                 documents.Document type.
	 * @param publisher            documents.Document publisher.
	 * @param edition              Year edition.
	 * @param bestseller           Bestseller status.
	 * @param journalName          Journal title.
	 * @param issue                Article issue.
	 * @param editor               documents.Document editor.
	 * @param publicationDate      documents.Document publication date.
	 * @throws SQLException inserted document with incorrect type.
	 */
	private void insertDocument(String name, String authors, boolean isAllowedForStudents, int numOfCopies,
	                            boolean isReference, double price, String keywords, String type, String publisher,
	                            int edition, boolean bestseller, String journalName, String issue, String editor,
	                            String publicationDate) throws SQLException {
		this.execute("INSERT INTO documents(name, authors, is_allowed_for_students," +
				" num_of_copies, is_reference, price, keywords, type, publisher, edition, bestseller," +
				" journal_name, issue, editor, publication_date)" +
				" VALUES('" + name + "','" + authors + "','" + isAllowedForStudents + "',"
				+ numOfCopies + ",'" + isReference + "'," + price + ",'" + keywords + "','" + type + "','"
				+ publisher + "'," + edition + ",'" + bestseller + "','" + journalName + "','" + issue + "','"
				+ editor + "','" + publicationDate + "')");

	}

	/**
	 * Insert provided book into database.
	 *
	 * @param book documents.Book to insert.
	 * @throws SQLException By default.
	 * @see Book
	 */
	public void insertBook(Book book) throws SQLException {
		String type = "BOOK";
		insertDocument(book.getTitle(), book.getAuthors(), book.isAllowedForStudents(), book.getNumberOfCopies(),
				book.isReference(), book.getPrice(), book.getKeyWords(), type, book.getPublisher(), book.getEdition(),
				book.isBestseller(), "-", "-", "-", "NULL");
		book.setID(this.getDocumentID(new Document(book.getTitle(), book.getAuthors(), book.isAllowedForStudents(),
				book.getNumberOfCopies(), book.isReference(), book.getPrice(), book.getKeyWords())));
	}

	/**
	 * Insert provided Audio/Video into database.
	 *
	 * @param av Audio/Video to insert.
	 * @throws SQLException By default.
	 * @see AudioVideoMaterial
	 */
	public void insertAV(AudioVideoMaterial av) throws SQLException {
		insertDocument(av.getTitle(), av.getAuthors(), av.isAllowedForStudents(), av.getNumberOfCopies(),
				av.isReference(), av.getPrice(), av.getKeyWords(), "AV", "-", 0, false,
				"-", "-", "-", "NULL");
		av.setID(this.getDocumentID(new Document(av.getTitle(), av.getAuthors(), av.isAllowedForStudents(), av.getNumberOfCopies(),
				av.isReference(), av.getPrice(), av.getKeyWords())));
	}

	/**
	 * Insert provided article into database.
	 *
	 * @param article Article to insert.
	 * @throws SQLException By default.
	 * @see JournalArticle
	 */
	public void insertArticle(JournalArticle article) throws SQLException {
		insertDocument(article.getTitle(), article.getAuthors(), article.isAllowedForStudents(),
				article.getNumberOfCopies(), article.isReference(), article.getPrice(), article.getKeyWords(),
				"ARTICLE", article.getPublisher(), 0, false, article.getJournalName(),
				article.getIssue(), article.getEditor(),
				(new SimpleDateFormat("yyyy-MM-dd")).format(article.getPublicationDate()));
		article.setID(this.getDocumentID(new Document(article.getTitle(), article.getAuthors(), article.isAllowedForStudents(),
				article.getNumberOfCopies(), article.isReference(), article.getPrice(), article.getKeyWords())));
	}

	/**
	 * Insert provided debt into database.
	 *
	 * @param debt tools.Debt to insert.
	 * @throws SQLException By default.
	 * @see Debt
	 */
	public void insertDebt(Debt debt) throws SQLException {
		execute("INSERT INTO debts(patron_id, document_id, booking_date, expire_date, fee, can_renew)"
				+ " VALUES(" + debt.getPatronId() + ", " + debt.getDocumentId() + ", \'"
				+ (new SimpleDateFormat("yyyy-MM-dd")).format(debt.getBookingDate()) + "\', \'" + (new SimpleDateFormat("yyyy-MM-dd")).format(debt.getExpireDate()) + "\', " + debt.getFee() + ", \'"
				+ debt.canRenew() + "\')");
		debt.setDebtId(this.findDebtID(debt.getPatronId(), debt.getDocumentId()));
	}

	/**
	 * Get the patrons list from database.
	 *
	 * @return List of patrons stored in database.
	 * @throws SQLException By default.
	 * @see List
	 * @see Patron
	 */
	public ArrayList<Patron> getPatronList() throws SQLException {
		ResultSet patronSet = executeQuery("SELECT * FROM users where status != 'LIBRARIAN'");
		ArrayList<Patron> patronList = new ArrayList<>();
		while (patronSet.next()) {
			Patron temp = new Patron(patronSet.getString(2),
					patronSet.getString(3), patronSet.getString(4), patronSet.getString(5),
					patronSet.getString(6), patronSet.getString(7), patronSet.getString(8));
			temp.setId(patronSet.getInt(1));
			patronList.add(temp);
		}
		if (patronList.size() != 0) {
			return patronList;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the librarians list from database.
	 *
	 * @return List of librarians stored in database.
	 * @throws SQLException By default.
	 * @see List
	 * @see Librarian
	 */
	public ArrayList<Librarian> getLibrarianList() throws SQLException {
		ResultSet librarianSet = executeQuery("SELECT * FROM users where status = 'LIBRARIAN'");
		ArrayList<Librarian> librarianList = new ArrayList<>();
		while (librarianSet.next()) {
			Librarian temp = new Librarian(librarianSet.getString(2),
					librarianSet.getString(3), librarianSet.getString(5),
					librarianSet.getString(6), librarianSet.getString(7), librarianSet.getString(8));
			temp.setId(librarianSet.getInt(1));
			librarianList.add(temp);
		}
		if (librarianList.size() != 0) {
			return librarianList;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the documents list from database.
	 *
	 * @return List of documents stored in database.
	 * @throws SQLException By default.
	 * @see List
	 * @see Document
	 */
	public ArrayList<Document> getDocumentList() throws SQLException {
		ResultSet documentSet = executeQuery("SELECT * FROM documents");
		ArrayList<Document> documentList = new ArrayList<>();
		while (documentSet.next()) {
			Document temp = new Document(documentSet.getString(2),
					documentSet.getString(3), Boolean.parseBoolean(documentSet.getString(4)),
					documentSet.getInt(5), Boolean.parseBoolean(documentSet.getString(6)),
					documentSet.getDouble(7), documentSet.getString(8));
			temp.setID(documentSet.getInt(1));
			documentList.add(temp);
		}
		if (documentList.size() != 0) {
			return documentList;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the documents descriptions from database.
	 *
	 * @return List of descriptions of documents stored in database.
	 * @throws SQLException By default.
	 * @see List
	 * @see String
	 * @see Document
	 */
	public ArrayList<String> getDocumentStringList() throws SQLException {
		ResultSet documentSet = executeQuery("SELECT * FROM documents");
		ArrayList<String> documentsTitleList = new ArrayList<>();
		while (documentSet.next()) {
			documentsTitleList.add(documentSet.getInt(1) + " \"" + documentSet.getString(2) + "\" "
					+ documentSet.getString(3) + " (" + documentSet.getString(4) + ") ");
		}
		if (documentsTitleList.size() != 0) {
			return documentsTitleList;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the book list from database.
	 *
	 * @return List of books stored in database.
	 * @throws SQLException By default.
	 * @see List
	 * @see Book
	 */
	public ArrayList<Book> getBookList() throws SQLException {
		ResultSet bookSet = executeQuery("SELECT * FROM documents where type = \'BOOK\';");
		ArrayList<Book> bookList = new ArrayList<>();
		while (bookSet.next()) {
			Book temp = new Book(bookSet.getString(2),
					bookSet.getString(3), Boolean.parseBoolean(bookSet.getString(4)), bookSet.getInt(5),
					Boolean.parseBoolean(bookSet.getString(6)), bookSet.getDouble(7), bookSet.getString(8),
					bookSet.getString(10), bookSet.getInt(11), Boolean.parseBoolean(bookSet.getString(12)));
			temp.setID(bookSet.getInt(1));
			bookList.add(temp);
		}
		if (bookList.size() != 0) {
			return bookList;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the audio/video list from database.
	 *
	 * @return List of audios/videos stored in database.
	 * @throws SQLException By default.
	 * @see List
	 * @see AudioVideoMaterial
	 */
	public ArrayList<AudioVideoMaterial> getAVList() throws SQLException {
		ResultSet AVSet = executeQuery("SELECT * FROM documents where type = \'AV\'");
		ArrayList<AudioVideoMaterial> AVList = new ArrayList<>();

		while (AVSet.next()) {
			AudioVideoMaterial temp = new AudioVideoMaterial(AVSet.getString(2),
					AVSet.getString(3), Boolean.parseBoolean(AVSet.getString(4)), AVSet.getInt(5),
					Boolean.parseBoolean(AVSet.getString(6)), AVSet.getDouble(7), AVSet.getString(8));
			temp.setID(AVSet.getInt(1));
			AVList.add(temp);
		}
		if (AVList.size() != 0) {
			return AVList;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the article list from database.
	 *
	 * @return List of articles stored in database.
	 * @throws SQLException   By default.
	 * @throws ParseException Incorrect data format.
	 * @see List
	 * @see JournalArticle
	 */
	public ArrayList<JournalArticle> getArticleList() throws SQLException, ParseException {
		ResultSet articleSet = executeQuery("SELECT * FROM documents where type = \'ARTICLE\'");
		ArrayList<JournalArticle> articleList = new ArrayList<>();
		while (articleSet.next()) {
			JournalArticle temp = new JournalArticle(articleSet.getString(2),
					articleSet.getString(3), Boolean.parseBoolean(articleSet.getString(4)),
					articleSet.getInt(5), Boolean.parseBoolean(articleSet.getString(6)),
					articleSet.getDouble(7), articleSet.getString(8),
					articleSet.getString(13), articleSet.getString(10),
					articleSet.getString(14), articleSet.getString(15),
					new SimpleDateFormat("yyyy-MM-dd").parse(articleSet.getString(16)));
			temp.setID(articleSet.getInt(1));
			articleList.add(temp);
		}
		if (articleList.size() != 0) {
			return articleList;
		}
		throw new NoSuchElementException();


	}

	/**
	 * Get the debts list from database.
	 *
	 * @return List of debts stored in database.
	 * @throws SQLException   By default.
	 * @throws ParseException Incorrect data format.
	 * @see List
	 * @see Debt
	 */
	public ArrayList<Debt> getDebtsList() throws SQLException, ParseException {
		ResultSet debtsSet = executeQuery("SELECT * FROM debts");
		ArrayList<Debt> debtsList = new ArrayList<>();
		while (debtsSet.next()) {
			Debt temp = new Debt(debtsSet.getInt(2), debtsSet.getInt(3),
					new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(4)),
					new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(5)),
					debtsSet.getDouble(6), Boolean.parseBoolean(debtsSet.getString(7)));
			temp.setDebtId(debtsSet.getInt(1));
			debtsList.add(temp);
		}

		if (debtsList.size() != 0) {
			return debtsList;
		}
		throw new NoSuchElementException();

	}

	/**
	 * Get the patron with following ID from database.
	 *
	 * @param ID Patrons' ID stored in database.
	 * @return users.Patron with following ID.
	 * @throws SQLException By default.
	 * @see Patron
	 */
	public Patron getPatron(int ID) throws SQLException {
		ResultSet patronSet = executeQuery("SELECT * FROM users where status != 'LIBRARIAN' and id = " + ID);
		if (patronSet.next()) {
			Patron temp = new Patron(patronSet.getString(2),
					patronSet.getString(3), patronSet.getString(4), patronSet.getString(5),
					patronSet.getString(6), patronSet.getString(7), patronSet.getString(8));
			temp.setId(patronSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	public User authorise(String login, String password) throws SQLException {
		ResultSet userSet = executeQuery("SELECT * FROM users WHERE login = \'" + login + "\' and password = \'" + password + "\'");
		if (userSet.next()) {
			Patron temp = new Patron(userSet.getString(2),
					userSet.getString(3), userSet.getString(4), userSet.getString(5),
					userSet.getString(6), userSet.getString(7), userSet.getString(8));
			temp.setId(userSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the librarian with following ID from database.
	 *
	 * @param ID Librarians' ID stored in database.
	 * @return users.Librarian with following ID.
	 * @throws SQLException By default.
	 * @see Librarian
	 */
	public Librarian getLibrarian(int ID) throws SQLException {
		ResultSet librarianSet = executeQuery("SELECT * FROM users where (status = 'LIBRARIAN') and id = " + ID);
		if (librarianSet.next()) {
			Librarian temp = new Librarian(librarianSet.getString(2),
					librarianSet.getString(3), librarianSet.getString(5),
					librarianSet.getString(6), librarianSet.getString(7), librarianSet.getString(8));
			temp.setId(librarianSet.getInt(1));
			return temp;
		}

		throw new NoSuchElementException();
	}

	/**
	 * Get the document with following ID from database.
	 *
	 * @param ID documents.Document ID stored in database.
	 * @return documents.Document with following ID.
	 * @throws SQLException By default.
	 * @see Document
	 */
	public Document getDocument(int ID) throws SQLException {
		ResultSet documentSet = executeQuery("SELECT * FROM documents where id = " + ID);
		if (documentSet.next()) {
			Document temp = new Document(documentSet.getString(2),
					documentSet.getString(3), Boolean.parseBoolean(documentSet.getString(4)),
					documentSet.getInt(5), Boolean.parseBoolean(documentSet.getString(6)),
					documentSet.getDouble(7), documentSet.getString(8));
			temp.setID(documentSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the book with following ID from database.
	 *
	 * @param ID documents.Book ID stored in database.
	 * @return documents.Book with following ID.
	 * @throws SQLException By default.
	 * @see Book
	 */
	public Book getBook(int ID) throws SQLException {
		//language=SQLite
		ResultSet bookSet = executeQuery("SELECT * FROM documents where type = \'BOOK\' and id =" + ID);
		if (bookSet.next()) {
			Book temp = new Book(bookSet.getString(2),
					bookSet.getString(3), Boolean.parseBoolean(bookSet.getString(4)), bookSet.getInt(5),
					Boolean.parseBoolean(bookSet.getString(6)), bookSet.getDouble(7), bookSet.getString(8),
					bookSet.getString(10), bookSet.getInt(11), Boolean.parseBoolean(bookSet.getString(12)));
			temp.setID(bookSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the audio/video with following ID from database.
	 *
	 * @param ID Audio/video ID stored in database.
	 * @return Audio/video with following ID.
	 * @throws SQLException By default.
	 * @see AudioVideoMaterial
	 */
	public AudioVideoMaterial getAV(int ID) throws SQLException {
		//language=SQLite
		ResultSet AVSet = executeQuery("SELECT * FROM documents where type = \'AV\' and id = " + ID);
		if (AVSet.next()) {
			AudioVideoMaterial temp = new AudioVideoMaterial(AVSet.getString(2),
					AVSet.getString(3), Boolean.parseBoolean(AVSet.getString(4)), AVSet.getInt(5),
					Boolean.parseBoolean(AVSet.getString(6)), AVSet.getDouble(7), AVSet.getString(8));
			temp.setID(AVSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the article with following ID from database.
	 *
	 * @param ID Article ID stored in database.
	 * @return Article with following ID.
	 * @throws SQLException   By default.
	 * @throws ParseException Incorrect data format.
	 * @see JournalArticle
	 */
	public JournalArticle getArticle(int ID) throws SQLException, ParseException {
		//language=SQLite
		ResultSet articleSet = executeQuery("SELECT * FROM documents where type = \'ARTICLE\' and id = " + ID);
		if (articleSet.next()) {
			JournalArticle temp = new JournalArticle(articleSet.getString(2),
					articleSet.getString(3), Boolean.parseBoolean(articleSet.getString(4)),
					articleSet.getInt(5), Boolean.parseBoolean(articleSet.getString(6)),
					articleSet.getDouble(7), articleSet.getString(8),
					articleSet.getString(13), articleSet.getString(10),
					articleSet.getString(14), articleSet.getString(15),
					new SimpleDateFormat("yyyy-MM-dd").parse(articleSet.getString(16)));
			temp.setID(articleSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the debt with following ID from database.
	 *
	 * @param ID tools.Debt ID stored in database.
	 * @return tools.Debt with following ID.
	 * @throws SQLException   By default.
	 * @throws ParseException Incorrect data format.
	 * @see Debt
	 */
	public Debt getDebt(int ID) throws SQLException, ParseException {
		//language=SQLite
		ResultSet debtsSet = executeQuery("SELECT * FROM debts WHERE debt_id = " + ID); // Fixed warning, may produce bug. RS
		if (debtsSet.next()) {
			Debt temp = new Debt(debtsSet.getInt(2), debtsSet.getInt(3),
					new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(4)),
					new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(5)),
					debtsSet.getDouble(6), Boolean.parseBoolean(debtsSet.getString(7)));
			temp.setDebtId(debtsSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get list of debts for the following user.
	 *
	 * @param userID Users' ID.
	 * @return List of debts for following user stored in database.
	 * @throws SQLException   By default.
	 * @throws ParseException Incorrect data format.
	 * @see List
	 * @see Debt
	 */
	public List<Debt> getDebtsForUser(int userID) throws SQLException, ParseException {
		//language=SQLite
		ResultSet debtsSet = executeQuery("SELECT * FROM debts WHERE patron_id =" + userID);
		LinkedList<Debt> debts = new LinkedList<>();

		while (debtsSet.next()) {
			Debt temp = new Debt(debtsSet.getInt(2), debtsSet.getInt(3),
					new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(4)),
					new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(5)),
					debtsSet.getDouble(6), Boolean.parseBoolean(debtsSet.getString(7)));
			temp.setDebtId(debtsSet.getInt(1));

			debts.add(temp);
		}

		return debts;
	}

	/**
	 * Get array list of users
	 *
	 * @return array list of users
	 * @throws SQLException something went wrong in database
	 */
	public ArrayList<User> getUsers() throws SQLException {
		ResultSet usersSet = executeQuery("SELECT * FROM users");
		ArrayList<User> usersList = new ArrayList<>();
		while (usersSet.next()) {
			String status = usersSet.getString(4).toLowerCase();
			if (status.equals("librarian")) {
				Librarian tempLib = new Librarian(usersSet.getString(2), usersSet.getString(3),
						usersSet.getString(5), usersSet.getString(6),
						usersSet.getString(7), usersSet.getString(8));
				tempLib.setId(usersSet.getInt(1));
				usersList.add(tempLib);
			} else {
				Patron tempPat = new Patron(usersSet.getString(2), usersSet.getString(3), status,
						usersSet.getString(5), usersSet.getString(6),
						usersSet.getString(7), usersSet.getString(8));
				tempPat.setId(usersSet.getInt(1));
				usersList.add(tempPat);
			}

		}
		return usersList;
	}

	/**
	 * Delete user with following ID from database.
	 *
	 * @param userID Users' ID.
	 * @throws SQLException By default.
	 */
	public void deleteUser(int userID) throws SQLException {
		this.executeUpdate("DELETE FROM users WHERE id=" + userID);
	}

	/**
	 * Delete document with following ID from database.
	 *
	 * @param documentID documents.Document ID.
	 * @throws SQLException By default.
	 */
	public void deleteDocument(int documentID) throws SQLException {
		//language=SQLite
		this.executeUpdate("DELETE FROM documents WHERE id=" + documentID);
	}

	/**
	 * Delete debt with following ID from database.
	 *
	 * @param debtID tools.Debt ID.
	 * @throws SQLException By default.
	 */
	public void deleteDebt(int debtID) throws SQLException {
		//language=SQLite
		this.executeUpdate("DELETE FROM debts WHERE debt_id=" + debtID);
	}

	/**
	 * Modifies the user data in database.
	 *
	 * @param userID Users' ID.
	 * @param column Column to edit. Available options:
	 *               <br>"firstname" to edit name</br>
	 *               <br>"lastname" to edit last name</br>
	 *               <br>"address" to edit address</br>
	 *               <br>"phone" to edit phone number</br>
	 *               <br>"status" to edit status</br>
	 *               <br>"login" to edit login</br>
	 *               <br>"password" to edit password</br>
	 *               <br></br>
	 * @param value  New value.
	 * @throws SQLException Incorrect column name or value.
	 */
	public void editUserColumn(int userID, String column, String value) throws SQLException {
		String quotes1 = "";
		String quotes2 = "";

		try {
			//noinspection ResultOfMethodCallIgnored
			Integer.parseInt(value); // Removed unused variable, may produce bug. RS
		} catch (NumberFormatException e) {
			quotes1 = "\'";
			quotes2 = "\'";
		}
		//language=SQLite
		this.executeUpdate("UPDATE users " +
				"SET " + column + " = " + quotes1 + value + quotes2 + " WHERE id = " + userID);
	}

	/**
	 * Modifies the document data in database.
	 *
	 * @param documentID documents.Document ID.
	 * @param column     Column to edit. Available options:
	 *                   <br>"bestseller" to edit bestseller status</br>
	 *                   <br>"edition" to edit edition</br>
	 *                   <br>"is_allowed_for_students" to edit student allowance</br>
	 *                   <br>"num_of_copies" to edit count of copies</br>
	 *                   <br>"price" to edit price</br>
	 *                   <br></br>
	 * @param value      New value.
	 * @throws SQLException Incorrect column name or value.
	 */
	public void editDocumentColumn(int documentID, String column, String value) throws SQLException {
		String quotes1 = "";
		String quotes2 = "";
		try {
			//noinspection ResultOfMethodCallIgnored
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			quotes1 = "\'";
			quotes2 = "\'";
		}
		//language=SQLite
		this.executeUpdate("UPDATE documents " +
				"SET " + column + " = " + quotes1 + value + quotes2 + " WHERE id = " + documentID);
	}

	/**
	 * Modifies the user data in database.
	 *
	 * @param debtID tools.Debt ID.
	 * @param column Column to edit.
	 * @param value  New value.
	 * @throws SQLException Incorrect column name or value.
	 */
	public void editDebtColumn(int debtID, String column, String value) throws SQLException {
		String quotes1 = "";
		String quotes2 = "";
		try {
			//noinspection ResultOfMethodCallIgnored
			Double.parseDouble(value);
		} catch (NumberFormatException e) {
			quotes1 = "\'";
			quotes2 = "\'";
		}
		//language=SQLite
		this.executeUpdate("UPDATE debts " +
				"SET " + column + " = " + quotes1 + value + quotes2 + " WHERE debt_id = " + debtID);
	}

	/**
	 * Erases all records in database and resets the indices.
	 *
	 * @throws SQLException If database is busy or something went incorrect.
	 */
	public void clear() throws SQLException {
		try {
			this.execute("DELETE FROM users");
			this.execute("DELETE FROM documents");
			this.execute("DELETE FROM debts");
			System.out.println("tools.Database: Records cleared.");
			this.execute("UPDATE sqlite_sequence SET seq=0");
			System.out.println("tools.Database: Indices reset.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("tools.Database: Clearing failed.");
		}
	}

	/**
	 * Authorize and find user with following credentials.
	 *
	 * @param login    users.User login.
	 * @param password users.User password.
	 * @return {@code true} if user with following credentials found in database, {@code false} otherwise.
	 * @throws SQLException By default.
	 */
	public boolean login(String login, String password) throws SQLException {
		//language=SQLite
		ResultSet answer = executeQuery("SELECT * FROM users WHERE login = \'" + login + "\' and password = \'" + password + "\'");
		return answer.next();
	}

	/**
	 * Returns user id with such login and password.
	 *
	 * @param login    users.User login.
	 * @param password users.User password.
	 * @return users.User's ID.
	 * @throws SQLException By default.
	 */
	public int loginId(String login, String password) throws SQLException {
		//language=SQLite
		ResultSet answer = executeQuery("SELECT * FROM users WHERE login = \'" + login + "\' and password = \'" + password + "\';");
		if (answer.next()) {
			return answer.getInt(1);
		}
		throw new NoSuchElementException();
	}

	/**
	 * Returns user's status by ID.
	 *
	 * @param ID users.User's ID.
	 * @return users.User's status.
	 * @throws SQLException By default.
	 */
	public String loginStatus(int ID) throws SQLException {
		//language=SQLite
		ResultSet answer = executeQuery("SELECT status FROM users WHERE id = " + ID);
		if (answer.next()) {
			return answer.getString(1);
		}
		throw new NoSuchElementException();
	}

	/**
	 * Find debt ID with following patron and document IDs.
	 *
	 * @param patronID users.Patron ID.
	 * @param docID    documents.Document ID.
	 * @return ID for found debt.
	 * @throws SQLException By default.
	 */
	public int findDebtID(int patronID, int docID) throws SQLException {
		ResultSet debt = executeQuery("SELECT debt_id FROM debts WHERE patron_id = " + patronID + " AND document_id = " + docID);
		if (debt.next()) {
			return debt.getInt(1);
		}
		throw new NoSuchElementException();
	}

	/**
	 * Find the ID for following document.
	 *
	 * @param document documents.Document to find.
	 * @return ID for found document.
	 * @throws SQLException By default.
	 */
	public int getDocumentID(Document document) throws SQLException {
		for (Document i : getDocumentList()) {
			if (i.compare(document)) return i.getID();
		}
		return -1;
	}

	/**
	 * Find the ID for following patron.
	 *
	 * @param patron users.Patron to find.
	 * @return ID for found patron.
	 * @throws SQLException By default.
	 */
	public int getPatronID(Patron patron) throws SQLException {
		for (Patron i : getPatronList()) {
			if (i.compare(patron)) return i.getId();
		}
		return -1;
	}

	/**
	 * Find the ID for following librarian.
	 *
	 * @param librarian users.Librarian to find.
	 * @return ID for found librarian.
	 * @throws SQLException By default.
	 */
	public int getLibrarianID(Librarian librarian) throws SQLException {
		for (Librarian i : getLibrarianList()) {
			if (i.compare(librarian)) return i.getId();
		}
		return -1;
	}

	/**
	 * inserts request to the library
	 *
	 * @param request request object which will be inserted
	 * @throws SQLException something went wrong in database
	 */
	public void insertRequest(Request request) throws SQLException {
		this.execute(String.format("INSERT INTO requests(patron_id,patron_name,patron_surname,document_id,priority, date,is_renew_request)" +
						"VALUES(%d, '%s', '%s', %d, %d,'%s','%b')", request.getIdPatron(), request.getNamePatron(),
				request.getSurnamePatron(), request.getIdDocument(), request.getPriority(),
				(new SimpleDateFormat("yyyy-MM-dd")).format(request.getDate()), request.isRenewRequest()));
	}

	/**
	 * @return list of all the unclosed requests from the database
	 * @throws SQLException   something went wrong in database
	 * @throws ParseException date parsing failed
	 */
	public List<Request> getRequests() throws SQLException, ParseException {
		ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE is_renew_request = 'false' ORDER BY priority, date");
		LinkedList<Request> requests = new LinkedList<>();
		while (requestsSet.next()) {
			Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
					new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
			temp.setRequestId(requestsSet.getInt(1));
			requests.add(temp);
		}
		return requests;
	}

	/**
	 * @return list of all the unclosed requests from the database
	 * @throws SQLException   something went wrong in database
	 * @throws ParseException date parsing failed
	 */
	public List<Request> getRenewRequests() throws SQLException, ParseException {
		ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE is_renew_request = 'true' ORDER BY priority, date");
		LinkedList<Request> requests = new LinkedList<>();
		while (requestsSet.next()) {
			Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
					new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
			temp.setRequestId(requestsSet.getInt(1));
			requests.add(temp);
		}
		return requests;
	}


	public List<Request> getRequestsForPatron(int patronID) throws SQLException, ParseException {
		ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE patron_id = " + patronID + " and is_renew_request = 'false' ORDER BY priority, date");
		ArrayList<Request> requests = new ArrayList<>();
		while (requestsSet.next()) {
			Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
					new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
			temp.setRequestId(requestsSet.getInt(1));
			requests.add(temp);
		}
		return requests;
	}

	public List<Request> getRenewRequestsForPatron(int patronID) throws SQLException, ParseException {
		ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE patron_id = " + patronID + " and is_renew_request = 'true'");
		LinkedList<Request> requests = new LinkedList<>();
		while (requestsSet.next()) {
			Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
					new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
			temp.setRequestId(requestsSet.getInt(1));
			requests.add(temp);
		}
		return requests;
	}

	/**
	 * @param patronId patron's id
	 * @param docId    document's id
	 * @return request from database with id = patronId
	 * @throws SQLException   something went wrong in database
	 * @throws ParseException date parsing failed
	 */
	public Request getRequest(int patronId, int docId) throws SQLException, ParseException {
		ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE patron_id = " + patronId + " and document_id = " + docId);
		if (requestsSet.next()) {
			Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
					new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
			temp.setRequestId(requestsSet.getInt(1));
			return temp;
		}

		throw new NoSuchElementException();
	}

	/**
	 * @param id - id of request we want to return
	 * @return request from the database
	 * @throws SQLException   something went wrong in database
	 * @throws ParseException date parsing failed
	 */
	public Request getRequest(int id) throws SQLException, ParseException {
		ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE request_id = " + id);
		if (requestsSet.next()) {
			Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
					new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
			temp.setRequestId(requestsSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * deletes request from the database by patron and document ids
	 *
	 * @param patronId   - id of patron whose request was closed
	 * @param documentId - id of document patron wanted to take
	 * @throws SQLException something went wrong in database
	 */
	public void deleteRequest(int patronId, int documentId) throws SQLException {
		executeUpdate("DELETE FROM requests WHERE patron_id = " + patronId + " AND document_id = " + documentId);
	}

	/**
	 * deletes request from the database by request id
	 *
	 * @param requestId request id in datbase
	 * @throws SQLException something went wrond in database
	 */
	public void deleteRequest(int requestId) throws SQLException {
		executeUpdate("DELETE FROM requests WHERE request_id = " + requestId);
	}

	/**
	 * edit request table
	 *
	 * @param requestId id of request we want to edit
	 * @param column    - column to edit.
	 * @param value     - new value
	 * @throws SQLException something went wrong in database
	 */
	public void editRequest(int requestId, String column, String value) throws SQLException {
		String quotes1 = "";
		String quotes2 = "";

		try {
			//noinspection ResultOfMethodCallIgnored
			Double.parseDouble(value); // Removed unused variable, may produce bug. RS
		} catch (NumberFormatException e) {
			quotes1 = "\'";
			quotes2 = "\'";
		}
		executeUpdate(String.format("UPDATE requests SET %s = %s" + value + "%s WHERE request_id = %d", column, quotes1, quotes2, requestId));
	}
}