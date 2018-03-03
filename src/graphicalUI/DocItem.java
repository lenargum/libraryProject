package graphicalUI;

import com.jfoenix.controls.JFXRippler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.Random;


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
		Color coverColor;
		switch (new Random().nextInt(4)) {
			case 0:
				cover.setStyle("-fx-background-color: #ffaa53; -fx-background-radius: 5");
				break;
			case 1:
				cover.setStyle("-fx-background-color: #53e5ff; -fx-background-radius: 5");
				break;
			case 2:
				cover.setStyle("-fx-background-color: #a7f26e; -fx-background-radius: 5");
				break;
			case 3:
				cover.setStyle("-fx-background-color: #f266aa; -fx-background-radius: 5");
				break;
			default:
				cover.setStyle("-fx-background-radius: 5");
				break;
		}
		cover.setLayoutX(13);
		cover.setLayoutY(13);

		//this.title.setStyle("-fx-font-size: 20; -fx-font-style: bold");
		this.title.setFont(new Font("Roboto Slab Regular", 18));
		this.title.setTextFill(Paint.valueOf("#ffffff"));
		this.title.setMaxWidth(120);
		this.title.setWrapText(true);
		this.title.setMaxHeight(90);
		this.title.setLayoutX(14);
		this.title.setLayoutY(30);

		//this.author.setStyle("-fx-font-size: 14; -fx-font-style: light");
		this.author.setFont(new Font("Roboto Condensed", 14));
		this.author.setTextFill(Paint.valueOf("#ffffff"));
		this.author.setMaxWidth(125);
		this.author.setWrapText(true);
		this.author.setMaxHeight(50);
		this.author.setLayoutX(14);
		this.author.setLayoutY(140);

		cover.getChildren().addAll(this.title, this.author);
		rippler = new JFXRippler(cover);
		rippler.setRipplerFill(Paint.valueOf("#fafafa"));
		this.getChildren().add(rippler);
	}

	public String getTitle() {
		return title.getText();
	}

	public String getAuthor() {
		return author.getText();
	}
}
