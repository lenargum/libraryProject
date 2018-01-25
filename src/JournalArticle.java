import java.util.ArrayList;
import java.util.Date;

public class JournalArticle extends Document{

    String journalTitle;
    Date publishingDate;
    String journalPublisher;

    public JournalArticle(String title, ArrayList<String> authors, boolean is_reference, float price, int id, String jourTitle, Date publDate, String publisher){
        setTitle(title);
        setAuthors(authors);
        setPrice(price);
        setID(id);
        setJournalPublisher(publisher);
        setJournalTitle(jourTitle);
        setPublishingDate(publDate);
        this.reference = is_reference;
        this.checked = false;
        this.User = null;
    }

    public void setJournalTitle(String jourTitle){
        this.journalTitle = jourTitle;
    }

    public void setPublishingDate(Date publDate){
        this.publishingDate = publDate;
    }

    public void setJournalPublisher(String publisher){
        this.journalPublisher = publisher;
    }
    //setters&getters&?
}
