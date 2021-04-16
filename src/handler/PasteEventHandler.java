package handler;

import module.Data;
import module.ImageNode;
import module.ToolTipBox;

import java.io.*;

/**
 * @author Platina
 */
public class PasteEventHandler {
    public PasteEventHandler() {
        String targetPath = Data.nowItem.getValue().getPath();
        boolean inTheSameFolder = isInTheSameFolder(targetPath);
        boolean copySucceed = true;
        if (!targetPath.equals(Data.lastTargetPath)) {
            Data.numberOfRepeatedPaste = 0;
        }

        for (File file : Data.copyImageList) {
            String fileName = file.getName();
            if (inTheSameFolder) {
                fileName = getFilePrefixName(fileName) +
                        " - 副本" + getFileSuffixName(fileName);
            }

            while (isFileNameAlreadyExists(fileName)) {
                String addedString;
                if (!inTheSameFolder && Data.numberOfRepeatedPaste <= 1) {
                    addedString = " - 副本";
                } else {
                    if (inTheSameFolder && Data.numberOfRepeatedPaste == 1) {
                        Data.numberOfRepeatedPaste++;
                    }
                    addedString = String.format(" (%d)",
                            Data.numberOfRepeatedPaste);
                }
                fileName = getFilePrefixName(fileName)
                        + addedString
                        + getFileSuffixName(fileName);
            }
            try (
                    FileInputStream fileInput = new FileInputStream(file);
                    FileOutputStream fileOutput = new FileOutputStream(targetPath + fileName)
            ) {
                byte[] bytes = new byte[fileInput.available()];
                if (fileInput.read(bytes) != -1) {
                    fileOutput.write(bytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
                copySucceed = false;
                ToolTipBox.createToolTipBox("粘贴失败", "出现了一些错误，粘贴图片失败");
                break;
            }
        }
        TreeViewListener.loadImage(Data.nowItem);
        if (copySucceed) {
            ToolTipBox.createToolTipBox("粘贴图片成功", "成功粘贴所复制图片");
            Data.numberOfRepeatedPaste++;
            Data.lastTargetPath = targetPath;
        }

    }

    private boolean isInTheSameFolder(String targetPath) {
        String imagePath = Data.copyImageList.get(0).getPath();
        String sourcePath = imagePath.substring(0, imagePath.lastIndexOf('\\') + 1);
        return targetPath.equals(sourcePath);
    }

    private boolean isFileNameAlreadyExists(String newFileName) {
        for (ImageNode imageNode : Data.imageNodesList) {
            if (imageNode.getImageFile().getName().equals(newFileName)) {
                return true;
            }
        }
        return false;
    }

    private String getFilePrefixName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    private String getFileSuffixName(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
