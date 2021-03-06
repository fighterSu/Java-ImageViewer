package handler;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import modules.Data;
import modules.ImageNode;
import modules.Popups;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

/**
 * 删除某个文件，并判断是否删除成功
 *
 * @author Platina
 */
public class DeleteEventHandler {
	public DeleteEventHandler() {
		//创建选择弹窗，确认是否删除
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("确认删除");
		alert.setHeaderText("确定要删除选中的图片吗?");
		alert.setContentText("删除操作不可撤销");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
            boolean deleteSucceed = true;
            Data.mainLayoutController.getFlowPane().getChildren().clear();
            for (ImageNode imageNode : Data.selectedImageList) {
                File imageFile = imageNode.getImageFile();
                try {
                    // 调用Files.delete()方法删除选择图片节点对应文件
                    Files.delete(imageFile.toPath());
                } catch (Exception e) {
                    // 捕获异常，展示错误信息弹窗
                    Popups.createToolTipBox("删除失败", "出现了一些错误，删除图片失败", -1, -1);
                    Popups.showExceptionDialog(e);
                    deleteSucceed = false;
                }
            }
			// 重新加载该目录下的图片节点
			TreeViewListener.loadImage(Data.nowItem);
			if (deleteSucceed) {
                Popups.createToolTipBox("删除图片成功", "成功删除选中图片", -1, -1);
            }
		}
	}

}
