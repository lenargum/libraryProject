public class Book extends Document {
	/**
	 * person or organisation published the book
	 */
	private String Publisher;
	/**
	 * year of books edition
	 */
	private int Edition;
	/**
	 * shows wether book is bestseller
	 */
	private boolean IsBestseller;

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
	public Book(String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords, String Publisher, int Edition, boolean IsBestseller) {
		super(Title, Authors, IsAllowedForStudents, NumberOfCopies, IsReference, Price, KeyWords);
		setPublisher(Publisher);
		setEdition(Edition);
		setBestseller(IsBestseller);
		setType("book");
	}

	/**
	 * @return publisher of book
	 */
	public String getPublisher() {
		return Publisher;
	}

	/**
	 * sets publisher of book
	 *
	 * @param publisher
	 */
	public void setPublisher(String publisher) {
		this.Publisher = publisher;
	}

	/**
	 * @return edition of book
	 */
	public int getEdition() {
		return Edition;
	}

	/**
	 * sets edition of the book
	 *
	 * @param edition
	 */
	public void setEdition(int edition) {
		this.Edition = edition;
	}

	/**
	 * @return is book bestseller
	 */
	public boolean isBestseller() {
		return IsBestseller;
	}

	/**
	 * sets wether book is bestseller
	 *
	 * @param isBestseller
	 */
	public void setBestseller(boolean isBestseller) {
		this.IsBestseller = isBestseller;
	}

	public String toString() {
		return ("Id: " + this.getID() + "\n" +
				"Title: " + this.getTitle() + "\n" +
				"Authors: " + this.getAuthors() + "\n" +
				"Allowed for students: " + this.isAllowedForStudents() + "\n" +
				"Number of copies: " + this.getNumberOfCopies() + "\n" +
				"This is reference book: " + this.isReference() + "\n" +
				"Price: " + this.getPrice() + "\n" +
				"KeyWords: " + this.getKeyWords() + "\n" +
				"Publisher: " + this.getPublisher() + "\n" +
				"Edition: " + this.getEdition() + "\n" +
				"This book is bestseller: " + this.getTitle() + "\n");
	}
}
