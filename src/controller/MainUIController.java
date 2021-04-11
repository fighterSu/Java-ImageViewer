package controller;

import handler.FlashIconEventHandler;
import handler.FlowPaneEventHandler;
import handler.TreeViewListener;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import module.Data;

import java.io.File;

/**
 * @author Platina
 */
public class MainUIController {
    @FXML
    public ScrollPane scrollPane;
    public ImageView flashIcon;
    public BorderPane rootPane;
    public HBox bottomPane;
    public HBox leftBox;
    public HBox rightBox;
    @FXML
    private TreeView<File> treeView;
    @FXML
    private FlowPane flowPane;
    @FXML
    private Text tipText;

    public MainUIController() {
    }

    @FXML
    private void initialize() {
        //初始化数据
        initData();
        // 初始化根目录
        initRootDirectory();
        //绑定大小，拉伸界面时保持主要组件显示
        bindLayout();

        // 添加事件监听
        new TreeViewListener(treeView);
        new FlowPaneEventHandler(flowPane);
        new FlashIconEventHandler(flashIcon);
        // 添加提示信息
        Tooltip flashTip = new Tooltip("幻灯片播放");
        flashTip.setStyle("-fx-background-color: #DEDEDE;-fx-text-fill:black;-fx-font-size:14");
        Tooltip.install(flashIcon, flashTip);
    }

    private static void initRootDirectory() {
        TreeItem<File> rootItem = new TreeItem<>();
        Data.treeView.setRoot(rootItem);
        Data.treeView.setShowRoot(false);
        for (File file : File.listRoots()) {
            TreeItem<File> tempItem = new TreeItem<>(file,
                    new ImageView(Data.FOLDER_ICON));
            rootItem.getChildren().add(tempItem);
        }
        TreeViewListener.addSubItem(rootItem.getChildren());
    }

    private void bindLayout() {
        // 将flowPane的宽度，高度绑定到scrollPane的宽度上，实现flowPane自动拉伸，收缩
        flowPane.prefWidthProperty().bind(scrollPane.widthProperty());
        flowPane.prefHeightProperty().bind(scrollPane.heightProperty());

        // 将HBox的宽度和borderPane的宽度绑定
        bottomPane.prefWidthProperty().bind(rootPane.widthProperty());
        //将子VBox宽度绑定到底部VBox的宽度的一半
        leftBox.prefWidthProperty().bind(bottomPane.widthProperty());
        rightBox.prefWidthProperty().bind(bottomPane.widthProperty());
    }

    private void initData() {
        // 初始化数据集中的数据
        Data.treeView = treeView;
        Data.tipText = tipText;
        Data.flowPane = flowPane;
    }

}
