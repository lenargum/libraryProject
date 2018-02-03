package graphicalUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage extends Application {

	private boolean loggedIn;
	private UserCredentials credentials;
	private ServerAPI server;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		loggedIn = false;
		server = new ServerAPI();
		AnchorPane welcomeLayout = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		primaryStage.setTitle("InnoLibrary Manager");
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(600);
		Scene welcomeScene = new Scene(welcomeLayout, 1024, 768);
		primaryStage.setScene(welcomeScene);
		primaryStage.show();

		Button loginButton = (Button) welcomeLayout.lookup("#loginButton");

		AnchorPane loginLayout = FXMLLoader.load(getClass().getResource("LoginDialog.fxml"));
		Scene loginScene = new Scene(loginLayout);
		loginButton.setOnAction(event -> {
			switchScene(primaryStage, loginScene);
		});

		Button goBackButton = (Button) loginLayout.lookup("#goBackButton");
		goBackButton.setOnAction(event -> {
			PasswordField passField = (PasswordField) loginLayout.lookup("#passField");
			passField.clear();
			switchScene(primaryStage, welcomeScene);
		});

		Button completeLoginButton = (Button) loginLayout.lookup("#loginButton");
		completeLoginButton.setOnAction(event -> {
			TextField loginField = (TextField) loginLayout.lookup("#loginField");
			PasswordField passField = (PasswordField) loginLayout.lookup("#passField");
			Text loginFailed = (Text) loginLayout.lookup("#loginFailedText");

			if (loginField.getLength() > 0 && passField.getLength() > 0) {
				credentials = new UserCredentials(loginField.getText(), passField.getText());
				server = credentials.authorize();
				loggedIn = credentials.isAuthorized();
			} else {
				if (loginField.getLength() < 1) {
					loginField.setStyle("-fx-border-color: RED");
				}
				if (passField.getLength() < 8) {
					passField.setStyle("-fx-border-color: RED");
				}
			}

			if (loggedIn) {
				passField.clear();
				loginButton.setDisable(true);
				loginButton.setVisible(false);
				switchScene(primaryStage, welcomeScene);
			} else {
				loginFailed.setVisible(true);
			}
		});

		Button pickBookButton = (Button) welcomeLayout.lookup("#pickBookButton");
		Button pickDocButton = (Button) welcomeLayout.lookup("#pickDocButton");
		Button pickAVButton = (Button) welcomeLayout.lookup("#pickAVButton");
		Button pickArticleButton = (Button) welcomeLayout.lookup("#pickArticleButton");

		BookSelector bookSelector = new BookSelector(primaryStage, welcomeScene);

		pickBookButton.setOnAction(event -> bookSelector.show(SelectorIntent.BOOK, server));
		pickDocButton.setOnAction(event -> bookSelector.show(SelectorIntent.DOCUMENT, server));
		pickAVButton.setOnAction(event -> bookSelector.show(SelectorIntent.AV, server));
		pickArticleButton.setOnAction(event -> bookSelector.show(SelectorIntent.ARTICLE, server));
	}

	private void switchScene(Stage targetStage, Scene newScene) {
		targetStage.setScene(newScene);
		targetStage.show();
	}
}
