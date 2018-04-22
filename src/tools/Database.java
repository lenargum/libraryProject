package tools;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import users.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

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
	                        String firstName, String lastName, String phone, String address,int privileges) throws SQLException {
		this.execute("INSERT INTO users(login, password, status, firstname, lastname, phone, address, privileges)" +
				" VALUES('" + login + "','" + password + "','"
				+ status + "','" + firstName + "','" + lastName + "','" +
				phone + "','" + address + "', "+privileges+")");
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
	 * Insert provided patron into database.
	 *
	 * @param patron users.Patron to insert.
	 * @see Patron
	 */
	public void insertPatron(Patron patron) {
		try {
			insertUser(patron.getLogin(), patron.getPassword(), patron.getStatus().toUpperCase(), patron.getName(), patron.getSurname(), patron.getPhoneNumber(), patron.getAddress(),-1);
			patron.setId(this.getPatronID(patron));
		} catch (SQLException e) {
			System.err.println("tools.Database: User with such login already exists");
		}
	}

	/**
	 * Insert provided librarian into database.
	 *
	 * @param librarian users.Librarian to insert.
	 * @see Librarian
	 */
	public void insertLibrarian(Librarian librarian) {
		try {
			insertUser(librarian.getLogin(), librarian.getPassword(), "LIBRARIAN", librarian.getName(), librarian.getSurname(), librarian.getPhoneNumber(), librarian.getAddress(),librarian.getPrivilege());
			librarian.setId(this.getLibrarianID(librarian));
		} catch (SQLException e) {
			System.err.println("tools.Database: User with such login already exists");
		}
	}



	/**
	 * Insert provided book into database.
	 *
	 * @param book documents.Book to insert.
	 * @see Book
	 */
	public void insertBook(Book book) {
		try {
			String type = "BOOK";

			insertDocument(book.getTitle(), book.getAuthors(), book.isAllowedForStudents(), book.getNumberOfCopies(),
					book.isReference(), book.getPrice(), book.getKeyWords(), type, book.getPublisher(), book.getEdition(),
					book.isBestseller(), "-", "-", "-", "NULL");
			book.setID(this.getDocumentID(book));
		} catch (SQLException e) {
			System.err.println("tools.Database: One of obligatory fields is empty or null (name, authors, num_of_copies, price, or type)");
		}
	}

	/**
	 * Insert provided Audio/Video into database.
	 *
	 * @param av Audio/Video to insert.
	 * @see AudioVideoMaterial
	 */
	public void insertAV(AudioVideoMaterial av) {
		try {
			insertDocument(av.getTitle(), av.getAuthors(), av.isAllowedForStudents(), av.getNumberOfCopies(),
					av.isReference(), av.getPrice(), av.getKeyWords(), "AV", "-", 0, false,
					"-", "-", "-", "NULL");
			av.setID(this.getDocumentID(av));
		} catch (SQLException e) {
			System.err.println("tools.Database: One of obligatory fields is empty or null (name, authors, num_of_copies, price, or type)");
		}
	}

	/**
	 * Insert provided article into database.
	 *
	 * @param article Article to insert.
	 * @see JournalArticle
	 */
	public void insertArticle(JournalArticle article) {
		try {
			insertDocument(article.getTitle(), article.getAuthors(), article.isAllowedForStudents(),
					article.getNumberOfCopies(), article.isReference(), article.getPrice(), article.getKeyWords(),
					"ARTICLE", article.getPublisher(), 0, false, article.getJournalName(),
					article.getIssue(), article.getEditor(),
					(new SimpleDateFormat("yyyy-MM-dd")).format(article.getPublicationDate()));
			article.setID(this.getDocumentID(article));
		} catch (SQLException e) {
			System.err.println("tools.Database: One of obligatory fields is empty or null (name, authors, num_of_copies, price, or type)");
		}
	}

	/**
	 * Insert provided debt into database.
	 *
	 * @param debt tools.Debt to insert.
	 * @see Debt
	 */
	public void insertDebt(Debt debt) {
		try {
			execute("INSERT INTO debts(patron_id, document_id, booking_date, expire_date, fee, can_renew)"
					+ " VALUES(" + debt.getPatronId() + ", " + debt.getDocumentId() + ", \'"
					+ (new SimpleDateFormat("yyyy-MM-dd")).format(debt.getBookingDate()) + "\', \'" + (new SimpleDateFormat("yyyy-MM-dd")).format(debt.getExpireDate()) + "\', " + debt.getFee() + ", \'"
					+ debt.canRenew() + "\')");
			debt.setDebtId(this.findDebtID(debt.getPatronId(), debt.getDocumentId()));
		} catch (SQLException e) {
			System.err.println("tools.Database: One of fields is empty or null");
		}
	}

	/**
	 * Inserts Request to the database.
	 *
	 * @param request Request object which will be inserted.
	 */
	public void insertRequest(Request request) {
		try {
			this.execute(String.format("INSERT INTO requests(patron_id,patron_name,patron_surname,document_id,priority, date,is_renew_request)" +
							"VALUES(%d, '%s', '%s', %d, %d,'%s','%b')", request.getIdPatron(), request.getNamePatron(),
					request.getSurnamePatron(), request.getIdDocument(), request.getPriority(),
					(new SimpleDateFormat("yyyy-MM-dd")).format(request.getDate()), request.isRenewRequest()));
			request.setRequestId(this.getRequest(request.getIdPatron(), request.getIdDocument()).getRequestId());
		} catch (SQLException e) {
			System.err.println("tools.Database: One of fields (patron_id, document_id, priority, date, is_renew_request) is empty or null");
		}
	}

	/**
	 * Get the patrons list from database.
	 *
	 * @return List of patrons stored in database.
	 * @see List
	 * @see Patron
	 */
	public ArrayList<Patron> getPatronList() {
		try {
			ResultSet patronSet = executeQuery("SELECT * FROM users where status != 'LIBRARIAN' AND status != 'ADMIN'");
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the librarians list from database.
	 *
	 * @return List of librarians stored in database.
	 * @see List
	 * @see Librarian
	 */
	public ArrayList<Librarian> getLibrarianList() {
		try {
			ResultSet librarianSet = executeQuery("SELECT * FROM users where status = 'LIBRARIAN'");
			ArrayList<Librarian> librarianList = new ArrayList<>();
			while (librarianSet.next()) {
				Librarian temp = new Librarian(librarianSet.getString(2),
						librarianSet.getString(3), librarianSet.getString(5),
						librarianSet.getString(6), librarianSet.getString(7), librarianSet.getString(8),librarianSet.getInt(9));
				temp.setId(librarianSet.getInt(1));
				librarianList.add(temp);
			}
			if (librarianList.size() != 0) {
				return librarianList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the documents list from database.
	 *
	 * @return List of documents stored in database.
	 * @see List
	 * @see Document
	 */
	public ArrayList<Document> getDocumentList() {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the documents descriptions from database.
	 *
	 * @return List of descriptions of documents stored in database.
	 * @see List
	 * @see String
	 * @see Document
	 */
	public ArrayList<String> getDocumentStringList() {
		try {
			ResultSet documentSet = executeQuery("SELECT * FROM documents");
			ArrayList<String> documentsTitleList = new ArrayList<>();
			while (documentSet.next()) {
				documentsTitleList.add(documentSet.getInt(1) + " \"" + documentSet.getString(2) + "\" "
						+ documentSet.getString(3) + " (" + documentSet.getString(4) + ") ");
			}
			if (documentsTitleList.size() != 0) {
				return documentsTitleList;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the book list from database.
	 *
	 * @return List of books stored in database.
	 * @see List
	 * @see Book
	 */
	public ArrayList<Book> getBookList() {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the audio/video list from database.
	 *
	 * @return List of audios/videos stored in database.
	 * @see List
	 * @see AudioVideoMaterial
	 */
	public ArrayList<AudioVideoMaterial> getAVList() {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the article list from database.
	 *
	 * @return List of articles stored in database.
	 * @see List
	 * @see JournalArticle
	 */
	public ArrayList<JournalArticle> getArticleList() {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		throw new NoSuchElementException();


	}

	/**
	 * Get the debts list from database.
	 *
	 * @return List of debts stored in database.
	 * @see List
	 * @see Debt
	 */
	public ArrayList<Debt> getDebtsList() {
		ArrayList<Debt> debtsList = new ArrayList<>();
		try {
			ResultSet debtsSet = executeQuery("SELECT * FROM debts");
			while (debtsSet.next()) {
				Debt temp = new Debt(debtsSet.getInt(2), debtsSet.getInt(3),
						new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(4)),
						new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(5)),
						debtsSet.getDouble(6), Boolean.parseBoolean(debtsSet.getString(7)));
				temp.setDebtId(debtsSet.getInt(1));
				debtsList.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return debtsList;
	}

	/**
	 * Get the patron with following ID from database.
	 *
	 * @param ID Patrons' ID stored in database.
	 * @return users.Patron with following ID.
	 * @see Patron
	 */
	public Patron getPatron(int ID) {
		try {
			ResultSet patronSet = executeQuery("SELECT * FROM users where status != 'LIBRARIAN' and status != 'ADMIN' and id = " + ID);
			if (patronSet.next()) {
				Patron temp = new Patron(patronSet.getString(2),
						patronSet.getString(3), patronSet.getString(4), patronSet.getString(5),
						patronSet.getString(6), patronSet.getString(7), patronSet.getString(8));
				temp.setId(patronSet.getInt(1));
				return temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * User authorisation with these login and password
	 *
	 * @param login    User login
	 * @param password User password
	 * @return User with such login and password if it exists
	 */
	public User authorise(String login, String password) {
		try {
			ResultSet userSet = executeQuery("SELECT * FROM users WHERE login = \'" + login + "\' and password = \'" + password + "\'");
			if (userSet.next()) {
				User temp;

				switch (userSet.getString(4)) {
					case "LIBRARIAN":
						temp = new Librarian(userSet.getString(2),
								userSet.getString(3), userSet.getString(5),
								userSet.getString(6), userSet.getString(7),
								userSet.getString(8),userSet.getInt(9));
						break;
					case "STUDENT":
						temp = new Student(userSet.getString(2),
								userSet.getString(3), userSet.getString(5),
								userSet.getString(6), userSet.getString(7),
								userSet.getString(8));
						break;
					case "PROFESSOR":
						temp = new Professor(userSet.getString(2),
								userSet.getString(3), userSet.getString(5),
								userSet.getString(6), userSet.getString(7),
								userSet.getString(8));
						break;
					case "INSTRUCTOR":
						temp = new Instructor(userSet.getString(2),
								userSet.getString(3), userSet.getString(5),
								userSet.getString(6), userSet.getString(7),
								userSet.getString(8));
						break;
					case "TA":
						temp = new TeachingAssistant(userSet.getString(2),
								userSet.getString(3), userSet.getString(5),
								userSet.getString(6), userSet.getString(7),
								userSet.getString(8));
						break;
					case "VP":
						temp = new VisitingProfessor(userSet.getString(2),
								userSet.getString(3), userSet.getString(5),
								userSet.getString(6), userSet.getString(7),
								userSet.getString(8));
						break;
					case "ADMIN":
						temp = new Admin(userSet.getString(2),
								userSet.getString(3), userSet.getString(5),
								userSet.getString(6), userSet.getString(7),
								userSet.getString(8));
						break;
					default:
						throw new WrongUserTypeException("System does not support type " +
								userSet.getString(4));
				}

				temp.setId(userSet.getInt(1));
				return temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the librarian with following ID from database.
	 *
	 * @param ID Librarians' ID stored in database.
	 * @return users.Librarian with following ID.
	 * @see Librarian
	 */
	public Librarian getLibrarian(int ID) {
		try {
			ResultSet librarianSet = executeQuery("SELECT * FROM users where (status = 'LIBRARIAN') and id = " + ID);
			if (librarianSet.next()) {
				Librarian temp = new Librarian(librarianSet.getString(2),
						librarianSet.getString(3), librarianSet.getString(5),
						librarianSet.getString(6), librarianSet.getString(7), librarianSet.getString(8),librarianSet.getInt(9));
				temp.setId(librarianSet.getInt(1));
				return temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the document with following ID from database.
	 *
	 * @param ID documents.Document ID stored in database.
	 * @return documents.Document with following ID.
	 * @see Document
	 */
	public Document getDocument(int ID) {
		try {
			ResultSet documentSet = executeQuery("SELECT * FROM documents where id = " + ID);
			if (documentSet.next()) {
				Document temp;
				switch (documentSet.getString("9")) {
					case "BOOK":
						temp = new Book(documentSet.getString(2),
								documentSet.getString(3), Boolean.parseBoolean(documentSet.getString(4)), documentSet.getInt(5),
								Boolean.parseBoolean(documentSet.getString(6)), documentSet.getDouble(7), documentSet.getString(8),
								documentSet.getString(10), documentSet.getInt(11), Boolean.parseBoolean(documentSet.getString(12)));
						temp.setID(documentSet.getInt(1));
						return temp;

					case "AV":
						temp = new AudioVideoMaterial(documentSet.getString(2),
								documentSet.getString(3), Boolean.parseBoolean(documentSet.getString(4)), documentSet.getInt(5),
								Boolean.parseBoolean(documentSet.getString(6)), documentSet.getDouble(7), documentSet.getString(8));
						temp.setID(documentSet.getInt(1));
						return temp;

					case "ARTICLE":
						try {
							temp = new JournalArticle(documentSet.getString(2),
									documentSet.getString(3), Boolean.parseBoolean(documentSet.getString(4)),
									documentSet.getInt(5), Boolean.parseBoolean(documentSet.getString(6)),
									documentSet.getDouble(7), documentSet.getString(8),
									documentSet.getString(13), documentSet.getString(10),
									documentSet.getString(14), documentSet.getString(15),
									new SimpleDateFormat("yyyy-MM-dd").parse(documentSet.getString(16)));
							temp.setID(documentSet.getInt(1));
							return temp;
						} catch (ParseException e) {
							e.printStackTrace();
							System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
						}
						break;

					default:
						throw new WrongUserTypeException("System does not support type " +
								documentSet.getString(4));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the book with following ID from database.
	 *
	 * @param ID documents.Book ID stored in database.
	 * @return documents.Book with following ID.
	 * @see Book
	 */
	public Book getBook(int ID) {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the audio/video with following ID from database.
	 *
	 * @param ID Audio/video ID stored in database.
	 * @return Audio/video with following ID.
	 * @see AudioVideoMaterial
	 */
	public AudioVideoMaterial getAV(int ID) {
		try {
			//language=SQLite
			ResultSet AVSet = executeQuery("SELECT * FROM documents where type = \'AV\' and id = " + ID);
			if (AVSet.next()) {
				AudioVideoMaterial temp = new AudioVideoMaterial(AVSet.getString(2),
						AVSet.getString(3), Boolean.parseBoolean(AVSet.getString(4)), AVSet.getInt(5),
						Boolean.parseBoolean(AVSet.getString(6)), AVSet.getDouble(7), AVSet.getString(8));
				temp.setID(AVSet.getInt(1));
				return temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the article with following ID from database.
	 *
	 * @param ID Article ID stored in database.
	 * @return Article with following ID.
	 * @see JournalArticle
	 */
	public JournalArticle getArticle(int ID) {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get the debt with following ID from database.
	 *
	 * @param ID tools.Debt ID stored in database.
	 * @return tools.Debt with following ID.
	 * @see Debt
	 */
	public Debt getDebt(int ID) {
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		throw new NoSuchElementException();
	}

	/**
	 * Get list of debts for the following user.
	 *
	 * @param userID Users' ID.
	 * @return List of debts for following user stored in database.
	 * @see List
	 * @see Debt
	 */
	public ArrayList<Debt> getDebtsForUser(int userID) {
		ArrayList<Debt> debts = new ArrayList<>();
		try {
			ResultSet debtsSet = executeQuery("SELECT * FROM debts WHERE patron_id =" + userID);

			while (debtsSet.next()) {
				Debt temp = new Debt(debtsSet.getInt(2), debtsSet.getInt(3),
						new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(4)),
						new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(5)),
						debtsSet.getDouble(6), Boolean.parseBoolean(debtsSet.getString(7)));
				temp.setDebtId(debtsSet.getInt(1));

				debts.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return debts;
	}

	/**
	 * Method for searching Debts for certain Document.
	 *
	 * @param documentId Document's ID.
	 * @return ArrayList of Debts for certain Document.
	 */
	public ArrayList<Debt> getDebtsForDocument(int documentId) {
		ArrayList<Debt> debts = new ArrayList<>();
		try {
			//language=SQLite
			ResultSet debtsSet = executeQuery("SELECT * FROM debts WHERE document_id =" + documentId);

			while (debtsSet.next()) {
				Debt temp = new Debt(debtsSet.getInt(2), debtsSet.getInt(3),
						new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(4)),
						new SimpleDateFormat("yyyy-MM-dd").parse(debtsSet.getString(5)),
						debtsSet.getDouble(6), Boolean.parseBoolean(debtsSet.getString(7)));
				temp.setDebtId(debtsSet.getInt(1));

				debts.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return debts;
	}

	/**
	 * Get array list of users
	 *
	 * @return array list of users
	 */
	public ArrayList<User> getUsers() {
		ArrayList<User> usersList = new ArrayList<>();
		try {
			ResultSet usersSet = executeQuery("SELECT * FROM users");

			while (usersSet.next()) {
				User temp;

				switch (usersSet.getString(4)) {
					case "LIBRARIAN":
						temp = new Librarian(usersSet.getString(2),
								usersSet.getString(3), usersSet.getString(5),
								usersSet.getString(6), usersSet.getString(7),
								usersSet.getString(8), usersSet.getInt(9));
						break;
					case "STUDENT":
						temp = new Student(usersSet.getString(2),
								usersSet.getString(3), usersSet.getString(5),
								usersSet.getString(6), usersSet.getString(7),
								usersSet.getString(8));
						break;
					case "PROFESSOR":
						temp = new Professor(usersSet.getString(2),
								usersSet.getString(3), usersSet.getString(5),
								usersSet.getString(6), usersSet.getString(7),
								usersSet.getString(8));
						break;
					case "INSTRUCTOR":
						temp = new Instructor(usersSet.getString(2),
								usersSet.getString(3), usersSet.getString(5),
								usersSet.getString(6), usersSet.getString(7),
								usersSet.getString(8));
						break;
					case "TA":
						temp = new TeachingAssistant(usersSet.getString(2),
								usersSet.getString(3), usersSet.getString(5),
								usersSet.getString(6), usersSet.getString(7),
								usersSet.getString(8));
						break;
					case "VP":
						temp = new VisitingProfessor(usersSet.getString(2),
								usersSet.getString(3), usersSet.getString(5),
								usersSet.getString(6), usersSet.getString(7),
								usersSet.getString(8));
						break;
					case "ADMIN":
						temp = new Admin(usersSet.getString(2),
								usersSet.getString(3), usersSet.getString(5),
								usersSet.getString(6), usersSet.getString(7),
								usersSet.getString(8));
						break;
					default:
						throw new WrongUserTypeException("System does not support type " +
								usersSet.getString(4));
				}

				temp.setId(usersSet.getInt(1));
				usersList.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usersList;
	}

	/**
	 * Delete user with following ID from database.
	 *
	 * @param userID Users' ID.
	 */
	public void deleteUser(int userID) {
		try {
			this.executeUpdate("DELETE FROM users WHERE id=" + userID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete document with following ID from database.
	 *
	 * @param documentID documents.Document ID.
	 */
	public void deleteDocument(int documentID) {
		try {
			//language=SQLite
			this.executeUpdate("DELETE FROM documents WHERE id=" + documentID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete debt with following ID from database.
	 *
	 * @param debtID tools.Debt ID.
	 */
	public void deleteDebt(int debtID) {
		try {
			//language=SQLite
			this.executeUpdate("DELETE FROM debts WHERE debt_id=" + debtID);
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	 * @throws NoSuchElementException Throws when there is no User with such id.
	 * @throws InputMismatchException Throws when column name or value inserted incorrectly.
	 */
	public void editUserColumn(int userID, String column, String value) {

		if (!hasUser(userID)) {
			throw new NoSuchElementException("tools.Database: There is no such User with " + userID + " id");
		}

		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InputMismatchException("tools.Database: Incorrect column name or value.");
		}
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
	 * @throws NoSuchElementException Throws when there is no Document with such id.
	 * @throws InputMismatchException Throws when column name or value inserted incorrectly.
	 */
	public void editDocumentColumn(int documentID, String column, String value) {
		if (!hasUser(documentID)) {
			throw new NoSuchElementException("tools.Database: There is no such Document with " + documentID + " id");
		}


		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
			throw new InputMismatchException("tools.Database: Incorrect column name or value.");
		}
	}

	/**
	 * Modifies the user data in database.
	 *
	 * @param debtID tools.Debt ID.
	 * @param column Column to edit. Available options:
	 *               <br>"patron_id" to edit patron ID</br>
	 *               <br>"document_id" to edit document ID</br>
	 *               <br>"booking_date" to edit booking date</br>
	 *               <br>"expire_date" to edit expire date</br>
	 *               <br>"fee" to edit fee</br>
	 *               <br>"can_renew" to edit possibility of renewing document</br>
	 *               <br></br>
	 * @param value  New value.
	 * @throws NoSuchElementException Throws when there is no Debt with such id.
	 * @throws InputMismatchException Throws when column name or value inserted incorrectly.
	 */
	public void editDebtColumn(int debtID, String column, String value) {
		if (hasUser(debtID)) {
			throw new NoSuchElementException("tools.Database: There is no such Debt with " + debtID + " id");
		}
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();

			throw new InputMismatchException("tools.Database: Incorrect column name or value.");
		}
	}

	/**
	 * Erases all records in database and resets the indices.
	 */
	public void clear() {
		try {
			this.execute("DELETE FROM users");
			this.execute("DELETE FROM documents");
			this.execute("DELETE FROM debts");
			this.execute("DELETE FROM requests");
			System.out.println("tools.Database: Records cleared.");

			this.execute("UPDATE sqlite_sequence SET seq=0");
			System.out.println("tools.Database: Indices reset.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("tools.Database: Database is busy");
		}
	}

	/**
	 * Authorize and find user with following credentials.
	 *
	 * @param login    users.User login.
	 * @param password users.User password.
	 * @return {@code true} if user with following credentials found in database, {@code false} otherwise.
	 */
	public boolean login(String login, String password) {
		try {
			//language=SQLite
			ResultSet answer = executeQuery("SELECT * FROM users WHERE login = \'" + login + "\' and password = \'" + password + "\'");
			return answer.next();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("tools.Database: Login error occurred.");
			return false;
		}
	}

	/**
	 * Returns user id with such login and password.
	 *
	 * @param login    users.User login.
	 * @param password users.User password.
	 * @return users.User's ID.
	 * @throws NoSuchElementException Throws when there is no User with such login and password.
	 */
	public int loginId(String login, String password) {
		try {
			//language=SQLite
			ResultSet answer = executeQuery("SELECT * FROM users WHERE login = \'" + login + "\' and password = \'" + password + "\';");
			if (answer.next()) {
				return answer.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Find debt ID with following patron and document IDs.
	 *
	 * @param patronID users.Patron ID.
	 * @param docID    documents.Document ID.
	 * @return ID for found debt.
	 * @throws NoSuchElementException Throws when there is no Debt with such ID.
	 */
	public int findDebtID(int patronID, int docID) {
		try {
			//language=SQLite
			ResultSet debt = executeQuery("SELECT debt_id FROM debts WHERE patron_id = " + patronID + " AND document_id = " + docID);
			if (debt.next()) {
				return debt.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Find the ID for following document.
	 *
	 * @param document documents.Document to find.
	 * @return ID for found document.
	 * @throws NoSuchElementException Throws when there is no Document with such ID in database.
	 */
	public int getDocumentID(Document document) {
		for (Document i : getDocumentList()) {
			if (i.compare(document)) return i.getID();
		}
		throw new NoSuchElementException("tools.Database: Document \"" + document.getTitle() + "\" is not registered yet.");
	}

	/**
	 * Find the ID for following patron.
	 *
	 * @param patron users.Patron to find.
	 * @return ID for found patron.
	 * @throws NoSuchElementException Throws when there is no Patron with such ID in database.
	 */
	public int getPatronID(Patron patron) {
		for (Patron i : getPatronList()) {
			if (i.compare(patron)) return i.getId();
		}
		throw new NoSuchElementException("tools.Database: Patron \"" + patron.getSurname() + " " + patron.getName() + "\" is not registered yet.");
	}

	/**
	 * Find the ID for following librarian.
	 *
	 * @param librarian users.Librarian to find.
	 * @return ID for found librarian.
	 * @throws NoSuchElementException Throws when there is no Librarian with such ID in database.
	 */
	public int getLibrarianID(Librarian librarian) {
		for (Librarian i : getLibrarianList()) {
			if (i.compare(librarian)) return i.getId();
		}
		throw new NoSuchElementException("tools.Database: Patron \"" + librarian.getSurname() + " " + librarian.getName() + "\" is not registered yet.");
	}

	/**
	 * Returns list of Requests.
	 *
	 * @return list of all the unclosed requests from the database
	 */
	public ArrayList<Request> getRequests() {
		ArrayList<Request> requests = new ArrayList<>();
		try {
			ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE is_renew_request = 'false' ORDER BY priority, date");
			while (requestsSet.next()) {
				Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
						new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
				temp.setRequestId(requestsSet.getInt(1));
				requests.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return requests;
	}

	/**
	 * Returns list of renew requests.
	 *
	 * @return List of all the unclosed requests from the database.
	 */
	public ArrayList<Request> getRenewRequests() {
		ArrayList<Request> requests = new ArrayList<>();
		try {
			ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE is_renew_request = 'true' ORDER BY priority, date");

			while (requestsSet.next()) {
				Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
						new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
				temp.setRequestId(requestsSet.getInt(1));
				requests.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return requests;
	}

	/**
	 * Method for getting all Requests for certain Patron.
	 *
	 * @param patronID certain Patron ID.
	 * @return ArrayList of Patron's Requests
	 */
	public ArrayList<Request> getRequestsForPatron(int patronID) {
		ArrayList<Request> requests = new ArrayList<>();
		try {
			ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE patron_id = " + patronID + " and is_renew_request = 'false' ORDER BY priority, date");
			while (requestsSet.next()) {
				Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
						new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
				temp.setRequestId(requestsSet.getInt(1));
				requests.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return requests;
	}

	/**
	 * Method for getting all renew Requests for certain Patron.
	 *
	 * @param patronID certain Patron's ID.
	 * @return ArrayList of Paton's renew Requests.
	 */
	public ArrayList<Request> getRenewRequestsForPatron(int patronID) {
		ArrayList<Request> requests = new ArrayList<>();
		try {
			ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE patron_id = " + patronID + " and is_renew_request = 'true'");

			while (requestsSet.next()) {
				Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
						new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
				temp.setRequestId(requestsSet.getInt(1));
				requests.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return requests;
	}


	/**
	 * Method for searching Request with such patron ID and document ID.
	 *
	 * @param patronId Patron's ID.
	 * @param docId    Document's ID.
	 * @return Request from the database with such patron ID and document ID.
	 * @throws NoSuchElementException Throws when there is no Request with such patron ID and such document ID
	 */
	public Request getRequest(int patronId, int docId) {
		try {
			ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE patron_id = " + patronId + " and document_id = " + docId);
			if (requestsSet.next()) {
				Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
						new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
				temp.setRequestId(requestsSet.getInt(1));
				return temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		throw new NoSuchElementException();
	}


	/**
	 * Method for searching Request with such Request ID.
	 *
	 * @param id - ID of Request.
	 * @return Request from the database with such ID.
	 * @throws NoSuchElementException Throws when there is no Request with such ID.
	 */
	public Request getRequest(int id) {
		try {
			ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE request_id = " + id);
			if (requestsSet.next()) {
				Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
						new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
				temp.setRequestId(requestsSet.getInt(1));
				return temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		throw new NoSuchElementException();
	}


	/**
	 * Deletes request from the database by patron and document ids
	 *
	 * @param patronId   - id of patron whose request was closed
	 * @param documentId - id of document patron wanted to take
	 */
	public void deleteRequest(int patronId, int documentId) {
		try {
			executeUpdate("DELETE FROM requests WHERE patron_id = " + patronId + " AND document_id = " + documentId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes request from the database by request ID.
	 *
	 * @param requestId request id in database
	 */
	public void deleteRequest(int requestId) {
		try {
			executeUpdate("DELETE FROM requests WHERE request_id = " + requestId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for getting all requests for certain Document.
	 *
	 * @param docID certain Document's ID.
	 * @return ArrayList of Requests for Document.
	 */
	public ArrayList<Request> getRequestsForDocument(int docID) {
		ArrayList<Request> requests = new ArrayList<>();
		try {
			ResultSet requestsSet = executeQuery("SELECT * FROM requests WHERE document_id = " + docID + " ORDER BY priority, date");

			if (requestsSet.next()) {
				Request temp = new Request(this.getPatron(requestsSet.getInt(2)), this.getDocument(requestsSet.getInt(5)),
						new SimpleDateFormat("yyyy-MM-dd").parse(requestsSet.getString(7)), Boolean.parseBoolean(requestsSet.getString(8)));
				temp.setRequestId(requestsSet.getInt(1));
				temp.setRequestId(requestsSet.getInt(1));
				requests.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return requests;
	}

	/**
	 * Edit request table
	 *
	 * @param requestId id of request we want to edit
	 * @param column    Column to edit. Available options:
	 *                  <br>"patron_id" to edit patron ID</br>
	 *                  <br>"patron_name" to edit patron's name</br>
	 *                  <br>"patron_surname" to edit patron's surname</br>
	 *                  <br>"document_id" to edit ID of requested document</br>
	 *                  <br>"priority" to edit priority level</br>
	 *                  <br>"date" to edit date of request creation</br>
	 *                  <br></br>
	 * @param value     New value.
	 * @throws NoSuchElementException Throws when there is no Request with such id.
	 * @throws InputMismatchException Throws when column name or value inserted incorrectly.
	 */
	public void editRequest(int requestId, String column, String value) {
		if (!hasRequest(requestId)) {
			throw new NoSuchElementException("tools.Database: There is no such Request with " + requestId + " id");

		}
		try {
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
		} catch (SQLException e) {
			throw new InputMismatchException("tools.Database: Incorrect column name or value.");
		}
	}

	/**
	 * Method for determination of Document's status.
	 *
	 * @param documentId Document's ID.
	 * @return Document's status.
	 * @throws NoSuchElementException Throws when there is no Document with such ID.
	 */
	public String getStatusForDocument(int documentId) {
		try {
			ResultSet resultSet = executeQuery("SELECT type FROM documents WHERE id = " + documentId);
			if (resultSet.next()) {
				return resultSet.getString(1).toLowerCase();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}


	/**
	 * Method for removing all Requests for certain Document.
	 *
	 * @param documentId Document's ID.
	 */
	public void deleteRequestsForDocument(int documentId) {
		try {
			executeUpdate("DELETE FROM requests WHERE document_id = " + documentId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for inserting Notification into database.
	 *
	 * @param requestId   Request's ID.
	 * @param userId      User's ID.
	 * @param description Description of Notification.
	 * @param date        Date of Notification.
	 */
	public void insertNotification(int requestId, int userId, String description, Date date) {
		try {
			execute(String.format("INSERT INTO notifications (request_id, user_id, description, date)" +
					" VALUES (%d,%d,'%s','%s')", requestId, userId, description, (new SimpleDateFormat("yyyy-MM-dd")).format(date)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for getting all Notifications from database.
	 *
	 * @return ArrayList of Notifications.
	 */
	public ArrayList<Notification> getNotificationsList() {
		ArrayList<Notification> notifications = new ArrayList<>();
		try {
			ResultSet rs = executeQuery("SELECT * FROM notifications");
			while (rs.next()) {
				Notification temp = new Notification(rs.getInt(2), rs.getInt(3),
						rs.getString(4), new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5)));
				temp.setId(rs.getInt(1));
				notifications.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return notifications;
	}

	/**
	 * Method for getting certain Notification.
	 *
	 * @param notificationId Notification's ID.
	 * @return Notification object with such ID.
	 * @throws NoSuchElementException Throws when there is no Notification with such ID.
	 */
	public Notification getNotification(int notificationId) {
		try {
			ResultSet rs = executeQuery("SELECT * FROM notifications WHERE id = " + notificationId);
			if (rs.next()) {
				Notification temp = new Notification(rs.getInt(2), rs.getInt(3),
						rs.getString(4), new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5)));
				temp.setId(rs.getInt(1));
				return temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		throw new NoSuchElementException();
	}

	/**
	 * Method for getting all Notifications for certain User.
	 *
	 * @param userId User's ID.
	 * @return ArrayList of Notifications for certain User.
	 */
	public ArrayList<Notification> getNotificationsForUser(int userId) {
		ArrayList<Notification> notifications = new ArrayList<>();
		try {
			ResultSet rs = executeQuery("SELECT * FROM notifications WHERE user_id = " + userId);
			while (rs.next()) {
				Notification temp = new Notification(rs.getInt(2), rs.getInt(3),
						rs.getString(4), new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString(5)));
				temp.setId(rs.getInt(1));
				notifications.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			System.err.println("tools.Database: Parsing failed. One of element's date in database's table has incorrect format(not as \"yyyy-MM-dd\").");
		}
		return notifications;
	}

	/**
	 * Method for removing Notification from database.
	 *
	 * @param notificationId Notification's ID.
	 */
	public void deleteNotification(int notificationId) {
		try {
			executeUpdate("DELETE FROM notifications WHERE notification_id = " + notificationId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insertion Admin in Database.
	 *
	 * @param admin Inserting Admin.
	 */
	public void insertAdmin(Admin admin) {

		try{
			getAdmin();
			System.err.println("tools.Database: One admin is already exist!");
		} catch (NoSuchElementException e) {
			try {
				insertUser(admin.getLogin(),admin.getPassword(),"ADMIN", admin.getName(),admin.getSurname(),admin.getPhoneNumber(),admin.getAddress(),-1);
				admin.setId(getAdmin().getId());
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Method for getting Admin from Database.
	 *
	 * @return Admin from Database.
	 * @throws NoSuchElementException Throws when there is no Admin in Database.
	 */
	public Admin getAdmin() {
		try{
			//language=SQLite
			ResultSet rs = executeQuery("SELECT * FROM users WHERE status = 'ADMIN'");
			if(rs.next()) {
				return new Admin(rs.getString(2),
						rs.getString(3), rs.getString(5),
						rs.getString(6), rs.getString(7),
						rs.getString(8));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new NoSuchElementException();
	}

	/**
	 * Method for checking existence of such User in database.
	 *
	 * @param id User's ID.
	 * @return Is such User exist?
	 */
	private boolean hasUser(int id) {
		try {
			//language=SQLite
			return executeQuery("SELECT * FROM users WHERE id = " + id).next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method for checking existence of such Document in database.
	 *
	 * @param id Document's ID.
	 * @return Is such Document exist?
	 */
	private boolean hasDocument(int id) {
		try {
			//language=SQLite
			return executeQuery("SELECT * FROM documents WHERE id = " + id).next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method for checking existence of Debt in database.
	 *
	 * @param id Debt's ID.
	 * @return Is such Debt exist?
	 */
	private boolean hasDebt(int id) {
		try {
			//language=SQLite
			return executeQuery("SELECT * FROM debts WHERE debt_id = " + id).next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method for checking existence of Request in database.
	 *
	 * @param id Request's ID.
	 * @return Is such Request exist?
	 */
	private boolean hasRequest(int id) {
		try {
			//language=SQLite
			return executeQuery("SELECT * FROM requests WHERE request_id = " + id).next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method for logging your actions.
	 *
	 * @param log Line for log
	 */
	public void log(String log) {
		try {
			String time = (new SimpleDateFormat("HH:mm:ss")).format(new Date());
			//language=SQLite
			executeUpdate("INSERT INTO log (log) VALUES (\'[" + time + "]: " + log + "\')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for returning logs
	 *
	 * @return logs
	 */
	public ArrayList<String> getLog() {
		ArrayList<String> logs = new ArrayList<>();
		try {
			//language=SQLite
			ResultSet temp = executeQuery("SELECT * FROM log");
			while (temp.next()) {
				logs.add(temp.getString(0));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return logs;
	}
}