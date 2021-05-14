package controller;

import handler.FlowPaneEventHandler;
import handler.TreeViewListener;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import modules.Data;
import modules.Popups;
import modules.Slide;

import java.io.File;
import java.io.IOException;

/**
 * the controller of MainUI.xml
 * 初始化数据集中的数据以及添加事件
 *
 * @author Platina
 */
public class MainLayoutController {
    @FXML
    public ScrollPane scrollPane;
    public ImageView flashIcon;
    public BorderPane rootPane;
    public HBox bottomPane;
    public HBox leftBox;
    public HBox rightBox;
    public HBox topPane;
    public HBox topLeftBox;
    public HBox topRightBox;
    public Text folderName;
    public Text folderInfo;
    public AnchorPane anchorPane;
    @FXML
    private TreeView<File> treeView;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Text tipText;

    @FXML
    private void initialize() {
        // 获取桌面大小
        Rectangle2D screenRectangle = Screen.getPrimary().getBounds();
        Data.screenWidth = screenRectangle.getWidth();
        Data.screenHeight = screenRectangle.getHeight();

        // 获得自动生成的本类的实例对象，用于获取主界面主要节点
        Data.mainLayoutController = this;

        // 初始化根目录
        initRootDirectory();

        // 绑定大小，拉伸界面时保持主要组件显示
        bindLayout();

        // 添加事件监听
        new TreeViewListener(treeView);
        new FlowPaneEventHandler(flowPane);

        // 添加提示信息
        Popups.createToolTip(flashIcon, "幻灯片播放");
    }

    /**
     * 初始化目录树的根目录及目录
     */
    private void initRootDirectory() {
        TreeItem<File> rootItem = new TreeItem<>();
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
        for (File file : File.listRoots()) {
            TreeItem<File> tempItem = new TreeItem<>(file);
            rootItem.getChildren().add(tempItem);
        }
        TreeViewListener.addSubItem(rootItem.getChildren());
    }

    /**
     * 绑定主界面主要部件的大小到其父组件上
     * 拉伸界面时保持主组件的正常显示
     */
    private void bindLayout() {
        // 将flowPane的宽度，高度绑定到scrollPane的宽度上，实现flowPane自动拉伸，收缩
        flowPane.prefWidthProperty().bind(scrollPane.widthProperty());
        flowPane.prefHeightProperty().bind(scrollPane.heightProperty());

        // 将HBox的宽度和borderPane的宽度绑定
        bottomPane.prefWidthProperty().bind(rootPane.widthProperty());
        // 将子VBox宽度绑定到底部VBox的宽度的一半
        leftBox.prefWidthProperty().bind(bottomPane.widthProperty());

        // 将顶部左VBox的宽度和目录树宽度绑定，实现右VBox和flowPane对齐
        topLeftBox.prefWidthProperty().bind(treeView.widthProperty());
    }

    /**
     * 定义幻灯片图标鼠标点击事件
     *
     * @param mouseEvent is the mouse event that we clicked on the flashIcon.
     */
    @FXML
    private void flashIconMouseClickedEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (Data.imageNodesList.isEmpty()) {
                Popups.createToolTipBox("无可播放图片", "暂无可播放图片，请在有图片的目录下点击幻灯片播放图标", -1, -1);
            } else {
                // 播放幻灯片
                try {
                    new Slide(0);
                } catch (IOException e) {
                    Popups.showExceptionDialog(e);
                }
            }
        }
    }

    public Text getTipText() {
        return tipText;
    }

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public Text getFolderInfo() {
        return folderInfo;
    }

    public Text getFolderName() {
        return folderName;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }
}
