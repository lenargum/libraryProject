package tools;

import documents.Document;
import users.Librarian;
import users.Patron;
import users.User;

import javax.print.Doc;
import java.util.Date;

public class Constants {

	public static int basicPrivilege = 0;
	public static int modifyPrivilege = 1;
	public static int addPrivilege = 2;
	public static int deletePrivilege = 3;

	public static String registerUserMessage(Patron user){
		return "New " + user.getStatus() + " " + user.getName() + " " + user.getSurname() + " registered!";
	}

	public static String addDocumentMessage(Document doc){
		return "New " + doc.getType() + " " + doc.getTitle() + " added to the Library!";
	}

	public static String deleteUserMessage(User user){
		return "User " + user.getName() + " " + user.getSurname() + " deleted from the Library";
	}

	public static String deleteDocumentMessage(Document doc){
		return "Document " + doc.getTitle() + " deleted from the Library";
	}

	public static String changedPatronFieldMessage(Librarian librarian, Patron user){
		return "Librarian " + librarian.getSurname() + " changed information about " + user.getStatus() + " " + user.getSurname();
	}

	public static String changedPatronFieldMessage(Librarian librarian, Document document){
		return "Librarian " + librarian.getSurname() + " changed information about " + document.getType() + " " + document.getTitle();
	}

	public static String changedLibrarianFieldMessage(Librarian librarian){
		return "Administrator changed information about librarian " + librarian.getSurname();
	}

	public static String registerLibrarianMessage(Librarian librarian){
		return "Administrator registered new librarian " + librarian.getSurname();
	}

	public static String deleteLibrarianMessage(Librarian librarian){
		return "Administrator deleted new librarian " + librarian.getSurname();
	}

	public static String placeOutstandingRequestMessage(Librarian librarian, Document doc){
		return "Librarian " + librarian.getSurname() + " placed outstanding request on " + doc.getType() + " " + doc.getTitle();
	}

	public static String takeDocumentMessage(Patron patron, Document doc){
		return patron.getStatus() + " " + patron.getSurname() + " took " + doc.getType() + " " + doc.getTitle();
	}

	public static String returnDocumentMessage(Patron patron, Document doc){
		return patron.getStatus() + " " + patron.getSurname() + " returned " + doc.getType() + " " + doc.getTitle();
	}

	public static String renewDocumentMessage(Patron patron, Document doc){
		return patron.getStatus() + " " + patron.getSurname() + " renewed " + doc.getType() + " " + doc.getTitle();
	}

	public static String patronNotifiedMessage(Patron patron, String mes){
		return "Notification " + mes + " was sent to " + patron.getStatus() + " " + patron.getSurname();
	}

	public static Date setWeek() {
		Date date = new Date();
		date.setTime(date.getTime() + 7 * 24 * 60 * 60 * 1000);
		return date;
	}

	public static Date setTwoWeeks() {
		Date date = new Date();
		date.setTime(date.getTime() + 14 * 24 * 60 * 60 * 1000);
		return date;
	}

	public static Date setThreeWeeks() {
		Date date = new Date();
		date.setTime(date.getTime() + 21 * 24 * 60 * 60 * 1000);
		return date;
	}

	public static Date setFourWeeks() {
		Date date = new Date();
		date.setTime(date.getTime() + 28L * 24 * 60 * 60 * 1000);
		return date;
	}

	public static Date setWeek(Date date) {
		date.setTime(date.getTime() + 7 * 24 * 60 * 60 * 1000);
		return date;
	}

	public static Date setTwoWeeks(Date date) {
		date.setTime(date.getTime() + 14 * 24 * 60 * 60 * 1000);
		return date;
	}

	public static Date setThreeWeeks(Date date) {
		date.setTime(date.getTime() + 21 * 24 * 60 * 60 * 1000);
		return date;
	}

	public static Date setFourWeeks(Date date) {
		date.setTime(date.getTime() + 28L * 24 * 60 * 60 * 1000);
		return date;
	}
}
