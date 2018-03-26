package graphicalUI;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DocSelector {
	private Stage primaryStage;
	private Scene selectorScene;
	private Scene previousScene;
	private boolean initialized;
	private AnchorPane selectorLayout;
	@FXML
	private JFXButton goBackBtn;
	@FXML
	private JFXTabPane tabs;
	private FlowPane booksFlow, articlesFlow, avFlow;
	private JFXScrollPane bookScroll, articleScroll, avScroll;
	private JFXDrawer detailsDrawer;
	private JFXDrawersStack drawerStack;
	@FXML
	private AnchorPane detailsLayout;
	@FXML
	private Pane coverContainer;
	@FXML
	private Text docTitle, docAuthors;
	@FXML
	private JFXButton bookThisBtn;
	@FXML
	private JFXBadge countBadge;

	private boolean booksAdded, articlesAdded, avsAdded;

	public DocSelector() {
	}

	public DocSelector(Stage primaryStage, Scene previousScene) {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		initialized = false;
	}

	public void show() throws IOException {
		if (!initialized) {
			initialize();
		}

		primaryStage.setScene(selectorScene);

		if (!articlesAdded) {
			tabs.getTabs().get(2).setContent(articleScroll);
			articlesAdded = true;
		}

		if (!avsAdded) {
			tabs.getTabs().get(3).setContent(avScroll);
			avsAdded = true;
		}

		booksFlow.setOnMouseClicked(event -> {
			try {
				DocItem picked = getSelectedDocItemFromEvent(event);
				System.out.println("Selected " + picked.getTitle());
				coverContainer.getChildren().add(picked);
				docTitle.setText(picked.getTitle());
				docAuthors.setText(picked.getAuthor());
//				countBadge.setText("5 items");
//				countBadge.refreshBadge();
				detailsDrawer.setSidePane(detailsLayout);
				drawerStack.toggle(detailsDrawer);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		});
	}

	private DocItem getSelectedDocItemFromEvent(MouseEvent event) throws NullPointerException {
		Node x = event.getPickResult().getIntersectedNode();
		while (!(x instanceof DocItem)) {
			x = x.getParent();
		}
		return ((DocItem) x).copy();
	}

	private void initialize() throws IOException {
		selectorLayout = FXMLLoader.load(getClass().getResource("DocSelector.fxml"));
		drawerStack = new JFXDrawersStack();
		drawerStack.setContent(selectorLayout);
		selectorScene = new Scene(drawerStack);

		goBackBtn = (JFXButton) selectorLayout.lookup("#goBackBtn");
		goBackBtn.setOnAction(event -> primaryStage.setScene(previousScene));

		tabs = (JFXTabPane) selectorLayout.lookup("#tabs");

		booksAdded = articlesAdded = avsAdded = false;

		booksFlow = new FlowPane();
		bookScroll = new JFXScrollPane();
		bookScroll.setContent(booksFlow);

		articlesFlow = new FlowPane();
		articleScroll = new JFXScrollPane();
		articleScroll.setContent(articlesFlow);

		avFlow = new FlowPane();
		avScroll = new JFXScrollPane();
		avScroll.setContent(avFlow);

		if (!booksAdded) {
			for (int i = 0; i < 20; i++) {
				booksFlow.getChildren().add(new DocItem("Introduction to something  " + (i + 1), "Author Authorovich", i));
			}
			tabs.getTabs().get(1).setContent(bookScroll);
			booksFlow.setPadding(new Insets(30));
			booksAdded = true;
		}

		detailsDrawer = new JFXDrawer();
		detailsDrawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
		detailsDrawer.setDefaultDrawerSize(350);

		detailsLayout = FXMLLoader.load(getClass().getResource("DocDetails.fxml"));
		coverContainer = (Pane) detailsLayout.lookup("#coverContainer");
		docTitle = (Text) detailsLayout.lookup("#docTitle");
		docAuthors = (Text) detailsLayout.lookup("#docAuthors");
		bookThisBtn = (JFXButton) detailsLayout.lookup("#bookThisBtn");
		countBadge = (JFXBadge) detailsLayout.lookup("#countBadge");
		if (MainPage.isLoggedIn()) {
			bookThisBtn.setDisable(false);
		}

		System.out.println("Book view initialized.");
		initialized = true;
	}
}
