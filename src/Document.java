import java.util.ArrayList;

//documentation
public class Document{
    
    private String  Authors;    //TODO: solve problem: if document has more then one author
    private String Title;
    private boolean reference;
    private float Price;
    private boolean checked;    //Взяли книгу или нет
    private User User;
    private int DocID;
    private boolean isAllowedForStudents;
    //  public ArrayList <Document> listOfCopies; TODO: solve problem of storage -- databases


    public Document(String authors, String Title, int DocId, boolean isAllowedForStudents){
        this.Authors = authors;
        this.Title = Title;
        this.DocID = DocId;
        this.checked = false;
        this.Price = 0;
        this.reference = false;
        this.User = null;
        this.isAllowedForStudents = true;
    }

    public void setTitle(String title){
	this.Title = title;
    }

    public void setAuthors(String authors){
	this.Authors = authors;
    }

    public void setPrice(float price){
	this.Price = price;
    }

    public void setID(int id){
        this.DocID = id;
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

    public boolean isChecked(){
	return checked;
    }

    public int getDocID() {
        return DocID;
    }

    public int whoTookDoc(){
	return User.getId();
    }

    public boolean isAllowedForStudents() {
        return isAllowedForStudents;
    }

    public boolean isFaculty(Patron x){
        Typetester t = new Typetester();
        t.setType(x);
        return t.getType().equals("Faculty");
    }

    public boolean canTake(Patron user){
        if(isFaculty(user)) return true;
        return isAllowedForStudents();
    }
}