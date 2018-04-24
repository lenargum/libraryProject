package graphicalUI;

import com.jfoenix.controls.*;
import com.jfoenix.svg.SVGGlyph;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
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

	private AnchorPane detailsLayout;
	private JFXDrawer detailsDrawer;
	private JFXDrawersStack drawerStack;

	@FXML
	private Pane coverContainer;
	@FXML
	private Text docTitle;
	@FXML
	private Text docAuthors;
	@FXML
	private JFXButton bookThisBtn;

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

		drawerStack = new JFXDrawersStack();
		drawerStack.setContent(layout);

		scene = new Scene(drawerStack);

		try {
			detailsLayout = FXMLLoader.load(getClass().getResource("layout/DocDetails.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		coverContainer = (Pane) detailsLayout.lookup("#coverContainer");
		docTitle = (Text) detailsLayout.lookup("#docTitle");
		docAuthors = (Text) detailsLayout.lookup("#docAuthors");
		bookThisBtn = (JFXButton) detailsLayout.lookup("#bookThisBtn");

		detailsDrawer = new JFXDrawer();
		detailsDrawer.setDirection(JFXDrawer.DrawerDirection.RIGHT);
		detailsDrawer.setDefaultDrawerSize(350);

		docsPane.setOnMouseClicked(event -> {
			DocItem picked = getSelectedDocItemFromEvent(event);
			if (picked == null) return;

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

			detailsDrawer.setSidePane(detailsLayout);
			drawerStack.toggle(detailsDrawer);
		});
	}

	public void show() {
		stage.setScene(scene);
	}

	/**
	 * Get selected document.
	 *
	 * @param event Mouse event.
	 * @return Clicked document item.
	 * @throws NullPointerException If selected not document item.
	 */
	private DocItem getSelectedDocItemFromEvent(MouseEvent event) throws NullPointerException {
		Node x = event.getPickResult().getIntersectedNode();
		while (!(x instanceof DocItem)) {
			x = x.getParent();
		}
		return ((DocItem) x).copy();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
