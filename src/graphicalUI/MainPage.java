package graphicalUI;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Application root class.
 *
 * @author Ruslan Shakirov.
 */
public class MainPage extends Application {
	private CoreAPI api;
	private WelcomePage welcomePage;
	private UserPage userPage;
	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Change views on login.
	 *
	 * @param credentials User credentials.
	 */
	public void resolveLoginTransition(Credentials credentials) {
		Thread authorizeParallel = new Thread(() -> api.authorize(credentials));
		authorizeParallel.start();

		userPage = new UserPage(primaryStage, this);
		userPage.show();

		welcomePage = null;
		System.gc();
	}

	/**
	 * Change views on logout.
	 */
	public void resolveLogoutTransition() {
		api.deauthorize();

		welcomePage = new WelcomePage(primaryStage, this);
		welcomePage.show();

		userPage = null;
		System.gc();
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.setTitle("InLibrary Manager");
		api = new CoreAPI();
		welcomePage = new WelcomePage(primaryStage, this);
		welcomePage.show();
	}

	public CoreAPI getApi() {
		return api;
	}
}
