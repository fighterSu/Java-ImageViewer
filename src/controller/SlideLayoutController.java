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
import modules.Data;
import modules.Popups;

/**
 * the class for handling slide stage
 *
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
     * indexOfImage is used to mark the index of the
     * currently playing picture in the Data.imageList
     */
    private Timeline timeline;
    private int indexOfImage;

    /**
     * 创建Scale类型对象，用于图片缩放
     */
    Scale scale = new Scale(1, 1, 0, 0);

    /**
     * 设置展示的ImageView的Image
     *
     * @param photo is the ImageView in the slide stage
     */
    public void setPhoto(ImageView photo) {
        this.photo = photo;
        photo.getTransforms().add(scale);
    }

    public SlideLayoutController() {
    }

    @FXML
    private void initialize() {
        // 初始化按钮的图片和布局
        initButton();
    }

    /**
     * 设置flashButton按钮点击事件
     */
    @FXML
    private void flashButtonOnAction() {
        resetPhoto();
        stopButton.setCancelButton(false);
        timeline.play();
    }

    /**
     * 根据点击的图片或者第一张图片设置时间轴，用于幻灯片播放
     *
     * @param index is the index of image in the imageList
     */
    public void setTimeLine(int index) {
        indexOfImage = index;
        // 设置 1s 切换一张图片
        KeyFrame keyFrame = new KeyFrame(Duration.millis(1500), e -> {
            indexOfImage++;
            // 实现循环播放
            if (Data.imageNodesList != null && indexOfImage == Data.imageNodesList.size()) {
                indexOfImage = 0;
            }
            if (Data.imageNodesList != null) {
                photo.setImage(Data.imageNodesList.get(indexOfImage).getImageView().getImage());
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
        stopButton.setStyle("-fx-border-color: #DEDEDE");
        stopButton.setCancelButton(false);
        timeline.stop();
    }

    @FXML
    private void enlargeButtonOnAction() {
        double maxMagnification = 1.5;
        if (scale.getX() >= maxMagnification) {
            Popups.createToolTipBox("达到最大放大倍数", "达到最大放大倍数啦，不能再大了", -1, -1);
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
        if (scale.getX() <= maximumReductionFactor) {
            Popups.createToolTipBox("达到最大缩小倍数", "达到最大缩小倍数啦，不能再小了", -1, -1);
        } else {
            scale.setPivotX(photo.getFitWidth() / 2);
            scale.setPivotY(photo.getFitHeight() / 2);
            scale.setX(scale.getX() - 0.1);
            scale.setY(scale.getY() - 0.1);
        }
    }

    @FXML
    private void leftArrowButtonOnAction() {
        resetPhoto();
        if (indexOfImage == 0) {
            Popups.createToolTipBox("第一张图片", "已经是本目录的第一张图片", -1, -1);
        } else {
            indexOfImage--;
            if (indexOfImage == 0) {
                Popups.createToolTipBox("第一张图片", "第一张图片", -1, -1);
            }
            photo.setImage(Data.imageNodesList.get(indexOfImage).getImageView().getImage());
        }
    }

    @FXML
    private void rightArrowButtonOnAction() {
        resetPhoto();
        if (indexOfImage == Data.imageNodesList.size() - 1) {
            Popups.createToolTipBox("最后一张图片", "已经是本目录的最后一张图片", -1, -1);
        } else {
            indexOfImage++;
            if (indexOfImage == Data.imageNodesList.size() - 1) {
                Popups.createToolTipBox("最后一张图片", "最后一张图片", -1, -1);
            }
            photo.setImage(Data.imageNodesList.get(indexOfImage).getImageView().getImage());
        }
    }

    /**
     * 初始化幻灯片界面按钮的图片及提示文字
     */
    private void initButton() {
        String[] imagePaths = {"flash.png", "stop.png", "enlarge.png", "narrow.png", "leftArrow.png",
                "rightArrow.png"};
        Button[] buttons = {flashButton, stopButton, enlargeButton, narrowButton, leftButton, rightButton};
        String[] tipContent = {"播放", "暂停", "放大", "缩小", "上一张图片", "下一张图片"};
        for (int i = 0; i < buttons.length; i++) {
            setButtonGraphic(imagePaths[i], buttons[i]);
            Popups.createToolTip(buttons[i], tipContent[i]);
        }
    }

    /**
     * 设置按钮的图标
     *
     * @param imagePath is the graphic of button
     * @param button    is the button needed to set graphic
     */
    private void setButtonGraphic(String imagePath, Button button) {
        ImageView tempImageView = new ImageView("file:src/image/" + imagePath);
        tempImageView.setSmooth(true);
        tempImageView.setCache(true);
        tempImageView.setFitWidth(50);
        tempImageView.setFitHeight(50);
        button.setGraphic(tempImageView);
    }

    /**
     * 重置ImageView的显示大小
     */
    private void resetPhoto() {
        scale.setX(1);
        scale.setY(1);
    }
}
