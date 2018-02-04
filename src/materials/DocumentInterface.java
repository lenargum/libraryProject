package materials;

public interface DocumentInterface {
    void setTitle(String Title);

    void setAuthors(String Authors);

    void setUserID(int userID);

    void setDocID(int id);

    void setPrice(float price);

    void setChecked(boolean checked);

    void setReference(boolean reference);

    void setAllowedForStudents(boolean allowedForStudents);

    String getTitle();

    String getAuthors();

    float getPrice();

    int getDocID();

    int getUserID();

    boolean isChecked(); //взяли книгу или нет

    boolean isReference(); //является ли документ справочным(влияет на возможность забрать его из библиотеки)

    boolean isAllowedForStudents();

//    public boolean canTake(Patron user);

//    public boolean isFaculty(Patron x);

    void addAuthor(String newAuthor);

    boolean isWrittenBy(String author);

}
