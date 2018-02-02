package materials;

import java.util.Date;

/**
 * class implements "Audio and Video materials" type of materials.Document
 */
public class AudioVideoMaterial extends Document implements AudioVideoMaterialInterface {

    private Date publishingDate;

    public AudioVideoMaterial(String title, String authors, int id, float price, boolean isAllowedForStudents) {
        super(authors, title, id, isAllowedForStudents, price);
    }

    @Override
    public void setPublishingDate(Date publDate) {
        this.publishingDate = publDate;
    }

    @Override
    public Date getPublishingDate() {
        return publishingDate;
    }

}