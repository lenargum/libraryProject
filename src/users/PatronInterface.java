package users;

import materials.Document;

import java.util.LinkedList;

public interface PatronInterface {

	public String getStatus();

	public void setStatus(String status);

	public LinkedList<Document> getListOfDocumentsPatron();

	public int getDebts();

	public void setDebts(int debts);

	public void addDocumentInList(Document document);

	public boolean getRequest(int idDocument, Librarian librarian);

	public void takeDocument(int idDocument, Librarian librarian);

	public LinkedList<Document> getDocumentsInLibrary(Librarian librarian);

}
