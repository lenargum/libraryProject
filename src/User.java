import java.util.LinkedList;
import java.util.List;

public class User implements UserInterface {

    private String name;
    private String address;
    private String phoneNumber;
    private int id;

    public List<Document> documents = new LinkedList<>();

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
