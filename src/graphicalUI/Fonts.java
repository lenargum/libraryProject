package graphicalUI;

import javafx.scene.text.Font;

public class Fonts {
	public static Font RobotoRegular() {
		return Font.loadFont(Thread.currentThread().getContextClassLoader().
				getResourceAsStream("font/roboto/Roboto-Regular.ttf"), 16);
	}
}
