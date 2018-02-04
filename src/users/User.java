package users;

public class User implements UserInterface {

    private String name;
    private String address;
    private String phoneNumber;
    private int id;

    User(String name, String address, String phoneNumber, int id) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public int getId() {
        return id;
    }

    /**
     * checks whether one user is copy of another - it can be done with repeated registration
     * @param user
     * @return true if current user is copy of other
     */
    public boolean equals(User user){
        return this.getId() != user.getId() && this.getAddress().equals(user.getAddress()) && this.getName().equals(user.getName()) && this.getPhoneNumber() == user.getPhoneNumber();
    }
}
