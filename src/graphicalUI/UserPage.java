package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.svg.SVGGlyph;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
	private UserDocs userDocs;
	private LibrarianPanel librarianPanel;
	private Scene mainScene;
	@FXML
	private JFXListView<DocCell> myLastBooks;
	private JFXPopup detailsPopup;
	@FXML
	private JFXButton seeMoreBtn;
	@FXML
	private Text nameSurname;
	@FXML
	private Text statusField;
	@FXML
	private Text addressField;
	@FXML
	private Text phoneField;

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

		userDocs = new UserDocs(rootPage.getApi());
		librarianPanel = new LibrarianPanel(rootPage.getApi());

		Thread initSelector = new Thread(() -> {
			System.out.print("Loading document selector in parallel thread:\n\t");
			selector = new DocSelector(primaryStage, mainScene, rootPage.getApi());
		});

		initSelector.start();

		initialize();
	}

	public void show() {
		primaryStage.setScene(mainScene);
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
		controlPanelBtn.setOnAction(event -> librarianPanel.show());

		seeMoreBtn = (JFXButton) userLayout.lookup("#seeMoreBtn");
		seeMoreBtn.setOnAction(event -> userDocs.show());

		myLastBooks = (JFXListView<DocCell>) userLayout.lookup("#myLastBooks");
		myLastBooks.setDepth(2);
		myLastBooks.getItems().addAll(rootPage.getApi().getRecent());

		myLastBooks.setOnMouseClicked(this::myLastBooksOnMouseClicked);

		nameSurname = (Text) userLayout.lookup("#nameSurname");
		statusField = (Text) userLayout.lookup("#statusField");
		addressField = (Text) userLayout.lookup("#addressField");
		phoneField = (Text) userLayout.lookup("#phoneField");

		nameSurname.setText(rootPage.getApi().getUser().getName() + " " + rootPage.getApi().getUser().getSurname());
		statusField.setText(rootPage.getApi().userType().name());
		addressField.setText(rootPage.getApi().getUser().getAddress());
		phoneField.setText(rootPage.getApi().getUser().getPhoneNumber());
	}

	private void accountBtnClicked(ActionEvent event) {
		JFXButton logoutButton = new JFXButton("Log out");
		logoutButton.setFont(new Font("Roboto", 14));
		logoutButton.setPadding(new Insets(10, 40, 10, 20));
		JFXPopup popup = new JFXPopup(logoutButton);
		logoutButton.setOnAction(event1 -> {
			popup.hide();
			rootPage.resolveLogoutTransition();
			System.out.println("Logged out.");
		});
		popup.show(accountBtn, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT);
	}

	private void myLastBooksOnMouseClicked(MouseEvent event) {
		DocCell selectedNode = myLastBooks.getSelectionModel().getSelectedItem();
		DocumentPopup popup = new DocumentPopup(selectedNode.getTitle(), "Author", selectedNode.getDocumentId());
		popup.show(myLastBooks, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, event.getX() - 335, event.getY());
	}
}
