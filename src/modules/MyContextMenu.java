package modules;

import handler.DeleteEventHandler;
import handler.PasteEventHandler;
import handler.RenameEventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * 右键菜单功能设计
 *
 * @author Platina、lvqingfeng
 */
public class MyContextMenu {
    private ContextMenu contextMenu;

    /**
     * 根据节点 node 类型添加对应右键菜单
     *
     * @param node        is the target node needed to add context menu.
     * @param isImageNode is just whether node is ImageNode or not.
     */
    public MyContextMenu(Node node, boolean isImageNode) {
        if (isImageNode) {
            createImageMenu(node);
        } else {
            createBlankMenu(node);
        }
    }

    public void createImageMenu(Node node) {
        contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("删除");
        MenuItem copyItem = new MenuItem("复制");
        MenuItem renameItem = new MenuItem("重命名");
        contextMenu.getItems().addAll(deleteItem, copyItem, renameItem);
        // 为节点添加右键事件，显示右键菜单
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else {
                if (contextMenu.isShowing()) {
                    contextMenu.hide();
                }
            }
        });

        deleteItem.setOnAction(actionEvent -> {
            if (Data.selectedImageList.isEmpty()) {
                Popups.createToolTipBox("未选中图片", "请选中图片后再使用删除功能！", -1, -1);
            } else {
                new DeleteEventHandler();
            }
        });

        // 设置删除功能的快捷键
        KeyCombination deleteKeyCombination = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
        Data.primaryScene.getAccelerators().put(deleteKeyCombination, deleteItem::fire);
        deleteItem.setAccelerator(deleteKeyCombination);


        copyItem.setOnAction(actionEvent -> {
            if (Data.selectedImageList.isEmpty()) {
                Popups.createToolTipBox("未选中图片", "请选中图片后再复制！", -1, -1);
            } else {
                Data.copyImageList.clear();
                for (ImageNode imageNode : Data.selectedImageList) {
                    Data.copyImageList.add(imageNode.getImageFile());
                }
                Popups.createToolTipBox("复制成功", "已成功复制选中图片！", -1, -1);
            }
        });

        KeyCombination copyKeyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        Data.primaryScene.getAccelerators().put(copyKeyCombination, copyItem::fire);
        copyItem.setAccelerator(copyKeyCombination);

        renameItem.setOnAction(actionEvent -> {
            if (Data.selectedImageList.isEmpty()) {
                Popups.createToolTipBox("未选中图片", "请选中图片后再进行重命名！", -1, -1);
            } else {
                new RenameEventHandler();
            }
        });

        KeyCombination renameKeyCombination = new KeyCodeCombination(KeyCode.F2);
        Data.primaryScene.getAccelerators().put(renameKeyCombination, renameItem::fire);
        renameItem.setAccelerator(renameKeyCombination);
    }

    public void createBlankMenu(Node node) {
        contextMenu = new ContextMenu();
        MenuItem pasteItem = new MenuItem("粘贴");
        MenuItem selectAllItem = new MenuItem("全选");
        contextMenu.getItems().addAll(pasteItem, selectAllItem);
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Node clickNode = mouseEvent.getPickResult().getIntersectedNode();
            if (!(clickNode instanceof Label) && !(clickNode instanceof Text) && !(clickNode instanceof VBox)
                    && mouseEvent.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(node, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            } else {
                if (contextMenu.isShowing()) {
                    contextMenu.hide();
                }
            }
        });

        pasteItem.setOnAction(actionEvent -> {
            if (Data.copyImageList.isEmpty()) {
                Popups.createToolTipBox("未复制文件", "还未复制过任何文件，请先复制图片再使用粘贴功能", -1, -1);
            } else {
                new PasteEventHandler();
            }
        });

        selectAllItem.setOnAction(actionEvent -> {
            Data.selectedImageList.clear();
            for (ImageNode imageNode : Data.imageNodesList) {
                imageNode.setStyle("-fx-background-color: #DEDEDE");
                Data.selectedImageList.add(imageNode);
                Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 %d 张图片",
                        Data.imageNodesList.size(), Data.sumOfImage, Data.unit, Data.selectedImageList.size()));
            }
        });

        KeyCombination pasteKeyCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        Data.primaryScene.getAccelerators().put(pasteKeyCombination, pasteItem::fire);
        pasteItem.setAccelerator(pasteKeyCombination);

        KeyCombination selectAllKeyCombination = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
        Data.primaryScene.getAccelerators().put(selectAllKeyCombination, selectAllItem::fire);
        selectAllItem.setAccelerator(selectAllKeyCombination);
    }
}
