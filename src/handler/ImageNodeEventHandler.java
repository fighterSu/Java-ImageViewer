package handler;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import module.Data;

/**
 * @author Platina
 */
public class ImageNodeEventHandler {
    public static void setImageNodeMouseClickedEvent(MouseEvent mouseEvent, VBox baseBox, ImageView imageView) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (mouseEvent.getClickCount() == 2) {
                // 执行幻灯片播放
                //获取桌面大小
                Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
                double width = screenRectangle.getWidth();
                double height = screenRectangle.getHeight();

                //播放幻灯片
                BorderPane pptPane = new BorderPane();
                ImageView tempImage = new ImageView(Data.imageList.get(Data.imageList.indexOf(imageView)).getImage());
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

            } else {
                boolean hasSelected = "-fx-background-color: #DEDEDE".equals(baseBox.getStyle());
                baseBox.setStyle("-fx-background-color: #DEDEDE");
                if (!mouseEvent.isControlDown()) {
                    clearSelectedState();
                    if (hasSelected) {
                        baseBox.setStyle("-fx-background-color: #DEDEDE");
                    }
                }
                Data.selectedImageList.add(imageView);
                Data.tipText.setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 %d 张图片",
                        Data.numberOfImage, Data.sumOfImage, Data.unit, Data.selectedImageList.size()));
            }
        }
    }

    public static void clearSelectedState() {
        for (int i = 0; i < Data.selectedImageList.size(); i++) {
            Data.selectedImageList.get(i).getParent().getParent().setStyle("-fx-background-color: white");
        }
        Data.selectedImageList.clear();
        Data.tipText.setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 0 张图片",
                Data.numberOfImage, Data.sumOfImage, Data.unit));
    }
}
