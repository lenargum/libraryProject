package users;

/**
 * interface describes user class
 */
public interface UserInterface {

	/**
	 * @return name of current user
	 */
	public String getName();

	/**
	 * sets name of  user
	 *
	 * @param name
	 */
	public void setName(String name);

	/**
	 * @return address of current user
	 */
	public String getAddress();

	/**
	 * sets address of user
	 *
	 * @param address
	 */
	public void setAddress(String address);

	/**
	 * @return phone number of user
	 */
	public String getPhoneNumber();

	/**
	 * sets phone number of user
	 *
	 * @param phoneNumber
	 */
	public void setPhoneNumber(String phoneNumber);

	/**
	 * @return ID of current user
	 */
	public int getId();
}
