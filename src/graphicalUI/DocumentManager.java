package graphicalUI;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.svg.SVGGlyph;
import documents.AudioVideoMaterial;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import users.Admin;
import users.Librarian;

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
	private JFXSnackbar snackbar;

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
		goBackBtn.setText("");
		SVGGlyph goBackGraphic = Glyphs.ARROW_BACK();
		goBackGraphic.setSize(20, 20);
		goBackGraphic.setFill(Paint.valueOf("#8d8d8d"));
		goBackBtn.setGraphic(goBackGraphic);
		goBackBtn.setOnAction(event -> stage.setScene(previousScene));

		if (api.getUser() instanceof Librarian && ((Librarian) api.getUser()).getPrivilege() >= 2) {
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
			addAVBtn.setOnAction(event -> {
				addDocPopup.hide();
				showAddAVDialog();
			});

			popupContainer.getChildren().addAll(addBookBtn, addArticleBtn, addAVBtn);
			addDocPopup.setPopupContent(popupContainer);
			popupContainer.setSpacing(5);
			popupContainer.setPadding(new Insets(5));
			addDocBtn.setOnAction(event -> {
				addDocPopup.show(addDocBtn);
			});

			addDocBtn.setDisable(false);
		}

		docsTable = (JFXTreeTableView<DocCell>) layout.lookup("#docsTable");
		initDocTable();

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

		if (api.getUser() instanceof Librarian && ((Librarian) api.getUser()).getPrivilege() >= 1) {
			docsTable.setOnMouseClicked(event -> {
				try {
					DocCell selected = docsTable.getSelectionModel().getSelectedItem().getValue();
					if (selected == null) return;

					switch (selected.type.getValue()) {
						case "Book":
							showEditBookDialog(selected);
							break;
						case "JournalArticle":
							showEditArticleDialog(selected);
							break;
						case "AudioVideoMaterial":
							showEditAVDialog(selected);
							break;
					}
				} catch (NullPointerException ignored) {
				}
			});
		}
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
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Added " + titleField.getText()));
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
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Added " + titleField.getText()));
		});

		dialogContainer.getChildren().addAll(addArticle, titleField,
				authorsField, journalField, publisherField,
				issueEditor, dateField, keywordsField,
				checkboxes, countNPrice, addBtn);
		addArticleDialog.setContent(dialogContainer);
		addArticleDialog.show(layout);
	}

	private void showAddAVDialog() {
		JFXDialog addBookDialog = new JFXDialog();
		VBox dialogContainer = new VBox();
		dialogContainer.setSpacing(20);
		dialogContainer.setPadding(new Insets(20));

		Label addAV = new Label("Add audio/video");
		addAV.setFont(new Font("Roboto", 26));

		JFXTextField titleField = new JFXTextField();
		titleField.setPromptText("Title");
		titleField.setLabelFloat(true);

		JFXTextField authorsField = new JFXTextField();
		authorsField.setPromptText("Authors");
		authorsField.setLabelFloat(true);

		JFXTextField keywordsField = new JFXTextField();
		keywordsField.setPromptText("Keywords");
		keywordsField.setLabelFloat(true);

		JFXTextField countField = new JFXTextField();
		countField.setPromptText("Count");
		countField.setLabelFloat(true);

		JFXTextField priceField = new JFXTextField();
		priceField.setPromptText("Price");
		priceField.setLabelFloat(true);

		HBox countNPrice = new HBox();
		countNPrice.setSpacing(20);
		countNPrice.getChildren().addAll(countField, priceField);

		JFXCheckBox allowedForStudents = new JFXCheckBox("Allowed for students");
		JFXCheckBox isReference = new JFXCheckBox("Reference");
		HBox checkboxes = new HBox();
		checkboxes.setSpacing(20);
		checkboxes.getChildren().addAll(allowedForStudents, isReference);

		JFXButton addBtn = new JFXButton("ADD");
		addBtn.setFont(new Font("Roboto Bold", 16));

		addBtn.setOnAction(event -> {
			AudioVideoMaterial newAv = new AudioVideoMaterial(titleField.getText(),
					authorsField.getText(), allowedForStudents.isSelected(),
					Integer.parseInt(countField.getText()), isReference.isSelected(),
					Double.parseDouble(priceField.getText()), keywordsField.getText());
			api.addNewDocument(newAv);
			addBookDialog.close();
			initDocTable();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Added " + titleField.getText()));
		});

		dialogContainer.getChildren().addAll(addAV, titleField, authorsField,
				keywordsField, checkboxes, countNPrice, addBtn);
		addBookDialog.setContent(dialogContainer);
		addBookDialog.show(layout);
	}

	private void showEditBookDialog(DocCell selected) {
		JFXDialog editBookDialog = new JFXDialog();
		VBox dialogContainer = new VBox();
		dialogContainer.setSpacing(20);
		dialogContainer.setPadding(new Insets(20));

		Label editBook = new Label("Edit book");
		editBook.setFont(new Font("Roboto", 26));

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

		JFXButton saveBtn = new JFXButton("SAVE");
		saveBtn.setFont(new Font("Roboto Bold", 16));
		saveBtn.setOnAction(event -> {
			api.editDocument(selected.id, "name", titleField.getText());
			api.editDocument(selected.id, "authors", authorsField.getText());
			api.editDocument(selected.id, "publisher", publisherField.getText());
			api.editDocument(selected.id, "edition", editionField.getText());
			api.editDocument(selected.id, "keywords", keywordsField.getText());
			api.editDocument(selected.id, "is_allowed_for_students",
					allowedForStudents.isSelected() ? "true" : "false");
			api.editDocument(selected.id, "bestseller",
					isBestseller.isSelected() ? "true" : "false");
			api.editDocument(selected.id, "is_referense",
					isReference.isSelected() ? "true" : "false");
			api.editDocument(selected.id, "num_of_copies",
					countField.getText());
			api.editDocument(selected.id, "price",
					priceField.getText());

			initDocTable();
			editBookDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Saved " + titleField.getText()));
		});

		JFXButton deleteBtn = new JFXButton("DELETE");
		deleteBtn.setDisable(true);
		deleteBtn.setFont(new Font("Roboto Bold", 16));
		deleteBtn.setTextFill(Paint.valueOf("#e53935"));
		if (api.getUser() instanceof Admin ||
				((Librarian) api.getUser()).getPrivilege() >= 3) {
			deleteBtn.setDisable(false);
		}
		deleteBtn.setOnAction(event -> {
			api.deleteDocument(selected.id);
			initDocTable();
			editBookDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Deleted " + selected.title.getValue()));
		});

		JFXButton unoutstandBtn = new JFXButton("UN-OUTSTAND");
		unoutstandBtn.setFont(new Font("Roboto Bold", 16));
		unoutstandBtn.setTextFill(Paint.valueOf("#43a047"));
		if (!api.getDocumentByID(selected.id).isUnderOutstandingRequest()) {
			unoutstandBtn.setDisable(true);
		}
		unoutstandBtn.setOnAction(event -> {
			api.unoutstandDocument(selected.id);
			editBookDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Outstanding request removed for " +
					selected.title.getValue()));
		});

		HBox buttons = new HBox();
		buttons.getChildren().addAll(saveBtn, deleteBtn, unoutstandBtn);
		buttons.setSpacing(20);

		Book found = (Book) api.getDocumentByID(selected.id);
		titleField.setText(found.getTitle());
		authorsField.setText(found.getAuthors());
		publisherField.setText(found.getPublisher());
		editionField.setText(String.valueOf(found.getEdition()));
		keywordsField.setText(found.getKeyWords());
		allowedForStudents.setSelected(found.isAllowedForStudents());
		isBestseller.setSelected(found.isBestseller());
		isReference.setSelected(found.isReference());
		countField.setText(String.valueOf(found.getNumberOfCopies()));
		priceField.setText(String.valueOf(found.getPrice()));

		dialogContainer.getChildren().addAll(editBook,
				titleField, authorsField,
				publisherEdition, keywordsField,
				allowedForStudents, isBestseller,
				isReference, countNPrice, buttons);

		editBookDialog.setContent(dialogContainer);
		editBookDialog.setTransitionType(JFXDialog.DialogTransition.TOP);
		editBookDialog.show(layout);
	}

	private void showEditArticleDialog(DocCell selected) {
		JFXDialog editArticleDialog = new JFXDialog();
		VBox dialogContainer = new VBox();
		dialogContainer.setSpacing(20);
		dialogContainer.setPadding(new Insets(20));

		Label editArticle = new Label("Edit article");
		editArticle.setFont(new Font("Roboto", 26));

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

		HBox countNPrice = new HBox();
		countNPrice.setSpacing(20);
		countNPrice.getChildren().addAll(countField, priceField);

		JFXCheckBox allowedForStudents = new JFXCheckBox("Allowed for students");
		JFXCheckBox isReference = new JFXCheckBox("Reference");
		HBox checkboxes = new HBox();
		checkboxes.setSpacing(20);
		checkboxes.getChildren().addAll(allowedForStudents, isReference);

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

		JournalArticle found = (JournalArticle) api.getDocumentByID(selected.id);
		titleField.setText(found.getTitle());
		authorsField.setText(found.getAuthors());
		countField.setText(String.valueOf(found.getNumberOfCopies()));
		priceField.setText(String.valueOf(found.getPrice()));
		allowedForStudents.setSelected(found.isAllowedForStudents());
		isReference.setSelected(found.isReference());
		keywordsField.setText(found.getKeyWords());
		journalField.setText(found.getJournalName());
		publisherField.setText(found.getPublisher());
		issueField.setText(found.getIssue());
		editorField.setText(found.getEditor());
		dateField.setValue(found.getPublicationDate().toInstant()
				.atZone(ZoneId.systemDefault()).toLocalDate());

		JFXButton saveBtn = new JFXButton("SAVE");
		saveBtn.setFont(new Font("Roboto Bold", 16));

		JFXButton deleteBtn = new JFXButton("DELETE");
		deleteBtn.setDisable(true);
		deleteBtn.setFont(new Font("Roboto Bold", 16));
		deleteBtn.setTextFill(Paint.valueOf("#e53935"));
		if (api.getUser() instanceof Admin ||
				((Librarian) api.getUser()).getPrivilege() >= 3) {
			deleteBtn.setDisable(false);
		}

		JFXButton unoutstandBtn = new JFXButton("UN-OUTSTAND");
		unoutstandBtn.setFont(new Font("Roboto Bold", 16));
		unoutstandBtn.setTextFill(Paint.valueOf("#43a047"));
		if (!api.getDocumentByID(selected.id).isUnderOutstandingRequest()) {
			unoutstandBtn.setDisable(true);
		}
		unoutstandBtn.setOnAction(event -> {
			api.unoutstandDocument(selected.id);
			editArticleDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Outstanding request removed for " +
					selected.title.getValue()));
		});

		HBox buttons = new HBox();
		buttons.setSpacing(20);
		buttons.getChildren().addAll(saveBtn, deleteBtn, unoutstandBtn);

		saveBtn.setOnAction(event -> {
			api.editDocument(selected.id, "name", titleField.getText());
			api.editDocument(selected.id, "authors", authorsField.getText());
			api.editDocument(selected.id, "num_of_copies", countField.getText());
			api.editDocument(selected.id, "price", priceField.getText());
			api.editDocument(selected.id, "is_allowed_for_students",
					allowedForStudents.isSelected() ? "true" : "false");
			api.editDocument(selected.id, "is_reference",
					isReference.isSelected() ? "true" : "false");
			api.editDocument(selected.id, "keywords", keywordsField.getText());
			api.editDocument(selected.id, "journal_name", journalField.getText());
			api.editDocument(selected.id, "publisher", publisherField.getText());
			api.editDocument(selected.id, "issue", issueField.getText());
			api.editDocument(selected.id, "editor", editorField.getText());
			//api.editDocument(selected.debtID, "publication_date", dateField.getValue());
			initDocTable();
			editArticleDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Saved " + titleField.getText()));
		});

		deleteBtn.setOnAction(event -> {
			api.deleteDocument(selected.id);
			initDocTable();
			editArticleDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Deleted " + selected.title.getValue()));
		});

		dialogContainer.getChildren().addAll(editArticle, titleField,
				authorsField, journalField, publisherField,
				issueEditor, dateField, keywordsField,
				checkboxes, countNPrice, buttons);

		editArticleDialog.setContent(dialogContainer);
		editArticleDialog.setTransitionType(JFXDialog.DialogTransition.TOP);
		editArticleDialog.show(layout);
	}

	private void showEditAVDialog(DocCell selected) {
		JFXDialog editAVDialog = new JFXDialog();
		VBox dialogContainer = new VBox();
		dialogContainer.setSpacing(20);
		dialogContainer.setPadding(new Insets(20));

		Label editAV = new Label("Edit audio/video");
		editAV.setFont(new Font("Roboto", 26));

		JFXTextField titleField = new JFXTextField();
		titleField.setPromptText("Title");
		titleField.setLabelFloat(true);

		JFXTextField authorsField = new JFXTextField();
		authorsField.setPromptText("Authors");
		authorsField.setLabelFloat(true);

		JFXTextField keywordsField = new JFXTextField();
		keywordsField.setPromptText("Keywords");
		keywordsField.setLabelFloat(true);

		JFXTextField countField = new JFXTextField();
		countField.setPromptText("Count");
		countField.setLabelFloat(true);

		JFXTextField priceField = new JFXTextField();
		priceField.setPromptText("Price");
		priceField.setLabelFloat(true);

		HBox countNPrice = new HBox();
		countNPrice.setSpacing(20);
		countNPrice.getChildren().addAll(countField, priceField);

		JFXCheckBox allowedForStudents = new JFXCheckBox("Allowed for students");
		JFXCheckBox isReference = new JFXCheckBox("Reference");
		HBox checkboxes = new HBox();
		checkboxes.setSpacing(20);
		checkboxes.getChildren().addAll(allowedForStudents, isReference);

		JFXButton saveBtn = new JFXButton("SAVE");
		saveBtn.setFont(new Font("Roboto Bold", 16));
		saveBtn.setOnAction(event -> {
			api.editDocument(selected.id, "name", titleField.getText());
			api.editDocument(selected.id, "authors", authorsField.getText());
			api.editDocument(selected.id, "keywords", keywordsField.getText());
			api.editDocument(selected.id, "num_of_copies", countField.getText());
			api.editDocument(selected.id, "price", priceField.getText());
			api.editDocument(selected.id, "is_allowed_for_students",
					allowedForStudents.isSelected() ? "true" : "false");
			api.editDocument(selected.id, "is_reference",
					isReference.isSelected() ? "true" : "false");
			initDocTable();
			editAVDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Saved " + titleField.getText()));
		});

		JFXButton deleteBtn = new JFXButton("DELETE");
		deleteBtn.setDisable(true);
		deleteBtn.setFont(new Font("Roboto Bold", 16));
		deleteBtn.setTextFill(Paint.valueOf("#e53935"));
		if (api.getUser() instanceof Admin ||
				((Librarian) api.getUser()).getPrivilege() >= 3) {
			deleteBtn.setDisable(false);
		}
		deleteBtn.setOnAction(event -> {
			api.deleteDocument(selected.id);
			initDocTable();
			editAVDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Deleted " + selected.title.getValue()));
		});

		JFXButton unoutstandBtn = new JFXButton("UN-OUTSTAND");
		unoutstandBtn.setFont(new Font("Roboto Bold", 16));
		unoutstandBtn.setTextFill(Paint.valueOf("#43a047"));
		if (!api.getDocumentByID(selected.id).isUnderOutstandingRequest()) {
			unoutstandBtn.setDisable(true);
		}
		unoutstandBtn.setOnAction(event -> {
			api.unoutstandDocument(selected.id);
			editAVDialog.close();
			snackbar.enqueue(new JFXSnackbar.SnackbarEvent("Outstanding request removed for " +
					selected.title.getValue()));
		});

		HBox buttons = new HBox();
		buttons.setSpacing(20);
		buttons.getChildren().addAll(saveBtn, deleteBtn, unoutstandBtn);

		AudioVideoMaterial found = (AudioVideoMaterial) api.getDocumentByID(selected.id);
		titleField.setText(found.getTitle());
		authorsField.setText(found.getAuthors());
		keywordsField.setText(found.getKeyWords());
		countField.setText(String.valueOf(found.getNumberOfCopies()));
		priceField.setText(String.valueOf(found.getPrice()));
		allowedForStudents.setSelected(found.isAllowedForStudents());
		isReference.setSelected(found.isReference());

		dialogContainer.getChildren().addAll(editAV, titleField, authorsField,
				keywordsField, checkboxes, countNPrice, buttons);
		editAVDialog.setContent(dialogContainer);
		editAVDialog.setTransitionType(JFXDialog.DialogTransition.TOP);
		editAVDialog.show(layout);
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
