package materials;

import users.Patron;

import java.util.HashSet;

public interface DocumentInterface {
    public void setTitle(String Title);

    public void setAuthors(String Authors);

    public void setUserID(int userID);

    public void setDocID(int id);

    public void setPrice(float price);

    public void setChecked(boolean checked);

    public void setReference(boolean reference);

    public void setAllowedForStudents(boolean allowedForStudents);

    public String getTitle();

    public String getAuthors();

    public float getPrice();

    public int getDocID();

    public int getUserID();

    public boolean isChecked(); //взяли книгу или нет

    public boolean isReference(); //является ли документ справочным(влияет на возможность забрать его из библиотеки)

    public boolean isAllowedForStudents();

//    public boolean canTake(Patron user);

//    public boolean isFaculty(Patron x);

    public void addAuthor(String newAuthor);

    public boolean isWrittenBy(String author);

}
