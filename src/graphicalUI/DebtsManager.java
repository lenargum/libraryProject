package graphicalUI;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.svg.SVGGlyph;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Debts manager window.
 *
 * @author Ruslan Shakirov
 */
public class DebtsManager {
	private CoreAPI api;
	private AnchorPane layout;
	private Stage stage;
	private Scene scene;
	private JFXSnackbar snackbar;
	@FXML
	private JFXButton goBackBtn;
	@FXML
	private JFXTreeTableView<DebtCell> debtsTable;

	public DebtsManager() {
	}

	/**
	 * Create new view.
	 *
	 * @param stage         Main stage.
	 * @param previousScene Previous scene.
	 * @param api           Core API.
	 */
	public DebtsManager(Stage stage, Scene previousScene, CoreAPI api) {
		this.stage = stage;
		this.api = api;
		try {
			layout = FXMLLoader.load(getClass().getResource("layout/DebtsManager.fxml"));
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

		debtsTable = (JFXTreeTableView<DebtCell>) layout.lookup("#debtsTable");
		initDebtsList();

		snackbar = new JFXSnackbar(layout);
	}

	/**
	 * Show view.
	 */
	public void show() {
		stage.setScene(scene);
	}

	/**
	 * Initialize table.
	 */
	private void initDebtsList() {
		JFXTreeTableColumn<DebtCell, Integer> ids = new JFXTreeTableColumn<>("ID");
		ids.setPrefWidth(50);
		ids.setCellValueFactory(param -> param.getValue().getValue().debtID.asObject());

		JFXTreeTableColumn<DebtCell, String> names = new JFXTreeTableColumn<>("USER");
		names.setPrefWidth(150);
		names.setCellValueFactory(param -> param.getValue().getValue().userName);

		JFXTreeTableColumn<DebtCell, String> titles = new JFXTreeTableColumn<>("DOCUMENT");
		titles.setPrefWidth(150);
		titles.setCellValueFactory(param -> param.getValue().getValue().documentTitle);

		JFXTreeTableColumn<DebtCell, String> datesTookes = new JFXTreeTableColumn<>("DATE TOOK");
		datesTookes.setPrefWidth(100);
		datesTookes.setCellValueFactory(param -> param.getValue().getValue().dateTook);

		JFXTreeTableColumn<DebtCell, String> datesReturnes = new JFXTreeTableColumn<>("RETURN DATE");
		datesReturnes.setPrefWidth(100);
		datesReturnes.setCellValueFactory(param -> param.getValue().getValue().dateReturn);

		final TreeItem<DebtCell> tableRoot = new RecursiveTreeItem<>(api.getAllDebts(), RecursiveTreeObject::getChildren);
		debtsTable.getColumns().setAll(ids, names, titles, datesTookes, datesReturnes);
		debtsTable.setRoot(tableRoot);
		debtsTable.setShowRoot(false);

		debtsTable.setOnContextMenuRequested(this::onTableClicked);
	}

	/**
	 * Action on table clicking.
	 *
	 * @param event Mouse event.
	 */
	private void onTableClicked(ContextMenuEvent event) {
		DebtCell selected;
		try {
			selected = debtsTable.getSelectionModel().getSelectedItem().getValue();
		} catch (NullPointerException e) {
			return;
		}
		JFXButton outstandingBtn = new JFXButton("Outstanding request");
		JFXButton confirmReturnBtn = new JFXButton("Confirm return");
		JFXPopup popup = new JFXPopup();
		outstandingBtn.setOnAction(event1 -> {
			api.makeOutstandingRequest(selected.userID, selected.docID);
			popup.hide();
			initDebtsList();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Outstanding request sent"));
		});
		confirmReturnBtn.setOnAction(event1 -> {
			api.confirmReturn(selected.debtID.get());
			popup.hide();
			initDebtsList();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Return confirmed"));
		});

		VBox container = new VBox();
		container.getChildren().addAll(outstandingBtn, confirmReturnBtn);
		container.setPadding(new Insets(5));
		popup.setPopupContent(container);
		popup.show(debtsTable, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, event.getX() - 600, event.getY());
	}

	/**
	 * Table cell.
	 */
	public static class DebtCell extends RecursiveTreeObject<DebtCell> {
		int userID, docID;
		IntegerProperty debtID;
		StringProperty userName;
		StringProperty documentTitle;
		StringProperty dateTook;
		StringProperty dateReturn;

		public DebtCell(Integer debtID, String userName, int userID, String documentTitle, int docID, String dateTook, String dateReturn) {
			this.userID = userID;
			this.docID = docID;
			this.debtID = new SimpleIntegerProperty(debtID);
			this.userName = new SimpleStringProperty(userName);
			this.documentTitle = new SimpleStringProperty(documentTitle);
			this.dateTook = new SimpleStringProperty(dateTook);
			this.dateReturn = new SimpleStringProperty(dateReturn);
		}
	}
}
