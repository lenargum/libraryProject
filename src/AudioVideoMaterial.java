public class AudioVideoMaterial extends Document {
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
	public AudioVideoMaterial(String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, double Price, String KeyWords) {
		super(Title, Authors, IsAllowedForStudents, NumberOfCopies, IsReference, Price, KeyWords);
		setType("audio/video material");
	}

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
