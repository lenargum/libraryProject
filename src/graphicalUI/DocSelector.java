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
				detailsDrawer.getChildren().add(picked);
				drawerStack.toggle(detailsDrawer);

			} catch (NullPointerException ignored) {
			}
		});
	}

	private DocItem getSelectedDocItemFromEvent(MouseEvent event) throws NullPointerException {
		Node x = event.getPickResult().getIntersectedNode();
		while (!(x instanceof DocItem)) {
			x = x.getParent();
		}
		return (DocItem) x;
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
		detailsDrawer.setDefaultDrawerSize(320);


		System.out.println("Book view initialized.");

		initialized = true;
	}
}
