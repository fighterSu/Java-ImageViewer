package handler;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import modules.Data;
import modules.ImageNode;
import modules.Slide;

import java.io.IOException;

/**
 * this class is used to add MouseClickListener and
 * clear selected state of imageNode
 *
 * @author Platina
 */
public class ImageNodeEventHandler {
    public static void setImageNodeMouseClickedEvent(MouseEvent mouseEvent, ImageNode imageNode) throws IOException {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            boolean hasSelected = "-fx-background-color: #DEDEDE".equals(imageNode.getBaseBox().getStyle());
            imageNode.getBaseBox().setStyle("-fx-background-color: #DEDEDE");
            if (!mouseEvent.isControlDown()) {
                clearSelectedState();
                if (hasSelected) {
                    imageNode.getBaseBox().setStyle("-fx-background-color: #DEDEDE");
                }
            }
            if (Data.selectedImageList.contains(imageNode.getImageView())) {
                Data.selectedImageList.remove(imageNode.getImageView());
                imageNode.getBaseBox().setStyle("-fx-background-color: white");
            } else {
                Data.selectedImageList.add(imageNode.getImageView());
            }
            Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 %d 张图片",
                    Data.imageNodesList.size(), Data.sumOfImage, Data.unit, Data.selectedImageList.size()));

            int doubleClick = 2;
            if (mouseEvent.getClickCount() == doubleClick) {
                new Slide(Data.imageNodesList.indexOf(imageNode));
            }
        }

        // 在图片节点上右键时，若未选中多个节点，则选中鼠标点击的图片节点
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            if (Data.selectedImageList.size() <= 1) {
                clearSelectedState();
                imageNode.getBaseBox().setStyle("-fx-background-color: #DEDEDE");
                Data.selectedImageList.add(imageNode.getImageView());
                Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 1 张图片",
                        Data.imageNodesList.size(), Data.sumOfImage, Data.unit));
            }
        }
    }

    /**
     * 清除选中状态
     */
    public static void clearSelectedState() {
        for (int i = 0; i < Data.selectedImageList.size(); i++) {
            Data.selectedImageList.get(i).getParent().getParent().setStyle("-fx-background-color: white");
        }
        Data.selectedImageList.clear();
        Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 0 张图片",
                Data.imageNodesList.size(), Data.sumOfImage, Data.unit));
    }
}
