public class Typetester {
    private String Type;

    public void setType(Librarian x){
        this.Type = "librarian";
    }

    public void setType(Patron x) {
        this.Type = x.getStatus().toLowerCase();
    }

    public void setType(Book x) {
        this.Type = "book";
    }

    public void setType(JournalArticle x) {
        this.Type = "journal article";
    }

    public void setType(AudioVideoMaterial x) {
        this.Type = "audio-video material";
    }

    public String getType(){
        return Type;
    }

}
