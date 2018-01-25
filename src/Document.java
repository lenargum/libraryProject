import java.util.ArrayList;

public class Document{
    
    private ArrayList <String>  Authors;
    private String Title;
    private boolean reference;
    private float Price;
    private boolean checked;
    private User User;
    private int DocID;

    
    public Document(String authors, String Title int DocId){
        this.Authors = authors;
        this.Title = Title;
        this.DocID = DocId;
        this.checked = null;
        this.Price = null;
        this.reference = null;
        this.User = null;
    }


    public void setTitle(String title){
	this.Title = title;
    }

    public void setAuthors(ArrayList<String> authors){
	this.Authors = authors;
    }

    public void setPrice(float price){
	this.Price = price;
    }

    public void setID(int id){
        this.DocID = id;
    }

    
    public String getTitle(){
	return Title;
    }

    public ArrayList <String> getAuthors(){
	return Authors;
    }

    public float getPrice(){
	return Price;
    }

    public boolean isChecked(){
	return checked;
    }

    public User whoTookDoc(){
	return User;
    }



    public void addCopy(){
    
    }
    

    
}
