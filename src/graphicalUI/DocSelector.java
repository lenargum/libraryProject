package graphicalUI;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DocSelector {
	private Stage primaryStage;
	private Scene selectorScene;
	private Scene previousScene;
	@FXML
	private AnchorPane selectorLayout;
	@FXML
	private JFXButton goBackBtn;

	public DocSelector() {
	}

	public DocSelector(Stage primaryStage, Scene previousScene) throws IOException {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		selectorLayout = FXMLLoader.load(getClass().getResource("DocSelector.fxml"));
		selectorScene = new Scene(selectorLayout);

		//goBackBtn.setOnAction(event -> primaryStage.setScene(previousScene));
	}

	public void show() {
		primaryStage.setScene(selectorScene);
	}

}
