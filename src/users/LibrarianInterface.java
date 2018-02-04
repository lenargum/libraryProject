package users;

import materials.Document;

import java.util.LinkedList;

public interface LibrarianInterface {

    void registerPatron(String name, String address, String phoneNumber, String status);

    void registerMaterial(Document document);

    void deletePatron(int idPatron);

 //   public void addDocumentInLibrary(Document document);

    void deleteDocumentFromLibrary(int idDocument);

    void setNamePatron(Patron patron, String namePatron);

    void setAddressPatron(Patron patron, String address);

    void setPhoneNumberPatron(Patron patron, String phoneNumber);

    void setStatusPatron(Patron patron, String status);

    LinkedList<Patron> getListOfPatrons();
}
