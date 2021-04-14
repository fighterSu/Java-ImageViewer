package module;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


/**
 * @author Platina
 */
public class MyContextMenu {
    private ContextMenu contextMenu;

    public MyContextMenu(Node node, boolean isImageNode) {
        if(isImageNode){
            imageMenu(node);
        } else {
            blankMenu(node);
        }
    }

    public void imageMenu(Node node){
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
    }

    public void blankMenu(Node node){
        contextMenu = new ContextMenu();
        MenuItem pasteItem = new MenuItem("粘贴");
        MenuItem selectAllItem = new MenuItem("全选");
        contextMenu.getItems().addAll(pasteItem, selectAllItem);
    }
}
