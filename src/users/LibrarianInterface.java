package users;

import materials.Document;

import java.util.LinkedList;

public interface LibrarianInterface {

    public void deletePatron(int idPatron);

    public void addDocumentInLibrary(Document document);

    public void deleteDocumentFromLibrary(int idDocument);

    public void setNamePatron(Patron patron, String namePatron);

    public void setAddressPatron(Patron patron, String address);

    public void setPhoneNumberPatron(Patron patron, String phoneNumber);

    public void setStatusPatron(Patron patron, String status);

    public LinkedList<Patron> getListOfPatrons();
}
