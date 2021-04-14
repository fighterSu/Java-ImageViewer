package handler;

import java.io.File;
import java.util.Date;

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
        // 加载未完成时切换目录
        // 睡眠时被canceled
        File[] files = newValue.getValue().listFiles();
        if (isCancelled()) {
            return  null;
        }
        if (files != null && files.length != 0) {
            for (File file : files) {
                // 进程被取消，退出加载
                if (isCancelled()) {
                    break;
                }
                if (file.isFile() && isImageFile(file)) {
                    ImageNode tempNode = new ImageNode(file);
                    Data.imageNodesList.add(tempNode);
                    // 更新UI
                    Platform.runLater(() -> {
                        // 如果任务取消，结束运行
                        if (isCancelled()) {
                            return;
                        }
                        Data.mainLayoutController.getFlowPane().getChildren().add(tempNode.getBaseBox());
                        Data.sumOfImage += file.length();
                        Data.mainLayoutController.getTipText().setText(
                                String.format("共 %d 张图片( %.2f B ) - 共选中 0 张图片",
                                        Data.imageNodesList.size(),
                                        Data.sumOfImage));
                        Data.mainLayoutController.getFolderInfo().setText("共 " +
                                Data.imageNodesList.size() + " 张图片");
                        if (isCancelled()) {
                            return;
                        }
                        try {
                            // 睡眠一段时间，让UI线程处理其它任务
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
