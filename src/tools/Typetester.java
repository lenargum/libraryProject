package tools;

import materials.AudioVideoMaterial;
import materials.Book;
import materials.JournalArticle;

/**
 * class implements "Typetester" in tools.Typetester
 */
@Deprecated
public class Typetester implements TypeTesterIntarface {
	private String Type;

	/**
	 * checks type librarian
	 *
	 * @param x
	 */
	@Override
	public void setType(Librarian x) {
		this.Type = "librarian";
	}

	/**
	 * checks type of patron
	 *
	 * @param x
	 */
	@Override
	public void setType(Patron x) {
		this.Type = x.getStatus().toLowerCase();
	}

	/**
	 * checks type book
	 *
	 * @param x
	 */
	@Override
	public void setType(Book x) {
		this.Type = "book";
	}

	/**
	 * checks type journal article
	 *
	 * @param x
	 */
	@Override
	public void setType(JournalArticle x) {
		this.Type = "journal article";
	}

	/**
	 * retruns type of current object
	 *
	 * @return
	 */
	@Override
	public String getType() {
		return Type;
	}

	/**
	 * checks type audio/video material
	 *
	 * @param x
	 */
	@Override
	public void setType(AudioVideoMaterial x) {
		this.Type = "audio-video material";
	}

}
