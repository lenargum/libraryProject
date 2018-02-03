package materials;

import java.util.Date;

/**
 * class implements journal articles in materials.Document
 */
public class JournalArticle extends Document implements JournalArticleInterface{

    private String journalTitle;
    private Date publishingDate;
    private String journalPublisher;

    /**
     * constructor
     * @param title
     * @param authors
     * @param id
     * @param price
     * @param isAllowedForStudents
     */
    public JournalArticle(String title, String authors, int id, float price, boolean isAllowedForStudents) {
        super(authors, title, id, isAllowedForStudents, price);
    }

    /**
     * sets title of journal in which the article was published
     * @param jourTitle
     */
    @Override
    public void setJournalTitle(String jourTitle) {
        this.journalTitle = jourTitle;
    }

    /**
     * sets date when was published the article
     * @param publDate
     */
    @Override
    public void setPublishingDate(Date publDate) {
        this.publishingDate = publDate;
    }

    /**
     * sets name of person or company published the journal
     * @param publisher
     */
    @Override
    public void setJournalPublisher(String publisher) {
        this.journalPublisher = publisher;
    }

    /**
     * we cannot take journals from library
     */
    @Override
    public void setDateOfTaking() {
        System.out.print("You can not take journal!");
    }

    /**
     * returns title of journal where the article was published
     * @return
     */
    @Override
    public String getJournalTitle(){
        return journalTitle;
    }

    /**
     * returns name of person or company published the article
     * @return
     */
    @Override
    public String getJournalPublisher(){
        return journalPublisher;
    }

    /**
     * returns date when was published the article
     * @return
     */
    @Override
    public String getPublishingDate(){
        return publishingDate.toString();
    }
}