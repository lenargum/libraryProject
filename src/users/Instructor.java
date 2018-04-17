package users;

public class Instructor extends Patron{
    public Instructor(String login, String password, String status, String name, String surname, String phone, String address){
        super(login, password,status, name, surname, phone, address);
        this.setPriority();
        this.setStatus("INSTRUCTOR");
    }

    @Override
    protected void setPriority() {
        this.priority = 1;
    }
}
