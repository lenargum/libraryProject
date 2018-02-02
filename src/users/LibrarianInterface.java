package users;

import materials.Document;

public interface LibrarianInterface {

    public void createPatron(String name, String address, String phoneNumber, String status);

    public void deletePatron(int idPatron);

    public void addDocumentInLibrary(Document document);

    public void deleteDocumentFromLibrary(int idDocument);

    public void setNamePatron(Patron patron, String namePatron);

    public void setAddressPatron(Patron patron, String address);

    public void setPhoneNumberPatron(Patron patron, String phoneNumber);

    public void setStatusPatron(Patron patron, String status);
}
