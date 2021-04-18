package modules;

import java.io.IOException;

import controller.SlideLayoutController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * this class is used to load SlideLayout and inits some necessary data
 * @author Platina
 */
public class Slide {
	public Slide(int indexOfImage) throws IOException {
		ImageView imageView = new ImageView(Data.imageNodesList.get(indexOfImage).getImageView().getImage());
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/layout/SlideUI.fxml"));
		BorderPane pptPane = loader.load();

		SlideLayoutController controller = loader.getController();
		controller.setPhoto(imageView);
		// 根据Index设置时间轴
		controller.setTimeLine(indexOfImage);

		imageView.setSmooth(true);
		imageView.setCache(true);
		imageView.setPreserveRatio(true);
		imageView.setFitWidth(0.5 * Data.screenWidth);
		imageView.setFitHeight(0.5 * Data.screenHeight);
		pptPane.setCenter(imageView);

		Scene scene = new Scene(pptPane, 0.8 * Data.screenWidth, 0.8 * Data.screenHeight);

		Stage pptStage = new Stage();
		pptStage.initOwner(Data.stage);
		pptStage.initModality(Modality.WINDOW_MODAL);
		pptStage.setScene(scene);
		pptStage.setTitle("幻灯片播放界面");
		pptStage.show();
		pptPane.requestFocus();
	}
}
