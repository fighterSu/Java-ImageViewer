package handler;

import java.io.IOException;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import module.Data;
import module.Popups;
import module.Slide;

/**
 * @author Platina
 */
public class FlashIconEventHandler {
	public FlashIconEventHandler(ImageView flashIcon) {
		flashIcon.setOnMouseClicked(mouseEvent -> {
			if (mouseEvent.getButton() == MouseButton.PRIMARY) {
				if (Data.imageNodesList.isEmpty()) {
					Popups.createToolTipBox("无可播放图片", "暂无可播放图片，请在有图片的目录下点击幻灯片播放图标", -1, -1);
				} else {
					// 播放幻灯片
					try {
						new Slide(0);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
