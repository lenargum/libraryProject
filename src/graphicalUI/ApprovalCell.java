package graphicalUI;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;


public class ApprovalCell extends HBox {
	private int docID, userID;
	private String docTitle;
	private String userName;

	public ApprovalCell() {

	}

	public ApprovalCell(String docTitle, int docID, String userName, int userID) {
		this.docID = docID;
		this.userID = userID;
		Label title = new Label(docTitle);
		Label name = new Label(userName);
		title.setFont(new Font("Roboto", 16));
		name.setFont(new Font("Roboto Italic", 16));

		title.setPadding(new Insets(0, 5, 0, 0));
		name.setPadding(new Insets(0, 5, 0, 0));

		getChildren().addAll(title, name);

		setPadding(new Insets(10));
	}

	public int getDocID() {
		return docID;
	}

	public int getUserID() {
		return userID;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public String getUserName() {
		return userName;
	}
}
