import java.util.Date;
import java.sql.SQLException;

public class Debt {
    private int patronId;
    private int documentId;
    private Date bookingDate;
    private Date expireDate;
    private int fee;
    private boolean canRenew;

    public Debt(int patronId, int documentId, Date bookingDate, Date expireDate, int fee, boolean canRenew) {
        this.patronId = patronId;
        this.documentId = documentId;
        this.bookingDate = bookingDate;
        this.expireDate = expireDate;
        this.fee = fee;
        this.canRenew = canRenew;
    }

    public String toString() {
        return patronId+" "+documentId+" ("+bookingDate+") ("+expireDate+") "+fee+" "+canRenew+" ";
    }

    public int getPatronId() {
        return patronId;
    }

    public void setPatronId(int patronId) {
        this.patronId = patronId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Database database) throws SQLException{
        String docType = database.getDocument(documentId).getType();
        String patStatus = database.getPatron(patronId).getStatus();
        if(docType.equals("book")) {
            switch (patStatus) {
                case "student": {
                    if(database.getBook(documentId).isBestseller())
                        expireDate.setTime(bookingDate.getTime() + 14*60*60*24*1000);
                    else
                        expireDate.setTime(bookingDate.getTime() + 21*60*60*24*1000);
                    break;
                }
                case "faculty": {
                    expireDate.setTime(bookingDate.getTime() + 28*60*60*24*1000);
                    break;
                }
            }
        } else {
            expireDate.setTime(bookingDate.getTime() + 14*60*60*24*1000);
        }
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public boolean canRenew() {
        return canRenew;
    }

    public void setCanRenew(boolean canRenew) {
        this.canRenew = canRenew;
    }

    public int daysLeft(){
        Date date = new Date();
        return (int)(expireDate.getTime() - date.getTime())/(60*60*24*1000);
    }
}
