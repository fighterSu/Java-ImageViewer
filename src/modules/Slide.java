package modules;

import controller.SlideLayoutController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 加载幻灯片播放界面以及进行数据初始化
 *
 * @author Platina
 */
public class Slide {
	public Slide(int indexOfImage) throws IOException {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/layout/SlideUI.fxml"));
            BorderPane pptPane = loader.load();

            SlideLayoutController controller = loader.getController();
        controller.setPhoto(Data.imageNodesList.get(indexOfImage).getImage());
            // 根据Index设置时间轴
            controller.setTimeLine(indexOfImage);

            Scene scene = new Scene(pptPane, 0.8 * Data.screenWidth, 0.8 * Data.screenHeight);

            Stage pptStage = new Stage();
            Image icon = new Image("image/flashStageIcon.png", 30, 30, false, false, false);
            pptStage.getIcons().add(icon);
            pptStage.initOwner(Data.primaryStage);
            pptStage.initModality(Modality.WINDOW_MODAL);
            pptStage.setScene(scene);
            pptStage.setTitle("幻灯片播放界面");
            pptStage.show();
            pptPane.requestFocus();
    }
}
