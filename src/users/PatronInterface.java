package users;

import materials.Document;

import java.util.ArrayList;
import java.util.LinkedList;

public interface PatronInterface {

    public void setStatus(String status);

    public String getStatus();

    public LinkedList<Document> getListOfDocumentsPatron();

    public void setDebts(int debts);

    public int getDebts();

    public void addDocumentInList(Document document);

    public boolean getRequest(int idDocument, Librarian librarian);

    public void takeDocument(int idDocument, Librarian librarian);

    public LinkedList<Document> getDocumentsInLibrary(Librarian librarian);

}
