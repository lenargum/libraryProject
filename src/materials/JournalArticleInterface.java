package materials;

import java.util.Date;

/**
 * interface describing Articles as class
 */
public interface JournalArticleInterface {

    /**
     * sets title of journal in which the article was published
     * @param jourTitle
     */
    public void setJournalTitle(String jourTitle);

    /**
     * sets date when was published the article
     * @param publDate
     */
    public void setPublishingDate(Date publDate);

    /**
     * sets name of person or company published the journal
     * @param publisher
     */
    public void setJournalPublisher(String publisher);

    /**
     * we cannot take journals from library
     */
    public void setDateOfTaking();

    /**
     * returns title of journal where the article was published
     * @return
     */
    public String getJournalTitle();

    /**
     * returns name of person or company published the article
     * @return
     */
    public String getJournalPublisher();

    /**
     * returns date when was published the article
     * @return
     */
    public String getPublishingDate();
}
