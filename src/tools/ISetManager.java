package tools;

import materials.Document;
import users.Patron;

public interface ISetManager {

	public void addPatron(Patron patron);

	public void deletePatron(int idPatron);

	public void addDocumentInLibrary(Document document);

	public void deleteDocumentFromLibrary(int idDocument);

}
