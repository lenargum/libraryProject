package materials;

import tools.Typetester;
import users.Patron;

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
    //  public ArrayList <materials.Document> listOfCopies; TODO: solve problem of storage -- databases


    public Document(String authors, String Title, int DocId, boolean isAllowedForStudents, float price){
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
    public void setTitle(String title){
	this.Title = title;
    }

    @Override
    public void setUserID(int userID){
        this.userID = userID;
    }

    @Override
    public void setAuthors(String authors){
        for (String newAuthor : authors.split(", ")) {
            addAuthor(newAuthor.toLowerCase());
        }
    }

    @Override
    public void setDocID(int id){
        this.DocID = id;
    }

    @Override
    public void setPrice(float price){
	this.Price = price;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    } //взяли книгу или нет

    @Override
    public void setReference(boolean reference) {
        this.reference = reference;
    }

    @Override
    public void setAllowedForStudents(boolean allowedForStudents) {
       this.isAllowedForStudents = allowedForStudents;
    }

    @Override
    public String getTitle(){
	return Title;
    }

    @Override
    public String getAuthors(){
	return Authors.toString();
    }

    @Override
    public float getPrice(){
	return Price;
    }

    @Override
    public int getDocID(){
        return DocID;
    }

    @Override
    public int getUserID(){
        return userID;
    }

    @Override
    public boolean isChecked(){
	return checked;
    } //взяли книгу или нет

    @Override
    public boolean isReference() {
        return reference;
    }

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

    @Override
    public void addAuthor(String newAuthor){
        this.Authors.add(newAuthor);
    }

    @Override
    public boolean isWrittenBy(String author){
        author = author.toLowerCase();
        return Authors.contains(author);
    } //если мы хотuм проверить есть ли среди авторов документа данный автор

}
