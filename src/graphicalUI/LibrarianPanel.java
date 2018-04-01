package graphicalUI;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrarianPanel {
	private AnchorPane layout;
	private Stage stage;
	private Scene scene;
	private TakeApproval takeApproval;
	private RenewApproval renewApproval;
	private CoreAPI api;
	@FXML
	private JFXButton takeApprovalBtn;
	@FXML
	private JFXButton renewApprovalBtn;
	@FXML
	private JFXButton manageUsersBtn;
	@FXML
	private JFXButton manageDocsBtn;

	public LibrarianPanel() {
	}

	public LibrarianPanel(CoreAPI api) {
		try {
			layout = FXMLLoader.load(getClass().getResource("LibrarianPanel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		takeApprovalBtn = (JFXButton) layout.lookup("#takeApprovalBtn");
		renewApprovalBtn = (JFXButton) layout.lookup("#renewApprovalBtn");
		manageUsersBtn = (JFXButton) layout.lookup("#manageUsersBtn");
		manageDocsBtn = (JFXButton) layout.lookup("#manageDocsBtn");

		this.api = api;
		scene = new Scene(layout);
		stage = new Stage();
		stage.setTitle("Librarian control panel");
		stage.setScene(scene);

		takeApproval = new TakeApproval(stage, scene, api);
		renewApproval = new RenewApproval(stage, scene, api);

		takeApprovalBtn.setOnAction(event -> takeApproval.show());
		renewApprovalBtn.setOnAction(event -> renewApproval.show());
	}

	public void show() {
		stage.show();
	}
}
