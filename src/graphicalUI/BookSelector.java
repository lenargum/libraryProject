package graphicalUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import materials.Document;
import users.User;

import java.io.IOException;
import java.util.List;

/**
 * Controller for BookSelector layout.
 */
public class BookSelector {
	/**
	 * Main stage where selector is going to be displayed.
	 */
	@FXML
	private Stage primaryStage;

	/**
	 * Previous scene stored for "Go back" button.
	 */
	@FXML
	private Scene previousScene;
	/**
	 * Current scene.
	 */
	@FXML
	private Scene bookSelectorScene;
	/**
	 * Current layout.
	 */
	@FXML
	private AnchorPane bookSelectorLayout;

	/**
	 * Currnt user is stored to access booking system.
	 */
	private User user;

	/**
	 * Currently selected in list item.
	 */
	private String pickedItem;

	/**
	 * Empty constructor for FXML loader.
	 */
	public BookSelector() {
	}

	public BookSelector(Stage primaryStage, Scene previousScene) throws IOException {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		this.bookSelectorLayout = FXMLLoader.load(getClass().getResource("BookSelector.fxml"));
		this.bookSelectorScene = new Scene(bookSelectorLayout);
	}

	public void show(SelectorIntent intent, ServerAPI server) {
		//this.credentials = credentials;
		switchScene(primaryStage, bookSelectorScene);
		ListView<String> bookList = (ListView<String>) bookSelectorLayout.lookup("#bookList");

		Button goBackButton = (Button) bookSelectorLayout.lookup("#goBackButton");
		goBackButton.setOnAction(event -> {
			switchScene(primaryStage, previousScene);
			bookList.getItems().clear();
		});

		user = server.getPatron();
		ObservableList<String> availableItems;
		switch (intent) {
			case BOOK:
				availableItems = extractTitles(server.getDocuments());
				break;
			case DOCUMENT:
				availableItems = FXCollections.observableArrayList("Document 1", "Document 2", "Document 3");
				break;
			case AV:
				availableItems = FXCollections.observableArrayList("Audio 1", "Video 1", "Video 2", "Audio 2");
				break;
			case ARTICLE:
				availableItems = FXCollections.observableArrayList("Article 1", "Article 2", "Article 3");
				break;
			default:
				availableItems = FXCollections.emptyObservableList();
		}

		bookList.getItems().addAll(availableItems);

		Button bookThisButton = (Button) bookSelectorLayout.lookup("#bookThisButton");
		bookThisButton.setOnAction(event -> {
			if (user != null) {
				pickedItem = bookList.getSelectionModel().getSelectedItem();
				server.bookItem(pickedItem, server.getPatron());
				System.out.println("Given " + pickedItem + " to " + user); //DEBUGGING OUTPUT
			} else {
				Alert notAuthorizedAlert = new Alert(Alert.AlertType.WARNING);
				notAuthorizedAlert.setTitle("Authorization needed");
				notAuthorizedAlert.setHeaderText("You are not authorized");
				notAuthorizedAlert.setContentText("Please log in before ordering items.");
				notAuthorizedAlert.showAndWait();
			}
		});
	}

	private ObservableList<String> extractTitles(List<Document> documents) {
		ObservableList<String> titles = FXCollections.emptyObservableList();
		for (Document doc : documents) {
			titles.add(doc.getTitle());
		}

		return titles;
	}

	private void switchScene(Stage targetStage, Scene newScene) {
		targetStage.setScene(newScene);
		targetStage.show();
	}
}
