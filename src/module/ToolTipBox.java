package module;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tooltip;

/**
 * @author Platina
 */
public class ToolTipBox {
    /**
     * add dialog to prompt information
     * @param title is the title of the dialog
     * @param content is the content of the dialog
     */
    public static void createToolTipBox(String title, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setContentText(content);
        dialog.getDialogPane().setStyle("-fx-background-color: #DEDEDE;-fx-font-size: 13");
        ButtonType okButton = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);
        dialog.show();

        // close the dialog after 2 seconds
        Thread closeDialogThread = new Thread(()->{
            try {
                int sleepTime = 2000;
                Thread.sleep(sleepTime);
                if(dialog.isShowing()){
                    Platform.runLater(dialog::close);
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        closeDialogThread.setDaemon(true);
        closeDialogThread.start();
    }

    /**
     * add a tooltip to the node
     * @param node is the node needed to add the tooltip
     * @param contents is he contents of the tooltip
     */
    public static void createToolTip(Node node, String contents){
        Tooltip tooltip = new Tooltip(contents);
        tooltip.setStyle("-fx-background-color: #DEDEDE;-fx-text-fill:black;" +
                "-fx-font-size:15");
        Tooltip.install(node, tooltip);
    }
}
