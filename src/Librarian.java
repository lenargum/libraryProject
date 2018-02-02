public class Librarian extends User {
	//TODO make interfaces
	SetManager manager = new SetManager();

	Librarian(String name, String address, String phoneNumber, int id) {
		super(name, address, phoneNumber, id);
	}

	public void createPatron(String name, String address, String phoneNumber, String status) {
		Patron patron = new Patron(name, address, phoneNumber, status, manager.vacantIdPatron);
		patron.setDebts(0);
		manager.addPatron(patron);
		//TODO: add new patron to database (if database exists)
	}

	public void deletePatron(int patronId) {
		manager.deletePatron(patronId);
		//TODO: remove from database (if database exists)
	}

	public void addDocumentInLibrary(Document document) {
		manager.addDocumentInLibrary(document);
	}

	public void deleteDocumentFromLibrary(int idDocument) {
		manager.deleteDocumentFromLibrary(idDocument);
	}

	public void setNamePatron(Patron patron, String name) {
		patron.setName(name);
	}

	public void setAddressPatron(Patron patron, String address) {
		patron.setAddress(address);
	}

	public void setPhoneNumberPatron(Patron patron, String phoneNumber) {
		patron.setPhoneNumber(phoneNumber);
	}

	public void setStatusPatron(Patron patron, String status) {
		patron.setStatus(status);
	}


}
