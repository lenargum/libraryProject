package documents;

/**
 * This class describes book of library system.
 *
 * @author Anastasia Minakova
 * @see Document
 */
public class Book extends Document {
	/**
	 * documents.Book publisher.
	 */
	private String Publisher;

	/**
	 * Edition year.
	 */
	private int Edition;

	/**
	 * Bestseller status.
	 */
	private boolean IsBestseller;

	/**
	 * Initialize new book.
	 *
	 * @param Title                Title.
	 * @param Authors              Authors.
	 * @param IsAllowedForStudents Student allowance status.
	 * @param NumberOfCopies       Count of copies.
	 * @param IsReference          Reference status.
	 * @param Price                Price.
	 * @param KeyWords             Search keywords.
	 * @param Publisher            Books publisher.
	 * @param Edition              Edition year.
	 * @param IsBestseller         Bestseller status.
	 */
	public Book(String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords, String Publisher, int Edition, boolean IsBestseller) {
		super(Title, Authors, IsAllowedForStudents, NumberOfCopies, IsReference, Price, KeyWords);
		setPublisher(Publisher);
		setEdition(Edition);
		setBestseller(IsBestseller);
		setType("book");
	}

	/**
	 * Get the publisher of this book.
	 *
	 * @return Publisher.
	 */
	public String getPublisher() {
		return Publisher;
	}

	/**
	 * Set the new publisher of this book.
	 *
	 * @param publisher New publisher.
	 */
	private void setPublisher(String publisher) {
		this.Publisher = publisher;
	}

	/**
	 * Get the edition year of this book.
	 *
	 * @return Edition year.
	 */
	public int getEdition() {
		return Edition;
	}

	/**
	 * Set the new edition year to this book.
	 *
	 * @param edition New edition year.
	 */
	public void setEdition(int edition) {
		this.Edition = edition;
	}

	/**
	 * Get bestseller status of this book.
	 *
	 * @return {@code true} if this book is bestseller, {@code false} otherwise.
	 */
	public boolean isBestseller() {
		return IsBestseller;
	}

	/**
	 * Set the new bestseller status.
	 *
	 * @param isBestseller New bestseller status.
	 */
	public void setBestseller(boolean isBestseller) {
		this.IsBestseller = isBestseller;
	}

	/**
	 * Get this book in string notation,
	 *
	 * @return String with book description.
	 */
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
