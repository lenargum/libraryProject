package users;

/**
 * interface describes user class
 */
public interface UserInterface {

    /**
     * sets name of  user
     * @param name
     */
    public void setName(String name);

    /**
     * sets address of user
     * @param address
     */
    public void setAddress(String address);

    /**
     * sets phone number of user
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber);

    /**
     *
     * @return name of current user
     */
    public String getName();

    /**
     *
     * @return address of current user
     */
    public String getAddress();

    /**
     *
     * @return phone number of user
     */
    public String getPhoneNumber();

    /**
     *
     * @return ID of current user
     */
    public int getId();
}
