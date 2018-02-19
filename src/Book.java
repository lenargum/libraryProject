public class Book extends Document{
    /**
     * person or organisation published the book
     */
    private String Publisher;
    /**
     * year of books edition
     */
    private int Edition;
    /**
     * shows wether book is bestseller
     */
    private boolean IsBestseller;

    /**
     * sets publisher of book
     * @param publisher
     */
    public void setPublisher(String publisher){
        this.Publisher = publisher;
    }
    /**
     * @return publisher of book
     */
    public String getPublisher(){
        return Publisher;
    }

    /**
     * sets edition of the book
     * @param edition
     */
    public void setEdition(int edition){
        this.Edition = edition;
    }
    /**
     * @return edition of book
     */
    public int getEdition(){
        return Edition;
    }

    /**
     * sets wether book is bestseller
     * @param isBestseller
     */
    public void setBestseller(boolean isBestseller){
        this.IsBestseller = isBestseller;
    }
    /**
     * @return is book bestseller
     */
    public boolean isBestseller(){
        return IsBestseller;
    }
}
