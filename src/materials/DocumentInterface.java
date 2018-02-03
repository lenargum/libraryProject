package materials;

/**
 * Interface describes all the documents as class
 */
public interface DocumentInterface {

	/**
	 * returns title of document
	 *
	 * @return
	 */
	public String getTitle();

	/**
	 * sets title of document
	 *
	 * @param Title
	 */
	public void setTitle(String Title);

	/**
	 * returns name or list of names of author(s) the document was created by
	 *
	 * @return
	 */
	public String getAuthors();

	/**
	 * sets name or list of names of author(s) the document was created by
	 *
	 * @param Authors
	 */
	public void setAuthors(String Authors);

	/**
	 * returns price of current document
	 *
	 * @return
	 */
	public float getPrice();

	/**
	 * sets price of current document
	 *
	 * @param price
	 */
	public void setPrice(float price);

	/**
	 * returns ID of current document
	 *
	 * @return
	 */
	public int getDocID();

	/**
	 * sets ID of current document
	 *
	 * @param id
	 */
	public void setDocID(int id);

	/**
	 * returns ID of user the Document was taken by
	 *
	 * @return
	 */
	public int getUserID();

	/**
	 * sets ID of user the Document was taken by
	 *
	 * @param userID
	 */
	public void setUserID(int userID);

	/**
	 * returns current state of document: true if it is taken by some user, false otherwise
	 *
	 * @return
	 */
	public boolean isChecked(); //взяли книгу или нет

	/**
	 * sets current state of document: true if it is taken by some user, false otherwise
	 *
	 * @param checked
	 */
	public void setChecked(boolean checked);

	/**
	 * returns value of current document: true if document is reference-book and false otherwise
	 *
	 * @return
	 */
	public boolean isReference(); //является ли документ справочным(влияет на возможность забрать его из библиотеки)

	/**
	 * sets value of current document: true if document is reference-book and false otherwise
	 *
	 * @param reference
	 */
	public void setReference(boolean reference);

	/**
	 * returns parametr which shows possibility for students to take the document
	 *
	 * @return
	 */
	public boolean isAllowedForStudents();

	/**
	 * sets parametr which shows possibility for students to take the document
	 *
	 * @param allowedForStudents
	 */
	public void setAllowedForStudents(boolean allowedForStudents);

//    public boolean canTake(Patron user);

//    public boolean isFaculty(Patron x);

	/**
	 * adds Author to the list of authors, the document was created by
	 *
	 * @param newAuthor is author that need to be added
	 */
	public void addAuthor(String newAuthor);

	/**
	 * checks if author is among list of persons the document was created by
	 *
	 * @param author
	 * @return true if author toook a part in document creation
	 */
	public boolean isWrittenBy(String author);

    /**
     * checks wether one document is copy of another
     * @param other
     * @return true if current document is copy of other
     */
    public boolean equals(Document other);

}
