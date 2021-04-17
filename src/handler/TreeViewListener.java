package handler;

import java.io.File;
import java.nio.file.Files;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import module.Data;

/**
 * @author Platina
 */
public class TreeViewListener {
    private final TreeView<File> treeView;

    public TreeViewListener(TreeView<File> treeView) {
        this.treeView = treeView;
        addTreeViewListener();
        addMouseClickedEventHandler();
        setOnlyShowDirectoryName();
    }

    /**
     * 文件树切换选中节点时加载新目录下的图片
     */
    private void addTreeViewListener() {
        treeView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            Data.nowItem = newValue;
            Data.mainLayoutController.getFolderName().setText(newValue.getValue().toString());
            Data.mainLayoutController.getFlowPane().getChildren().clear();
            // 判断是否已经有Task在运行，有就cancel掉
            if (Data.task != null && Data.task.isRunning()) {
                Data.task.cancel();
            }
            // 没有任务运行过，上一个任务已经被取消或者上一个任务已经完成，执行新任务，加载新目录
            if (Data.task == null || Data.task.isCancelled() || Data.task.isDone()) {
                if (!Data.mainLayoutController.getFlowPane().getChildren().isEmpty()) {
                    Data.mainLayoutController.getFlowPane().getChildren().clear();
                } else {
                    loadImage(newValue);
                }
            }
        });
    }

    /**
     * 添加加载两层目录到点击节点事件
     */
    private void addMouseClickedEventHandler() {
        treeView.getRoot().addEventHandler(TreeItem.<File>branchExpandedEvent(), e -> {
            ObservableList<TreeItem<File>> itemList = e.getTreeItem().getChildren();
            // 如果头尾都是叶子，大概率没加载过子节点，重新进行加载
            // 否则视为加载过的，不再进行加载，提高效率
            if (itemList.get(0).isLeaf() && itemList.get(itemList.size() - 1).isLeaf()) {
                addSubItem(e.getTreeItem().getChildren());
            }
        });
    }

    /**
     * 为提供的列表中的节点添加子节点
     *
     * @param itemList is the list consist of which need to add subItem
     */
    public static void addSubItem(ObservableList<TreeItem<File>> itemList) {
        for (TreeItem<File> item : itemList) {
            File[] tempFiles = item.getValue().listFiles();
            assert tempFiles != null;
            if (tempFiles.length != 0) {
                for (File file : tempFiles) {
                    if (file.isDirectory() && Files.isReadable(file.toPath()) && !file.isHidden()) {
                        TreeItem<File> tempItem = new TreeItem<>(file);
                        item.getChildren().add(tempItem);
                    }
                }
            }
        }
    }

    /**
     * 启动加载图片的线程
     *
     * @param nowValue is the node of the directory to be loaded
     */
    public static void loadImage(TreeItem<File> nowValue) {
        Data.sumOfImage = 0;
        Data.selectedImageList.clear();
        Data.imageNodesList.clear();
        LoadImageNode task = new LoadImageNode(nowValue);
        Thread loadImage = new Thread(task);
        // 设置为守护线程
        loadImage.setDaemon(true);
        Data.task = task;
        Data.mainLayoutController.getFlowPane().getChildren().clear();
        loadImage.start();
    }

    /**
     * 设置目录树只展示所在目录
     */
    private void setOnlyShowDirectoryName() {
        treeView.setCellFactory(callback -> new TreeCell<>() {
                    @Override
                    protected void updateItem(File file, boolean empty) {
                        super.updateItem(file, empty);
                        if (!empty) {
                            String fileName = file.getName();
                            if("".equals(fileName)){
                                fileName = file.getPath();
                            }
                            this.setText(fileName);
                            this.setGraphic(new ImageView(Data.FOLDER_ICON));
                        } else {
                            this.setText("");
                            this.setGraphic(null);
                        }
                    }
                }
        );
    }
}
