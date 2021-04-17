package handler;

import java.io.File;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import module.Data;
import module.ImageNode;
import module.Popups;

/**
 * this class is used to load images from selected directory
 *
 * @author Platina
 */
public class LoadImageNode extends Task<Number> {
    private final File[] files;

    public LoadImageNode(TreeItem<File> newValue) {
        files = newValue.getValue().listFiles();
        // 任务完成更新提示框信息
        this.setOnSucceeded(workerStateEvent -> {
            setTheUnitOfTheTotalSizeOfThePicture();
            Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 0 张图片",
                    Data.imageNodesList.size(), Data.sumOfImage, Data.unit));
            Data.mainLayoutController.getFolderInfo().setText("共 " + Data.imageNodesList.size() + " 张图片");
            Data.mainLayoutController.getFlowPane().requestFocus();
        });
    }

    @Override
    protected Number call() {
        for (File file : files) {
            if (this.isCancelled()) {
                break;
            }
            if (file.isFile() && isImageFile(file)) {
                ImageNode tempNode = new ImageNode(file);
                Data.imageNodesList.add(tempNode);
                // 更新UI
                Platform.runLater(() -> {
                    // 如果任务取消，结束运行
                    if (this.isCancelled()) {
                        return;
                    }
                    Data.mainLayoutController.getFlowPane().getChildren().add(tempNode.getBaseBox());
                    Data.sumOfImage += file.length();
                    Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f B ) - 共选中 0 张图片",
                            Data.imageNodesList.size(), Data.sumOfImage));
                    Data.mainLayoutController.getFolderInfo().setText("共 " + Data.imageNodesList.size() + " 张图片");
                });
                try {
                    // 睡眠一段时间，让UI线程处理其它任务
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    if (isCancelled()) {
                        break;
                    }
                }
            }
        }
        return null;
    }

    /**
     * determine whether the file is an image file
     *
     * @param file is the file needed to determine
     * @return whether the file is image file or not
     */
    private boolean isImageFile(File file) {
        String fileName = file.getName().toLowerCase();
        return (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".gif")
                || fileName.endsWith(".png") || fileName.endsWith(".bmp"));
    }

    /**
     * 设置图像文件总大小的单位
     */
    private static void setTheUnitOfTheTotalSizeOfThePicture() {
        String unit = "B";
        int kb = 1024;
        int mb = 1024 * 1024;
        int gb = 1024 * 1024 * 1024;
        if (Data.sumOfImage >= gb) {
            Data.sumOfImage /= gb;
            unit = "GB";
        } else {
            if (Data.sumOfImage >= mb) {
                Data.sumOfImage /= mb;
                unit = "MB";
            } else if (Data.sumOfImage >= kb) {
                Data.sumOfImage /= kb;
                unit = "KB";
            }
        }
        Data.unit = unit;
    }
}
