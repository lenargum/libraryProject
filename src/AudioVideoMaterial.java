import java.util.ArrayList;
import java.util.Date;

public class AudioVideoMaterial extends Document{

    public Date publishingDate;

    public AudioVideoMaterial(String title, ArrayList<String> authors, boolean is_reference, float price, int id, Date publDate){
        setTitle(title);
        setAuthors(authors);
        setPrice(price);
        setID(id);
        this.reference = is_reference;
        this.checked = false;
        this.User = null;
    }

    public void setPublishingDate(Date publDate){
        this.publishingDate = publDate;
    }

    public Date getPublishingDate(){
        return publishingDate;
    }

}
