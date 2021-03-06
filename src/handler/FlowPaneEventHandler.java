package handler;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import modules.Data;
import modules.ImageNode;

/**
 * 定义流式布局的监听事件
 *
 * @author Platina
 */
public class FlowPaneEventHandler {
    private final FlowPane flowPane;

    /**
     * 定义画板
     */
    private final AnchorPane anchorPane;

    /**
     * 定义记录鼠标按下位置的变量
     */
    private double selectorX, selectorY;

    /**
     * selectedRectangle is used to judge
     * whether a imageNode is selected or not
     */
    private final Rectangle selectRectangle = new Rectangle(0, 0, Paint.valueOf("#7dbfffAA"));

    /**
     * 初始化信息
     *
     * @param flowPane is the flowPane want to add event handlers
     */
    public FlowPaneEventHandler(FlowPane flowPane) {
        this.flowPane = flowPane;
        anchorPane = Data.mainLayoutController.anchorPane;
        setMouseClickBlank();
        addDraggedListener();
    }

    /**
     * 设置flowPane的鼠标拖拽事件
     * 按下鼠标时记录当前鼠标所在位置，并初始化选择矩形的位置
     * 开始拖动后，标记拖动
     * 鼠标释放，如果之前标记是在拖动，则获取当前鼠标位置
     * 判断哪些图片节点与选择矩形相交
     */
    private void addDraggedListener() {
        // 按下鼠标记录鼠标坐标，并初始化选中的矩形
        flowPane.setOnMousePressed(mouseEvent -> {
            selectRectangle.setWidth(0);
            selectRectangle.setHeight(0);
            anchorPane.getChildren().add(selectRectangle);

            selectorX = mouseEvent.getX();
            selectorY = mouseEvent.getY();
            selectRectangle.setLayoutX(selectorX);
            selectRectangle.setLayoutY(selectorY);
        });

        // 开始拖动，记录在拖动
        flowPane.setOnMouseDragged(mouseEvent -> {
            double nowMouseX = mouseEvent.getX();
            double nowMouseY = mouseEvent.getY();

            // 右下拖拽
            if (nowMouseX > selectorX && nowMouseY > selectorY) {
                // 没有拖拽大过边界
                if (nowMouseX < anchorPane.getWidth()) {
                    selectRectangle.setWidth(nowMouseX - selectorX);
                }
                selectRectangle.setHeight(Math.min(nowMouseY, anchorPane.getHeight()) - selectorY);
            }

            // 左下
            if (nowMouseX < selectorX && nowMouseY > selectorY) {
                // 没有拖拽离开边界
                if (nowMouseX > 0) {
                    selectRectangle.setLayoutX(nowMouseX);
                    selectRectangle.setWidth(selectorX - nowMouseX);
                }
                selectRectangle.setHeight(Math.min(nowMouseY, anchorPane.getHeight()) - selectorY);
            }

            // 往右上
            if (selectorX < nowMouseX && nowMouseY < selectorY) {
                // 没有拖拽大过边界
                if (nowMouseX < anchorPane.getWidth()) {
                    selectRectangle.setWidth(nowMouseX - selectorX);
                }
                selectRectangle.setLayoutY(nowMouseY);
                selectRectangle.setHeight(selectorY - nowMouseY);
            }
            // 往左上
            if (nowMouseX < selectorX && nowMouseY < selectorY) {
                // 没有拖拽离开边界
                if (0 < nowMouseX) {
                    selectRectangle.setLayoutX(nowMouseX);
                    selectRectangle.setWidth(selectorX - nowMouseX);
                }
                selectRectangle.setLayoutY(nowMouseY);
                selectRectangle.setHeight(selectorY - nowMouseY);
            }

            // 清除选中状态，选中在矩形范围内的图片节点
            ImageNodeEventHandler.clearSelectedState();
            for (Node child : flowPane.getChildren()) {
                if (isInSelectRectangle(child)) {
                    ImageNode imageNode = (ImageNode) child;
                    imageNode.setStyle("-fx-background-color: #DEDEDE");
                    Data.selectedImageList.add(imageNode);
                }
            }
            Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 %d 张图片",
                    Data.imageNodesList.size(), Data.sumOfImage, Data.unit, Data.selectedImageList.size()));
        });

        flowPane.setOnMouseReleased(mouseEvent -> anchorPane.getChildren().remove(selectRectangle));
    }

    /**
     * @param node is the node that needs test whether it is in selectRectangle
     * @return whether the node is in selectRectangle
     */
    private boolean isInSelectRectangle(Node node) {
        double nodeHalfSize = 60;
        double imageNodeCenterX = node.getLayoutX() + nodeHalfSize;
        double imageNodeCenterY = node.getLayoutY() + nodeHalfSize;
        double selectRectangleCenterX = selectRectangle.getLayoutX() + selectRectangle.getWidth() / 2;
        double selectRectangleCenterY = selectRectangle.getLayoutY() + selectRectangle.getHeight() / 2;
        return Math.abs(imageNodeCenterX - selectRectangleCenterX) <= (nodeHalfSize + 0.5 * selectRectangle.getWidth())
                && Math.abs(imageNodeCenterY - selectRectangleCenterY) <= (nodeHalfSize
                + 0.5 * selectRectangle.getHeight());
    }

    /**
     * When mouse click in the blank of flowPane, clear the selected state of
     * ImageNode in the flowPane
     */
    private void setMouseClickBlank() {
        flowPane.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                Node clickNode = mouseEvent.getPickResult().getIntersectedNode();
                if (!(clickNode instanceof Label) && !(clickNode instanceof Text) && !(clickNode instanceof VBox)) {
                    ImageNodeEventHandler.clearSelectedState();
                }
        });
    }
}
