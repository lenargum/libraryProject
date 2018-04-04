package graphicalUI;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Document list view cell.
 *
 * @author Ruslan Shakirov
 */
public class DocListItem extends HBox {
	private int id;
	private int daysLeft;
	private String title;

	public DocListItem() {
	}

	public DocListItem(String title, int daysLeft, int id) {
		this.id = id;
		this.title = title;
		this.daysLeft = daysLeft;

		Label name = new Label(title);
		Label days = new Label(String.valueOf(daysLeft));

		name.setPadding(new Insets(0, 5, 0, 0));
		days.setPadding(new Insets(0, 0, 0, 5));
		name.setFont(new Font("Roboto", 16));
		days.setFont(new Font("Roboto Black", 16));

		getChildren().addAll(name, days);
		setPadding(new Insets(5));
	}

	public int getDocumentId() {
		return id;
	}

	public int getDaysLeft() {
		return daysLeft;
	}

	public String getTitle() {
		return title;
	}
}
