package documents;

/**
 * This class describes audio and video materials of library system.
 *
 * @author Anastasia Minakova
 * @see Document
 */
public class AudioVideoMaterial extends Document {
	/**
	 * Initialize new audio/video.
	 *
	 * @param Title                Title.
	 * @param Authors              Authors.
	 * @param IsAllowedForStudents Student allowance.
	 * @param NumberOfCopies       Count of copies.
	 * @param IsReference          Reference status.
	 * @param Price                Price.
	 * @param KeyWords             Search keywords.
	 */
	public AudioVideoMaterial(String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords) {
		super(Title, Authors, IsAllowedForStudents, NumberOfCopies, IsReference, Price, KeyWords);
		setType("audio/video material");
	}

	/**
	 * Get this audio/video in string notation.
	 *
	 * @return String with audio/video description.
	 */
	public String toString() {
		return ("Id: " + this.getID() + "\n" +
				"Title: " + this.getTitle() + "\n" +
				"Authors: " + this.getAuthors() + "\n" +
				"Allowed for students: " + this.isAllowedForStudents() + "\n" +
				"Number of copies: " + this.getNumberOfCopies() + "\n" +
				"This is reference book: " + this.isReference() + "\n" +
				"Price: " + this.getPrice() + "\n" +
				"KeyWords: " + this.getKeyWords() + "\n");
	}
}
