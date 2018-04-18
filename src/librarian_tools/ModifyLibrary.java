package librarian_tools;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.JournalArticle;
import tools.Database;
import users.Patron;

import java.sql.SQLException;


public class ModifyLibrary {
    /**
     * Add new book to the database.
     *
     * @param book     documents.Book to add.
     * @param database tools.Database that stores the information.
     */
    public void addBook(Book book, Database database) {
            database.insertBook(book);

    }

    /**
     * Add new audio/video to the database.
     *
     * @param AV       Audio/video to add.
     * @param database tools.Database that stores the information.
     */
    public void addAV(AudioVideoMaterial AV, Database database)  {
            database.insertAV(AV);


    }

    /**
     * Add new article to the database.
     *
     * @param journalArticle Article to add.
     * @param database       tools.Database that stores the information.
     */
    public void addArticle(JournalArticle journalArticle, Database database)  {

            database.insertArticle(journalArticle);
        }



    /**
     * Add new patron to the database.
     *
     * @param patron   users.Patron to add.
     * @param database tools.Database that stores the information.
     */
    public void registerPatron(Patron patron, Database database)  {

            database.insertPatron(patron);

    }

    /**
     * Delete the document from the database.
     *
     * @param idDocument ID of document which is going to be deleted.
     * @param database   tools.Database that stores the information.
     */
    public void deleteDocument(int idDocument, Database database)  {

            database.deleteDocument(idDocument);

    }

    /**
     * Delete the patron from the database.
     *
     * @param idPatron ID of patron which is going to be deleted.
     * @param database tools.Database that stores the information.
     */
    public void deletePatron(int idPatron, Database database)  {

            database.deleteUser(idPatron);


    }

}
