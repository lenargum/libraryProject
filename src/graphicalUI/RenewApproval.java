package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.svg.SVGGlyph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Renew approval window.
 *
 * @author Ruslan Shakirov
 */
public class RenewApproval {
	CoreAPI api;
	AnchorPane layout;
	Stage stage;
	Scene scene, previousScene;
	JFXSnackbar snackbar;
	@FXML
	JFXButton goBackBtn;
	JFXListView<ApprovalCell> listView;

	public RenewApproval() {
	}

	/**
	 * Create new view.
	 *
	 * @param mainStage     Main stage.
	 * @param previousScene Previous scene.
	 * @param api           Core API.
	 */
	public RenewApproval(Stage mainStage, Scene previousScene, CoreAPI api) {
		this.api = api;
		stage = mainStage;
		this.previousScene = previousScene;
		try {
			layout = FXMLLoader.load(getClass().getResource("layout/RenewApprovalLayout.fxml"));
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

		listView = (JFXListView<ApprovalCell>) layout.lookup("#approvalList");
		listView.getItems().addAll(api.getRenewRequests());

		listView.setOnContextMenuRequested(this::onListClicked);

		snackbar = new JFXSnackbar(layout);
	}

	/**
	 * Action on list clicked.
	 *
	 * @param event Mouse event.
	 */
	private void onListClicked(ContextMenuEvent event) {
		ApprovalCell selected = listView.getSelectionModel().getSelectedItem();
		if (selected == null) return;
		int selectedIndex = listView.getSelectionModel().getSelectedIndex();

		JFXPopup popup = new JFXPopup();
		VBox container = new VBox();
		JFXButton accept = new JFXButton("Accept");
		JFXButton reject = new JFXButton("Reject");

		accept.setOnAction(event1 -> {
			api.acceptRenewRequest(selected.getRequestId());
			listView.getItems().remove(selectedIndex);
			popup.hide();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Accepted"));
		});
		reject.setOnAction(event1 -> {
			api.rejectRenewRequest(selected.getRequestId());
			listView.getItems().remove(selectedIndex);
			popup.hide();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Rejected"));
		});

		container.getChildren().addAll(accept, reject);
		container.setPadding(new Insets(5));
		popup.setPopupContent(container);

		popup.show(listView, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, event.getX() - 600, event.getY());
	}

	/**
	 * Show view.
	 */
	public void show() {
		stage.setScene(scene);
	}
}
