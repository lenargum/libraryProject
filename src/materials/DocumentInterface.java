package materials;

/**
 * interface represents functions and properties of documents
 */
public interface DocumentInterface {

    /**
     * sets title of current document
     * @param Title
     */
    public void setTitle(String Title);

    /**
     * sets author(s) names
     * @param Authors
     */
    public void setAuthors(String Authors);

    /**
     * sets Id of user who takes the document
     * @param userID
     */
    public void setUserID(int userID);

    /**
     * sets ID of current document
     * @param id
     */
    public void setDocID(int id);

    /**
     * sets price of current document
     * @param price
     */
    public void setPrice(float price);

    /**
     * sets status of this document - if it is already checked we cannot take it
     * @param checked
     */
    public void setChecked(boolean checked);

    /**
     * sets "function" of this document - if it is reference-book we cannot take it
     * @param reference
     */
    public void setReference(boolean reference);

    /**
     * sets whether students can use this document
     * @param allowedForStudents
     */
    public void setAllowedForStudents(boolean allowedForStudents);

    /**
     *
     * @return title of current document
     */
    public String getTitle();

    /**
     *
     * @return list of author of document
     */
    public String getAuthors();

    /**
     *
     * @return price of current document
     */
    public float getPrice();

    /**
     *
     * @return ID of current document
     */
    public int getDocID();

    /**
     *
     * @return ID of user who took Document
     */
    public int getUserID();

    /**
     *
     * @return status of this document - if it is already checked we cannot take it
     */
    public boolean isChecked(); //взяли книгу или нет

    /**
     *
     * @return "function" of this document - if it is reference-book we cannot take it
     */
    public boolean isReference(); //является ли документ справочным(влияет на возможность забрать его из библиотеки)

    /**
     *
     * @return whether students can use this document
     */
    public boolean isAllowedForStudents();

//    public boolean canTake(Patron user);

//    public boolean isFaculty(Patron x);

    /**
     * if librarian finds out that there are one more author of this document he can add him
     * @param newAuthor
     */
    public void addAuthor(String newAuthor);

    /**
     * sometimes we need to check whether on person is author of some document
     * @param author
     * @return whether the person is author of current document
     */
    public boolean isWrittenBy(String author);

}
