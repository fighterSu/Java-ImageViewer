package modules;

import controller.MainLayoutController;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * 记录一些应用过程之中多处需要用到的数据结构
 *
 * @author Platina
 */
public class Data {
	/**
	 * 指示当前目录图片文件总大小
	 */
	public static double sumOfImage = 0;

	/**
	 * 指示当前目录图片文件总大小的单位
	 */
	public static String unit;

	/**
	 * 记录当前所在目录树节点，用与获取粘贴目标文件夹
	 */
	public static TreeItem<File> nowItem;

	/**
	 * 记录自动生成的主布局的控制器类对象
	 */
	public static MainLayoutController mainLayoutController;

	/**
	 * 记录Main函数里面的Scene 用于添加快捷键
	 */
	public static Scene primaryScene;

	/**
	 * 记录Main函数里面的Stage 用于子窗口绑定
	 */
	public static Stage primaryStage;

	/**
	 * 定义电脑屏幕大小
	 */
	public static double screenWidth;
	public static double screenHeight;

	/**
	 * 存放选中的图片
	 */
	public static ArrayList<ImageNode> selectedImageList = new ArrayList<>();

	/**
	 * 存放复制或者剪贴的图片
	 */
	public static ArrayList<File> copyImageList = new ArrayList<>();

	/**
	 * 存放该目录下所有图片，用于幻灯片播放
	 */
	public static ArrayList<ImageNode> imageNodesList = new ArrayList<>();
}
