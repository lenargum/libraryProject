package users;

public class TeachingAssistant extends Patron {
	public TeachingAssistant(String login, String password, String name, String surname, String phone, String address) {
		super(login, password, name, surname, phone, address);
		this.setPriority();
		this.setStatus("TA");
	}

	@Override
	protected void setPriority() {
		this.priority = 2;
	}
}
