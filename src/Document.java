import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;


//documentation
public class Document implements  DocumentInterface{
    
    private HashSet<String>  Authors;    //TODO: i hope DONE
    private String Title;
    private boolean reference;
    private float Price;
    private boolean checked;    //Взяли книгу или нет
    private int userID;
    private int DocID;
    private boolean isAllowedForStudents;
    private Date dateOfTaking;//TODO: разобраться с датами -- вроде DONE
    private long seconds;
    //  public ArrayList <Document> listOfCopies; TODO: solve problem of storage -- databases


    public Document(String authors, String Title, int DocId, boolean isAllowedForStudents, float price){
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

    public void setAuthors(String authors){
        for (String newAuthor : authors.split(", ")) {
            addAuthor(newAuthor.toLowerCase());
        }
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

  /*  public void setDateOfTaking(Date dateOfTaking, long sec){
        this.dateOfTaking = dateOfTaking;
        this.seconds = sec;
    }*/

    public String getTitle(){
	return Title;
    }

    public String getAuthors(){
	return Authors.toString();
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

   /* public String getDateOfTaking(){
        return dateOfTaking.toString();
    }*/

    /*public String tillWhenShouldReturn(Patron user){
        Date date = new Date();
        if(!isFaculty(user))
            date.setTime(seconds + 14*24*60*60*1000);
        else date.setTime(seconds + 21*24*60*60*1000);
        return date.toString();
    }*/

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

    public boolean isWrittenBy(String author){
        author = author.toLowerCase();
        return Authors.contains(author);
    } //если мы хотuм проверить есть ли среди авторов документа данный автор

}
