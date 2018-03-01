package graphicalUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.io.IOException;

public class CustomLoader {
	private String name;

	public CustomLoader(String name) {
		this.name = name;
	}

	public <T> T load() throws IOException {
		return FXMLLoader.load(getClass().getResource(name));
	}
}
