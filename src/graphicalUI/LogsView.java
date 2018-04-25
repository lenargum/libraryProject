package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.svg.SVGGlyph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class LogsView {
	Stage stage;
	private Scene scene;
	private AnchorPane layout;
	@FXML
	private JFXButton goBackBtn;
	@FXML
	private JFXListView<String> logsList;

	public LogsView() {
	}

	public LogsView(Stage mainStage, Scene previousScene, CoreAPI api) {
		stage = mainStage;

		try {
			layout = FXMLLoader.load(getClass().getResource("layout/LogsView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene = new Scene(layout);

		goBackBtn = (JFXButton) layout.lookup("#goBackBtn");
		goBackBtn.setText("");
		SVGGlyph goBackGraphic = Glyphs.ARROW_BACK();
		goBackGraphic.setSize(20, 20);
		goBackGraphic.setFill(Paint.valueOf("#8d8d8d"));
		goBackBtn.setGraphic(goBackGraphic);
		goBackBtn.setOnAction(event -> stage.setScene(previousScene));

		logsList = (JFXListView<String>) layout.lookup("#logsList");
		logsList.setItems(api.getLogs());
	}

	public void show() {
		stage.setScene(scene);
	}
}
