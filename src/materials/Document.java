package materials;

import java.util.TreeSet;


/**
 * represents "Document" class in materials.Document
 */
public abstract class Document implements DocumentInterface {

<<<<<<< HEAD
	private HashSet<String> Authors;    //TODO: i hope DONE
	private String Title;
	private boolean reference;
	private float Price;
	private boolean checked;    //Взяли книгу или нет
	private int userID;
	private int DocID;
	private boolean isAllowedForStudents;
	//  public ArrayList <materials.Document> listOfCopies; TODO: solve problem of storage -- databases


	public Document(String authors, String Title, int DocId, boolean isAllowedForStudents, float price) {
		this.Authors = new HashSet<>();
		setAuthors(authors);
		this.Title = Title;
		this.DocID = DocId;
		this.checked = false;
		setPrice(price);
		this.reference = false;
		this.userID = 0;
		setAllowedForStudents(isAllowedForStudents);
	}

	@Override
	public String getTitle() {
		return Title;
	}

	@Override
	public void setTitle(String title) {
		this.Title = title;
	}

	@Override
	public String getAuthors() {
		return Authors.toString();
	}

	@Override
	public void setAuthors(String authors) {
		for (String newAuthor : authors.split(", ")) {
			addAuthor(newAuthor.toLowerCase());
		}
	}

	@Override
	public float getPrice() {
		return Price;
	}

	@Override
	public void setPrice(float price) {
		this.Price = price;
	}

	@Override
	public int getDocID() {
		return DocID;
	}

	@Override
	public void setDocID(int id) {
		this.DocID = id;
	}

	@Override
	public int getUserID() {
		return userID;
	}

	@Override
	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public boolean isChecked() {
		return checked;
	} //взяли книгу или нет

	@Override
	public void setChecked(boolean checked) {
		this.checked = checked;
	} //взяли книгу или нет

	@Override
	public boolean isReference() {
		return reference;
	}

	@Override
	public void setReference(boolean reference) {
		this.reference = reference;
	}

	@Override
	public boolean isAllowedForStudents() {
		return isAllowedForStudents;
	}

	@Override
	public void setAllowedForStudents(boolean allowedForStudents) {
		this.isAllowedForStudents = allowedForStudents;
	}
=======
    private TreeSet<String> Authors;    //list of author(s)
    private String Title; //title of current document
    private boolean reference; //represents "function" of this document - if it is reference-book we cannot take it
    private float Price;// is price of current document
    private boolean checked;    //represents status of this document - if it is already checked we cannot take it
    private int userID; //ID of user who took the document
    private int DocID; //ID of current document
    private boolean isAllowedForStudents; //represents whether students can use this document
    //  public ArrayList <materials.Document> listOfCopies; TODO: solve problem of storage -- databases


    /**
     * constructor
     * @param authors
     * @param Title
     * @param DocId
     * @param isAllowedForStudents
     * @param price
     */
    public Document(String authors, String Title, int DocId, boolean isAllowedForStudents, float price) {
        this.Authors = new TreeSet<>();
        setAuthors(authors);
        this.Title = Title;
        this.DocID = DocId;
        this.checked = false;
        setPrice(price);
        this.reference = false;
        this.userID = 0;
        setAllowedForStudents(isAllowedForStudents);
    }

    /**
     * sets title of current document
     * @param Title
     */
    @Override
    public void setTitle(String title) {
        this.Title = title;
    }

    /**
     * sets Id of user who takes the document
     * @param userID
     */
    @Override
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * sets author(s) names
     * @param authors
     */
    @Override
    public void setAuthors(String authors) {
        for (String newAuthor : authors.split(", ")) {
            addAuthor(newAuthor.toLowerCase());
        }
    }

    /**
     * sets ID of current document
     * @param id
     */
    @Override
    public void setDocID(int id) {
        this.DocID = id;
    }

    /**
     * sets price of current document
     * @param price
     */
    @Override
    public void setPrice(float price) {
        this.Price = price;
    }

    /**
     * sets status of this document - if it is already checked we cannot take it
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    } //взяли книгу или нет

    /**
     * sets "function" of this document - if it is reference-book we cannot take it
     * @param reference
     */
    @Override
    public void setReference(boolean reference) {
        this.reference = reference;
    }

    /**
     * sets whether students can use this document
     * @param allowedForStudents
     */
    @Override
    public void setAllowedForStudents(boolean allowedForStudents) {
        this.isAllowedForStudents = allowedForStudents;
    }

    /**
     *
     * @return title of current document
     */
    @Override
    public String getTitle() {
        return Title;
    }

    /**
     *
     * @return list of author of document
     */
    @Override
    public String getAuthors() {
        return Authors.toString();
    }

    /**
     *
     * @return price of current document
     */
    @Override
    public float getPrice() {
        return Price;
    }

    /**
     *
     * @return ID of current document
     */
    @Override
    public int getDocID() {
        return DocID;
    }

    /**
     *
     * @return ID of user who took Document
     */
    @Override
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @return status of this document - if it is already checked we cannot take it
     */
    @Override
    public boolean isChecked() {
        return checked;
    } //взяли книгу или нет

    /**
     *
     * @return "function" of this document - if it is reference-book we cannot take it
     */
    @Override
    public boolean isReference() {
        return reference;
    }

    /**
     *
     * @return whether students can use this document
     */
    @Override
    public boolean isAllowedForStudents() {
        return isAllowedForStudents;
    }
>>>>>>> User-Documents-Connection

//    public boolean isFaculty(Patron x){
//        Typetester t = new Typetester();
//        t.setType(x);
//        return t.getType().equals("faculty");
//    }

//    public boolean canTake(Patron user){
//        if(isFaculty(user) && !checked) return true;
//        return isAllowedForStudents() && !checked;
//    }

<<<<<<< HEAD
	@Override
	public void addAuthor(String newAuthor) {
		this.Authors.add(newAuthor);
	}

	@Override
	public boolean isWrittenBy(String author) {
		author = author.toLowerCase();
		return Authors.contains(author);
	} //если мы хотuм проверить есть ли среди авторов документа данный автор

	/**
	 * checks wether one document is copy of another
	 *
	 * @param document
	 * @return true if current document is copy of other
	 */
	public boolean equals(Document document) {
		return this.getDocID() != document.getDocID() && this.getAuthors().equals(document.getAuthors()) && this.getTitle().equals(document.getTitle());
	}
=======
    /**
     * if librarian finds out that there are one more author of this document he can add him
     * @param newAuthor
     */
    @Override
    public void addAuthor(String newAuthor) {
        this.Authors.add(newAuthor);
    }

    /**
     * sometimes we need to check whether on person is author of some document
     * @param author
     * @return whether the person is author of current document
     */
    @Override
    public boolean isWrittenBy(String author) {
        author = author.toLowerCase();
        return Authors.contains(author);
    } //если мы хотuм проверить есть ли среди авторов документа данный автор

    /**
     * checks wether one document is copy of another
     * @param document
     * @return true if current document is copy of other
     */
    public boolean equals(Document document){
        return this.getDocID() != document.getDocID() && this.getAuthors().equals(document.getAuthors()) && this.getTitle().equals( document.getTitle());
    }
>>>>>>> User-Documents-Connection

}
