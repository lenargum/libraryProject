package users;

public class User implements UserInterface {

	private String name;
	private String address;
	private String phoneNumber;
	private int id;

	User(String name, String address, String phoneNumber, int id) {
		this.name = name;
		this.id = id;
		this.address = address;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int getId() {
		return id;
	}
}
