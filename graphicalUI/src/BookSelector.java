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

	private UserCredentials credentials;

	private String pickedBook;

	public BookSelector() {
	}

	public BookSelector(Stage primaryStage, Scene previousScene) throws IOException {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		this.bookSelectorLayout = FXMLLoader.load(getClass().getResource("BookSelector.fxml"));
		this.bookSelectorScene = new Scene(bookSelectorLayout);
	}

	public void show(UserCredentials credentials) {
		this.credentials = credentials;
		switchScene(primaryStage, bookSelectorScene);
		ListView<String> bookList = (ListView<String>) bookSelectorLayout.lookup("#bookList");

		Button goBackButton = (Button) bookSelectorLayout.lookup("#goBackButton");
		goBackButton.setOnAction(event -> {
			switchScene(primaryStage, previousScene);
			bookList.getItems().clear();
		});

		ObservableList<String> availableBooks = FXCollections.<String>observableArrayList("Touch of Class",
				"Introduction to Algorithms", "Algorithms in Java");
		bookList.getItems().addAll(availableBooks);

		Button bookThisButton = (Button) bookSelectorLayout.lookup("#bookThisButton");
		bookThisButton.setOnAction(event -> {
			if (credentials != null && credentials.isAuthorized()) {
				pickedBook = bookList.getSelectionModel().getSelectedItem();
				System.out.println("Given " + pickedBook + " to " + credentials.getLoginString()); //DEBUGGING OUTPUT
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
