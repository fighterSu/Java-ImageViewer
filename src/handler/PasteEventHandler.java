package handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import module.Data;
import module.ImageNode;
import module.Popups;

/**
 * this class is define the event handler for paste event
 *
 * @author Platina
 */
public class PasteEventHandler {
    public PasteEventHandler() {
        // 粘贴地址
        String targetPath = Data.nowItem.getValue().getPath();
        // 是否在同一目录粘贴
        boolean inTheSameFolder = isInTheSameFolder(targetPath);
        // 粘贴成功
        boolean pasteSucceed = true;
        if (!targetPath.equals(Data.lastTargetPath)) {
            Data.numberOfRepeatedPaste = 0;
        }

        for (File file : Data.copyImageList) {
            String fileName = file.getName();
            if (inTheSameFolder) {
                fileName = getFilePrefixName(fileName) + " - 副本" + getFileSuffixName(fileName);
            }

            while (isFileNameAlreadyExists(fileName)) {
                String addedString;
                if (!inTheSameFolder && Data.numberOfRepeatedPaste <= 1) {
                    addedString = " - 副本";
                } else {
                    if (inTheSameFolder && Data.numberOfRepeatedPaste == 1) {
                        Data.numberOfRepeatedPaste++;
                    }
                    addedString = String.format(" (%d)", Data.numberOfRepeatedPaste);
                }
                fileName = getFilePrefixName(fileName) + addedString + getFileSuffixName(fileName);
            }

            try (FileInputStream fileInput = new FileInputStream(file);
                 FileOutputStream fileOutput = new FileOutputStream(targetPath + fileName)) {
                byte[] bytes = new byte[fileInput.available()];
                if (fileInput.read(bytes) != -1) {
                    fileOutput.write(bytes);
                }
            } catch (IOException e) {
                Popups.showExceptionDialog(e);
                pasteSucceed = false;
                Popups.createToolTipBox("粘贴失败", "出现了一些错误，粘贴图片失败", -1, -1);
                break;
            }
        }
        TreeViewListener.loadImage(Data.nowItem);
        if (pasteSucceed) {
            Popups.createToolTipBox("粘贴图片成功", "成功粘贴所复制图片", -1, -1);
            Data.numberOfRepeatedPaste++;
            Data.lastTargetPath = targetPath;
        }

    }

    /**
     * judge whether the target path as same as the source path.
     *
     * @param targetPath is the target path of paste.
     * @return whether the target path as same as the source path or not.
     */
    private boolean isInTheSameFolder(String targetPath) {
        String imagePath = Data.copyImageList.get(0).getPath();
        String sourcePath = imagePath.substring(0, imagePath.lastIndexOf('\\') + 1);
        return targetPath.equals(sourcePath);
    }

    /**
     * judge whether the fileName is already exists in target directory.
     *
     * @param newFileName is the fileName needed to judge.
     * @return whether the fileName is already exists in target directory or
     * not.
     */
    private boolean isFileNameAlreadyExists(String newFileName) {
        for (ImageNode imageNode : Data.imageNodesList) {
            if (imageNode.getImageFile().getName().equals(newFileName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the prefix of the fileName.
     *
     * @param fileName is the fileName.
     * @return the prefix of the fileName.
     */
    private String getFilePrefixName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    /**
     * get the suffix of the fileName.
     *
     * @param fileName is the fileName.
     * @return the suffix of the fileName.
     */
    private String getFileSuffixName(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
