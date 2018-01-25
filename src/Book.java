import java.util.ArrayList;

public class Book extends Document{
    String Publisher;
    int editionYear;

    public Book(String title, ArrayList<String> authors, int id){
        super(authors, title,  id);
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
