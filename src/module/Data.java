package module;

import java.io.File;
import java.util.ArrayList;

import handler.LoadImageNode;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

/**
 * @author Platina
 */
public class Data {
    public final static Image FOLDER_ICON = new Image("file:src/layout/Folder.png",
            20, 20, false, false, false);

    /**
     * 获得fxml里面设计好的界面
     */
    public static TreeView<File> treeView;
    public static Text tipText = null;
    public static FlowPane flowPane;
    public static int numberOfImage = 0;
    public static double sumOfImage = 0;
    public static String unit = "B";
    public static LoadImageNode task = null;

    /**
     * 存放选中的图片
     */
    public static ArrayList<ImageView> selectedImageList = new ArrayList<>();

    /**
     * 存放复制或者剪贴的图片
     */
    public static ArrayList<ImageView> copyImageList = new ArrayList<>();

    /**
     * 存放该目录下所有图片，用于幻灯片播放
     */
    public static ArrayList<ImageView> imageList = new ArrayList<>();
}
