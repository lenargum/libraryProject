package tools;

import materials.AudioVideoMaterial;
import materials.Book;
import materials.JournalArticle;
import users.Librarian;
import users.Patron;

public class Typetester implements TypeTesterIntarface {
    private String Type;

    @Override
    public void setType(Librarian x) {
        this.Type = "librarian";
    }

    @Override
    public void setType(Patron x) {
        this.Type = x.getStatus().toLowerCase();
    }

    @Override
    public void setType(Book x) {
        this.Type = "book";
    }

    @Override
    public void setType(JournalArticle x) {
        this.Type = "journal article";
    }

    @Override
    public void setType(AudioVideoMaterial x) {
        this.Type = "audio-video material";
    }

    @Override
    public String getType() {
        return Type;
    }

}
