package users;

public class Instructor extends Patron{
    public Instructor(String login, String password, String name, String surname, String phone, String address){
        super(login, password, name, surname, phone, address);
        this.setPriority();
    }

    @Override
    protected void setPriority() {
        this.priority = 1;
    }
}
