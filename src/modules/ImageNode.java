package modules;

import handler.ImageNodeEventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;

/**
 * this class is design a ImageNode
 * and inits the node which will be displayed in flowPane
 *
 * @author Platina
 */
public class ImageNode extends VBox {
    private final ImageView imageView;
    private final Labeled nameLabel;
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
        nameLabel = new Label();
        nameLabel.setPrefSize(110, 10);
        nameLabel.setText(imageFile.getName());
        nameLabel.setAlignment(Pos.CENTER);
        this.getChildren().addAll(imageLabel, nameLabel);
        this.setPrefSize(120, 120);
        this.setAlignment(Pos.BOTTOM_CENTER);
        this.setSpacing(10);
        ImageNodeEventHandler.setImageNodeMouseClickedEvent(this);
        new MyContextMenu(this, true);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public File getImageFile() {
        return imageFile;
    }

    public Labeled getNameLabel() {
        return nameLabel;
    }
}
