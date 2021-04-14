package handler;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import module.Data;
import module.ToolTipBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * @author Platina
 */
public class DeleteEventHandler {
    public DeleteEventHandler() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认删除");
        alert.setHeaderText("确定要删除选中的图片吗?");
        alert.setContentText("删除操作不可撤销");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            Data.mainLayoutController.getFlowPane().getChildren().clear();
            for (ImageView imageView : Data.selectedImageList) {
                String imagePath = imageView.getImage().getUrl();
                File imageFile =
                        new File(imagePath.substring(imagePath.indexOf(":") + 1));
                Files.delete(imageFile.toPath());
            }
            TreeViewListener.loadImage(Data.nowItem);
            ToolTipBox.createToolTipBox("删除图片成功", "成功删除选中图片");
        }
    }
}
