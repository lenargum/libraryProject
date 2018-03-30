package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.svg.SVGGlyph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;

public class UserPage {
	private Stage primaryStage;
	private MainPage rootPage;
	private AnchorPane userLayout;
	@FXML
	private JFXButton controlPanelBtn;
	@FXML
	private JFXButton accountBtn;
	@FXML
	private JFXButton browseLibBtn;
	private DocSelector selector;
	private Scene mainScene;
	@FXML
	private GridPane infoGrid;
	@FXML
	private JFXListView myLastBooks;

	private boolean initialized;

	public UserPage() {
	}

	public UserPage(Stage primaryStage, MainPage rootPage) {
		this.primaryStage = primaryStage;
		this.rootPage = rootPage;
		try {
			userLayout = FXMLLoader.load(getClass().getResource("UserPage.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainScene = new Scene(userLayout);

		initialize();
	}

	public void show() {
		primaryStage.setScene(mainScene);

		Thread initSelector = new Thread(() -> {
			System.out.println("Loading document selector in parallel thread:\n\t");
			selector = new DocSelector(primaryStage, mainScene, rootPage.getApi());
		});
		initSelector.start();
	}

	private void initialize() {
		accountBtn = (JFXButton) userLayout.lookup("#accountBtn");
		SVGGlyph accountIcon = Glyphs.ACCOUNT_CIRCLE;
		accountIcon.setSize(20, 20);
		accountIcon.setFill(Paint.valueOf("#757575"));
		accountBtn.setGraphic(accountIcon);

		browseLibBtn = (JFXButton) userLayout.lookup("#browseLibBtn");
		SVGGlyph bookIcon = Glyphs.BOOK_BLACK;
		bookIcon.setSize(20, 25);
		browseLibBtn.setGraphic(bookIcon);
		browseLibBtn.setOnAction(event -> selector.show());

		controlPanelBtn = (JFXButton) userLayout.lookup("#controlPanelBtn");
		if (rootPage.getApi().userType() == CoreAPI.UserType.LIBRARIAN) {
			controlPanelBtn.setDisable(false);
			controlPanelBtn.setVisible(true);
		}

		//infoGrid = (GridPane) userLayout.lookup("#infoGrid");

		myLastBooks = (JFXListView) userLayout.lookup("#myLastBooks");

		initialized = true;
	}
}
