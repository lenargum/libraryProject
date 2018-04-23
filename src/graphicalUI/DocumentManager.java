package graphicalUI;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import documents.Book;
import documents.JournalArticle;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;

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
		JFXPopup addDocPopup = new JFXPopup();
		VBox popupContainer = new VBox();

		JFXButton addBookBtn = new JFXButton("Add book");
		addBookBtn.setOnAction(event -> {
			addDocPopup.hide();
			showAddBookDialog();
		});

		JFXButton addArticleBtn = new JFXButton("Add article");
		addArticleBtn.setOnAction(event -> {
			addDocPopup.hide();
			showAddArticleDialog();
		});

		JFXButton addAVBtn = new JFXButton("Add audio/video");
		popupContainer.getChildren().addAll(addBookBtn, addArticleBtn, addAVBtn);
		addDocPopup.setPopupContent(popupContainer);
		popupContainer.setSpacing(5);
		popupContainer.setPadding(new Insets(5));
		addDocBtn.setOnAction(event -> {
			addDocPopup.show(addDocBtn);
		});

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

	private void showAddBookDialog() {
		JFXDialog addBookDialog = new JFXDialog();
		VBox dialogContainer = new VBox();
		dialogContainer.setSpacing(20);
		dialogContainer.setPadding(new Insets(20));

		Label addBook = new Label("Add book");
		addBook.setFont(new Font("Roboto", 26));

		JFXTextField titleField = new JFXTextField();
		titleField.setPromptText("Title");
		titleField.setLabelFloat(true);

		JFXTextField authorsField = new JFXTextField();
		authorsField.setPromptText("Authors");
		authorsField.setLabelFloat(true);

		JFXTextField publisherField = new JFXTextField();
		publisherField.setPromptText("Publisher");
		publisherField.setMinWidth(200);
		publisherField.setLabelFloat(true);

		JFXTextField editionField = new JFXTextField();
		editionField.setPromptText("Edition year");
		editionField.setLabelFloat(true);

		HBox publisherEdition = new HBox();
		publisherEdition.setSpacing(20);
		publisherEdition.getChildren().addAll(publisherField, editionField);

		JFXTextField keywordsField = new JFXTextField();
		keywordsField.setPromptText("Keywords");
		keywordsField.setLabelFloat(true);

		JFXCheckBox allowedForStudents = new JFXCheckBox("Allowed for students");
		JFXCheckBox isBestseller = new JFXCheckBox("Bestseller");
		JFXCheckBox isReference = new JFXCheckBox("Reference");

		JFXTextField countField = new JFXTextField();
		countField.setPromptText("Count");
		countField.setLabelFloat(true);

		JFXTextField priceField = new JFXTextField();
		priceField.setPromptText("Price");
		priceField.setLabelFloat(true);

		HBox countNPrice = new HBox();
		countNPrice.setSpacing(20);
		countNPrice.getChildren().addAll(countField, priceField);

		JFXButton addBtn = new JFXButton("ADD");
		addBtn.setFont(new Font("Roboto Bold", 16));

		addBtn.setOnAction(event -> {
			Book newBook = new Book(titleField.getText(), authorsField.getText(),
					allowedForStudents.isSelected(), Integer.parseInt(countField.getText()),
					isReference.isSelected(), Double.parseDouble(priceField.getText()),
					keywordsField.getText(), publisherField.getText(),
					Integer.parseInt(editionField.getText()), isBestseller.isSelected());
			api.addNewDocument(newBook);
			addBookDialog.close();
			initDocTable();
		});

		dialogContainer.getChildren().addAll(addBook,
				titleField, authorsField,
				publisherEdition, keywordsField,
				allowedForStudents, isBestseller, isReference,
				countNPrice, addBtn);

		addBookDialog.setContent(dialogContainer);
		addBookDialog.show(layout);
	}

	private void showAddArticleDialog() {
		JFXDialog addArticleDialog = new JFXDialog();
		VBox dialogContainer = new VBox();
		dialogContainer.setSpacing(20);
		dialogContainer.setPadding(new Insets(20));

		Label addArticle = new Label("Add article");
		addArticle.setFont(new Font("Roboto", 26));

		JFXTextField titleField = new JFXTextField();
		titleField.setPromptText("Title");
		titleField.setLabelFloat(true);

		JFXTextField authorsField = new JFXTextField();
		authorsField.setPromptText("Authors");
		authorsField.setLabelFloat(true);

		JFXTextField countField = new JFXTextField();
		countField.setPromptText("Count");
		countField.setLabelFloat(true);

		JFXTextField priceField = new JFXTextField();
		priceField.setPromptText("Price");
		priceField.setLabelFloat(true);

		JFXCheckBox allowedForStudents = new JFXCheckBox("Allowed for students");
		JFXCheckBox isReference = new JFXCheckBox("Reference");
		HBox checkboxes = new HBox();
		checkboxes.setSpacing(20);
		checkboxes.getChildren().addAll(allowedForStudents, isReference);

		HBox countNPrice = new HBox();
		countNPrice.setSpacing(20);
		countNPrice.getChildren().addAll(countField, priceField);

		JFXTextField keywordsField = new JFXTextField();
		keywordsField.setPromptText("Keywords");
		keywordsField.setLabelFloat(true);

		JFXTextField journalField = new JFXTextField();
		journalField.setPromptText("Journal");
		journalField.setLabelFloat(true);

		JFXTextField publisherField = new JFXTextField();
		publisherField.setPromptText("Publisher");
		publisherField.setMinWidth(200);
		publisherField.setLabelFloat(true);

		JFXTextField issueField = new JFXTextField();
		issueField.setPromptText("Issue");
		issueField.setLabelFloat(true);

		JFXTextField editorField = new JFXTextField();
		editorField.setPromptText("Editor");
		editorField.setLabelFloat(true);

		HBox issueEditor = new HBox();
		issueEditor.setSpacing(20);
		issueEditor.getChildren().addAll(issueField, editorField);

		JFXDatePicker dateField = new JFXDatePicker();

		JFXButton addBtn = new JFXButton("ADD");
		addBtn.setFont(new Font("Roboto Bold", 16));

		addBtn.setOnAction(event -> {
			Date date = Date.from(dateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			JournalArticle article = new JournalArticle(titleField.getText(), authorsField.getText(),
					allowedForStudents.isSelected(), Integer.parseInt(countField.getText()),
					isReference.isSelected(), Double.parseDouble(priceField.getText()),
					keywordsField.getText(), journalField.getText(),
					publisherField.getText(), issueField.getText(),
					editorField.getText(), date);
			initDocTable();
			api.addNewDocument(article);
			addArticleDialog.close();
			initDocTable();
		});

		dialogContainer.getChildren().addAll(addArticle, titleField,
				authorsField, journalField, publisherField,
				issueEditor, dateField, keywordsField,
				checkboxes, countNPrice, addBtn);
		addArticleDialog.setContent(dialogContainer);
		addArticleDialog.show(layout);
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
