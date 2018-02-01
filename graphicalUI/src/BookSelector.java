import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class BookSelector {
	@FXML
	private Stage primaryStage;
	@FXML
	private Scene previousScene;
	@FXML
	private Scene bookSelectorScene;
	@FXML
	private AnchorPane bookSelectorLayout;

	private UserCredentials credentials;

	public BookSelector() {
	}

	public BookSelector(Stage primaryStage, Scene previousScene, UserCredentials credentials) throws IOException {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		this.credentials = credentials;
		this.bookSelectorLayout = FXMLLoader.load(getClass().getResource("BookSelector.fxml"));
		this.bookSelectorScene = new Scene(bookSelectorLayout);
	}

	public void show() {
		switchScene(primaryStage, bookSelectorScene);

		Button goBackButton = (Button) bookSelectorLayout.lookup("#goBackButton");
		goBackButton.setOnAction(event -> switchScene(primaryStage, previousScene));
	}

	private void switchScene(Stage targetStage, Scene newScene) {
		targetStage.setScene(newScene);
		targetStage.show();
	}
}
