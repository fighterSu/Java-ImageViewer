package module;

import java.io.File;
import java.util.ArrayList;

import controller.MainLayoutController;
import handler.LoadImageNode;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Platina
 */
public class Data {
    public final static Image FOLDER_ICON = new Image("file:src/image/Folder" +
            ".png", 20, 20, false, false, false);

    public static double sumOfImage = 0;
    public static String unit = "B";
    public static LoadImageNode task = null;
    public static TreeItem<File> nowItem;
    public static MainLayoutController mainLayoutController;

    /**
     * 定义电脑屏幕大小
     */
    public static double screenWidth;
    public static double screenHeight;

    /**
     * 存放选中的图片
     */
    public static ArrayList<ImageView> selectedImageList = new ArrayList<>();

    /**
     * 存放复制或者剪贴的图片
     */
    public static ArrayList<File> copyImageList = new ArrayList<>();

    /**
     * 存放该目录下所有图片，用于幻灯片播放
     */
    public static ArrayList<ImageNode> imageNodesList = new ArrayList<>();
}
