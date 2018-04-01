import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * This class describes document of library system.
 *
 * @author Anastasia Minakova
 */
public class Document {
	/**
	 * Document ID.
	 */
	private int ID;

	/**
	 * Document title.
	 */
	private String Title;

	/**
	 * Document authors.
	 */
	private String Authors;

	/**
	 * Student allowance status.
	 */
	private boolean allowedForStudents;

	/**
	 * Count of copies.
	 */
	private int NumberOfCopies;

	/**
	 * Reference status.
	 */
	private boolean IsReference;

	/**
	 * Document price.
	 */
	private double Price;

	/**
	 * Search keywords.
	 */
	private String KeyWords;

	/**
	 * Document type.
	 */
	private String Type;

	/**
	 * Initialize new Document.
	 *
	 * @param Title              Title.
	 * @param Authors            Authors.
	 * @param allowedForStudents Student allowance status.
	 * @param NumberOfCopies     Count of copies.
	 * @param IsReference        Reference status.
	 * @param Price              Price.
	 * @param KeyWords           Search keywords.
	 */
	public Document(String Title, String Authors, boolean allowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords) {
		setTitle(Title);
		setAuthors(Authors);
		setAllowedForStudents(allowedForStudents);
		setNumberOfCopies(NumberOfCopies);
		setReference(IsReference);
		setPrice(Price);
		setKeyWords(KeyWords);
	}

	/**
	 * Get the type of document.
	 * Possible values:
	 * <table>
	 * <tr>
	 * <td>book</td>
	 * <td>journal article</td>
	 * <td>audio/video material</td>
	 * </tr>
	 * </table>
	 *
	 * @return String with document type.
	 */
	public String getType() {
		return Type;
	}

	/**
	 * Set the type of document.
	 * Possible values:
	 * <table>
	 * <tr>
	 * <td>book</td>
	 * <td>journal article</td>
	 * <td>audio/video material</td>
	 * </tr>
	 * </table>
	 *
	 * @param type String with document type.
	 */
	public void setType(String type) {
		this.Type = type;
	}

	/**
	 * Get the document ID.
	 *
	 * @return Document ID.
	 */
	public int getID() {
		return ID;
	}

	/**
	 * Set the new ID to this document.
	 *
	 * @param id New ID.
	 */
	public void setID(int id) {
		this.ID = id;
	}

	/**
	 * Get the document title.
	 *
	 * @return Document title.
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * Set the new title to this document.
	 *
	 * @param title New title.
	 */
	public void setTitle(String title) {
		this.Title = title;
	}

	/**
	 * Add author to this document.
	 *
	 * @param author Additional author.
	 */
	public void addAuthor(String author) {
		this.Authors += ", " + author;
	}

	/**
	 * Checks whether current document has following author.
	 *
	 * @param author Author to find.
	 * @return {@code true} if document has following author, {@code false} otherwise.
	 */
	public boolean includesAuthor(String author) {//TODO: write it!
		throw new NotImplementedException();
	}

	/**
	 * Get all the authors of this document.
	 *
	 * @return All document authors.
	 */
	public String getAuthors() {
		return Authors;
	}

	/**
	 * Set new authors to this document.
	 *
	 * @param authors New authors.
	 */
	public void setAuthors(String authors) {
		this.Authors = authors;
	}

	/**
	 * Get the student allowance status.
	 *
	 * @return Student allowance status.
	 */
	public boolean isAllowedForStudents() {
		return allowedForStudents;
	}

	/**
	 * Set the new student allowance status.
	 *
	 * @param isAllowed New allowance status.
	 */
	public void setAllowedForStudents(boolean isAllowed) {
		this.allowedForStudents = isAllowed;
	}

	/**
	 * Increases number of document copies.
	 */
	public void addCopy() {
		this.NumberOfCopies++;
	}

	/**
	 * Decreases the number of document copies.
	 */
	public void deleteCopy() {
		this.NumberOfCopies--;
	}

	/**
	 * Get number of document copies stored in the library.
	 *
	 * @return Number of copies.
	 */
	public int getNumberOfCopies() {
		return this.NumberOfCopies;
	}

	/**
	 * Set the new number of document copies stored in the library.
	 *
	 * @param number New number of copies.
	 */
	public void setNumberOfCopies(int number) {
		this.NumberOfCopies = number;
	}

	/**
	 * Get the reference status of this document.
	 *
	 * @return Reference status.
	 */
	public boolean isReference() {
		return IsReference;
	}

	/**
	 * Set the new reference status for this document.
	 *
	 * @param isReference New reference status.
	 */
	public void setReference(boolean isReference) {
		this.IsReference = isReference;
	}

	/**
	 * Add new keyword to this document.
	 *
	 * @param newWord New keyword.
	 */
	public void addKeyWord(String newWord) {
		this.KeyWords += ", " + newWord;
	}

	/**
	 * Checks out does following keyword belongs to this document.
	 *
	 * @param keyWord Keyword to find.
	 * @return {@code true} if keyword belongs to this document, {@code false} otherwise.
	 */
	public boolean includeKeyWord(String keyWord) {//TODO: write it!
		throw new NotImplementedException();
	}

	/**
	 * Get this document keywords.
	 *
	 * @return Keywords of this document.
	 */
	public String getKeyWords() {
		return KeyWords;
	}

	/**
	 * Sets the new search keywords for this document.
	 *
	 * @param keyWords New keywords.
	 */
	public void setKeyWords(String keyWords) {
		this.KeyWords = keyWords;
	}

	/**
	 * Get the price of this document.
	 *
	 * @return Document price.
	 */
	public double getPrice() {
		return Price;
	}

	/**
	 * Set the new price for this document.
	 *
	 * @param price New price.
	 */
	public void setPrice(double price) {
		this.Price = price;
	}

	/**
	 * Compare two documents.
	 *
	 * @param document Another document to compare.
	 * @return {@code true} if documents are similar, {@code false} otherwise.
	 */
	public boolean compare(Document document) {
		return this.getAuthors().equals(document.getAuthors())
				&& this.getTitle().equals(document.getTitle())
				&& this.KeyWords.equals(document.getKeyWords());
	}

	/**
	 * Compare two documents.
	 *
	 * @param document Another document to compare.
	 * @return {@code true} if documents are similar, {@code false} otherwise.
	 */
	public boolean equals(Document document) {
		return compare(document);
	}
}

