package documents;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class describes journal article of library system.
 *
 * @author Anastasia Minakova
 * @see Document
 */
public class JournalArticle extends Document {
	/**
	 * Name of the journal where article is being published.
	 */
	private String JournalName;

	/**
	 * Article publisher.
	 */
	private String Publisher;

	/**
	 * Article issue.
	 */
	private String Issue;

	/**
	 * Article editor.
	 */
	private String Editor;

	/**
	 * Publication date.
	 */
	private Date PublicationDate;

	/**
	 * Initialize new article.
	 *
	 * @param Title                Title.
	 * @param Authors              Authors.
	 * @param IsAllowedForStudents Student allowance status.
	 * @param NumberOfCopies       Count of copies.
	 * @param IsReference          Reference status.
	 * @param Price                Price.
	 * @param KeyWords             Search keywords.
	 * @param JournalName          Publishing journal name.
	 * @param Publisher            Article publisher.
	 * @param Issue                Article issue.
	 * @param Editor               Article editor.
	 * @param PublicationDate      Article publication date.
	 */
	public JournalArticle(String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords, String JournalName, String Publisher, String Issue, String Editor, Date PublicationDate) {
		super(Title, Authors, IsAllowedForStudents, NumberOfCopies, IsReference, Price, KeyWords);
		setJournalName(JournalName);
		setPublisher(Publisher);
		setIssue(Issue);
		setEditor(Editor);
		setPublicationDate(PublicationDate);
		setType("article");
	}

	/**
	 * Get the publishing journal name.
	 *
	 * @return Journal name.
	 */
	public String getJournalName() {
		return JournalName;
	}

	/**
	 * Set the new publishing journal name.
	 *
	 * @param journalName New journal name.
	 */
	private void setJournalName(String journalName) {
		this.JournalName = journalName;
	}

	/**
	 * Get the article publisher.
	 *
	 * @return Article publisher.
	 */
	public String getPublisher() {
		return Publisher;
	}

	/**
	 * Set the new article publisher.
	 *
	 * @param publisher New article publisher.
	 */
	private void setPublisher(String publisher) {
		this.Publisher = publisher;
	}

	/**
	 * Get this article issue.
	 *
	 * @return Article issue.
	 */
	public String getIssue() {
		return Issue;
	}

	/**
	 * Set the new article issue.
	 *
	 * @param issue New article issue.
	 */
	private void setIssue(String issue) {
		this.Issue = issue;
	}

	/**
	 * Get this article editor.
	 *
	 * @return Article editor.
	 */
	public String getEditor() {
		return Editor;
	}

	/**
	 * Set the new article editor.
	 *
	 * @param editor New article editor.
	 */
	private void setEditor(String editor) {
		this.Editor = editor;
	}

	/**
	 * Get the article publication date.
	 *
	 * @return Article publication date.
	 */
	public Date getPublicationDate() {
		return PublicationDate;
	}

	/**
	 * Set the new article publication date.
	 *
	 * @param date New article publication date.
	 */
	private void setPublicationDate(Date date) {
		this.PublicationDate = date;
	}

	/**
	 * Get this article in string notation.
	 *
	 * @return String with article description.
	 */
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

	@Override
	public String getType() {
		return "article";
	}
}
