package tools;

import materials.AudioVideoMaterial;
import materials.Book;
import materials.JournalArticle;
import users.Librarian;
import users.Patron;

/**
 * interface describing class that checks type of current object
 * (not bug, but feature)
 */
@Deprecated
public interface TypeTesterIntarface {
	/**
	 * checks type librarian
	 *
	 * @param librarian
	 */
	public void setType(Librarian librarian);

	/**
	 * checks type of patron
	 *
	 * @param patron
	 */
	public void setType(Patron patron);

	/**
	 * checks type book
	 *
	 * @param book
	 */
	public void setType(Book book);

	/**
	 * check type journal article
	 *
	 * @param journalArticle
	 */
	public void setType(JournalArticle journalArticle);

	/**
	 * retruns type of current object
	 *
	 * @return
	 */
	public String getType();

	/**
	 * checks type audio/video material
	 *
	 * @param audioVideoMaterial
	 */
	public void setType(AudioVideoMaterial audioVideoMaterial);
}
