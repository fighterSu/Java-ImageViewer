package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modules.Data;
import modules.MyContextMenu;

import java.io.IOException;
import java.util.Objects;

/**
 * 主类，加载界面，进行数据初始化
 *
 * @author Platina
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layout/MainUI.fxml")));

        Scene scene = new Scene(root, 0.8 * Data.screenWidth, 0.8 * Data.screenHeight);
        Data.primaryScene = scene;
        Data.primaryStage = primaryStage;
        new MyContextMenu(Data.mainLayoutController.getFlowPane(), false);

        root.prefWidthProperty().bind(scene.widthProperty());
        root.prefHeightProperty().bind(scene.heightProperty());
        Data.mainLayoutController.getAnchorPane().prefHeightProperty().bind(root.widthProperty().subtract(100));
        Data.mainLayoutController.getAnchorPane().prefWidthProperty().bind(root.heightProperty().subtract(70));

        primaryStage.setScene(scene);
        primaryStage.setTitle("电⼦图⽚管理程序");
        Image icon = new Image("image/icon.png", 30, 30, false, false, false);
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

	public static void main(String[] args) {
		launch(args);
	}
}
