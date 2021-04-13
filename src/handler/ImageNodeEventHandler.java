package handler;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import module.Data;
import module.Slide;

import java.io.IOException;

/**
 * @author Platina
 */
public class ImageNodeEventHandler {
    public static void setImageNodeMouseClickedEvent(MouseEvent mouseEvent, VBox baseBox, ImageView imageView) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            int doubleClick = 2;
            if (mouseEvent.getClickCount() == doubleClick) {
                new Slide(Data.imageList.indexOf(imageView));
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
                Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 %d 张图片",
                        Data.imageList.size(), Data.sumOfImage, Data.unit, Data.selectedImageList.size()));
            }
        }
    }

    public static void clearSelectedState() {
        for (int i = 0; i < Data.selectedImageList.size(); i++) {
            Data.selectedImageList.get(i).getParent().getParent().setStyle("-fx-background-color: white");
        }
        Data.selectedImageList.clear();
        Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 0 张图片",
                Data.imageList.size(), Data.sumOfImage, Data.unit));
    }
}
