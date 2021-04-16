package module;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * @author Platina
 */
public class Popups {
	/**
	 * add dialog to prompt information
	 *
	 * @param title   is the title of the dialog
	 * @param content is the content of the dialog
	 */
	public static void createToolTipBox(String title, String content, double x, double y) {
		Dialog<String> dialog = new Dialog<>();
		if (x >= 0) {
			dialog.setX(x);
		}
		if (y >= 0) {
			dialog.setY(y);
		}
		dialog.setTitle(title);
		dialog.setContentText(content);
		dialog.getDialogPane().setStyle("-fx-background-color: white;" + "-fx-font-size: 13");
		ButtonType okButton = new ButtonType("确定", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(okButton);
		dialog.show();
		autoCloseDialog(dialog);
	}

	/**
	 * add a tooltip to the node
	 *
	 * @param node     is the node needed to add the tooltip
	 * @param contents is he contents of the tooltip
	 */
	public static void createToolTip(Node node, String contents) {
		Tooltip tooltip = new Tooltip(contents);
		tooltip.setStyle("-fx-background-color: #white;-fx-text-fill:black;" + "-fx-font-size:15");
		Tooltip.install(node, tooltip);
	}

	/**
	 * 展示错误信息弹窗
	 *
	 * @param exception is the exception caught
	 */
	public static void showExceptionDialog(Exception exception) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("错误弹窗");
		alert.setHeaderText("发生了一个错误");
		alert.setContentText(exception.getMessage());

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		exception.printStackTrace(printWriter);
		String exceptionText = stringWriter.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setMaxHeight(Double.MAX_VALUE);
		textArea.setMaxWidth(Double.MAX_VALUE);

		GridPane.setHgrow(textArea, Priority.ALWAYS);
		GridPane.setVgrow(textArea, Priority.ALWAYS);

		GridPane exceptionPane = new GridPane();
		exceptionPane.setMaxWidth(Double.MAX_VALUE);
		exceptionPane.add(label, 0, 0);
		exceptionPane.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(exceptionPane);

		alert.showAndWait();
	}

	/**
	 * 自动关闭弹窗
	 *
	 * @param dialog is the dialog needed to close
	 */
	public static void autoCloseDialog(Dialog<String> dialog) {
		// close the dialog after 2 seconds
		Thread closeDialogThread = new Thread(() -> {
			try {
				int sleepTime = 2000;
				Thread.sleep(sleepTime);
				if (dialog.isShowing()) {
					Platform.runLater(dialog::close);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		closeDialogThread.setDaemon(true);
		closeDialogThread.start();
	}
}
