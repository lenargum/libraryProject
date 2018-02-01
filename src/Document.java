import java.util.ArrayList;

//documentation
public class Document{
    
    private String  Authors;    //TODO: solve problem: if document has more then one author
    private String Title;
    private boolean reference;
    private float Price;
    private boolean checked;    //Взяли книгу или нет
    private int userID;
    private int DocID;
    private boolean isAllowedForStudents;
    //  public ArrayList <Document> listOfCopies; TODO: solve problem of storage -- databases


    public Document(String authors, String Title, int DocId, boolean isAllowedForStudents, float price){
        this.Authors = authors;
        this.Title = Title;
        this.DocID = DocId;
        this.checked = false;
        setPrice(price);
        this.reference = false;
        this.userID = 0;
        setAllowedForStudents(isAllowedForStudents);
    }

    public void setTitle(String title){
	this.Title = title;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public void setAuthors(String authors){
	this.Authors = authors;
    }

    public void setDocID(int id){
        this.DocID = id;
    }

    public void setPrice(float price){
	this.Price = price;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    } //взяли книгу или нет

    public void setReference(boolean reference) {
        this.reference = reference;
    }

    public void setAllowedForStudents(boolean allowedForStudents) {
       this.isAllowedForStudents = allowedForStudents;
    }

    public String getTitle(){
	return Title;
    }

    public String getAuthors(){
	return Authors;
    }

    public float getPrice(){
	return Price;
    }

    public int getDocID(){
        return DocID;
    }

    public int getUserID(){
        return userID;
    }

    public boolean isChecked(){
	return checked;
    } //взяли книгу или нет

    public boolean isReference() {
        return reference;
    }

    public boolean isAllowedForStudents() {
        return isAllowedForStudents;
    }

    public boolean isFaculty(Patron x){
        Typetester t = new Typetester();
        t.setType(x);
        return t.getType().equals("faculty");
    }

    /**public boolean isJournal(Document x) {
        Typetester t = new Typetester();
        t.setType(x);
    }*/

    public boolean canTake(Patron user){
        if(isFaculty(user) && !checked) return true;
        return isAllowedForStudents() && !checked;
    }

    public void addAuthor(String newAuthor){
        this.Authors += " " + newAuthor;
    }

}
