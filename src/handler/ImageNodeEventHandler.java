package handler;

import java.io.IOException;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import module.Data;
import module.ImageNode;
import module.Slide;

/**
 * @author Platina
 */
public class ImageNodeEventHandler {
	public static void setImageNodeMouseClickedEvent(MouseEvent mouseEvent, ImageNode imageNode) throws IOException {
		if (mouseEvent.getButton() == MouseButton.PRIMARY) {
			int doubleClick = 2;
			if (mouseEvent.getClickCount() == doubleClick) {
				new Slide(Data.imageNodesList.indexOf(imageNode));
			} else {
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
			}
		}
		if (mouseEvent.getButton() == MouseButton.SECONDARY) {
			if (Data.selectedImageList.isEmpty()) {
				imageNode.getBaseBox().setStyle("-fx-background-color: #DEDEDE");
				Data.selectedImageList.add(imageNode.getImageView());
				Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 %d 张图片",
						Data.imageNodesList.size(), Data.sumOfImage, Data.unit, Data.selectedImageList.size()));
			}
		}
	}

	public static void clearSelectedState() {
		for (int i = 0; i < Data.selectedImageList.size(); i++) {
			Data.selectedImageList.get(i).getParent().getParent().setStyle("-fx-background-color: white");
		}
		Data.selectedImageList.clear();
		Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 0 张图片",
				Data.imageNodesList.size(), Data.sumOfImage, Data.unit));
	}
}
