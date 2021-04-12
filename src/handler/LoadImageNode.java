package handler;

import java.io.File;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import module.Data;
import module.ImageNode;

/**
 * @author Platina
 */
public class LoadImageNode extends Task<Number> {
    private final TreeItem<File> newValue;

    public LoadImageNode(TreeItem<File> newValue) {
        this.newValue = newValue;
    }

    @Override
    protected Number call() {
        // 多次检测是否被canceled
        // 加载未完成切换目录
        // 睡眠时被canceled
        Data.sumOfImage = 0;
        Data.selectedImageList.clear();
        Data.imageList.clear();
        Data.mainLayoutController.getFolderName().setText(newValue.getValue().toString());
        File[] files = newValue.getValue().listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                // 进程被取消，退出加载
                if (isCancelled()) {
                    break;
                }
                if (file.isFile() && isImageFile(file)) {
                    ImageNode tempNode = new ImageNode(file.getName(), file.getAbsolutePath());
                    Data.imageList.add((ImageView) tempNode.getImageLabel().getGraphic());
                    // 更新UI
                    Platform.runLater(() -> {
                        // 如果任务取消，结束运行
                        if (isCancelled()) {
                            return;
                        }
                        Data.mainLayoutController.getFlowPane().getChildren().add(tempNode.getBaseBox());
                        Data.sumOfImage += file.length();
                        Data.mainLayoutController.getTipText().setText(String.format(
                                "共 %d 张图片( %.2f B ) - 共选中 0 张图片",
                                Data.imageList.size(), Data.sumOfImage));
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            if (isCancelled()) {
                                return;
                            }
                            e.printStackTrace();
                        }
                    });
                }
            }
        }
        return null;
    }

    private boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
                || fileName.endsWith(".gif") || fileName.endsWith(".png")
                || fileName.endsWith(".bmp"));
    }
}
