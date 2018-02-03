package materials;

import java.util.Date;

/**
 * class implements "Audio and Video materials" type of materials.Document
 */
public class AudioVideoMaterial extends Document implements AudioVideoMaterialInterface {

    private Date publishingDate;

    /**
     * constructor
     * @param title
     * @param authors
     * @param id
     * @param price
     * @param isAllowedForStudents
     */
    public AudioVideoMaterial(String title, String authors, int id, float price, boolean isAllowedForStudents) {
        super(authors, title, id, isAllowedForStudents, price);
    }

    /**
     * sets date when the material was published
     * @param publDate
     */
    @Override
    public void setPublishingDate(Date publDate) {
        this.publishingDate = publDate;
    }

    /**
     * returns date when the material was published
     * @return
     */
    @Override
    public Date getPublishingDate() {
        return publishingDate;
    }

}