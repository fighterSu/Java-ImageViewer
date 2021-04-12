package module;

import handler.ImageNodeEventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * @author Platina
 */
public class ImageNode extends Node {
    private final ImageView imageView;
    private final Labeled imageLabel = new Label();
    private final VBox baseBox = new VBox(10);

    public ImageNode(String imageName, String imagePath) {
        imageView = new ImageView(new Image("file:" + imagePath));
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setFitWidth(110);
        imageView.setFitHeight(110);
        imageView.setPreserveRatio(true);
        imageLabel.setGraphic(imageView);
        imageLabel.setPrefSize(110, 110);
        imageLabel.setAlignment(Pos.CENTER);
        Label nameLabel = new Label();
        nameLabel.setPrefSize(110, 10);
        nameLabel.setText(imageName);
        nameLabel.setAlignment(Pos.CENTER);
        baseBox.setPrefSize(120, 120);
        baseBox.getChildren().addAll(imageLabel, nameLabel);
        baseBox.setAlignment(Pos.BOTTOM_CENTER);
        baseBox.setOnMouseClicked(mouseEvent ->
        {
            try {
                ImageNodeEventHandler.setImageNodeMouseClickedEvent(mouseEvent, baseBox, imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public VBox getBaseBox() {
        return baseBox;
    }

    public Labeled getImageLabel() {
        return imageLabel;
    }

}
