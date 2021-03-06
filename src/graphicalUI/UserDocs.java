package graphicalUI;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * User docs and waitlist view.
 *
 * @author Ruslan Shakirov
 */
public class UserDocs {
	private CoreAPI api;
	private Stage stage;
	private Scene scene;
	private AnchorPane layout;
	@FXML
	private JFXTabPane tabPane;
	private JFXTreeTableView<MyDocsView> myDocsTable;
	private JFXTreeTableView<WaitlistView> waitlistTable;

	public UserDocs() {
	}

	/**
	 * Create new view.
	 *
	 * @param api Core API.
	 */
	public UserDocs(CoreAPI api) {
		this.api = api;
		try {
			layout = FXMLLoader.load(getClass().getResource("layout/UserDocs.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		tabPane = (JFXTabPane) layout.lookup("#tabPane");

		myDocsTable = new JFXTreeTableView<>();
		initMyDocsTable();

		waitlistTable = new JFXTreeTableView<>();
		initWaitlistTable();

		myDocsTable.setPadding(new Insets(50));
		tabPane.getTabs().get(0).setContent(myDocsTable);
		waitlistTable.setPadding(new Insets(50));
		tabPane.getTabs().get(1).setContent(waitlistTable);

		myDocsTable.setOnMouseClicked(this::onDocsTableClicked);

		scene = new Scene(layout);
		stage = new Stage();
		stage.setTitle("Your documents (" + api.getUser().getName() + ")");
		stage.setScene(scene);
	}

	/**
	 * Show view.
	 */
	public void show() {
		stage.show();
	}

	/**
	 * Initialize documents table view.
	 */
	private void initMyDocsTable() {
		JFXTreeTableColumn<MyDocsView, String> docTitles = new JFXTreeTableColumn<>("TITLE");
		docTitles.setPrefWidth(300);
		docTitles.setCellValueFactory(param -> param.getValue().getValue().docTitle);

		JFXTreeTableColumn<MyDocsView, Integer> daysLeft = new JFXTreeTableColumn<>("DAYS LEFT");
		daysLeft.setPrefWidth(200);
		daysLeft.setCellValueFactory(param -> param.getValue().getValue().daysLeft.asObject());

		final TreeItem<MyDocsView> tableRoot = new RecursiveTreeItem<>(api.getUserDocs(), RecursiveTreeObject::getChildren);
		myDocsTable.getColumns().setAll(docTitles, daysLeft);
		myDocsTable.setRoot(tableRoot);
		myDocsTable.setShowRoot(false);
	}

	/**
	 * Initialize waitlist table view.
	 */
	private void initWaitlistTable() {
		JFXTreeTableColumn<WaitlistView, String> docTitles = new JFXTreeTableColumn<>("TITLE");
		docTitles.setPrefWidth(300);
		docTitles.setCellValueFactory(param -> param.getValue().getValue().docTitle);

		JFXTreeTableColumn<WaitlistView, Integer> peopleWaiting = new JFXTreeTableColumn<>("PEOPLE WAITING");
		peopleWaiting.setPrefWidth(200);
		peopleWaiting.setCellValueFactory(param -> param.getValue().getValue().peopleWaiting.asObject());

		final TreeItem<WaitlistView> tableRoot = new RecursiveTreeItem<>(api.getWaitList(), RecursiveTreeObject::getChildren);
		waitlistTable.getColumns().setAll(docTitles, peopleWaiting);
		waitlistTable.setRoot(tableRoot);
		waitlistTable.setShowRoot(false);
	}

	/**
	 * Action on documents table clicked.
	 *
	 * @param event Mouse event.
	 */
	private void onDocsTableClicked(MouseEvent event) {
		MyDocsView selected;
		try {
			selected = myDocsTable.getSelectionModel().getSelectedItem().getValue();
		} catch (NullPointerException e) {
			return;
		}

		DocumentPopup popup = new DocumentPopup(selected.docTitle.getValue(), "Author", selected.debtID);
		popup.setOnRenewAction(event1 -> {
			api.makeRenewRequest(selected.debtID);
			JFXSnackbar snackbar = new JFXSnackbar(layout);
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Request submitted"));
			popup.hide();
		});
		popup.setOnReturnAction(event1 -> {
			JFXSnackbar snackbar = new JFXSnackbar(layout);
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("To return book, please go to your company's library"));
			popup.hide();
		});
		popup.show(myDocsTable, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, event.getX() - 600, event.getY());
	}

	/**
	 * Action on waitlist table clicked.
	 *
	 * @param event Mouse event.
	 */
	private void onWaitlistClicked(MouseEvent event) {
		WaitlistView selected = waitlistTable.getSelectionModel().getSelectedItem().getValue();
	}

	/**
	 * Waitlist table cell.
	 */
	public static class WaitlistView extends RecursiveTreeObject<WaitlistView> {
		int id;
		StringProperty docTitle;
		IntegerProperty peopleWaiting;

		public WaitlistView(String docTitle, Integer peopleWaiting, int id) {
			this.id = id;
			this.docTitle = new SimpleStringProperty(docTitle);
			this.peopleWaiting = new SimpleIntegerProperty(peopleWaiting);
		}
	}

	/**
	 * User documents table cell.
	 */
	public static class MyDocsView extends RecursiveTreeObject<MyDocsView> {
		int debtID;
		StringProperty docTitle;
		IntegerProperty daysLeft;

		public MyDocsView(String docTitle, Integer daysLeft, int debtID) {
			this.debtID = debtID;
			this.docTitle = new SimpleStringProperty(docTitle);
			this.daysLeft = new SimpleIntegerProperty(daysLeft);
		}
	}
}
