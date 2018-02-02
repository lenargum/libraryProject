package materials;

public class Book extends Document implements BookInterface{
    String Publisher;
    int editionYear;

    public Book(String title, String authors, int id, boolean allowed, float price){
        super(authors, title,  id, allowed, price);
    }

    @Override
    public void setPublisher(String publisher){
        this.Publisher = publisher;
    }

    @Override
    public void setEditionYear(int edition){
        this.editionYear = edition;
    }

    @Override
    public int getEditionYear(){
        return this.editionYear;
    }

    @Override
    public String getPublisher(){
        return Publisher;
    }

}