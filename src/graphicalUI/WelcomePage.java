package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePage {
	@FXML
	private JFXButton browseLibBtn;
	private Stage primaryStage;
	@FXML
	private StackPane welcomeLayout;
	@FXML
	private JFXButton showLoginBtn;

	private String username;
	private String password;

	public WelcomePage() {
	}

	public WelcomePage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		username = "";
	}

	public void show() throws IOException {
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		welcomeLayout = FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
		Scene mainScene = new Scene(welcomeLayout);
		primaryStage.setScene(mainScene);
		primaryStage.show();

		JFXButton showLoginBtn = (JFXButton) welcomeLayout.lookup("#showLoginBtn");

		AnchorPane loginLayout = FXMLLoader.load(getClass().getResource("LoginDialog.fxml"));
		JFXDialog loginDialog = new JFXDialog();
		loginDialog.setContent(loginLayout);
		JFXTextField usernameField = (JFXTextField) loginLayout.lookup("#usernameField");
		JFXPasswordField passwordField = (JFXPasswordField) loginLayout.lookup("#passwordField");
		JFXButton proceedLoginButton = (JFXButton) loginLayout.lookup("#proceedLoginButton");

		showLoginBtn.setOnAction(event -> {
			loginDialog.show(welcomeLayout);
		});
		proceedLoginButton.setOnAction(event -> {
			username = usernameField.getText();
			password = passwordField.getText();
			passwordField.clear();
			loginDialog.close();
			// TODO Sign in
			System.out.println(username);
			System.out.println(password);
		});

		JFXButton browseLibBtn = (JFXButton) welcomeLayout.lookup("#browseLibBtn");
		DocSelector selector = new DocSelector(primaryStage, mainScene);
		browseLibBtn.setOnAction(event -> selector.show());
	}
}
