package users;

import documents.AudioVideoMaterial;
import documents.Book;
import documents.Document;
import documents.JournalArticle;
import librarian_tools.*;
import tools.Database;
import tools.Request;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * This class describes librarian in library system.
 *
 * @author Madina Gafarova
 * @see User
 */
public class Librarian extends User {

	private int privilege;
	/**
	 * Initialize new librarian.
	 *
	 * @param login    Login.
	 * @param password Password.
	 * @param name     First name.
	 * @param surname  Last name.
	 * @param phone    Phone number.
	 * @param address  Living address.
	 */
	public Librarian(String login, String password, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
		setPrivilege(0);
	}

	public void setPrivilege(int p){
		this.privilege = p;
	}


    public int getPrivilege(){
        return privilege;
    }

    /**
     * Tools for Librarian
     */
    private BookingSystem bookingSystem = new BookingSystem();
    private Modify modify = new Modify();
    private ModifyLibrary modifyLibrary = new ModifyLibrary();
    private OutstandingRequest outstandingRequest = new OutstandingRequest();
    private ReturningSystem returningSystem = new ReturningSystem();

    /**
     * Add new book to the database.
     *
     * @param book     documents.Book to add.
     * @param database tools.Database that stores the information.
     */
    public void addBook(Book book, Database database) {
        this.modifyLibrary.addBook(book, database);
    }

    /**
     * Add new audio/video to the database.
     *
     * @param AV       Audio/video to add.
     * @param database tools.Database that stores the information.
     */
    public void addAV(AudioVideoMaterial AV, Database database) {
        this.modifyLibrary.addAV(AV, database);
    }

    /**
     * Add new article to the database.
     *
     * @param journalArticle Article to add.
     * @param database       tools.Database that stores the information.
     */
    public void addArticle(JournalArticle journalArticle, Database database) {
        this.modifyLibrary.addArticle(journalArticle, database);
    }

    /**
     * Add new patron to the database.
     *
     * @param patron   users.Patron to add.
     * @param database tools.Database that stores the information.
     */
    public void registerPatron(Patron patron, Database database) {
        this.modifyLibrary.registerPatron(patron, database);
    }

    /**
     * Delete the document from the database.
     *
     * @param idDocument ID of document which is going to be deleted.
     * @param database   tools.Database that stores the information.
     */
    public void deleteDocument(int idDocument, Database database) {
        this.modifyLibrary.deleteDocument(idDocument, database);
    }

    /**
     * Delete the patron from the database.
     *
     * @param idPatron ID of patron which is going to be deleted.
     * @param database tools.Database that stores the information.
     */
    public void deletePatron(int idPatron, Database database) {
        this.modifyLibrary.deletePatron(idPatron, database);
    }

    /**
     * Modify the price of document stored in database.
     *
     * @param idDocument ID of document which is going to be modified.
     * @param database   tools.Database that stores the information.
     * @param price      New price.
     */
    public void modifyDocumentPrice(int idDocument, Database database, double price) {
        this.modify.modifyDocumentPrice(idDocument, database, price);
    }

    /**
     * Modify the edition year of book stored in database.
     *
     * @param idBook   ID of book which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param edition  New edition year.
     */
    public void modifyBookEdition(int idBook, Database database, int edition) {
        this.modify.modifyBookEdition(idBook, database, edition);
    }

    /**
     * Modify the student allowance status of document stored in database.
     *
     * @param idDocument           ID of document which is going to be modified.
     * @param database             tools.Database that stores the information.
     * @param isAllowedForStudents New status.
     */
    public void modifyDocumentAllowance(int idDocument, Database database, boolean isAllowedForStudents) {
        this.modify.modifyDocumentAllowance(idDocument, database, isAllowedForStudents);
    }

    /**
     * Modify the count of copies of document stored in database.
     *
     * @param idDocument    ID of document which is going to be modified.
     * @param database      tools.Database that stores the information.
     * @param countOfCopies New number.
     */
    public void modifyDocumentCopies(int idDocument, Database database, int countOfCopies) {
        this.modify.modifyDocumentCopies(idDocument, database, countOfCopies);
    }

    /**
     * Modify the bestseller status of book stored in database.
     *
     * @param idBook     ID of book which is going to be modified.
     * @param database   tools.Database that stores the information.
     * @param bestseller New status.
     */
    public void modifyBookBestseller(int idBook, Database database, boolean bestseller) {
        this.modify.modifyBookBestseller(idBook, database, bestseller);
    }

    /**
     * Modify the last name of patron stored in database.
     *
     * @param idPatron ID of patron which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param surname  New last name.
     */
    public void modifyPatronSurname(int idPatron, Database database, String surname) {
        this.modify.modifyPatronSurname(idPatron, database, surname);
    }

    /**
     * Modify the living address of patron stored in database.
     *
     * @param idPatron ID of patron which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param address  New living address.
     */
    public void modifyPatronAddress(int idPatron, Database database, String address) {
        this.modify.modifyPatronAddress(idPatron, database, address);
    }

    /**
     * Modify the phone number of patron stored in database.
     *
     * @param idPatron    ID of patron which is going to be modified.
     * @param database    tools.Database that stores the information.
     * @param phoneNumber New phone number.
     */
    public void modifyPatronPhoneNumber(int idPatron, Database database, String phoneNumber) {
        this.modify.modifyPatronPhoneNumber(idPatron, database, phoneNumber);
    }

    /**
     * Modify the status of patron stored in database.
     * Possible values: {@code "instructor"}, {@code "student"}, {@code "ta"}, {@code "professor"}
     *
     * @param idPatron ID of patron which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param status   New status.
     */
    public void modifyPatronStatus(int idPatron, Database database, String status) {
        this.modify.modifyPatronStatus(idPatron, database, status);
    }

    /**
     * Get count of all possible copies of documents in the library.
     *
     * @param database tools.Database that stores the information.
     * @return Count of all copies.
     * @throws SQLException If database is busy.
     */
    public int getNumberOfDocument(Database database) throws SQLException {
        int n = 0;
        for (Document i : database.getDocumentList()) {
            n += i.getNumberOfCopies();
        }
        return n;
    }

    /**
     * Compare this librarian with another.
     *
     * @param librarian Another librarian to compare.
     * @return {@code true} if librarians are similar, {@code false} otherwise.
     */
    public boolean compare(Librarian librarian) {
        return this.getLogin().equals(librarian.getLogin());
    }


    /**
     * Return confirmation
     *
     * @param debtID   - id of debt patron wants to close
     * @param database - information storage
     */
    public void confirmReturn(int debtID, Database database) {
        this.returningSystem.confirmReturn(debtID, database);
    }

    /**
     * renew document confirmation
     *
     * @param request  - request that patron sent to renew document
     * @param database - information storage
     */
    public void confirmRenew(Request request, Database database) {
        this.returningSystem.confirmRenew(request, database);
    }


    /**
     * confirmation of getting fee
     *
     * @param debtID   - id of debt patron wants to close
     * @param database - information storage
     */
    public void getFee(int debtID, Database database) {
        this.returningSystem.getFee(debtID, database);
    }

    /**
     * taking document request confirmation
     *
     * @param request  - request librarian confirms
     * @param database - information storage
     */
    public void submitRequest(Request request, Database database) {
        this.bookingSystem.submitRequest(request, database);
    }

    /**
     * delete taking document request
     *
     * @param request  - request the librarian refuses
     * @param database - information storage
     */
    public void deleteRequest(Request request, Database database) {
        this.bookingSystem.deleteRequest(request, database);
    }

    public void makeOutstandingRequest(Request request, Database database) {
        this.outstandingRequest.makeOutstandingRequest(request, database);
    }

    public void setAvailability(int docID, Database database) {
        this.outstandingRequest.setAvailability(docID, database);
    }

}
