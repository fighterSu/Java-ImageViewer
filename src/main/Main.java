package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Platina
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/layout/MainUI.fxml")));

        //获取桌面大小
        Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
        double width = screenRectangle.getWidth();
        double height = screenRectangle.getHeight();

        Scene scene = new Scene(root, 0.8 * width, 0.8 * height);
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
