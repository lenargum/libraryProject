package materials;

import java.util.Date;

/**
 * class implements "Audio and Video materials" type of materials.Document
 */
public class AudioVideoMaterial extends Document implements AudioVideoMaterialInterface {

    private Date publishingDate; // date when current Document was published

    /**
     * constructor
     *
     * @param title
     * @param authors
     * @param id
     * @param price
     * @param isAllowedForStudents
     */
    public AudioVideoMaterial(String title, String authors, int id, float price, boolean isCopy, boolean isAllowedForStudents) {
        super(authors, title, id, isAllowedForStudents, isCopy, price);
    }

    /**
     * returns date when the material was published
     *
     * @return
     */
    @Override
    public String getPublishingDate() {
        return publishingDate.toString();
    }

    /**
     * sets date when the material was published
     *
     * @param publDate
     */
    @Override
    public void setPublishingDate(Date publDate) {
        this.publishingDate = publDate;
    }

    /**
     * checks wether one document is copy of another
     *
     * @param document
     * @return true if current document is copy of other
     */
    public boolean equals(AudioVideoMaterial document) {
        if (this.getDocID() == document.getDocID()) {
            System.out.println("The comparing objects are the same material");
            return false;
        }
        return this.getAuthors().equals(document.getAuthors()) && this.getTitle().equals(document.getTitle());
    }
}