package librarianTools;

import tools.Database;
import tools.Logic;

public class Modify {

    /**
     * Modify the price of document stored in database.
     *
     * @param idDocument ID of document which is going to be modified.
     * @param database   tools.Database that stores the information.
     * @param price      New price.
     */
    public void modifyDocumentPrice(int librarianId, int idDocument, Database database, double price)  {
            if(Logic.canModify(librarianId, database)) {
                database.getDocument(idDocument).setPrice(price);
                database.editDocumentColumn(idDocument, "price", Double.toString(price));
            } else {

            }
        }


    /**
     * Modify the edition year of book stored in database.
     *
     * @param idBook   ID of book which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param edition  New edition year.
     */
    public void modifyBookEdition(int librarianId, int idBook, Database database, int edition)  {
        if(Logic.canModify(librarianId, database)) {
            database.getBook(idBook).setEdition(edition);
            database.editDocumentColumn(idBook, "edition", Integer.toString(edition));
        } else {

        }
    }


    /**
     * Modify the student allowance status of document stored in database.
     *
     * @param idDocument           ID of document which is going to be modified.
     * @param database             tools.Database that stores the information.
     * @param isAllowedForStudents New status.
     */
    public void modifyDocumentAllowance(int librarianId, int idDocument, Database database, boolean isAllowedForStudents)  {
        if(Logic.canModify(librarianId, database)) {
            database.getDocument(idDocument).setAllowedForStudents(isAllowedForStudents);
            database.editDocumentColumn(idDocument, "is_allowed_for_students", Boolean.toString(isAllowedForStudents));
        } else {

        }
    }

    /**
     * Modify the count of copies of document stored in database.
     *
     * @param idDocument    ID of document which is going to be modified.
     * @param database      tools.Database that stores the information.
     * @param countOfCopies New number.
     */
    public void modifyDocumentCopies(int librarianId, int idDocument, Database database, int countOfCopies) {
       if(Logic.canModify(librarianId, database)) {
           database.getDocument(idDocument).setNumberOfCopies(countOfCopies);
           database.editDocumentColumn(idDocument, "num_of_copies", Integer.toString(countOfCopies));
       } else {

       }
    }

    /**
     * Modify the bestseller status of book stored in database.
     *
     * @param idBook     ID of book which is going to be modified.
     * @param database   tools.Database that stores the information.
     * @param bestseller New status.
     */
    public void modifyBookBestseller(int librarianId, int idBook, Database database, boolean bestseller)  {
        if(Logic.canModify(librarianId, database)) {
            database.getBook(idBook).setBestseller(bestseller);
            database.editDocumentColumn(idBook, "bestseller", Boolean.toString(bestseller));
        } else {

        }
    }

    /**
     * Modify the last name of patron stored in database.
     *
     * @param idPatron ID of patron which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param surname  New last name.
     */
    public void modifyPatronSurname(int librarianId, int idPatron, Database database, String surname)  {
        if(Logic.canModify(librarianId, database)) {
            database.getPatron(idPatron).setSurname(surname);
            database.editUserColumn(idPatron, "lastname", surname);
        } else {

        }

    }

    /**
     * Modify the living address of patron stored in database.
     *
     * @param idPatron ID of patron which is going to be modified.
     * @param database tools.Database that stores the information.
     * @param address  New living address.
     */
    public void modifyPatronAddress(int librarianId, int idPatron, Database database, String address)  {
        if(Logic.canModify(librarianId, database)) {
            database.getPatron(idPatron).setAddress(address);
            database.editUserColumn(idPatron, "address", address);
        } else {

        }
    }

    /**
     * Modify the phone number of patron stored in database.
     *
     * @param idPatron    ID of patron which is going to be modified.
     * @param database    tools.Database that stores the information.
     * @param phoneNumber New phone number.
     */
    public void modifyPatronPhoneNumber(int librarianId, int idPatron, Database database, String phoneNumber) {
        if(Logic.canModify(librarianId, database)) {
            database.getPatron(idPatron).setPhoneNumber(phoneNumber);
            database.editUserColumn(idPatron, "phone", phoneNumber);
        } else {

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
    public void modifyPatronStatus(int librarianId, int idPatron, Database database, String status)  {
        if(Logic.canModify(librarianId, database)) {
            database.getPatron(idPatron).setStatus(status);
            database.editDocumentColumn(idPatron, "status", status);
        } else {

        }
    }
}
