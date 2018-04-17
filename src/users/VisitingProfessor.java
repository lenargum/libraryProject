package users;

public class VisitingProfessor extends Patron{
    public VisitingProfessor(String login, String password, String name, String surname, String phone, String address){
        super(login, password, name, surname, phone, address);
        this.setPriority();
        this.setStatus("VP");
    }

    @Override
    protected void setPriority() {
        this.priority = 3;
    }
}
