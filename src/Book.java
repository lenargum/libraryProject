public class Book extends Document{
    String Publisher;
    int editionYear;

    public Book(String title, String authors, int id, boolean allowed){
        super(authors, title,  id, allowed);
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