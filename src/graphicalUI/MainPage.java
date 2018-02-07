package graphicalUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import materials.Document;

import java.io.IOException;
import java.util.List;

public class MainPage extends Application {

	private boolean loggedIn;
	private UserCredentials credentials;
	private ServerAPI server;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		// Initialize variables
		loggedIn = false;
		server = new ServerAPI();

		/*
		Load the main page layout,
		set title and minimal window size.
		*/
		AnchorPane welcomeLayout = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		primaryStage.setTitle("InLibrary Manager");
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(600);

		// Create new scene, set up stage and show it.
		Scene welcomeScene = new Scene(welcomeLayout, 1024, 768);
		primaryStage.setScene(welcomeScene);
		primaryStage.show();

		// Pick a list of documents user currently have
		ListView<String> yourDocs = (ListView<String>) welcomeLayout.lookup("#yourDocList");

		/*
		Pick login button, load login layout
		and create login scene.
		*/
		Button loginButton = (Button) welcomeLayout.lookup("#loginButton");
		AnchorPane loginLayout = FXMLLoader.load(getClass().getResource("LoginDialog.fxml"));
		Scene loginScene = new Scene(loginLayout);
		loginButton.setOnAction(event -> {
			switchScene(primaryStage, loginScene);
		});

		/*
		Pick navigation button on login layout
		and set up event handler.
		*/
		Button goBackButton = (Button) loginLayout.lookup("#goBackButton");
		goBackButton.setOnAction(event -> {
			PasswordField passField = (PasswordField) loginLayout.lookup("#passField");
			passField.clear();
			switchScene(primaryStage, welcomeScene);
		});

		// Pick login button and set up event handler
		Button completeLoginButton = (Button) loginLayout.lookup("#loginButton");
		completeLoginButton.setOnAction(event -> {
			// Pick text fields and "login failed" label
			TextField loginField = (TextField) loginLayout.lookup("#loginField");
			PasswordField passField = (PasswordField) loginLayout.lookup("#passField");
			Text loginFailed = (Text) loginLayout.lookup("#loginFailedText");

			// If typed correct credentials
			if (loginField.getLength() > 0 && passField.getLength() >= 8) {
				credentials = new UserCredentials(loginField.getText(), passField.getText());
				server = credentials.authorize();
				loggedIn = credentials.isAuthorized();
			} else {
				// Else highlight the fields
				if (loginField.getLength() < 1) {
					loginField.setStyle("-fx-border-color: RED");
				}
				if (passField.getLength() < 8) {
					passField.setStyle("-fx-border-color: RED");
				}
			}

			// Go back after successful authorization
			if (loggedIn) {
				passField.clear();
				loginButton.setDisable(true);
				loginButton.setVisible(false);
				switchScene(primaryStage, welcomeScene);
			} else {
				loginFailed.setVisible(true);
			}
		});

		// Pick document selector buttons
		Button pickBookButton = (Button) welcomeLayout.lookup("#pickBookButton");
		Button pickDocButton = (Button) welcomeLayout.lookup("#pickDocButton");
		Button pickAVButton = (Button) welcomeLayout.lookup("#pickAVButton");
		Button pickArticleButton = (Button) welcomeLayout.lookup("#pickArticleButton");

		// Initialize book selector
		BookSelector bookSelector = new BookSelector(primaryStage, welcomeScene);

		// Set up event handlers
		pickBookButton.setOnAction(event -> bookSelector.show(SelectorIntent.BOOK, server));
		pickArticleButton.setOnAction(event -> bookSelector.show(SelectorIntent.ARTICLE, server));
		pickAVButton.setOnAction(event -> bookSelector.show(SelectorIntent.AUDIOVIDEO, server));
		pickDocButton.setOnAction(event -> bookSelector.show(SelectorIntent.ALL, server));

		Button refreshListButton = (Button) welcomeLayout.lookup("#refreshListButton");
		refreshListButton.setOnAction(event -> refreshList(yourDocs, extractTitles(server.getMyDocs())));
	}

	/**
	 * Refreshes the list data.
	 *
	 * @param listView ListView to refresh.
	 * @param list     New data.
	 */
	private void refreshList(ListView listView, ObservableList list) {
		if (credentials != null) {
			listView.getItems().clear();
			listView.getItems().addAll(list);
		}
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
