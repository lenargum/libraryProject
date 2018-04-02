package graphicalUI;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class DocSelector {
	private CoreAPI api;
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

	public DocSelector() {
	}

	public DocSelector(Stage primaryStage, Scene previousScene, CoreAPI api) {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		this.api = api;
		initialized = false;
		initialize();
	}

	public void show() {
		if (!initialized) {
			initialize();
		}

		primaryStage.setScene(selectorScene);
	}

	private DocItem getSelectedDocItemFromEvent(MouseEvent event) throws NullPointerException {
		Node x = event.getPickResult().getIntersectedNode();
		while (!(x instanceof DocItem)) {
			x = x.getParent();
		}
		return ((DocItem) x).copy();
	}

	private void initialize() {
		// Load selector layout
		try {
			selectorLayout = FXMLLoader.load(getClass().getResource("DocSelector.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		Define new drawer stack,
		add layout to it and
		create new scene with it
		*/
		drawerStack = new JFXDrawersStack();
		drawerStack.setContent(selectorLayout);
		selectorScene = new Scene(drawerStack);

		/*
		Find "go back" button on layout,
		set its graphic and add action on it
		*/
		goBackBtn = (JFXButton) selectorLayout.lookup("#goBackBtn");
		SVGGlyph arrowBack = Glyphs.ARROW_BACK;
		arrowBack.setFill(Paint.valueOf("#fafafa"));
		arrowBack.setSize(15, 15);
		goBackBtn.setGraphic(arrowBack);
		goBackBtn.setOnAction(event -> primaryStage.setScene(previousScene));

		// Find tab pane on layout
		tabs = (JFXTabPane) selectorLayout.lookup("#tabs");

		// Initialize books view
		booksFlow = new FlowPane();
		bookScroll = new JFXScrollPane();
		bookScroll.setContent(booksFlow);

		// Initialize articles view
		articlesFlow = new FlowPane();
		articleScroll = new JFXScrollPane();
		articleScroll.setContent(articlesFlow);

		// Initialize AVs view
		avFlow = new FlowPane();
		avScroll = new JFXScrollPane();
		avScroll.setContent(avFlow);

		// Add books to the view
		booksFlow.getChildren().addAll(api.getAllBooks());
		tabs.getTabs().get(1).setContent(bookScroll);
		booksFlow.setPadding(new Insets(30));

		// Add articles to the view
		tabs.getTabs().get(2).setContent(articleScroll);

		// Add AVs to the view
		tabs.getTabs().get(3).setContent(avScroll);

		// Pick clicked item and show menu
		booksFlow.setOnMouseClicked(event -> {
			try {
				DocItem picked = getSelectedDocItemFromEvent(event);
				System.out.println("Selected " + picked.getTitle());
				coverContainer.getChildren().add(picked);
				docTitle.setText(picked.getTitle());
				docAuthors.setText(picked.getAuthor());

				if (api.canTakeDocument(picked.getDocId())) {
					bookThisBtn.setDisable(false);
				}
				bookThisBtn.setOnAction(event1 -> {
					api.bookOrRequest(picked.getDocId());
					bookThisBtn.setDisable(true);
				});
//				countBadge.setText("5 items");
//				countBadge.refreshBadge();
				detailsDrawer.setSidePane(detailsLayout);
				drawerStack.toggle(detailsDrawer);
			} catch (NullPointerException ignored) {
			}
		});

		detailsDrawer = new JFXDrawer();
		detailsDrawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
		detailsDrawer.setDefaultDrawerSize(350);

		detailsDrawer.setOnDrawerClosing(event -> bookThisBtn.setDisable(true));

		try {
			detailsLayout = FXMLLoader.load(getClass().getResource("DocDetails.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		coverContainer = (Pane) detailsLayout.lookup("#coverContainer");
		docTitle = (Text) detailsLayout.lookup("#docTitle");
		docAuthors = (Text) detailsLayout.lookup("#docAuthors");
		bookThisBtn = (JFXButton) detailsLayout.lookup("#bookThisBtn");
		countBadge = (JFXBadge) detailsLayout.lookup("#countBadge");

		System.out.println("Book view initialized.");
		initialized = true;
	}
}
