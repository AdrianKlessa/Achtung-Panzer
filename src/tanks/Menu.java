package tanks;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class Menu extends Scene{
	private static GridPane pane = new GridPane();
	String ip;
	int port;
	public Menu() {
		super(pane,1200,800);

		pane.setPadding(new Insets(50,50,50,50));
		pane.setVgap(10);
		pane.setHgap(10);		
		pane.setStyle("-fx-background-color: PaleTurquoise");
		TextField ipField = new TextField();
		TextField portConnectField = new TextField();
		TextField portHostField = new TextField();
		Button buttonConnect = new Button("Connect");
		Button buttonHost = new Button("Host");
		Button buttonAbout = new Button("About");
		Button buttonExit = new Button("Exit");
		Button buttonTest = new Button("Test game");
		Label menuText = new Label("Achtung Panzer!");
		
		Label portThing = new Label(":");
		GridPane.setConstraints(menuText,0,0,14,1);
		menuText.setPrefWidth(1000);
		GridPane.setConstraints(ipField,0,1,5,1);
		ipField.setPrefWidth(400);
		GridPane.setConstraints(portThing,5,1,1,1);
		GridPane.setConstraints(portConnectField,6,1,3,1);
		portConnectField.setPrefWidth(250);
		GridPane.setConstraints(buttonConnect,9,1,5,1);
		buttonConnect.setPrefWidth(400);
		GridPane.setConstraints(portHostField,0,2,3,1);
		portHostField.setPrefWidth(250);
		GridPane.setConstraints(buttonHost,9,2,5,1);
		buttonHost.setPrefWidth(400);
		GridPane.setConstraints(buttonAbout,8,3,5,1);
		buttonAbout.setPrefWidth(400);
		GridPane.setConstraints(buttonExit,8,4,5,1);
		buttonExit.setPrefWidth(400);
		
		//
		GridPane.setConstraints(buttonTest,9,5,5,5);
		buttonTest.setPrefHeight(100);
		buttonTest.setPrefWidth(100);
		
		pane.getChildren().addAll(menuText,ipField,portThing,portConnectField,buttonConnect,portHostField,buttonHost,buttonAbout,buttonExit,buttonTest);		
		pane.setPrefSize(1200, 800);
		
		
		buttonTest.setOnAction(e->{
			
			GameTest game = new GameTest();
			Main.primaryStage.setScene(game);			
		});
		
		buttonConnect.setOnAction(e->{
			port=Integer.parseInt(portConnectField.getText());
			ip=ipField.getText();
			System.out.println(ip+":"+port);
		});
		
		buttonHost.setOnAction(e->{
			port=Integer.parseInt(portHostField.getText());
			System.out.print(port);
		});
		
	}

}
