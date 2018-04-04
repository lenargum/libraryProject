package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
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

		container.getChildren().addAll(item, new JFXButton("RENEW"), new JFXButton("RETURN"));

		container.setPadding(new Insets(20));

		setPopupContent(container);
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
