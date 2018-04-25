package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

/**
 * Popup view with document info.
 *
 * @author Ruslan Shakirov
 */
public class DocumentPopup extends JFXPopup {
	private int id;
	private String title;
	private String author;
	private JFXButton renewBtn, returnBtn;

	public DocumentPopup() {
	}

	/**
	 * Create new popup.
	 *
	 * @param title  Document title.
	 * @param author Document author.
	 * @param id     Document ID.
	 */
	public DocumentPopup(String title, String author, int id) {
		this.id = id;
		this.title = title;
		this.author = author;

		VBox container = new VBox();

		DocItem item = new DocItem(title, author, id);

		renewBtn = new JFXButton("RENEW");
		returnBtn = new JFXButton("RETURN");

		container.getChildren().addAll(item, renewBtn, returnBtn);

		container.setPadding(new Insets(20));

		setPopupContent(container);
	}

	public void setOnRenewAction(EventHandler<ActionEvent> value) {
		renewBtn.setOnAction(value);
	}

	public void setOnReturnAction(EventHandler<ActionEvent> value) {
		returnBtn.setOnAction(value);
	}

	public int getDocumentId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}
}
