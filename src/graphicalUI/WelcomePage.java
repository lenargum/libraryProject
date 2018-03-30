package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class WelcomePage {
	private MainPage rootPage;
	private Stage primaryStage;
	private Scene mainScene;
	@FXML
	private StackPane welcomeLayout;
	@FXML
	private JFXTextField usernameField;
	@FXML
	private JFXPasswordField passwordField;
	@FXML
	private JFXDialog loginDialog;

	private DocSelector selector;

	public WelcomePage() {
	}

	public WelcomePage(Stage primaryStage, MainPage rootPage) {
		this.rootPage = rootPage;
		this.primaryStage = primaryStage;
		try {
			welcomeLayout = FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainScene = new Scene(welcomeLayout);
	}

	public void show() {
		primaryStage.setScene(mainScene);
		primaryStage.show();

		Thread initSelector = new Thread(() -> {
			System.out.print("Loading document selector in parallel thread:\n\t");
			selector = new DocSelector(primaryStage, mainScene, rootPage.getApi());
		});
		initSelector.start();

		JFXButton showLoginBtn = (JFXButton) welcomeLayout.lookup("#showLoginBtn");

		AnchorPane loginLayout = null;
		try {
			loginLayout = FXMLLoader.load(getClass().getResource("LoginDialog.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		loginDialog = new JFXDialog();
		loginDialog.setContent(loginLayout);
		assert loginLayout != null;
		usernameField = (JFXTextField) loginLayout.lookup("#usernameField");
		passwordField = (JFXPasswordField) loginLayout.lookup("#passwordField");
		JFXButton proceedLoginButton = (JFXButton) loginLayout.lookup("#proceedLoginButton");

		showLoginBtn.setOnAction(event -> loginDialog.show(welcomeLayout));

		proceedLoginButton.setOnAction(event -> processLogin());

		passwordField.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				processLogin();
			}
		});

		JFXButton browseLibBtn = (JFXButton) welcomeLayout.lookup("#browseLibBtn");
		browseLibBtn.setOnAction(event -> selector.show());
	}

	private void processLogin() {
		Credentials credentials = new Credentials(usernameField.getText(), passwordField.getText());
		passwordField.clear();
		loginDialog.close();
		System.out.println("Logged in as " + credentials.getLogin());
		rootPage.resolveLoginTransition(credentials);
	}
}
