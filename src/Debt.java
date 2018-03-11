import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Debt {
	private int debtId;
	private int patronId;
	private int documentId;
	private Date bookingDate;
	private Date expireDate;
	private double fee;
	private boolean canRenew;

	public Debt(int patronId, int documentId, Date bookingDate, Date expireDate, double fee, boolean canRenew) {
		this.patronId = patronId;
		this.documentId = documentId;
		this.bookingDate = bookingDate;
		this.expireDate = expireDate;
		this.fee = fee;
		this.canRenew = canRenew;
	}

	public String toString() {
		return patronId + " " + documentId + " (" + (new SimpleDateFormat("dd-MMM-yyyy")).format(bookingDate) + ") (" + (new SimpleDateFormat("dd-MMM-yyyy")).format(expireDate) + ") " + fee + " " + canRenew + " ";
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

	public void setExpireDate(Date date) throws SQLException {
		this.expireDate = date;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public boolean canRenew() {
		return canRenew;
	}

	public void setCanRenew(boolean canRenew) {
		this.canRenew = canRenew;
	}

	public int daysLeft() {
		Date date = new Date();
		return (int) (expireDate.getTime() - date.getTime() + 60 * 60 * 24 * 1000 - 1) / (60 * 60 * 24 * 1000);
	}

	public int getDebtId() {
		return debtId;
	}

	public void setDebtId(int debtId) {
		this.debtId = debtId;
	}

	public void countFee(Database database)throws SQLException{
		if(daysLeft() > 0) setFee(min(daysLeft()*100, database.getDocument(documentId).getPrice()));
	}

	public double min(double a, double b){
		if(a > b) return a;
		return b;
	}
}
