import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage extends Application {

	private boolean loggedIn;
	private UserCredentials credentials;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		loggedIn = false;
		AnchorPane welcome = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		primaryStage.setTitle("InnoLibrary Manager");
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(600);
		Scene welcomeScene = new Scene(welcome, 1024, 768);
		primaryStage.setScene(welcomeScene);
		primaryStage.show();

		Button loginButton = (Button) welcome.lookup("#loginButton");

		AnchorPane loginLayout = FXMLLoader.load(getClass().getResource("loginDialog.fxml"));
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

			if (loginField.getLength() > 0 && passField.getLength() > 0) {
				credentials = new UserCredentials(loginField.getText(), passField.getText());
				loggedIn = credentials.isAuthorized();
			} else {
				if (loginField.getLength() < 1) {
					loginField.setStyle("-fx-border-color: RED");
				}
				if (passField.getLength() < 1) {
					passField.setStyle("-fx-border-color: RED");
				}
			}

			Text loginFailed = (Text) loginLayout.lookup("#loginFailedText");
			if (loggedIn) {
				passField.clear();
				loginButton.setDisable(true);
				loginButton.setVisible(false);
				switchScene(primaryStage, welcomeScene);
			} else {
				loginFailed.setVisible(true);
			}
		});
	}

	private void switchScene(Stage targetStage, Scene newScene) {
		targetStage.setScene(newScene);
		targetStage.show();
	}
}
