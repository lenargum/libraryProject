package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Document manager window.
 *
 * @author Ruslan Shakirov
 */
public class DocumentManager {
	private CoreAPI api;
	private Stage stage;
	private Scene scene;
	private StackPane layout;

	// FXML bindings
	@FXML
	private JFXButton goBackBtn;
	@FXML
	private JFXTreeTableView<DocCell> docsTable;
	@FXML
	private JFXButton addDocBtn;

	public DocumentManager() {
	}

	/**
	 * Create new view.
	 *
	 * @param stage         Main stage.
	 * @param previousScene Previous scene.
	 * @param api           Core API.
	 */
	public DocumentManager(Stage stage, Scene previousScene, CoreAPI api) {
		this.stage = stage;
		this.api = api;

		try {
			layout = FXMLLoader.load(getClass().getResource("layout/DocumentManager.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene = new Scene(layout);

		goBackBtn = (JFXButton) layout.lookup("#goBackBtn");
		goBackBtn.setOnAction(event -> stage.setScene(previousScene));

		addDocBtn = (JFXButton) layout.lookup("#addDocBtn");

		docsTable = (JFXTreeTableView<DocCell>) layout.lookup("#docsTable");
		initDocTable();
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
	private void initDocTable() {
		JFXTreeTableColumn<DocCell, String> titles = new JFXTreeTableColumn<>("TITLE");
		titles.setPrefWidth(200);
		titles.setCellValueFactory(param -> param.getValue().getValue().title);

		JFXTreeTableColumn<DocCell, String> authors = new JFXTreeTableColumn<>("AUTHOR");
		authors.setPrefWidth(200);
		authors.setCellValueFactory(param -> param.getValue().getValue().author);

		JFXTreeTableColumn<DocCell, String> types = new JFXTreeTableColumn<>("TYPE");
		types.setPrefWidth(100);
		types.setCellValueFactory(param -> param.getValue().getValue().type);

		JFXTreeTableColumn<DocCell, Integer> numbersCopies = new JFXTreeTableColumn<>("COPIES");
		numbersCopies.setPrefWidth(50);
		numbersCopies.setCellValueFactory(param -> param.getValue().getValue().numOfCopies.asObject());

		JFXTreeTableColumn<DocCell, Boolean> allowances = new JFXTreeTableColumn<>("ALLOWED FOR STUDENTS");
		allowances.setPrefWidth(100);
		allowances.setCellValueFactory(param -> param.getValue().getValue().isAllowedForStudents);

		JFXTreeTableColumn<DocCell, Boolean> areReferences = new JFXTreeTableColumn<>("REFERENCE");
		areReferences.setPrefWidth(100);
		areReferences.setCellValueFactory(param -> param.getValue().getValue().isReference);

		final TreeItem<DocCell> tableRoot = new RecursiveTreeItem<>(api.getAllDocs(), RecursiveTreeObject::getChildren);
		docsTable.getColumns().setAll(titles, authors, types, numbersCopies, allowances, areReferences);
		docsTable.setRoot(tableRoot);
		docsTable.setShowRoot(false);
	}

	/**
	 * Table cell.
	 */
	public static class DocCell extends RecursiveTreeObject<DocCell> {
		int id;
		StringProperty title;
		StringProperty author;
		StringProperty type;
		IntegerProperty numOfCopies;
		BooleanProperty isAllowedForStudents;
		BooleanProperty isReference;

		public DocCell(int id, String title, String author, String type, Integer numOfCopies,
		               Boolean isAllowedForStudents, Boolean isReference) {
			this.id = id;
			this.title = new SimpleStringProperty(title);
			this.author = new SimpleStringProperty(author);
			this.type = new SimpleStringProperty(type);
			this.numOfCopies = new SimpleIntegerProperty(numOfCopies);
			this.isAllowedForStudents = new SimpleBooleanProperty(isAllowedForStudents);
			this.isReference = new SimpleBooleanProperty(isReference);
		}
	}
}
