public class Patron extends User{
    private String status;

    Patron(String name, int id){
        super(name, id);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
