package main;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import module.Data;
import module.MyContextMenu;

/**
 * @author Platina
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layout/MainUI.fxml")));

		// 获取桌面大小
		Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
		Data.screenWidth = screenRectangle.getWidth();
		Data.screenHeight = screenRectangle.getHeight();

		Scene scene = new Scene(root, 0.8 * Data.screenWidth, 0.8 * Data.screenHeight);
		Data.scene = scene;
		Data.stage = primaryStage;
		new MyContextMenu(Data.mainLayoutController.getFlowPane(), false);
		root.prefWidthProperty().bind(scene.widthProperty());
		root.prefHeightProperty().bind(scene.heightProperty());

		primaryStage.setScene(scene);
		primaryStage.setTitle("电⼦图⽚管理程序");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
