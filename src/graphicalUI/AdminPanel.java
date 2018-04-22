package graphicalUI;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminPanel {
	private CoreAPI api;
	private Stage stage;
	private AnchorPane layout;

	@FXML
	private JFXButton manageLibrariansBtn;

	public AdminPanel() {
	}

	public AdminPanel(CoreAPI api) {
		this.api = api;

		try {
			layout = FXMLLoader.load(getClass().getResource("layout/AdminPanel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		manageLibrariansBtn = (JFXButton) layout.lookup("#manageLibrariansBtn");

		stage = new Stage();
		stage.setTitle("Admin control panel");
		stage.setScene(new Scene(layout));
	}

	public void show() {
		stage.show();
	}
}
