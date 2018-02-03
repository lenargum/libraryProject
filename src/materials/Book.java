package materials;

/**
 * class implements books in materials.Document
 */
public class Book extends Document implements BookInterface {
    private String Publisher;
    private int editionYear;

    /**
     * constructor
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
     * sets name of person or company published the book
     * @param publisher
     */
    @Override
    public void setPublisher(String publisher) {
        this.Publisher = publisher;
    }

    /**
     * sets year when the book was edited
     * @param editionYear
     */
    @Override
    public void setEditionYear(int editionYear) {
        this.editionYear = editionYear;
    }

    /**
     * returns the year when book was edited
     * @return
     */
    @Override
    public int getEditionYear() {
        return this.editionYear;
    }

    /**
     * returns name of person or company the book was published by
     * @return
     */
    @Override
    public String getPublisher() {
        return Publisher;
    }

}