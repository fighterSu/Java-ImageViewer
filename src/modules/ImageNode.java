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
 * 自定义图片节点
 *
 * @author Platina
 */
public class ImageNode extends VBox {
    private final Image image;
    private final Labeled nameLabel;
    private final File imageFile;

    public ImageNode(File imageFile) {
        this.imageFile = imageFile;
        image = new Image("file:" + imageFile.getAbsolutePath());
        ImageView imageView = new ImageView(image);
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

    public Image getImage() {
        return image;
    }

    public File getImageFile() {
        return imageFile;
    }

    public Labeled getNameLabel() {
        return nameLabel;
    }
}
