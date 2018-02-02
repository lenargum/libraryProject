package tools;

import materials.AudioVideoMaterial;
import materials.Book;
import materials.JournalArticle;
import users.Librarian;
import users.Patron;

public interface TypeTesterIntarface {

    public void setType(Librarian librarian);

    public void setType(Patron patron);

    public void setType(Book book);

    public void setType(JournalArticle journalArticle);

    public void setType(AudioVideoMaterial audioVideoMaterial);

    public String getType();
}
