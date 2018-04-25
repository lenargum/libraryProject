package graphicalUI;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import users.Admin;

import java.io.IOException;

/**
 * Librarian panel window.
 *
 * @author Ruslan Shakirov
 */
public class ControlPanel {
	private AnchorPane layout;
	private Stage stage;
	private TakeApproval takeApproval;
	private RenewApproval renewApproval;
	private DebtsManager debtsManager;
	private UserManager userManager;
	private DocumentManager documentManager;
	private LogsView logsView;

	// FXML bindings
	@FXML
	private JFXButton takeApprovalBtn;
	@FXML
	private JFXButton renewApprovalBtn;
	@FXML
	private JFXButton manageUsersBtn;
	@FXML
	private JFXButton manageDocsBtn;
	@FXML
	private JFXButton manageDebtsBtn;
	@FXML
	private JFXButton viewLogsBtn;
	@FXML
	private Text logsLabel;

	public ControlPanel() {
	}

	/**
	 * Create new view.
	 *
	 * @param api Core API.
	 */
	public ControlPanel(CoreAPI api) {
		try {
			layout = FXMLLoader.load(getClass().getResource("layout/ControlPanel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		takeApprovalBtn = (JFXButton) layout.lookup("#takeApprovalBtn");
		renewApprovalBtn = (JFXButton) layout.lookup("#renewApprovalBtn");
		manageUsersBtn = (JFXButton) layout.lookup("#manageUsersBtn");
		manageDocsBtn = (JFXButton) layout.lookup("#manageDocsBtn");
		manageDebtsBtn = (JFXButton) layout.lookup("#manageDebtsBtn");
		viewLogsBtn = (JFXButton) layout.lookup("#viewLogsBtn");
		logsLabel = (Text) layout.lookup("#logsLabel");

		Scene scene = new Scene(layout);
		stage = new Stage();
		stage.setTitle("Control panel ― InLibrary Manager ("
				+ api.getUser().getName() + ")");
		stage.setScene(scene);

		takeApproval = new TakeApproval(stage, scene, api);
		renewApproval = new RenewApproval(stage, scene, api);
		debtsManager = new DebtsManager(stage, scene, api);
		userManager = new UserManager(stage, scene, api);
		documentManager = new DocumentManager(stage, scene, api);
		logsView = new LogsView(stage, scene, api);

		takeApprovalBtn.setOnAction(event -> takeApproval.show());
		renewApprovalBtn.setOnAction(event -> renewApproval.show());
		manageDebtsBtn.setOnAction(event -> debtsManager.show());
		manageUsersBtn.setOnAction(event -> userManager.show());
		manageDocsBtn.setOnAction(event -> documentManager.show());

		if (api.getUser() instanceof Admin) {
			viewLogsBtn.setOnAction(event -> logsView.show());
			viewLogsBtn.setVisible(true);
			viewLogsBtn.setDisable(false);
			logsLabel.setVisible(true);
		}
	}

	/**
	 * Show view.
	 */
	public void show() {
		stage.show();
	}
}
