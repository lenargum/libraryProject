import java.util.Date;

public class Debt {
    private int patronId;
    private int documentId;
    private Date bookingDate;
    private Date expireDate;
    private int fee;
    private boolean canRenew;

    public Debt(int patronId, int documentId, Date bookingDate,
                Date expireDate, int fee, boolean canRenew, boolean isRenewed) {
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

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public void setCanRenew(boolean canRenew) {
        this.canRenew = canRenew;
    }

    public void setRenewed(boolean renewed) {
        canRenew = renewed;
    }
}
