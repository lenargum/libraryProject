package materials;

/**
 * class implements "Book" itype of materials.Document
 */
public class Book extends Document implements BookInterface {
	private String Publisher; //name of person or company published current book
	private int editionYear; //year on which the book was published

	/**
	 * constructor
	 *
	 * @param title
	 * @param authors
	 * @param id
	 * @param allowed
	 * @param price
	 */
	public Book(String title, String authors, int id, boolean allowed, float price) {
		super(authors, title, id, allowed, price);
	}

	/**
	 * returns the year when book was edited
	 *
	 * @return
	 */
	@Override
	public int getEditionYear() {
		return this.editionYear;
	}

	/**
	 * sets year when the book was edited
	 *
	 * @param editionYear
	 */
	@Override
	public void setEditionYear(int editionYear) {
		this.editionYear = editionYear;
	}

	/**
	 * returns name of person or company the book was published by
	 *
	 * @return
	 */
	@Override
	public String getPublisher() {
		return Publisher;
	}

	/**
	 * sets name of person or company published the book
	 *
	 * @param publisher
	 */
	@Override
	public void setPublisher(String publisher) {
		this.Publisher = publisher;
	}

}