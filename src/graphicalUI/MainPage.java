package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage extends Application {

	private StackPane welcomeLayout;

	public MainPage() {
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		welcomeLayout = FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
		Scene mainScene = new Scene(welcomeLayout);
		primaryStage.setScene(mainScene);
		primaryStage.show();

		JFXButton showLoginBtn = (JFXButton) welcomeLayout.lookup("#showLoginBtn");
		JFXDialog loginDialog = new JFXDialog();
		AnchorPane loginLayout = FXMLLoader.load(getClass().getResource("LoginDialog.fxml"));
		loginDialog.setContent(loginLayout);
		showLoginBtn.setOnAction(event -> loginDialog.show(welcomeLayout));
	}
}
