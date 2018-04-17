package librarian_tools;

import documents.AudioVideoMaterial;
import documents.Book;

import documents.JournalArticle;
import tools.Database;
import users.Patron;

import java.sql.SQLException;

public class Modify {

    /**
     * Modify the price of document stored in database.
     *
     * @param idDocument ID of document which is going to be modified.
     * @param database   tools.Database that stores the information.
     * @param price      New price.
     */
    public void modifyDocumentPrice(int idDocument, Database database, double price)  {
        try {
            database.getDocument(idDocument).setPrice(price);
            database.editDocumentColumn(idDocument, "price", Double.toString(price));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Modify the edition year of book stored in database.
     *
     * @param idBook   ID of book which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param edition  New edition year.
     */
    public void modifyBookEdition(int idBook, Database database, int edition)  {

        try {
            database.getBook(idBook).setEdition(edition);
            database.editDocumentColumn(idBook, "edition", Integer.toString(edition));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /**
     * Modify the student allowance status of document stored in database.
     *
     * @param idDocument           ID of document which is going to be modified.
     * @param database             tools.Database that stores the information.
     * @param isAllowedForStudents New status.
     */
    public void modifyDocumentAllowance(int idDocument, Database database, boolean isAllowedForStudents)  {

        try {
            database.getDocument(idDocument).setAllowedForStudents(isAllowedForStudents);
            database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowedForStudents));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Modify the count of copies of document stored in database.
     *
     * @param idDocument    ID of document which is going to be modified.
     * @param database      tools.Database that stores the information.
     * @param countOfCopies New number.
     */
    public void modifyDocumentCopies(int idDocument, Database database, int countOfCopies) {

        try {
            database.getDocument(idDocument).setNumberOfCopies(countOfCopies);
            database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(countOfCopies));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the bestseller status of book stored in database.
     *
     * @param idBook     ID of book which is going to be modified.
     * @param database   tools.Database that stores the information.
     * @param bestseller New status.
     */
    public void modifyBookBestseller(int idBook, Database database, boolean bestseller)  {

        try {
            database.getBook(idBook).setBestseller(bestseller);
            database.editDocumentColumn(idBook, "bestseller", Boolean.toString(bestseller));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the last name of patron stored in database.
     *
     * @param idPatron ID of patron which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param surname  New last name.
     */
    public void modifyPatronSurname(int idPatron, Database database, String surname)  {

        try {
            database.getPatron(idPatron).setSurname(surname);
            database.editUserColumn(idPatron, "lastname", surname);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the living address of patron stored in database.
     *
     * @param idPatron ID of patron which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param address  New living address.
     */
    public void modifyPatronAddress(int idPatron, Database database, String address)  {

        try {
            database.getPatron(idPatron).setAddress(address);
            database.editUserColumn(idPatron, "address", address);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Modify the phone number of patron stored in database.
     *
     * @param idPatron    ID of patron which is going to be modified.
     * @param database    tools.Database that stores the information.
     * @param phoneNumber New phone number.
     */
    public void modifyPatronPhoneNumber(int idPatron, Database database, String phoneNumber) {

        try {
            database.getPatron(idPatron).setPhoneNumber(phoneNumber);
            database.editUserColumn(idPatron, "phone", phoneNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modify the status of patron stored in database.
     * Possible values: {@code "instructor"}, {@code "student"}, {@code "ta"}, {@code "professor"}
     *
     * @param idPatron ID of patron which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param status   New status.
     */
    public void modifyPatronStatus(int idPatron, Database database, String status)  {

        try {
            database.getPatron(idPatron).setStatus(status);
            database.editDocumentColumn(idPatron, "status", status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
