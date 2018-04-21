package users;

public class Professor extends Patron {
	public Professor(String login, String password, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
		this.setPriority();
		this.setStatus("PROFESSOR");
	}

	@Override
	protected void setPriority() {
		this.priority = 4;
	}
}
