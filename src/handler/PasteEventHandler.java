package handler;

import modules.Data;
import modules.ImageNode;
import modules.Popups;

import java.io.*;

/**
 * 粘贴文件的相关处理
 *
 * @author Platina
 */
public class PasteEventHandler {
    public PasteEventHandler() {
        // 粘贴地址
        StringBuilder targetPath = new StringBuilder(Data.nowItem.getValue().getPath());
        // 是否在同一目录粘贴
        boolean inTheSameFolder = isInTheSameFolder(targetPath.toString());
        // 是否粘贴成功
        boolean pasteSucceed = true;

        for (File imageFile : Data.copyImageList) {
            String imageFileName = imageFile.getName();

            // 如果是粘贴在复制的那个文件夹，必定名字重复，直接先加个 - 副本
            if (inTheSameFolder) {
                imageFileName = createNewFileName(imageFileName, " - 副本");
            } else {
                // 如果不是粘贴在复制的那个文件夹，并且名字已经存在了，先加一个 - 副本
                if (isFileNameAlreadyExists(imageFileName)) {
                    imageFileName = createNewFileName(imageFileName, " - 副本");
                }
            }

            // 如果加了 - 副本 还是重复，那么要确定编号序号 即类似 photo(d).jpg 之中的 d 到底是哪个数字
            String addedString = "";
            int serialNumber = 1;
            while (isFileNameAlreadyExists(createNewFileName(imageFileName, addedString))) {
                // 从 2 开始编号，因此采用 ++serialNumber
                addedString = String.format(" (%d)", ++serialNumber);
            }

            // 根据添加字符串拼接新图片文件名
            imageFileName = createNewFileName(imageFileName, addedString);


            // 在非根目录粘贴时要给目标路径加个'\', 不然无法复制文件到真正的目标路径
            if (!targetPath.toString().endsWith("\\")) {
                targetPath.append('\\');
            }

            // 复制文件
            try (BufferedInputStream fileInput =
                         new BufferedInputStream(new FileInputStream(imageFile));
                 BufferedOutputStream fileOutput =
                         new BufferedOutputStream(new FileOutputStream(targetPath + imageFileName))) {
                byte[] bytes = new byte[1024];
                int i;
                while ((i = fileInput.read(bytes)) != -1) {
                    fileOutput.write(bytes, 0, i);
                }
            } catch (IOException e) {
                Popups.showExceptionDialog(e);
                pasteSucceed = false;
                Popups.createToolTipBox("粘贴失败", "出现了一些错误，粘贴图片失败", -1, -1);
                break;
            }
        }

        if (pasteSucceed) {
            Popups.createToolTipBox("粘贴图片成功", "成功粘贴所复制图片", -1, -1);
        }
        TreeViewListener.loadImage(Data.nowItem);
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
     * 判断一个文件名是否已经在目录存在
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
     * 根据添加字符串拼接新图片文件名
     *
     * @param imageFileName is original image file name.
     * @param addedString   is the string that will be added to the imageFileName
     * @return the new imageFileName
     */
    private String createNewFileName(String imageFileName, String addedString) {
        String prefixName = imageFileName.substring(0, imageFileName.lastIndexOf('.'));
        String suffixName = imageFileName.substring(imageFileName.lastIndexOf('.'));
        return prefixName + addedString + suffixName;
    }
}
