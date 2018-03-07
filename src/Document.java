public class Document {
	/**
	 * ID of  document
	 */
	private int ID;
	/**
	 * Title of document
	 */
	private String Title;
	/**
	 * Author(s) of document
	 */
	private String Authors;
	/**
	 * shows wether students can take the document
	 */
	private boolean IsAllowedForStudents;
	/**
	 * number of document copies stored in the library
	 */
	private int NumberOfCopies;
	/**
	 * shows wether document is reference - if it is patrons cannot take it from the library
	 */
	private boolean IsReference;
	/**
	 * Price of document
	 */
	private double Price;
	/**
	 * keywords we use to search document
	 */
	private String KeyWords;
	private String Type;

	/**
	 * constructor
	 *
	 * @param Title
	 * @param Authors
	 * @param IsAllowedForStudents
	 * @param NumberOfCopies
	 * @param IsReference
	 * @param Price
	 * @param KeyWords
	 */
	public Document(String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords) {
		setTitle(Title);
		setAuthors(Authors);
		setAllowedForStudents(IsAllowedForStudents);
		setNumberOfCopies(NumberOfCopies);
		setReference(IsReference);
		setPrice(Price);
		setKeyWords(KeyWords);
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		this.Type = type;
	}

	/**
	 * @return ID of document
	 */
	public int getID() {
		return ID;
	}

	/**
	 * sets ID of document
	 *
	 * @param id
	 */
	public void setID(int id) {
		this.ID = id;
	}

	/**
	 * @return title of document
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * sets Title of document
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		this.Title = title;
	}

	/**
	 * adds author to list of authors
	 *
	 * @param author
	 */
	public void addAuthor(String author) {
		this.Authors += ", " + author;
	}

	/**
	 * @param author
	 * @return true if author was among the authors created the document
	 */
	public boolean includesAuthor(String author) {//TODO: write it!
		return true;
	}

	/**
	 * @return list of authors created the document
	 */
	public String getAuthors() {
		return Authors;
	}

	/**
	 * sets author(s) of document
	 *
	 * @param authors
	 */
	public void setAuthors(String authors) {
		this.Authors = authors;
	}

	/**
	 * @return wether sstudents can take the document
	 */
	public boolean isAllowedForStudents() {
		return IsAllowedForStudents;
	}

	/**
	 * shows wether students can take document
	 *
	 * @param isAllowed
	 */
	public void setAllowedForStudents(boolean isAllowed) {
		this.IsAllowedForStudents = isAllowed;
	}

	/**
	 * adds copy of document
	 */
	public void addCopy() {
		this.NumberOfCopies++;
	}

	/**
	 * deletes copy of document
	 */
	public void deleteCopy() {
		this.NumberOfCopies--;
	}

	/**
	 * @return number of documents copies stored in the library
	 */
	public int getNumberOfCopies() {
		return this.NumberOfCopies;
	}

	/**
	 * sets number of copies of document that are stored in the library
	 *
	 * @param number
	 */
	public void setNumberOfCopies(int number) {
		this.NumberOfCopies = number;
	}

	/**
	 * @return is document reference or not
	 */
	public boolean isReference() {
		return IsReference;
	}

	/**
	 * shows wether document is reference
	 *
	 * @param isReference
	 */
	public void setReference(boolean isReference) {
		this.IsReference = isReference;
	}

	/**
	 * adds keyword for document
	 *
	 * @param newWord
	 */
	public void addKeyWord(String newWord) {
		this.KeyWords += ", " + newWord;
	}

	/**
	 * checks out does keyword belong to keywords for document
	 *
	 * @param keyWord
	 * @return
	 */
	public boolean includeKeyWord(String keyWord) {//TODO: write it!
		return true;
	}

	/**
	 * @return keywords of document
	 */
	public String getKeyWords() {
		return KeyWords;
	}

	/**
	 * sets keywords we can use to search for document
	 *
	 * @param keyWords
	 */
	public void setKeyWords(String keyWords) {
		this.KeyWords = keyWords;
	}

	/**
	 * @return price of document
	 */
	public double getPrice() {
		return Price;
	}

	/**
	 * sets price of document
	 *
	 * @param price
	 */
	public void setPrice(double price) {
		this.Price = price;
	}

	boolean compare(Document document){
		return this.getAuthors().equals(document.getAuthors())
				&& this.getTitle().equals(document.getTitle())
				&& this.KeyWords.equals(document.getKeyWords());
	}
}

