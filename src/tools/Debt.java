package tools;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class describes the debt in library system.
 *
 * @author Lenar Gumerov
 * @author Anastasia Minakova
 * @author Madina Gafarova
 */
public class Debt {
	/**
	 * tools.Debt ID.
	 */
	private int debtId;

	/**
	 * Associated patron's ID.
	 */
	private int patronId;

	/**
	 * Associated document ID.
	 */
	private int documentId;

	/**
	 * Booking date.
	 */
	private Date bookingDate;

	/**
	 * Expire date.
	 */
	private Date expireDate;

	/**
	 * Fee applied to this debt.
	 */
	private double fee;

	/**
	 * Renew status. Is associated document can be renewed.
	 */
	private boolean canRenew;

	/**
	 * Initialize new debt.
	 *
	 * @param patronId    Associated patron ID.
	 * @param documentId  Associated document ID.
	 * @param bookingDate Booking date.
	 * @param expireDate  Expire date.
	 * @param fee         Fee applied to this debt.
	 * @param canRenew    Renew status.
	 */
	public Debt(int patronId, int documentId, Date bookingDate, Date expireDate, double fee, boolean canRenew) {
		this.patronId = patronId;
		this.documentId = documentId;
		this.bookingDate = bookingDate;
		this.expireDate = expireDate;
		this.fee = fee;
		this.canRenew = canRenew;
	}

	/**
	 * Get this debt in string notation.
	 *
	 * @return String with debt description.
	 */
	public String toString() {
		return patronId + " " + documentId + " (" + (new SimpleDateFormat("dd-MMM-yyyy")).format(bookingDate) + ") (" + (new SimpleDateFormat("dd-MMM-yyyy")).format(expireDate) + ") " + fee + " " + canRenew + " ";
	}

	/**
	 * Get the associated patron ID.
	 *
	 * @return users.Patron ID.
	 */
	public int getPatronId() {
		return patronId;
	}

	/**
	 * Set the new associated patron ID.
	 *
	 * @param patronId New patron ID.
	 */
	public void setPatronId(int patronId) {
		this.patronId = patronId;
	}

	/**
	 * Get the associated document ID.
	 *
	 * @return documents.Document ID.
	 */
	public int getDocumentId() {
		return documentId;
	}

	/**
	 * Set the new associated document ID.
	 *
	 * @param documentId New document ID.
	 */
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	/**
	 * Get the booking date.
	 *
	 * @return Booking date.
	 * @see Date
	 */
	public Date getBookingDate() {
		return bookingDate;
	}

	/**
	 * Set the new booking date.
	 *
	 * @param bookingDate New booking date.
	 * @see Date
	 */
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}

	/**
	 * Get the expire date.
	 *
	 * @return Expire date.
	 * @see Date
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * Set the new expire date.
	 *
	 * @param date New expire date.
	 * @see Date
	 */
	public void setExpireDate(Date date) {
		this.expireDate = date;
	}

	/**
	 * Get the fee applied to this debt.
	 *
	 * @return Fee.
	 */
	public double getFee() {
		return fee;
	}

	/**
	 * Apply the new fee to this debt.
	 *
	 * @param fee New fee.
	 */
	public void setFee(double fee) {
		this.fee = fee;
	}

	/**
	 * Get the info about ability to renew associated document.
	 *
	 * @return Renew status.
	 */
	public boolean canRenew() {
		return canRenew;
	}

	/**
	 * Set the new renew status. Info about ability to renew associated document.
	 *
	 * @param canRenew New renew status.
	 */
	public void setCanRenew(boolean canRenew) {
		this.canRenew = canRenew;
	}

	/**
	 * Get the remaining time to expire date in days.
	 *
	 * @return Days remain.
	 */
	public int daysLeft() {
		return (int) ((expireDate.getTime() - System.currentTimeMillis()) / (60 * 60 * 24 * 1000));
	}

	/**
	 * Get this debt ID.
	 *
	 * @return tools.Debt ID.
	 */
	public int getDebtId() {
		return debtId;
	}

	/**
	 * Set the new debt ID.
	 *
	 * @param debtId New debt ID.
	 */
	public void setDebtId(int debtId) {
		this.debtId = debtId;
	}

	/**
	 * Apply fee for this debt.
	 *
	 * @param database tools.Database that stores the documents information.
	 */
	public void countFee(Database database) throws SQLException {
		if (daysLeft() < 0) {
			setFee(min(daysLeft() * (-100), database.getDocument(documentId).getPrice()));
		}
	}

	/**
	 * For internal use.
	 * Returns the minimal number.
	 */
	private double min(double a, double b) {
		return a < b ? a : b;
	}
}
