package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.svg.SVGGlyph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class RenewApproval {
	AnchorPane layout;
	Stage stage;
	Scene scene, previousScene;
	@FXML
	JFXButton goBackBtn;
	JFXListView<ApprovalCell> listView;

	public RenewApproval() {
	}

	public RenewApproval(Stage mainStage, Scene previousScene, CoreAPI api) {
		stage = mainStage;
		this.previousScene = previousScene;
		try {
			layout = FXMLLoader.load(getClass().getResource("RenewApprovalLayout.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		scene = new Scene(layout);
		goBackBtn = (JFXButton) layout.lookup("#goBackBtn");
		goBackBtn.setOnAction(event -> stage.setScene(previousScene));
		SVGGlyph goBackGraphic = Glyphs.ARROW_BACK;
		goBackGraphic.setSize(20, 20);
		goBackBtn.setTextFill(Paint.valueOf("#727272"));
		goBackBtn.setGraphic(goBackGraphic);

		listView = (JFXListView<ApprovalCell>) layout.lookup("#approvalList");
		listView.getItems().addAll(api.getRenewRequests());

		listView.setOnMouseClicked(this::onListClicked);
	}

	private void onListClicked(MouseEvent event) {
		ApprovalCell selected = listView.getSelectionModel().getSelectedItem();
		if (selected == null) return;
		int selectedIndex = listView.getSelectionModel().getSelectedIndex();
		JFXPopup popup = new JFXPopup();
		HBox container = new HBox();
		JFXButton accept = new JFXButton("ACCEPT");
		JFXButton reject = new JFXButton("REJECT");
		accept.setOnAction(event1 -> {
			// do smth
			listView.getItems().remove(selectedIndex);
			popup.hide();
		});
		reject.setOnAction(event1 -> {
			// do smth
			listView.getItems().remove(selectedIndex);
			popup.hide();
		});
		container.getChildren().addAll(accept, reject);
		container.setPadding(new Insets(5));
		popup.setPopupContent(container);
		popup.show(listView, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, event.getX() - 600, event.getY());
	}

	public void show() {
		stage.setScene(scene);
	}
}
