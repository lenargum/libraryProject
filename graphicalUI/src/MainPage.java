import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainPage extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent welcome = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
		primaryStage.setTitle("InnoLibrary Manager");
		primaryStage.setMinWidth(1024);
		primaryStage.setMinHeight(768);
		primaryStage.setScene(new Scene(welcome, 1024, 768));
		primaryStage.show();


	}
}
