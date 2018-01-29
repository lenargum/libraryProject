import java.util.ArrayList;
import java.util.Date;

/**
 * class implements "Audio and Video materials" type of Document
 */
public class AudioVideoMaterial extends Document{

    public Date publishingDate;

    public AudioVideoMaterial(String title, String authors, int id){
        super(authors, title,  id);
    }

    public void setPublishingDate(Date publDate){
        this.publishingDate = publDate;
    }

    public Date getPublishingDate(){
        return publishingDate;
    }

}