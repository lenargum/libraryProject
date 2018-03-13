import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Database API for library system.
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
				System.out.println("Database: Already connected");
				return;
			}

			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				throw new Exception("Database: Database driver not found");
			}

			String connectionURL = "jdbc:sqlite:library.db";

			connection = DriverManager.getConnection(connectionURL);
			connected = true;
			System.out.println("Database: Connection successful");
		} catch (Exception e) {

			System.out.println("Database: Connection failed");
		}
	}

	/**
	 * Get the current connection state.
	 *
	 * @return {@code true} if connection established, {@code false} otherwise.
	 */
	public boolean isConnected() {
		return this.connected;
	}

	/**
	 * Stop the current session, disconnect from database.
	 */
	public void close() {
		if (connection != null) {
			try {
				this.connection.close();
				System.out.println("Database: Connection closed");
				this.connected = false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Database: Connection was not opened, already closed.");
		}
	}

	// TODO LENAR NAPISHI DOCUMENTATSIY PLS

	/**
	 * @param MySQLStatement
	 * @throws SQLException
	 */
	private void executeUpdate(String MySQLStatement) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(MySQLStatement);
		statement.close();
	}

	/**
	 * @param MySQLStatement
	 * @throws SQLException
	 */
	private void execute(String MySQLStatement) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(MySQLStatement);
		statement.close();
	}

	/**
	 * @param MySQLStatement
	 * @return
	 * @throws SQLException
	 */
	private ResultSet executeQuery(String MySQLStatement) throws SQLException {
		Statement statement = connection.createStatement();
		return statement.executeQuery(MySQLStatement);
	}

	/**
	 * Insert the user with following parameters into database.
	 *
	 * @param login     User login.
	 * @param password  User password.
	 * @param status    User status.
	 * @param firstName First name.
	 * @param lastName  Last name.
	 * @param phone     Phone number.
	 * @param address   Living address.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param patron Patron to insert.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS I DON'T UNDERSTAND
	 * @see Patron
	 */
	public void insertPatron(Patron patron) throws SQLException {
		String status = "STUDENT";
		if (patron.getStatus().toLowerCase().equals("faculty")) {
			status = "FACULTY";
		}
		insertUser(patron.getLogin(), patron.getPassword(), status, patron.getName(), patron.getSurname(), patron.getPhoneNumber(), patron.getAddress());
	}

	/**
	 * Insert provided librarian into database.
	 *
	 * @param librarian Librarian to insert.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @see Librarian
	 */
	public void insertLibrarian(Librarian librarian) throws SQLException {
		insertUser(librarian.getLogin(), librarian.getPassword(), "LIBRARIAN", librarian.getName(), librarian.getSurname(), librarian.getPhoneNumber(), librarian.getAddress());
	}

	/**
	 * Insert document with following properties into database.
	 *
	 * @param name                 Document title.
	 * @param authors              Document authors.
	 * @param isAllowedForStudents Student allowance.
	 * @param numOfCopies          Count of copies.
	 * @param isReference          Document reference status.
	 * @param price                Document price.
	 * @param keywords             Search keywords.
	 * @param type                 Document type.
	 * @param publisher            Document publisher.
	 * @param edition              Year edition.
	 * @param bestseller           Bestseller status.
	 * @param journalName          Journal title.
	 * @param issue                Article issue.
	 * @param editor               Document editor.
	 * @param publicationDate      Document publication date.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param book Book to insert.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @see Book
	 */
	public void insertBook(Book book) throws SQLException {
		String type = "BOOK";
		insertDocument(book.getTitle(), book.getAuthors(), book.isAllowedForStudents(), book.getNumberOfCopies(),
				book.isReference(), book.getPrice(), book.getKeyWords(), type, book.getPublisher(), book.getEdition(),
				book.isBestseller(), "-", "-", "-", "NULL");
		book.setID(this.getDocumentID(new Document(book.getTitle(), book.getAuthors(), book.isAllowedForStudents(), book.getNumberOfCopies(),
				book.isReference(), book.getPrice(), book.getKeyWords())));
	}

	/**
	 * Insert provided Audio/Video into database.
	 *
	 * @param av Audio/Video to insert.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param debt Debt to insert.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @see List
	 * @see Patron
	 */
	public ArrayList<Patron> getPatronList() throws SQLException {
		ResultSet patronSet = executeQuery("SELECT * FROM users where status = 'FACULTY' or status = 'STUDENT'");
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
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
		// TODO Maybe it's ok to return empty list instead of throwing exception?
		throw new NoSuchElementException();
	}

	/**
	 * Get the book list from database.
	 *
	 * @return List of books stored in database.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException   // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @throws ParseException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException   // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @throws ParseException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @return Patron with following ID.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @see Patron
	 */
	public Patron getPatron(int ID) throws SQLException {
		ResultSet patronSet = executeQuery("SELECT * FROM users where (status = 'FACULTY' or status = 'STUDENT') and id = " + ID);
		if (patronSet.next()) {
			Patron temp = new Patron(patronSet.getString(2),
					patronSet.getString(3), patronSet.getString(4), patronSet.getString(5),
					patronSet.getString(6), patronSet.getString(7), patronSet.getString(8));
			temp.setId(patronSet.getInt(1));
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the librarian with following ID from database.
	 *
	 * @param ID Librarians' ID stored in database.
	 * @return Librarian with following ID.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param ID Document ID stored in database.
	 * @return Document with following ID.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param ID Book ID stored in database.
	 * @return Book with following ID.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException   // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @throws ParseException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param ID Debt ID stored in database.
	 * @return Debt with following ID.
	 * @throws SQLException   // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @throws ParseException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @throws SQLException   // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 * @throws ParseException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * Print the debts list for the following user.
	 *
	 * @param userID Users' ID.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	@Deprecated
	public void printDebtsForUser(int userID) throws SQLException {
		//language=SQLite
		ResultSet debtsSet = executeQuery("SELECT * FROM debts where patron_id =" + userID);

		ArrayList<String> output = new ArrayList<>();
		while (debtsSet.next()) {
			output.add("Document id: " + debtsSet.getInt(3));
			output.add("Booking date: " + debtsSet.getString(4));
			output.add("Expire date: " + debtsSet.getString(5));
			output.add("Fee: " + debtsSet.getDouble(6));
			output.add("Can renew: " + debtsSet.getString(7));
			output.add("");
		}
		ArrayList<String> finalOutput = new ArrayList<>();
		if (output.size() != 0) {
			finalOutput.add("All borrowed documents:\n");
			finalOutput.addAll(output);
		} else {
			finalOutput.add("There is no any borrowed documents! Go grab one!");
		}
		for (String temp : finalOutput) {
			System.out.println(temp);
		}
	}

	/**
	 * Print all the documents stored in database.
	 *
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	@Deprecated
	public void printDocs() throws SQLException {
		System.out.println("\nAll documents in database: ");
		for (String temp : getDocumentStringList()) {
			System.out.println(temp);
		}
		System.out.println();
	}

	/**
	 * Print all the users stored in database.
	 *
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	@Deprecated
	public void printUsers() throws SQLException {
		ResultSet usersSet = executeQuery("SELECT * FROM users");
		System.out.println("\nAll users in database:");
		while (usersSet.next()) {
			System.out.println(usersSet.getInt(1) + " " + usersSet.getString(2) + " "
					+ usersSet.getString(3) + " " + usersSet.getString(4) + " "
					+ usersSet.getString(5) + " " + usersSet.getString(6) + " "
					+ usersSet.getString(7) + " " + usersSet.getString(8));
		}
		System.out.println();
	}

	/**
	 * Delete user with following ID from database.
	 *
	 * @param userID Users' ID.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	public void deleteUser(int userID) throws SQLException {
		this.executeUpdate("DELETE FROM users WHERE id=" + userID);
	}

	/**
	 * Delete document with following ID from database.
	 *
	 * @param documentID Document ID.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	public void deleteDocument(int documentID) throws SQLException {
		//language=SQLite
		this.executeUpdate("DELETE FROM documents WHERE id=" + documentID);
	}

	/**
	 * Delete debt with following ID from database.
	 *
	 * @param debtID Debt ID.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 *               <br>"address" to edit address</br>
	 *               <br>"phone" to edit phone number</br>
	 *               <br>"lastname" to edit last name</br>
	 *               <br></br>
	 * @param value  New value.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	public void editUserColumn(int userID, String column, String value) throws SQLException {
		String quotes1 = "";
		String quotes2 = "";

		try {
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
	 * @param documentID Document ID.
	 * @param column     Column to edit. Available options:
	 *                   <br>"bestseller" to edit bestseller status</br>
	 *                   <br>"edition" to edit edition</br>
	 *                   <br>"is_allowed_for_students" to edit student allowance</br>
	 *                   <br>"num_of_copies" to edit count of copies</br>
	 *                   <br>"price" to edit price</br>
	 *                   <br></br>
	 * @param value      New value.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	public void editDocumentColumn(int documentID, String column, String value) throws SQLException {
		String quotes1 = "";
		String quotes2 = "";
		try {
			Integer.parseInt(value); // Removed unused variable, may produce bug. RS
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
	 * @param debtID Debt ID.
	 * @param column Column to edit.
	 * @param value  New value.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	public void editDebtColumn(int debtID, String column, String value) throws SQLException {
		String quotes1 = "";
		String quotes2 = "";
		try {
			Integer.parseInt(value); // Removed unused variable, may produce bug. RS
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
	 * @throws SQLException if database is busy or something went wrong.
	 */
	public void clear() throws SQLException {
		try {
			this.execute("DELETE FROM users");
			this.execute("DELETE FROM documents");
			this.execute("DELETE FROM debts");
			System.out.println("Database: Records cleared.");
			this.execute("UPDATE sqlite_sequence SET seq=0");
			System.out.println("Database: Indices reset.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Database: Clearing failed.");
		}
	}

	/**
	 * Authorize and find user with following credentials.
	 *
	 * @param login    User login.
	 * @param password User password.
	 * @return {@code true} if user with following credentials found in database, {@code false} otherwise.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	public boolean login(String login, String password) throws SQLException {
		//language=SQLite
		ResultSet answer = executeQuery("SELECT * FROM users WHERE login = \'" + login + "\' and password = \'" + password + "\'");
		return answer.next();
	}

	// TODO Wtf this method does????
	public int loginId(String login, String password) throws SQLException {
		//language=SQLite
		ResultSet answer = executeQuery("SELECT * FROM users WHERE login = \'" + login + "\' and password = \'" + password + "\';");
		if (answer.next()) {
			return answer.getInt(1);
		}
		throw new NoSuchElementException();
	}

	// TODO Wtf this method does????
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
	 * @param patronID Patron ID.
	 * @param docID    Document ID.
	 * @return ID for found debt.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param document Document to find.
	 * @return ID for found document.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param patron Patron to find.
	 * @return ID for found patron.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
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
	 * @param librarian Librarian to find.
	 * @return ID for found librarian.
	 * @throws SQLException // TODO LENAR, WHY AND WHEN THIS SHIT THROWS EXCEPTION, WHY PLS
	 */
	public int getLibrarianID(Librarian librarian) throws SQLException {
		for (Librarian i : getLibrarianList()) {
			if (i.compare(librarian)) return i.getId();
		}
		return -1;
	}
}