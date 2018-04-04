package tools;

import java.util.Date;

public class Notification {
	private int id;
	private int requestId;
	private int userId;
	private Date date;
	private String description;

	public Notification(int requestId,int userId, String description,Date date) {
		this.requestId = requestId;
		this.userId = userId;
		this.description = description;
		this.date = date;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}