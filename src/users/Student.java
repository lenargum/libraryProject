package users;

public class Student extends Patron {
	public Student(String login, String password, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
		this.setPriority();
		this.setStatus("STUDENT");
	}

	@Override
	protected void setPriority() {
		this.priority = 0;
	}
}
