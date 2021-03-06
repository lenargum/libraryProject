package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.svg.SVGGlyph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tools.Notification;
import users.Admin;
import users.Librarian;
import users.Patron;

import java.io.IOException;

/**
 * User profile page.
 *
 * @author Ruslan Shakirov
 */
public class UserPage {
	private Stage primaryStage;
	private MainPage rootPage;
	private AnchorPane userLayout;

	// FXML bindings
	@FXML
	private JFXButton controlPanelBtn;
	@FXML
	private JFXButton accountBtn;
	@FXML
	private JFXButton browseLibBtn;
	private DocSelector selector;
	private UserDocs userDocs;
	private ControlPanel controlPanel;
	private Scene mainScene;
	@FXML
	private JFXListView<Label> notificationList;
	@FXML
	private JFXButton seeMoreBtn;
	@FXML
	private Text nameSurname;
	@FXML
	private Label statusField;
	@FXML
	private Label addressField;
	@FXML
	private Label phoneField;
	@FXML
	private JFXButton refreshBtn;
	@FXML
	private VBox profileContainer;

	public UserPage() {
	}

	/**
	 * Create new view.
	 *
	 * @param primaryStage Main stage.
	 * @param rootPage     Root page.
	 */
	public UserPage(Stage primaryStage, MainPage rootPage) {
		this.primaryStage = primaryStage;
		this.rootPage = rootPage;
		try {
			userLayout = FXMLLoader.load(getClass().getResource("layout/UserPage.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		mainScene = new Scene(userLayout);

		userDocs = new UserDocs(rootPage.getApi());

		if (rootPage.getApi().getUser() instanceof Librarian ||
				rootPage.getApi().getUser() instanceof Admin) {
			controlPanel = new ControlPanel(rootPage.getApi());
		}

		Thread initSelector = new Thread(() -> {
			System.out.print("Loading document selector in parallel thread:\n\t");
			selector = new DocSelector(primaryStage, mainScene, rootPage.getApi());
		});
		initSelector.setDaemon(true);
		initSelector.start();

		initialize();
	}

	/**
	 * Update data on views.
	 */
	public void updateViews() {
		userDocs = new UserDocs(rootPage.getApi());
		selector = new DocSelector(primaryStage, mainScene, rootPage.getApi());
		initNotifications();
		if (!(rootPage.getApi().getUser() instanceof Patron)) {
			controlPanel = new ControlPanel(rootPage.getApi());
		}
	}

	/**
	 * Show view.
	 */
	public void show() {
		primaryStage.setScene(mainScene);
	}

	/**
	 * Initialize view.
	 */
	private void initialize() {
		accountBtn = (JFXButton) userLayout.lookup("#accountBtn");
		SVGGlyph accountIcon = Glyphs.ACCOUNT_CIRCLE();
		accountIcon.setSize(20, 20);
		accountIcon.setFill(Paint.valueOf("#757575"));
		accountBtn.setGraphic(accountIcon);
		accountBtn.setOnAction(this::accountBtnClicked);

		browseLibBtn = (JFXButton) userLayout.lookup("#browseLibBtn");
		SVGGlyph bookIcon = Glyphs.BOOK_BLACK();
		bookIcon.setSize(20, 25);
		browseLibBtn.setGraphic(bookIcon);
		browseLibBtn.setOnAction(event -> selector.show());

		controlPanelBtn = (JFXButton) userLayout.lookup("#controlPanelBtn");
		if (rootPage.getApi().getUser() instanceof Librarian ||
				rootPage.getApi().getUser() instanceof Admin) {
			controlPanelBtn.setDisable(false);
			controlPanelBtn.setVisible(true);
		}
		controlPanelBtn.setOnAction(event -> controlPanel.show());

		seeMoreBtn = (JFXButton) userLayout.lookup("#seeMoreBtn");
		seeMoreBtn.setOnAction(event -> userDocs.show());

		notificationList = (JFXListView<Label>) userLayout.lookup("#notificationList");
		notificationList.setDepth(3);

		profileContainer = (VBox) userLayout.lookup("#profileContainer");
		nameSurname = (Text) userLayout.lookup("#nameSurname");
		statusField = (Label) userLayout.lookup("#statusField");
		addressField = (Label) userLayout.lookup("#addressField");
		phoneField = (Label) userLayout.lookup("#phoneField");

		nameSurname.setText(rootPage.getApi().getUser().getName() + " " + rootPage.getApi().getUser().getSurname());
		statusField.setText(rootPage.getApi().getUser().getClass().getSimpleName());
		addressField.setText(rootPage.getApi().getUser().getAddress());
		phoneField.setText(rootPage.getApi().getUser().getPhoneNumber());

		SVGGlyph statusGraphic = Glyphs.ASSIGNMENT_IND();
		statusGraphic.setFill(Paint.valueOf("8d8d8d"));
		statusGraphic.setSize(16);
		SVGGlyph addressGraphic = Glyphs.LOCATION();
		addressGraphic.setFill(Paint.valueOf("8d8d8d"));
		addressGraphic.setSize(16);
		SVGGlyph phoneGraphic = Glyphs.PHONE();
		phoneGraphic.setFill(Paint.valueOf("8d8d8d"));
		phoneGraphic.setSize(16);

		statusField.setGraphic(statusGraphic);
		addressField.setGraphic(addressGraphic);
		phoneField.setGraphic(phoneGraphic);

		profileContainer.setPadding(new Insets(10));

		refreshBtn = (JFXButton) userLayout.lookup("#refreshBtn");
		refreshBtn.setOnAction(event -> updateViews());

		initNotifications();
	}

	private void initNotifications() {
		ObservableList<Label> notifications = FXCollections.observableArrayList();
		for (Notification notification : rootPage.getApi().getUserNotifications()) {
			Label text = new Label(notification.getDescription());
			text.setPadding(new Insets(5));
			notifications.add(text);
		}
		notificationList.setItems(notifications);
	}

	/**
	 * Action on account button clicked.
	 *
	 * @param event Action event.
	 */
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
}
