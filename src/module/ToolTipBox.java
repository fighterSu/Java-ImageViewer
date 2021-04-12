package module;

import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 * @author Platina
 */
public class ToolTipBox {
    public static void createToolTipBox(String title, String content) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setContentText(content);
        dialog.getDialogPane().setStyle("-fx-background-color: #DEDEDE;-fx-font-size: 13");
        ButtonType okButton = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButton);
        dialog.show();

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
}
