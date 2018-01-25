import java.util.ArrayList;
import java.util.Date;

public class AudioVideoMaterial extends Document{

    public Date publishingDate;

    public AudioVideoMaterial(String title, ArrayList<String> authors, int id){
        super(authors, title,  id);
    }

    public void setPublishingDate(Date publDate){
        this.publishingDate = publDate;
    }

    public Date getPublishingDate(){
        return publishingDate;
    }

}
