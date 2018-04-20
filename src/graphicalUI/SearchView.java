package graphicalUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.svg.SVGGlyph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchView implements Initializable {
	private CoreAPI api;
	private Stage stage;
	private Scene scene, previousScene;

	private AnchorPane layout;
	@FXML
	private JFXChipView<String> keywordChips;
	@FXML
	private JFXButton goBackBtn;
	@FXML
	private JFXMasonryPane docsPane;

	public SearchView() {
	}

	public SearchView(Stage stage, Scene previousScene, CoreAPI api) {
		this.api = api;
		this.stage = stage;
		this.previousScene = previousScene;

		try {
			FXMLLoader loader = new FXMLLoader();
			layout = loader.load(getClass().getResourceAsStream("layout/SearchView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		goBackBtn = (JFXButton) layout.lookup("#goBackBtn");
		goBackBtn.setOnAction(event -> stage.setScene(previousScene));
		SVGGlyph goBackGraphic = Glyphs.ARROW_BACK();
		goBackGraphic.setSize(20);
		goBackGraphic.setFill(Paint.valueOf("8d8d8d"));
		goBackBtn.setGraphic(goBackGraphic);

		docsPane = (JFXMasonryPane) layout.lookup("#docsPane");

		keywordChips = (JFXChipView<String>) layout.lookup("#keywordChips");
		keywordChips.getSuggestions().addAll("book", "article", "audio/video");
		keywordChips.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				ObservableList<DocItem> searchResult = FXCollections.observableArrayList();
				Thread performSearch = new Thread(() -> {
					List<String> keywords = new LinkedList<>();
					keywords.addAll(keywordChips.getChips());
					searchResult.addAll(api.search(keywords));
				});
				performSearch.start();
				try {
					performSearch.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					docsPane.getChildren().setAll(searchResult);
				}
			}
		});

		scene = new Scene(layout);
	}

	public void show() {
		stage.setScene(scene);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
