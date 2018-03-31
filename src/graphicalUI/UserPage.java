package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.svg.SVGGlyph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
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
	private JFXListView<Label> myLastBooks;
	private JFXPopup detailsPopup;

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
		accountBtn.setOnAction(this::accountBtnClicked);

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

		myLastBooks = (JFXListView<Label>) userLayout.lookup("#myLastBooks");
		myLastBooks.setDepth(2);
		myLastBooks.getItems().addAll(new Label("Booooooook"), new Label("ARRRRRRticle"), new Label("AV"));

		myLastBooks.setOnMouseClicked(this::myLastBooksOnMouseClicked);

		initialized = true;
	}

	private void accountBtnClicked(ActionEvent event) {
		JFXButton logoutButton = new JFXButton("Log out");
		logoutButton.setFont(new Font("Roboto", 14));
		logoutButton.setPadding(new Insets(10, 40, 10, 20));
		JFXPopup popup = new JFXPopup(logoutButton);
		logoutButton.setOnAction(event1 -> {
			popup.hide();
			rootPage.resolveLogoutTransition();
		});
		popup.show(accountBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
	}

	private void myLastBooksOnMouseClicked(MouseEvent event) {
		JFXListCell<Label> intersectedNode = (JFXListCell<Label>) event.getPickResult().getIntersectedNode();
		DocItem selectedItem = new DocItem(intersectedNode.getItem().getText(), "Author", 1);
		VBox popupContainer = new VBox();
		popupContainer.getChildren().addAll(selectedItem, new JFXButton("RENEW"), new JFXButton("RETURN"));
		popupContainer.setPadding(new Insets(20));
		detailsPopup = new JFXPopup();
		detailsPopup.setPopupContent(popupContainer);
		detailsPopup.show(myLastBooks, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, event.getX() - 335, event.getY());
	}
}
