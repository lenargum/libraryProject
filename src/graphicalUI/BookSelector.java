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
	 * Current user is stored to access booking system.
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

	/**
	 * Main constructor for creating selector.
	 *
	 * @param primaryStage  Main stage.
	 * @param previousScene Previous scene.
	 * @throws IOException When FXMLLoader fails to find or load a layout file.
	 */
	public BookSelector(Stage primaryStage, Scene previousScene) throws IOException {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		this.bookSelectorLayout = (AnchorPane) FXMLLoader.load(getClass().getResource("BookSelector.fxml"));
		this.bookSelectorScene = new Scene(bookSelectorLayout);
	}

	/**
	 * Shows selector and starts event handling.
	 *
	 * @param intent Kind of document to show.
	 * @param server Currently connected server.
	 */
	public void show(SelectorIntent intent, ServerAPI server) {
		// Switches the scene
		switchScene(primaryStage, bookSelectorScene);

		String intentString;
		switch (intent) {
			case BOOK:
				intentString = "book";
				break;
			case ARTICLE:
				intentString = "article";
				break;
			case AUDIOVIDEO:
				intentString = "AV";
				break;
			default:
				intentString = "item";
				break;
		}

		// Pick list from layout
		ListView<String> bookList = (ListView<String>) bookSelectorLayout.lookup("#bookList");

		// Pick "Go back" button and set up event handler
		Button goBackButton = (Button) bookSelectorLayout.lookup("#goBackButton");
		goBackButton.setOnAction(event -> {
			// Go back to previous scene
			switchScene(primaryStage, previousScene);
			bookList.getItems().clear();
		});

		// Pick user
		user = server.getPatron();

		// Create list of available items
		ObservableList<String> availableItems;
		// Fill the list according to current intent
		switch (intent) {
			case BOOK:
				availableItems = extractTitles(server.getBooks());
				break;
			case AUDIOVIDEO:
				availableItems = extractTitles(server.getAVs());
				break;
			case ARTICLE:
				availableItems = extractTitles(server.getJournalArticles());
				break;
			default:
				availableItems = FXCollections.emptyObservableList();
		}

		// Fill the ListView
		bookList.getItems().addAll(availableItems);

		// Pick a "Book this" button and set up event handler
		Button bookThisButton = (Button) bookSelectorLayout.lookup("#bookThisButton");
		bookThisButton.setOnAction(event -> {
			// If authorization completed, try book a document
			if (user != null) {
				// Pick currently selected item
				pickedItem = bookList.getSelectionModel().getSelectedItem();

				// Book item
				if (server.bookItem(pickedItem, server.getPatron())) {
					System.out.println("Given " + pickedItem + " to " + user.getName()); //DEBUGGING OUTPUT
					Alert successfulAlert = new Alert(Alert.AlertType.INFORMATION);
					successfulAlert.setTitle("Success");
					successfulAlert.setHeaderText("You have a new " + intentString);
					successfulAlert.setContentText(pickedItem + " successfully added to your library.");
					successfulAlert.showAndWait();
				} else {
					System.out.println(user.getName() + " is not allowed to take " + pickedItem);
					Alert failAlert = new Alert(Alert.AlertType.ERROR);
					failAlert.setTitle("Failed");
					failAlert.setHeaderText("You are not allowed to take this " + intentString);
					failAlert.setContentText(pickedItem + " is not for you.");
					failAlert.showAndWait();
				}
			} else {
				// If not authorized, show an alert
				Alert notAuthorizedAlert = new Alert(Alert.AlertType.WARNING);
				notAuthorizedAlert.setTitle("Authorization needed");
				notAuthorizedAlert.setHeaderText("You are not authorized");
				notAuthorizedAlert.setContentText("Please log in before ordering items.");
				notAuthorizedAlert.showAndWait();
			}
		});
	}

	/**
	 * Extracts titles from document list.
	 * Used to fill the ListView.
	 *
	 * @param documents Documents list.
	 * @return Observable list of titles.
	 */
	private ObservableList<String> extractTitles(List<Document> documents) {
		ObservableList<String> titles = FXCollections.observableArrayList();
		for (Document doc : documents) {
			titles.add(doc.getTitle());
		}

		return titles;
	}

	/**
	 * Switches the scene.
	 *
	 * @param targetStage Target stage.
	 * @param newScene    New scene.
	 */
	private void switchScene(Stage targetStage, Scene newScene) {
		targetStage.setScene(newScene);
		targetStage.show();
	}
}
