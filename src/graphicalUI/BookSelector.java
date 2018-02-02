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
import users.User;

import java.io.IOException;

public class BookSelector {
	@FXML
	private Stage primaryStage;
	@FXML
	private Scene previousScene;
	@FXML
	private Scene bookSelectorScene;
	@FXML
	private AnchorPane bookSelectorLayout;

	private User user;

	private String pickedItem;

	public BookSelector() {
	}

	public BookSelector(Stage primaryStage, Scene previousScene) throws IOException {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		this.bookSelectorLayout = FXMLLoader.load(getClass().getResource("BookSelector.fxml"));
		this.bookSelectorScene = new Scene(bookSelectorLayout);
	}

	public void show(SelectorIntent intent, User user) {
		this.user = user;
		switchScene(primaryStage, bookSelectorScene);
		ListView<String> bookList = (ListView<String>) bookSelectorLayout.lookup("#bookList");

		Button goBackButton = (Button) bookSelectorLayout.lookup("#goBackButton");
		goBackButton.setOnAction(event -> {
			switchScene(primaryStage, previousScene);
			bookList.getItems().clear();
		});

		ObservableList<String> availableItems;
		switch (intent) {
			case BOOK:
				availableItems = FXCollections.observableArrayList("Touch of Class",
						"Introduction to Algorithms", "Algorithms in Java");
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
				System.out.println("Given " + pickedItem + " to " + user.getName()); //DEBUGGING OUTPUT
			} else {
				Alert notAuthorizedAlert = new Alert(Alert.AlertType.WARNING);
				notAuthorizedAlert.setTitle("Authorization needed");
				notAuthorizedAlert.setHeaderText("You are not authorized");
				notAuthorizedAlert.setContentText("Please log in before ordering items.");
				notAuthorizedAlert.showAndWait();
			}
		});
	}

	private void switchScene(Stage targetStage, Scene newScene) {
		targetStage.setScene(newScene);
		targetStage.show();
	}
}
