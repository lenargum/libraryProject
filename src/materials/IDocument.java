package materials;

import users.Patron;

public interface IDocument {
	public String getTitle();

	public void setTitle(String Title);

	public String getAuthors();

	public void setAuthors(String Authors);

	public float getPrice();

	public void setPrice(float price);

	public int getDocID();

	public void setDocID(int id);

	public int getUserID();

	public void setUserID(int userID);

	public boolean isChecked(); //взяли книгу или нет

	public void setChecked(boolean checked);

	public boolean isReference(); //является ли документ справочным(влияет на возможность забрать его из библиотеки)

	public void setReference(boolean reference);

	public boolean isAllowedForStudents();

	public void setAllowedForStudents(boolean allowedForStudents);

	public boolean canTake(Patron user);

	public boolean isFaculty(Patron x);

	public void addAuthor(String newAuthor);

	public boolean isWrittenBy(String author);

}
