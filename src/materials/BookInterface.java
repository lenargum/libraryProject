package materials;

/**
 * interface describing book as class
 */
public interface BookInterface {

	/**
	 * returns the year when book was edited
	 *
	 * @return
	 */
	public int getEditionYear();

	/**
	 * sets year when the book was edited
	 *
	 * @param editionYear
	 */
	public void setEditionYear(int editionYear);

	/**
	 * returns name of person or company the book was published by
	 *
	 * @return
	 */
	public String getPublisher();

	/**
	 * sets name of person or company published the book
	 *
	 * @param publisher
	 */
	public void setPublisher(String publisher);
}
