package module;

import java.io.File;
import java.io.IOException;

import handler.ImageNodeEventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * this class is design a ImageNode
 * and inits the node which will be displayed in flowPane
 *
 * @author Platina
 */
public class ImageNode extends Node {
    private final ImageView imageView;
    private final VBox baseBox = new VBox(10);
    private final File imageFile;

    public ImageNode(File imageFile) {
        this.imageFile = imageFile;
        imageView = new ImageView(new Image("file:" + imageFile.getAbsolutePath()));
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setFitWidth(110);
        imageView.setFitHeight(110);
        imageView.setPreserveRatio(true);
        Labeled imageLabel = new Label();
        imageLabel.setGraphic(imageView);
        imageLabel.setAlignment(Pos.CENTER);
        Labeled nameLabel = new Label();
        nameLabel.setPrefSize(110, 10);
        nameLabel.setText(imageFile.getName());
        nameLabel.setAlignment(Pos.CENTER);
        baseBox.setPrefSize(120, 120);
        baseBox.getChildren().addAll(imageLabel, nameLabel);
        baseBox.setAlignment(Pos.BOTTOM_CENTER);
        baseBox.setOnMouseClicked(mouseEvent -> {
            try {
                ImageNodeEventHandler.setImageNodeMouseClickedEvent(mouseEvent, this);
            } catch (IOException e) {
                Popups.showExceptionDialog(e);
            }
        });
        new MyContextMenu(baseBox, true);
    }

    public VBox getBaseBox() {
        return baseBox;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public File getImageFile() {
        return imageFile;
    }
}
