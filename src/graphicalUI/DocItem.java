package graphicalUI;

import com.jfoenix.controls.JFXRippler;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;


public class DocItem extends Pane {
	private Label title, author;
	private Pane cover;
	private JFXRippler rippler;

	public DocItem() {
	}

	public DocItem(String title, String author) {
		this.title = new Label(title);
		this.author = new Label("by " + author);

		this.setPrefSize(165, 215);

		cover = new Pane();
		cover.setPrefSize(140, 190);
		cover.setStyle("-fx-background-color: #ffaa53; -fx-background-radius: 5");
		cover.setLayoutX(13);
		cover.setLayoutY(13);

		this.title.setStyle("-fx-font-size: 20; -fx-font-style: bold");
		this.title.setTextFill(Paint.valueOf("#ffffff"));
		this.title.setMaxWidth(130);
		this.title.setLayoutX(14);
		this.title.setLayoutY(39);
		this.author.setStyle("-fx-font-size: 14; -fx-font-style: light");
		this.author.setTextFill(Paint.valueOf("#ffffff"));
		this.author.setMaxWidth(130);
		this.author.setWrapText(true);
		this.author.setLayoutX(14);
		this.author.setLayoutY(140);


		cover.getChildren().addAll(this.title, this.author);
		rippler = new JFXRippler(cover);
		this.getChildren().add(rippler);
	}
}
