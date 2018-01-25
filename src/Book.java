import java.util.ArrayList;

public class Book extends Document{
    String Publisher;
    int editionYear;

    public Book(String title, ArrayList<String> authors, boolean is_reference, float price, int id, String publisher, int edition){
        setTitle(title);
        setAuthors(authors);
        setPrice(price);
        setID(id);
        setEditionYear(edition);
        setPublisher(publisher);
        this.reference = is_reference;
        this.checked = false;
        this.User = null;
    }

    public void setPublisher(String publisher){
        this.Publisher = publisher;
    }

    public void setEditionYear(int edition){
        this.editionYear = edition;
    }

    public int getEditionYear(){
        return this.editionYear;
    }

    public String getPublisher(){
        return Publisher;
    }

}
