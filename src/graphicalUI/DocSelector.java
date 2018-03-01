package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DocSelector {
	private Stage primaryStage;
	private Scene selectorScene;
	private Scene previousScene;
	private AnchorPane selectorLayout;
	@FXML
	private JFXButton goBackBtn;
	@FXML
	private JFXTabPane tabs;
	@FXML
	private FlowPane booksTab, articlesTab, avTab;

	private boolean booksAdded, articlesAdded, avsAdded;

	public DocSelector() {
	}

	public DocSelector(Stage primaryStage, Scene previousScene) throws IOException {
		this.primaryStage = primaryStage;
		this.previousScene = previousScene;
		selectorLayout = FXMLLoader.load(getClass().getResource("DocSelector.fxml"));
		selectorScene = new Scene(selectorLayout);

		goBackBtn = (JFXButton) selectorLayout.lookup("#goBackBtn");
		goBackBtn.setOnAction(event -> primaryStage.setScene(previousScene));

		tabs = (JFXTabPane) selectorLayout.lookup("#tabs");

		this.booksTab = (FlowPane) tabs.getTabs().get(1).getContent().lookup("#booksTab");
		this.articlesTab = (FlowPane) tabs.getTabs().get(2).getContent().lookup("#articlesTab");
		this.avTab = (FlowPane) tabs.getTabs().get(3).getContent().lookup("#avTab");
	}

	public void show() {
		primaryStage.setScene(selectorScene);

		List<DocItem> books = new LinkedList<>();
		for (int i = 0; i < 20; i++) {
			books.add(new DocItem("Book " + (i + 1), "Author Authorovich"));
		}

		if (!booksAdded) {
			booksTab.getChildren().addAll(books);
			booksAdded = true;
		}
	}

}
