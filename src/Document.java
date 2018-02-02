import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


//documentation
public class Document implements  DocumentInterface{
    
    private HashSet<String>  Authors;    //TODO: solve problem: if document has more then one author - solved 
    private String Title;
    private boolean reference;
    private float Price;
    private boolean checked;    //Взяли книгу или нет
    private int userID;
    private int DocID;
    private boolean isAllowedForStudents;
    private Date dateOfTaking; //TODO: разобраться с датами
    //  public ArrayList <Document> listOfCopies; TODO: solve problem of storage -- databases


    public Document(String[] authors, String Title, int DocId, boolean isAllowedForStudents, float price){
        this.Authors = new HashSet<>();
        setAuthors(authors);
        this.Title = Title;
        this.DocID = DocId;
        this.checked = false;
        setPrice(price);
        this.reference = false;
        this.userID = 0;
        this.dateOfTaking = null;
        setAllowedForStudents(isAllowedForStudents);
    }

    public void setTitle(String title){
	this.Title = title;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public void setAuthors(String[] authors){
	    for(int i = 0; i < authors.length; i++) addAuthor(authors[i]);
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

    public void setDateOfTaking(Date dateOfTaking){
        this.dateOfTaking = dateOfTaking;
    }

    public String getTitle(){
	return Title;
    }

    public HashSet<String> getAuthors(){
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

    public Date getDateOfTaking(){
        return dateOfTaking;
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

    public boolean canTake(Patron user){
        if(isFaculty(user) && !checked) return true;
        return isAllowedForStudents() && !checked;
    }

   public void addAuthor(String newAuthor){
        this.Authors.add(newAuthor);
    }



}
