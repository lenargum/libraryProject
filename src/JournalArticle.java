import java.util.ArrayList;
import java.util.Date;

public class JournalArticle extends Document{

    String journalTitle;
    Date publishingDate;
    String journalPublisher;

    public JournalArticle(String title, String authors, int id){
        super(authors, title,  id);
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