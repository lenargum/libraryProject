package librarianTools;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.JournalArticle;
import tools.Database;
import tools.Logic;
import users.Patron;


public class ModifyLibrary {
    /**
     * Add new book to the database.
     *
     * @param book     documents.Book to add.
     * @param database tools.Database that stores the information.
     */
    public void addBook(int librarianId, Book book, Database database) {
        if(Logic.canAdd(librarianId, database)) {
            database.insertBook(book);
        } else {

        }
    }

    /**
     * Add new audio/video to the database.
     *
     * @param AV       Audio/video to add.
     * @param database tools.Database that stores the information.
     */
    public void addAV(int librarianId, AudioVideoMaterial AV, Database database)  {
        if(Logic.canAdd(librarianId, database)) {
            database.insertAV(AV);
        } else {

        }
    }

    /**
     * Add new article to the database.
     *
     * @param journalArticle Article to add.
     * @param database       tools.Database that stores the information.
     */
    public void addArticle(int librarianId, JournalArticle journalArticle, Database database)  {
        if(Logic.canAdd(librarianId, database)) {
            database.insertArticle(journalArticle);
        } else {

        }
    }



    /**
     * Add new patron to the database.
     *
     * @param patron   users.Patron to add.
     * @param database tools.Database that stores the information.
     */
    public void registerPatron(int librarianId, Patron patron, Database database)  {
        if(Logic.canAdd(librarianId, database)) {
            database.insertPatron(patron);
        } else {

        }
    }

    /**
     * Delete the document from the database.
     *
     * @param idDocument ID of document which is going to be deleted.
     * @param database   tools.Database that stores the information.
     */
    public void deleteDocument(int librarianId, int idDocument, Database database)  {
        if(Logic.canDelete(librarianId, database)) {
            database.deleteDocument(idDocument);
        } else {

        }
    }

    /**
     * Delete the patron from the database.
     *
     * @param idPatron ID of patron which is going to be deleted.
     * @param database tools.Database that stores the information.
     */
    public void deletePatron(int librarianId, int idPatron, Database database)  {
        if(Logic.canDelete(librarianId, database)) {
            database.deleteUser(idPatron);
        } else {

        }
    }

}