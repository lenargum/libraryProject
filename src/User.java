public class User {

    private String name;
    private String address;
    private String phoneNumber;
    private int id;

    User() {}

    User(String name, int id){
        this.name = name;
        this.id = id;
        address = null;
        phoneNumber = null;
    }

    User(String name, String address, String phoneNumber, int id) {
        this.name = name;
        this.id = id;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getId() {
        return id;
    }
}
