package users;

/**
 * class represents "User" type in the users.User
 */
public abstract class User implements UserInterface {

    private String name; //name of current user
    private String address;// address of current user
    private String phoneNumber;//phone number of current user
    private int id; //id of current user. ID can be set ONLY BY ONE OF THE LIBRARIANS!

    /**
     * constructor
     *
     * @param name
     * @param address
     * @param phoneNumber
     * @param id
     */
    User(String name, String address, String phoneNumber, int id) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return name of current user
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * sets name of current user
     *
     * @param name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return address of current user
     */
    @Override
    public String getAddress() {
        return address;
    }

    /**
     * sets address of current user
     *
     * @param address
     */
    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return phone number of user
     */
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets phone number of current user
     *
     * @param phoneNumber
     */
    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return ID of current user
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * checks whether one user is copy of another - it can be done with repeated registration
     *
     * @param user
     * @return true if current user is copy of other
     */
    public boolean equals(User user) {
        if (this.getId() == user.getId()) return false;
        return this.getAddress().equals(user.getAddress()) &&
                this.getName().equals(user.getName()) &&
                this.getPhoneNumber().equals(user.getPhoneNumber());
    }
}
