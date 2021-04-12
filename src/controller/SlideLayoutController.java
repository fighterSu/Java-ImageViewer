package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import module.Data;
import module.ToolTipBox;
import org.jetbrains.annotations.NotNull;

/**
 * @author Platina, LiJiaHao
 */

public class SlideLayoutController {
    public BorderPane slideRootPane;
    public ImageView photo;
    public Button flashButton;
    public Button stopButton;
    public Button enlargeButton;
    public Button narrowButton;
    public Button leftButton;
    public Button rightButton;

    /**
     * timeline is used for slide show
     * indexOfImage is used to mark the index of the currently playing
     * picture in the Data.imageList
     */
    private Timeline timeline;
    private int indexOfImage;

    Scale scale = new Scale(1, 1, 0, 0);

    public void setPhoto(ImageView photo) {
        this.photo = photo;
        photo.getTransforms().add(scale);
    }

    public SlideLayoutController() {
    }

    @FXML
    private void initialize() {
        initButton();
    }

    @FXML
    private void flashButtonOnAction() {
        stopButton.setCancelButton(false);
        timeline.play();
    }

    public void setTimeLine(int index) {
        indexOfImage = index;
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), e -> {
            indexOfImage++;
            //实现循环播放
            if (Data.imageList != null && indexOfImage == Data.imageList.size()) {
                indexOfImage = 0;
            }
            if (Data.imageList != null) {
                photo.setImage(Data.imageList.get(indexOfImage).getImage());
            }
        });
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        // 点击图片暂停播放
        photo.setOnMouseClicked(e -> {
            if (timeline.getStatus() == Animation.Status.PAUSED) {
                timeline.play();
            } else {
                timeline.pause();
            }
        });
    }

    @FXML
    private void stopButtonOnAction() {
        stopButton.setCancelButton(false);
        timeline.stop();
    }

    @FXML
    private void enlargeButtonOnAction() {
        double maxMagnification = 1.5;
        if(scale.getX() >= maxMagnification) {
            ToolTipBox.createToolTipBox("达到最大放大倍数", "达到最大放大倍数啦，不能再大了");
        } else {
            scale.setPivotX(photo.getFitWidth() / 2);
            scale.setPivotY(photo.getFitHeight() / 2);
            scale.setX(scale.getX() + 0.1);
            scale.setY(scale.getY() + 0.1);
        }
    }

    @FXML
    private void narrowButtonOnAction() {
        double maximumReductionFactor = 0.5;
        if(scale.getX() <= maximumReductionFactor) {
            ToolTipBox.createToolTipBox("达到最大缩小倍数", "达到最大缩小倍数啦，不能再小了");
        } else {
            scale.setPivotX(photo.getFitWidth() / 2);
            scale.setPivotY(photo.getFitHeight() / 2);
            scale.setX(scale.getX() - 0.1);
            scale.setY(scale.getY() - 0.1);
        }
    }

    @FXML
    private void leftArrowButtonOnAction() {
        scale.setX(1);
        scale.setY(1);
        if (indexOfImage == 0) {
            ToolTipBox.createToolTipBox("第一张图片", "已经是本目录的第一张图片");
        } else {
            indexOfImage--;
            photo.setImage(Data.imageList.get(indexOfImage).getImage());
        }
    }

    @FXML
    private void rightArrowButtonOnAction() {
        scale.setX(1);
        scale.setY(1);
        if (indexOfImage == Data.imageList.size() - 1) {
            ToolTipBox.createToolTipBox("最后一张图片", "已经是本目录的最后一张图片");
        } else {
            indexOfImage++;
            photo.setImage(Data.imageList.get(indexOfImage).getImage());
        }
    }

    private void initButton() {
        setButtonGraphic("flash.png", flashButton);
        setButtonGraphic("stop.png", stopButton);
        setButtonGraphic("enlarge.png", enlargeButton);
        setButtonGraphic("narrow.png", narrowButton);
        setButtonGraphic("leftArrow.png", leftButton);
        setButtonGraphic("rightArrow.png", rightButton);
    }

    private void setButtonGraphic(String imagePath, @NotNull Button button) {
        ImageView tempImageView = new ImageView("file:src/image/" + imagePath);
        tempImageView.setSmooth(true);
        tempImageView.setCache(true);
        tempImageView.setFitWidth(50);
        tempImageView.setFitHeight(50);
        button.setGraphic(tempImageView);
    }

}
