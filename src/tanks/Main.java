package tanks;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static Stage primaryStage;
	private Menu menuScene;
	private Scene gameScene;
	public static int stage=1;
	
	
	public static void main(String[] args) {
		launch(args);
	}

	
	public void start(Stage mainStage) throws Exception {
		primaryStage=mainStage;
		
		
		menuScene = new Menu();
		primaryStage.setScene(menuScene);
		primaryStage.setTitle("Achtung Panzer!");
		primaryStage.show();
		
		
	}
	
}
