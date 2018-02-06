package materials;

import java.util.LinkedList;
import java.util.TreeSet;


/**
 * represents "Document" class in materials.Document
 */
public abstract class Document implements DocumentInterface {

<<<<<<< HEAD
    private TreeSet<String> Authors;    //list of author(s)
    private String Title; //title of current document
    private boolean reference; //represents "function" of this document - if it is reference-book we cannot take it
    private float Price;// is price of current document
    private boolean checked;    //represents status of this document - if it is already checked we cannot take it
    private int userID; //ID of user who took the document
    private int DocID; //ID of current document
    private boolean isAllowedForStudents; //represents whether students can use this document
    private boolean isCopy;
    private int originID;
    private LinkedList<Integer> copiesIDs;


    /**
     * constructor
     *
     * @param authors
     * @param Title
     * @param DocId
     * @param isAllowedForStudents
     * @param price
     */
    public Document(String authors, String Title, int DocId, boolean isAllowedForStudents, boolean isCopy, float price) {
        this.Authors = new TreeSet<>();
        setAuthors(authors);
        this.Title = Title;
        this.DocID = DocId;
        this.checked = false;
        setPrice(price);
        this.reference = false;
        this.userID = 0;
        this.isCopy = isCopy;
        setAllowedForStudents(isAllowedForStudents);
    }

    /**
     * @return title of current document
     */
    @Override
    public String getTitle() {
        return Title;
    }

    /**
     * sets title of current document
     *
     * @param title
     */
    @Override
    public void setTitle(String title) {
        this.Title = title;
    }

    /**
     * @return list of author of document
     */
    @Override
    public String getAuthors() {
        return Authors.toString();
    }

    /**
     * sets author(s) names
     *
     * @param authors
     */
    @Override
    public void setAuthors(String authors) {
        for (String newAuthor : authors.split(", ")) {
            addAuthor(newAuthor.toLowerCase());
        }
    }

    /**
     * @return price of current document
     */
    @Override
    public float getPrice() {
        return Price;
    }

    /**
     * sets price of current document
     *
     * @param price
     */
    @Override
    public void setPrice(float price) {
        this.Price = price;
    }

    /**
     * @return ID of current document
     */
    @Override
    public int getDocID() {
        return DocID;
    }

    /**
     * sets ID of current document
     *
     * @param id
     */
    @Override
    public void setDocID(int id) {
        this.DocID = id;
    }

    /**
     * @return ID of user who took Document
     */
    @Override
    public int getUserID() {
        return userID;
    }

    /**
     * sets Id of user who takes the document
     *
     * @param userID
     */
    @Override
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return status of this document - if it is already checked we cannot take it
     */
    @Override
    public boolean isChecked() {
        return checked;
    } //взяли книгу или нет

    /**
     * sets status of this document - if it is already checked we cannot take it
     *
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    } //взяли книгу или нет

    /**
     * @return "function" of this document - if it is reference-book we cannot take it
     */
    @Override
    public boolean isReference() {
        return reference;
    }

    /**
     * sets "function" of this document - if it is reference-book we cannot take it
     *
     * @param reference
     */
    @Override
    public void setReference(boolean reference) {
        this.reference = reference;
    }

    /**
     * @return whether students can use this document
     */
    @Override
    public boolean isAllowedForStudents() {
        return isAllowedForStudents;
    }

    /**
     * sets whether students can use this document
     *
     * @param allowedForStudents
     */
    @Override
    public void setAllowedForStudents(boolean allowedForStudents) {
        this.isAllowedForStudents = allowedForStudents;
    }

    /**
     * if document is copy
     *
     * @return
     */
    public boolean isCopy() {
        return isCopy;
    }

    /**
     * sets true if such document already exists in the library
     *
     * @param is_copy
     */
    public void setCopy(boolean is_copy) {
        this.isCopy = is_copy;
        if (is_copy) this.copiesIDs.add(this.getDocID());
        else this.copiesIDs = new LinkedList<>();
    }

    /**
     * @return ID of origin document
     */
    public int getOriginID() {
        return originID;
    }

    /**
     * sets ID of original document
     *
     * @param id
     */
    public void setOriginID(int id) {
        this.originID = id;
    }

    /**
     * @return list of document copies
     */
    public LinkedList<Integer> getCopiesIDs() {
        return copiesIDs;
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
	private boolean isCopy;
	private int originID;
	private LinkedList <Integer> copiesIDs;


	/**
	 * constructor
	 *
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
	 * @return title of current document
	 */
	@Override
	public String getTitle() {
		return Title;
	}

	/**
	 * sets title of current document
	 *
	 * @param title
	 */
	@Override
	public void setTitle(String title) {
		this.Title = title;
	}

	/**
	 * @return list of author of document
	 */
	@Override
	public String getAuthors() {
		return Authors.toString();
	}

	/**
	 * sets author(s) names
	 *
	 * @param authors
	 */
	@Override
	public void setAuthors(String authors) {
		for (String newAuthor : authors.split(", ")) {
			addAuthor(newAuthor.toLowerCase());
		}
	}

	/**
	 * sets ID of original document
	 * @param id
	 */
	public void setOriginID(int id){
		this.originID = id;
	}

	/**
	 * sets true if such document already exists in the library
	 * @param is_copy
	 */
	public void setCopy(boolean is_copy){
		this.isCopy = is_copy;
		if(is_copy) addCopy(getDocID());
		else this.copiesIDs = new LinkedList<>();
	}

	/**
	 * @return price of current document
	 */
	@Override
	public float getPrice() {
		return Price;
	}

	/**
	 * sets price of current document
	 *
	 * @param price
	 */
	@Override
	public void setPrice(float price) {
		this.Price = price;
	}

	/**
	 * @return ID of current document
	 */
	@Override
	public int getDocID() {
		return DocID;
	}

	/**
	 * sets ID of current document
	 *
	 * @param id
	 */
	@Override
	public void setDocID(int id) {
		this.DocID = id;
	}

	/**
	 * @return ID of user who took Document
	 */
	@Override
	public int getUserID() {
		return userID;
	}

	/**
	 * sets Id of user who takes the document
	 *
	 * @param userID
	 */
	@Override
	public void setUserID(int userID) {
		this.userID = userID;
	}

	/**
	 * @return status of this document - if it is already checked we cannot take it
	 */
	@Override
	public boolean isChecked() {
		return checked;
	} //взяли книгу или нет

	/**
	 * sets status of this document - if it is already checked we cannot take it
	 *
	 * @param checked
	 */
	@Override
	public void setChecked(boolean checked) {
		this.checked = checked;
	} //взяли книгу или нет

	/**
	 * @return "function" of this document - if it is reference-book we cannot take it
	 */
	@Override
	public boolean isReference() {
		return reference;
	}

	/**
	 * sets "function" of this document - if it is reference-book we cannot take it
	 *
	 * @param reference
	 */
	@Override
	public void setReference(boolean reference) {
		this.reference = reference;
	}

	/**
	 * @return whether students can use this document
	 */
	@Override
	public boolean isAllowedForStudents() {
		return isAllowedForStudents;
	}

	/**
	 * if document is copy
	 * @return
	 */
	public boolean isCopy(){
		return isCopy;
	}

	/**
	 *
	 * @return ID of origin document
	 */
	public int getOriginID(){
		return originID;
	}

	/**
	 *
	 * @return list of document copies
	 */
	public LinkedList<Integer> getCopiesIDs() {
		return copiesIDs;
	}

	/**
	 * sets whether students can use this document
	 *
	 * @param allowedForStudents
	 */
	@Override
	public void setAllowedForStudents(boolean allowedForStudents) {
		this.isAllowedForStudents = allowedForStudents;
	}
>>>>>>> 97a48623ca0bf5b7ee8733bf5f1b6cee0611e589

//    public boolean isFaculty(Patron x){
//        Typetester t = new Typetester();
//        t.setType(x);
//        return t.getType().equals("faculty");
//    }

//    public boolean canTake(Patron user){
//        if(isFaculty(user) && !checked) return true;
//        return isAllowedForStudents() && !checked;
//    }

    /**
     * if librarian finds out that there are one more author of this document he can add him
     *
     * @param newAuthor
     */
    @Override
    public void addAuthor(String newAuthor) {
        this.Authors.add(newAuthor.toLowerCase());
    }

    /**
     * sometimes we need to check whether on person is author of some document
     *
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
     *
     * @param document
     * @return true if current document is copy of other
     */
    public boolean equals(Document document) {
        if (this.getDocID() == document.getDocID()) {
            System.out.println("The comparing objects are the same document");
            return false;
        }
        return this.getAuthors().equals(document.getAuthors())
                && this.getTitle().equals(document.getTitle());
    }

	/**
	 * adds id of copy to list
	 * @param id
	 */
	public void addCopy(int id){
		this.copiesIDs.add(id);
	}

}
