import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalArticle extends Document {
	/**
	 * name of journal in which the article was published
	 */
	private String JournalName;
	/**
	 * name of person or organisation published the article
	 */
	private String Publisher;
	/**
	 * name of issue where journal was published
	 */
	private String Issue;
	/**
	 * name of person or organisation edited the article
	 */
	private String Editor;
	/**
	 * date when the article was published
	 */
	private Date PublicationDate;

	/**
	 * constructor
	 *
	 * @param Title
	 * @param Authors
	 * @param IsAllowedForStudents
	 * @param NumberOfCopies
	 * @param IsReference
	 * @param Price
	 * @param KeyWords
	 */
	public JournalArticle(String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords, String JournalName, String Publisher, String Issue, String Editor, Date PublicationDate) {
		super(Title, Authors, IsAllowedForStudents, NumberOfCopies, IsReference, Price, KeyWords);
		setJournalName(JournalName);
		setPublisher(Publisher);
		setIssue(Issue);
		setEditor(Editor);
		setPublicationDate(PublicationDate);
		setType("journal article");
	}

	/**
	 * @return name of the journal where article was published
	 */
	public String getJournalName() {
		return JournalName;
	}

	/**
	 * sets name of the journal where article was published
	 *
	 * @param journalName
	 */
	public void setJournalName(String journalName) {
		this.JournalName = journalName;
	}

	/**
	 * @return name of person or organisation published the article
	 */
	public String getPublisher() {
		return Publisher;
	}

	/**
	 * sets name of person or organisation published the article
	 *
	 * @param publisher
	 */
	public void setPublisher(String publisher) {
		this.Publisher = publisher;
	}

	/**
	 * @return name of issue where journal was published
	 */
	public String getIssue() {
		return Issue;
	}

	/**
	 * sets name of issue where journal was published
	 *
	 * @param issue
	 */
	public void setIssue(String issue) {
		this.Issue = issue;
	}

	/**
	 * @return name of person or organisation edited the article
	 */
	public String getEditor() {
		return Editor;
	}

	/**
	 * sets name of person or organisation edited the article
	 *
	 * @param editor
	 */
	public void setEditor(String editor) {
		this.Editor = editor;
	}

	/**
	 * @return date when the article was published
	 */
	public Date getPublicationDate() {
		return PublicationDate;
	}

	/**
	 * sets date when the article was published
	 *
	 * @param date
	 */
	public void setPublicationDate(Date date) {
		this.PublicationDate = date;
	}

	public String toString() {
		return ("Id: " + this.getID() + "\n" +
				"Title: " + this.getTitle() + "\n" +
				"Authors: " + this.getAuthors() + "\n" +
				"Allowed for students: " + this.isAllowedForStudents() + "\n" +
				"Number of copies: " + this.getNumberOfCopies() + "\n" +
				"This is reference book: " + this.isReference() + "\n" +
				"Price: " + this.getPrice() + "\n" +
				"KeyWords: " + this.getKeyWords() + "\n" +
				"Journal name: " + this.getJournalName() + "\n" +
				"Publisher: " + this.getPublisher() + "\n" +
				"Issue: " + this.getIssue() + "\n" +
				"Editor: " + this.getEditor() + "\n" +
				"Publication date: " +
				(new SimpleDateFormat("dd-MMM-yyyy")).format(this.getPublicationDate()) + "\n");
	}
}
