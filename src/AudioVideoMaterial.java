import java.util.ArrayList;
import java.util.Date;

/**
 * class implements "Audio and Video materials" type of Document
 */
public class AudioVideoMaterial extends Document{

    private Date publishingDate;

    public AudioVideoMaterial(String title, String authors, int id, float price, boolean isAllowedForStudents){
        super(authors, title,  id, isAllowedForStudents, price);
    }

    public void setPublishingDate(Date publDate){
        this.publishingDate = publDate;
    }

    public Date getPublishingDate(){
        return publishingDate;
    }

}