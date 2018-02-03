package materials;

/**
 * interface describing book as class
 */
public interface BookInterface {

    /**
     * sets name of person or company published the book
     * @param publisher
     */
    public void setPublisher(String publisher);

    /**
     * sets year when the book was edited
     * @param editionYear
     */
    public void setEditionYear(int editionYear);

    /**
     * returns the year when book was edited
     * @return
     */
    public int getEditionYear();

    /**
     * returns name of person or company the book was published by
     * @return
     */
    public String getPublisher();
}
