import java.security.Key;

public abstract class Document {
    /**
     * ID of  document
     */
    private int ID;
    /**
     * Title of document
     */
    private String Title;
    /**
     * Author(s) of document
     */
    private String Authors;
    /**
     * shows wether students can take the document
     */
    private boolean IsAllowedForStudents;
    /**
     * number of document copies stored in the library
     */
    private int NumberOfCopies;
    /**
     * shows wether document is reference - if it is patrons cannot take it from the library
     */
    private boolean IsReference;
    /**
     * Price of document
     */
    private float Price;
    /**
     * keywords we use to search document
     */
    private String KeyWords;

    /**
     * constructor
     * @param ID
     * @param Title
     * @param Authors
     * @param IsAllowedForStudents
     * @param NumberOfCopies
     * @param IsReference
     * @param Price
     * @param KeyWords
     */
    public Document(int ID, String Title, String Authors, boolean IsAllowedForStudents, int NumberOfCopies, boolean IsReference, float Price, String KeyWords){
        setID(ID);
        setTitle(Title);
        setAuthors(Authors);
        setAllowedForStudents(IsAllowedForStudents);
        setNumberOfCopies(NumberOfCopies);
        setReference(IsReference);
        setPrice(Price);
        setKeyWords(KeyWords);
    }

    /**
     * sets ID of document
     * @param id
     */
    public void setID(int id){
        this.ID = id;
    }
    /**
     * @return ID of document
     */
    public int getID(){
        return ID;
    }


    /**
     * sets Title of document
     * @param title
     */
    public void setTitle(String title){
        this.Title = title;
    }
    /**
     * @return title of document
     */
    public String getTitle(){
        return Title;
    }

    /**
     * sets author(s) of document
     * @param authors
     */
    public void setAuthors(String authors){
        this.Authors = authors;
    }
    /**
     * adds author to list of authors
     * @param author
     */
    public void addAuthor(String author){
        this.Authors += ", " + author;
    }
    /**
     * @param author
     * @return true if author was among the authors created the document
     */
    public boolean includesAuthor(String author){
        String[] authors;
        authors = Authors.split(", ");
        for (String a:authors
             ) {
            if(a.equals(author))
                return true;
        }
        return false;
    }
    /**
     * @return list of authors created the document
     */
    public String getAuthors(){
        return Authors;
    }

    /**
     * shows wether students can take document
     * @param isAllowed
     */
    public void setAllowedForStudents(boolean isAllowed){
        this.IsAllowedForStudents = isAllowed;
    }
    /**
     * @return wether sstudents can take the document
     */
    public boolean isAllowedForStudents(){
        return IsAllowedForStudents;
    }

    /**
     * sets number of copies of document that are stored in the library
     * @param number
     */
    public void setNumberOfCopies(int number){
        this.NumberOfCopies = number;
    }
    /**
     *adds copy of document
     */
    public void AddCopy(){
        this.NumberOfCopies++;
    }
    /**
     * deletes copy of document
     */
    public void deleteCopy(){
        this.NumberOfCopies--;
    }
    /**
     * @return number of documents copies stored in the library
     */
    public int getNumberOfCopies(){
        return this.NumberOfCopies;
    }

    /**
     * shows wether document is reference
     * @param isReference
     */
    public void setReference(boolean isReference){
        this.IsReference = isReference;
    }
    /**
     * @return is document reference or not
     */
    public boolean isReference() {
        return IsReference;
    }

    /**
     * sets keywords we can use to search for document
     * @param keyWords
     */
    public void setKeyWords(String keyWords) {
        this.KeyWords = keyWords;
    }
    /**
     * adds keyword for document
     * @param newWord
     */
    public void addKeyWord(String newWord){
        this.KeyWords += ", " + newWord;
    }
    /**
     * checks out does keyword belong to keywords for document
     * @param keyWord
     * @return
     */
    public boolean includeKeyWord(String keyWord){
        String[] keywords = KeyWords.split(", ");
        for (String k:keywords
             ) {
            if(k.equals(keyWord)) return true;
        }
        return false;
    }
    /**
     * @return keywords of document
     */
    public String getKeyWords(){
        return KeyWords;
    }

    /**
     * sets price of document
     * @param price
     */
    public void setPrice(float price){
        this.Price = price;
    }
    /**
     * @return price of document
     */
    public float getPrice(){
        return Price;
    }
}
