package materials;

import java.util.TreeSet;
import java.util.Objects;

/**
 * class implements "Document" type in materials.Document
 */
public class Document implements DocumentInterface{

    private TreeSet<String> Authors; //storage of author(group of authors) the document was created by
    private String Title; //title of current document
    private boolean reference; //назначение of current document
    private float Price; //price of document
    private boolean checked; //status of document showing if document is booked
    private int userID; //ID of user the document was taken by
    private int DocID; //ID of current document
    private boolean isAllowedForStudents; //shows students' possibility to take current document

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
     * sets title of document
     * @param title
     */
    @Override
    public void setTitle(String title) {
        this.Title = title;
    }

    /**
     * sets ID of user the Document was taken by
     * @param userID
     */
    @Override
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * sets name or list of names of author(s) the document was created by
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
     * sets current state of document: true if it is taken by some user, false otherwise
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    } //взяли книгу или нет

    /**
     * sets value of current document: true if document is reference-book and false otherwise
     * @param reference
     */
    @Override
    public void setReference(boolean reference) {
        this.reference = reference;
    }

    /**
     * sets value showing possibility for students to take this document
     * @param allowedForStudents
     */
    @Override
    public void setAllowedForStudents(boolean allowedForStudents) {
        this.isAllowedForStudents = allowedForStudents;
    }

    /**
     * returns title of document
     * @return
     */
    @Override
    public String getTitle() {
        return Title;
    }

    /**
     * returns name or list of names of author(s) the document was created by
     * @return
     */
    @Override
    public String getAuthors() {
        return Authors.toString();
    }

    /**
     * returns price of current document
     * @return
     */
    @Override
    public float getPrice() {
        return Price;
    }

    /**
     * returns ID of current document
     * @return
     */
    @Override
    public int getDocID() {
        return DocID;
    }

    /**
     * returns ID of user the Document was taken by
     * @return
     */
    @Override
    public int getUserID() {
        return userID;
    }

    /**
     * retruns current state of document: true if it is taken by some user, false otherwise
     * @return
     */
    @Override
    public boolean isChecked() {
        return checked;
    } //взяли книгу или нет

    /**
     * returns value of current document: true if document is reference-book and false otherwise
     * @return
     */
    @Override
    public boolean isReference() {
        return reference;
    }

    /**
     * returns value showing possibility for students to take this document
     * @return
     */
    @Override
    public boolean isAllowedForStudents() {
        return isAllowedForStudents;
    }

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
     * adds Author to the list of authors, the document was created by
     * @param newAuthor is author that need to be added
     */
    @Override
    public void addAuthor(String newAuthor) {
        this.Authors.add(newAuthor);
    }

    /**
     * checks if author is among list of persons the document was created by
     * @param author
     * @return true if author toook a part in document creation
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
        return this.DocID != document.getDocID() && this.Authors.toString().equals(document.getAuthors()) && this.Title == document.getTitle();
    }

    /*@Override
    public int hashCode(){
        return (456*DocID + 15)%134;
    }*/

}
