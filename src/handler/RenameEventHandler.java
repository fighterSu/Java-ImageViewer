package handler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import module.Data;
import module.ImageNode;
import module.Popups;

/**
 * @author Platina
 */
public class RenameEventHandler {
	public RenameEventHandler() {
		// 单个文件重命名
		if (Data.selectedImageList.size() == 1) {
			renameSingleFiles();
		} else {
			// 多个文件重命名
			renameMultipleFiles();
		}
	}

	/**
	 * 重命名单个文件
	 */
	private void renameSingleFiles() {
		ImageNode targetImageNode = getSelectedImageNode()[0];
		String targetImageName = targetImageNode.getImageFile().getName();
		while (true) {
			TextInputDialog dialog = new TextInputDialog(
					targetImageName.substring(0, targetImageName.lastIndexOf('.')));
			dialog.initOwner(Data.stage);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("重命名文件");
			dialog.setHeaderText("文件名不能包含右侧任何字符 / : * ? \\ \" < >");
			String invalidInput = "[^/:*?\\\\<>\"]+";
			dialog.setContentText("请输入新的⽂件名");
			Optional<String> result = dialog.showAndWait();
			String newFilename;
			if (result.isPresent()) {
				newFilename = result.get();
				if (!newFilename.matches(invalidInput)) {
					Popups.createToolTipBox("输入文件名非法", "输入的文件名非法，不能含有下列字符\n / : * ? \\ \" < >", dialog.getX(),
							dialog.getY() + dialog.getWidth());
					continue;
				}
				if (newFilename.equals(getFilePrefixName(targetImageNode.getImageFile().getName()))) {
					return;
				}
				Path targetFilePath = targetImageNode.getImageFile().toPath();
				boolean renameSucceed = true;
				try {
					Files.move(targetFilePath, targetFilePath
							.resolveSibling(newFilename + getFileSuffixName(targetImageNode.getImageFile().getName())));
				} catch (IOException e) {
					Popups.showExceptionDialog(e);
					renameSucceed = false;
				}
				if (renameSucceed) {
					Popups.createToolTipBox("重命名成功", "成功重命名选中文件", -1, -1);
					TreeViewListener.loadImage(Data.nowItem);
				}
			}
			break;
		}
	}

	/**
	 * 重命名多个文件
	 */
	private void renameMultipleFiles() {
		GridPane renamePane = new GridPane();
		renamePane.setHgap(10);
		renamePane.setVgap(10);
		renamePane.setPadding(new Insets(10));
		renamePane.setAlignment(Pos.BASELINE_CENTER);

		TextField imageNamePrefix = new TextField("名称前缀(不能包含 / : * ? \\ \" < >)");
		TextField startNumber = new TextField("起始编号(请输入位数小于编号位数的正整数)");
		TextField numberOfDigits = new TextField("编号位数(1-9)");
		renamePane.add(new Label("名称前缀: "), 0, 0);
		renamePane.add(imageNamePrefix, 1, 0);
		renamePane.add(new Label("起始编号: "), 0, 1);
		renamePane.add(startNumber, 1, 1);
		renamePane.add(new Label("编号位数: "), 0, 2);
		renamePane.add(numberOfDigits, 1, 2);

		Button okButton = new Button("确定");
		renamePane.add(okButton, 0, 3);
		renamePane.add(new Label("名称前缀不能包含右侧任何字符 / : * ? \\ \" < >"), 1, 3);

		Scene scene = new Scene(renamePane, 380, 150);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("批量重命名");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setAlwaysOnTop(true);
		stage.initOwner(Data.stage);
		stage.show();

		// 获取选中的图片节点
		ImageNode[] selectedImageNodes = getSelectedImageNode();
		okButton.setOnAction(actionEvent -> {
			int startNumbers = 0;
			int numberOfDigit = 0;
			int maxStartNumbers = 0;
			boolean allInputsAreValid = true;
			String warningMessage = "";
			String validInput = "[^/:*?\\\\<>\"]+";
			if (!imageNamePrefix.getText().matches(validInput)) {
				warningMessage += "名称前缀：不能包含右侧任何字符 / : * ? \\ \" < >\n";
				allInputsAreValid = false;
			}
			String positiveInteger = "[0-9]+";
			if (!startNumber.getText().matches(positiveInteger)) {
				warningMessage += "起始编号：请输入一个正整数！\n";
				allInputsAreValid = false;
			} else {
				startNumbers = Integer.parseInt(startNumber.getText());
			}
			String nonNegativeInteger = "[1-9]";
			if (!numberOfDigits.getText().matches(nonNegativeInteger)) {
				warningMessage += "编号位数：请输入1-9之间的一个整数！";
				allInputsAreValid = false;
			} else {
				numberOfDigit = Integer.parseInt(numberOfDigits.getText());
				maxStartNumbers = (int) Math.pow(10, numberOfDigit) - selectedImageNodes.length;
			}

			if (startNumbers > maxStartNumbers) {
				warningMessage += "起始编号过大，最大为：" + maxStartNumbers;
				allInputsAreValid = false;
			}

			if (!allInputsAreValid) {
				Popups.createToolTipBox("输入非法", warningMessage, stage.getX(), stage.getY() + stage.getHeight() + 10);
			} else {
				int indexOfImageFile = (int) Math.pow(10, numberOfDigit) + startNumbers;
				boolean renameSucceed = true;
				String imageNamePrefixName = imageNamePrefix.getText();
				for (ImageNode imageNode : selectedImageNodes) {
					Path targetFilePath = imageNode.getImageFile().toPath();
					String fileName = imageNamePrefixName + Integer.toString(indexOfImageFile).substring(1);
					try {
						Files.move(targetFilePath, targetFilePath
								.resolveSibling(fileName + getFileSuffixName(imageNode.getImageFile().getName())));
					} catch (IOException e) {
						stage.close();
						Popups.showExceptionDialog(e);
						renameSucceed = false;
						break;
					}
					indexOfImageFile++;
				}
				if (renameSucceed) {
					Popups.createToolTipBox("重命名成功", "成功重命名选中文件", stage.getX(), stage.getY() + stage.getHeight() + 10);
					stage.close();
					TreeViewListener.loadImage(Data.nowItem);
				}
			}
		});
	}

	private String getFilePrefixName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf('.'));
	}

	private String getFileSuffixName(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'));
	}

	/**
	 * 获得选中的图片的ImageNode对象
	 *
	 * @return the array of selected ImageNode
	 */
	private ImageNode[] getSelectedImageNode() {
		ImageNode[] tempList = new ImageNode[Data.selectedImageList.size()];
		int indexOfTargetImage = 0;
		for (ImageView imageView : Data.selectedImageList) {
			for (int i = 0; i < Data.imageNodesList.size(); i++) {
				if (imageView == Data.imageNodesList.get(i).getImageView()) {
					tempList[indexOfTargetImage++] = Data.imageNodesList.get(i);
					break;
				}
			}
		}
		return tempList;
	}
}
