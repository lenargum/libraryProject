package graphicalUI;

import com.jfoenix.controls.JFXRippler;
import com.jfoenix.svg.SVGGlyph;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class UserPage {
	Stage primaryStage;
	private MainPage creator;
	private AnchorPane userLayout;

	public UserPage() {
	}

	public UserPage(Stage primaryStage, MainPage creator) {
		this.primaryStage = primaryStage;
		this.creator = creator;
	}

	public void show() {
		try {
			userLayout = FXMLLoader.load(getClass().getResource("UserPage.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Scene mainScene = new Scene(userLayout);
		primaryStage.setScene(mainScene);
		SVGGlyph accountIcon = new SVGGlyph("M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 " +
				"10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 " +
				"0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z");
		accountIcon.setSize(30, 30);
		accountIcon.setFill(Paint.valueOf("#757575"));
		JFXRippler rippler = new JFXRippler(accountIcon);
		rippler.setRipplerFill(Paint.valueOf("#c7c7c7"));
		Pane accountIconContainer = (Pane) userLayout.lookup("#accountIconContainer");
		accountIconContainer.getChildren().add(rippler);
	}
}
