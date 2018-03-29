package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class UserPage {
	private Stage primaryStage;
	private MainPage rootPage;
	private AnchorPane userLayout;
	@FXML
	private JFXButton controlPanelBtn;

	public UserPage() {
	}

	public UserPage(Stage primaryStage, MainPage rootPage) {
		this.primaryStage = primaryStage;
		this.rootPage = rootPage;
	}

	public void show() {
		try {
			userLayout = FXMLLoader.load(getClass().getResource("UserPage.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Scene mainScene = new Scene(userLayout);
		primaryStage.setScene(mainScene);

		JFXButton accountButton = (JFXButton) userLayout.lookup("#accountBtn");
		SVGGlyph accountIcon = Glyphs.ACCOUNT_CIRCLE;
		accountIcon.setSize(20, 20);
		accountIcon.setFill(Paint.valueOf("#757575"));
		accountButton.setGraphic(accountIcon);

		controlPanelBtn = (JFXButton) userLayout.lookup("#controlPanelBtn");
		if (rootPage.getApi().userType() == CoreAPI.UserType.LIBRARIAN) {
			controlPanelBtn.setDisable(false);
			controlPanelBtn.setVisible(true);
		}
	}
}
