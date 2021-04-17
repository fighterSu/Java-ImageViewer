package module;

import java.io.File;

import handler.DeleteEventHandler;
import handler.PasteEventHandler;
import handler.RenameEventHandler;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Platina
 */
public class MyContextMenu {
    private ContextMenu contextMenu;

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
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(node, e.getScreenX(), e.getScreenY());
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

        KeyCombination deleteKeyCombination = new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN);
        Data.scene.getAccelerators().put(deleteKeyCombination, deleteItem::fire);
        deleteItem.setAccelerator(deleteKeyCombination);

        copyItem.setOnAction(actionEvent -> {
            if (Data.selectedImageList.isEmpty()) {
                Popups.createToolTipBox("未选中图片", "请选中图片后再复制！", -1, -1);
            } else {
                Data.copyImageList.clear();
                for (ImageView imageView : Data.selectedImageList) {
                    String imageUrl = imageView.getImage().getUrl();
                    String imagePath = imageUrl.substring(imageUrl.indexOf(':') + 1);
                    File imageFile = new File(imagePath);
                    Data.copyImageList.add(imageFile);
                }
                Popups.createToolTipBox("复制成功", "已成功复制选中图片！", -1, -1);
                Data.numberOfRepeatedPaste = 0;
                Data.lastTargetPath = null;
            }
        });

        KeyCombination copyKeyCombination = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN);
        Data.scene.getAccelerators().put(copyKeyCombination, copyItem::fire);
        copyItem.setAccelerator(copyKeyCombination);

        renameItem.setOnAction(actionEvent -> {
            if (Data.selectedImageList.isEmpty()) {
                Popups.createToolTipBox("未选中图片", "请选中图片后再进行重命名！", -1, -1);
            } else {
                new RenameEventHandler();
            }
        });

        KeyCombination renameKeyCombination = new KeyCodeCombination(KeyCode.F2);
        Data.scene.getAccelerators().put(renameKeyCombination, renameItem::fire);
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
                imageNode.getBaseBox().setStyle("-fx-background-color: #DEDEDE");
                Data.selectedImageList.add(imageNode.getImageView());
                Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 %d 张图片",
                        Data.imageNodesList.size(), Data.sumOfImage, Data.unit, Data.selectedImageList.size()));
            }
        });

        KeyCombination pasteKeyCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN);
        Data.scene.getAccelerators().put(pasteKeyCombination, pasteItem::fire);
        pasteItem.setAccelerator(pasteKeyCombination);

        KeyCombination selectAllKeyCombination = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);
        Data.scene.getAccelerators().put(selectAllKeyCombination, selectAllItem::fire);
        selectAllItem.setAccelerator(selectAllKeyCombination);
    }
}
