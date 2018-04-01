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

	public UserDocs(CoreAPI api) {
		this.api = api;
		try {
			layout = FXMLLoader.load(getClass().getResource("UserDocs.fxml"));
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
		stage.setTitle("Your documents");
		stage.setScene(scene);
	}

	public void show() {
		stage.show();
	}

	private void initMyDocsTable() {
		JFXTreeTableColumn<MyDocsView, String> docTitles = new JFXTreeTableColumn<>("TITLE");
		docTitles.setPrefWidth(300);
		docTitles.setCellValueFactory(param -> param.getValue().getValue().docTitle);

		JFXTreeTableColumn<MyDocsView, Integer> daysLeft = new JFXTreeTableColumn<>("DAYS LEFT");
		daysLeft.setPrefWidth(200);
		daysLeft.setCellValueFactory(param -> param.getValue().getValue().daysLeft.asObject());

		final TreeItem<MyDocsView> tableRoot = new RecursiveTreeItem<>(api.getUserBooks(), RecursiveTreeObject::getChildren);
		myDocsTable.getColumns().setAll(docTitles, daysLeft);
		myDocsTable.setRoot(tableRoot);
		myDocsTable.setShowRoot(false);
	}

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

	private void onDocsTableClicked(MouseEvent event) {
		MyDocsView selected = myDocsTable.getSelectionModel().getSelectedItem().getValue();
		DocumentPopup popup = new DocumentPopup(selected.docTitle.getValue(), "Author", selected.id);
		popup.show(myDocsTable, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, event.getX() - 600, event.getY());
	}

	private void onWaitlistClicked() {

	}

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

	public static class MyDocsView extends RecursiveTreeObject<MyDocsView> {
		int id;
		StringProperty docTitle;
		IntegerProperty daysLeft;

		public MyDocsView(String docTitle, Integer daysLeft, int id) {
			this.id = id;
			this.docTitle = new SimpleStringProperty(docTitle);
			this.daysLeft = new SimpleIntegerProperty(daysLeft);
		}
	}
}
