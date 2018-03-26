package graphicalUI;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage extends Application {
	private static boolean loggedIn;
	private String username, password;
	private WelcomePage welcomePage;
	private UserPage userPage;
	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	public static boolean isLoggedIn() {
		return loggedIn;
	}

	public static void setLoggedIn(boolean loggedIn) {
		MainPage.loggedIn = loggedIn;
	}

	public void resolveLoginTransition() {
		userPage = new UserPage(primaryStage, this);
		userPage.show();

		welcomePage = null;
		System.gc();
	}

	public void resolveLogoutTrensition() {


		userPage = null;
		System.gc();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("InLibrary Manager");
		loggedIn = false;
		welcomePage = new WelcomePage(primaryStage, this);
		welcomePage.show();
	}
}
