package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class LoginDialog {
	@FXML
	private JFXTextField usernameField;
	@FXML
	private JFXPasswordField passwordField;
	@FXML
	private JFXButton proceedLoginButton;
	private JFXDialog loginDialog;
	private AnchorPane loginLayout;

	public LoginDialog() {
	}

	public LoginDialog(AnchorPane loginLayout) {
		loginDialog = new JFXDialog();
		this.loginLayout = loginLayout;
	}

	public String getUsername() {
		return usernameField.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

	public void setOnProceedLoginAction(EventHandler<ActionEvent> value) {
		proceedLoginButton.setOnAction(value);
	}

	public void show(StackPane root) {
		loginDialog.setContent(loginLayout);
		loginDialog.show(root);
	}
}
