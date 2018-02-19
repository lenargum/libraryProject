public class Patron extends User {
    private String status;

    Patron(String name, String surname, String address, String status, int id, String login, String password){
        super(name, surname, address, id,login, password);
        this.status = status;
    }

    /**
     *
     * @return true: if Patron can get the document, otherwise false
     */
    public boolean requestDocument(){
        return true;
    }

    /**
     * @param : id of Document
     */
    public void takeDocument(){

    }

    /**
     * @param : id of Document
     */
    public void returnDocument(){

    }

    /**
     *
     * @return true: if Patron can extend document, otherwise false
     */
    public boolean canRenewDocument(){
        return true;
    }

    /**
     * @param  : id of document
     */
    public void renewDocument(){

    }
}
