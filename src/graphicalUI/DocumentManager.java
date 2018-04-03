package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class DocumentManager {
	private CoreAPI api;
	private Stage stage;
	private Scene scene, previousScene;
	private StackPane layout;
	@FXML
	private JFXButton goBackBtn;
	@FXML
	private JFXTreeTableView docsTable;
	@FXML
	private JFXButton addDocBtn;

	public DocumentManager() {
	}

	public DocumentManager(Stage stage, Scene previousScene, CoreAPI api) {
		this.stage = stage;
		this.previousScene = previousScene;
		this.api = api;

		try {
			layout = FXMLLoader.load(getClass().getResource("DocumentManager.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void show() {
		stage.setScene(scene);
	}
}
