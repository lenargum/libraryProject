public class Librarian extends User {

    Librarian(String name, String address, String phoneNumber, int id){
        setName(name);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setId(id);
    }

    public void createPatron(String n, String ad, String phone,String st, int i){
        Patron patron = new Patron(n, i);
        setAddressPatron(patron, ad);
        setPhoneNumberPatron(patron, phone);
        setStatusPatron(patron, st);
    }

    public void deletePatron(Patron patron){
        patron.setName(null);
        patron.setAddress(null);
        patron.setPhoneNumber(null);
        patron.setId(-1);
        patron.setStatus("deleted");
    }

    public void giveABook(Patron patron, Document document){
        //TODO: Patron's access to the document!!!
    }

    public void addCopy(int i){

    }

    public void setNamePatron(Patron patron, String name) {
        patron.setName(name);
    }

    public void setAddressPatron(Patron patron,String address) {
        patron.setAddress(address);
    }

    public void setPhoneNumberPatron(Patron patron, String phoneNumber) {
        patron.setPhoneNumber(phoneNumber);
    }

    public void setIdPatron(Patron patron, int id) {
        patron.setId(id);
    }

    public void setStatusPatron(Patron patron, String status){
        patron.setStatus(status);
    }

}
