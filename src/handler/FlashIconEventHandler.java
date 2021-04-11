package handler;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import module.Data;


/**
 * @author Platina
 */
public class FlashIconEventHandler {
    public FlashIconEventHandler(ImageView flashIcon) {
        flashIcon.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (Data.imageList.isEmpty()) {
                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("无可播放图片");
                    dialog.setContentText("暂无可播放图片，请在有图片的目录下点击幻灯片播放图标");
                    dialog.getDialogPane().setStyle("-fx-background-color: #DEDEDE;-fx-font-size: 13");
                    ButtonType okButton = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
                    dialog.getDialogPane().getButtonTypes().add(okButton);
                    dialog.show();
                } else {
                    //获取桌面大小
                    Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
                    double width = screenRectangle.getWidth();
                    double height = screenRectangle.getHeight();

                    //播放幻灯片
                    BorderPane pptPane = new BorderPane();
                    ImageView tempImage = new ImageView(Data.imageList.get(0).getImage());
                    tempImage.setSmooth(true);
                    tempImage.setCache(true);
                    tempImage.setPreserveRatio(true);
                    tempImage.setFitWidth(0.7 * width);
                    tempImage.setFitHeight(0.7 * height);
                    pptPane.setCenter(tempImage);

                    Scene scene = new Scene(pptPane, 0.8 * width, 0.8 * height);

                    Stage pptStage = new Stage();
                    pptStage.setScene(scene);
                    pptStage.setTitle("幻灯片播放界面");
                    pptStage.show();
                }
            }
        });
    }
}
