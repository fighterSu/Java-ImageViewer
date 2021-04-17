package handler;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import module.Data;

/**
 * this class is used to add event handlers to flowPane
 * @author Platina
 */
public class FlowPaneEventHandler {
	private final FlowPane flowPane;

	/**
	 * selectedRectangle is used to judge
	 * whether a imageNode is selected or not
	 */
	private final Rectangle selectRectangle = new Rectangle(0, 0, Paint.valueOf("#DEDEDE"));
	private boolean isDragged;

	/**
	 * 初始化信息
	 * @param flowPane is the flowPane want to add event handlers
	 */
	public FlowPaneEventHandler(FlowPane flowPane) {
		this.flowPane = flowPane;
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
		flowPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
			isDragged = false;
			double nowMouseX = mouseEvent.getX();
			double nowMouseY = mouseEvent.getY();
			selectRectangle.setX(nowMouseX);
			selectRectangle.setY(nowMouseY);
			selectRectangle.setWidth(0);
			selectRectangle.setHeight(0);
		});

		// 开始拖动，记录在拖动
		flowPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent -> isDragged = true);

		// 释放鼠标时检测之前是否是在拖动，是的话就更新选择矩形的数据，设置选择哪些图片
		flowPane.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
			if (isDragged) {
				double nowMouseX = mouseEvent.getX();
				double nowMouseY = mouseEvent.getY();
				double previousMouseX = selectRectangle.getX();
				double previousMouseY = selectRectangle.getY();

				selectRectangle.setX(Math.min(nowMouseX, previousMouseX));
				selectRectangle.setY(Math.min(nowMouseY, previousMouseY));
				selectRectangle.setWidth(Math.abs(nowMouseX - previousMouseX));
				selectRectangle.setHeight(Math.abs(nowMouseY - previousMouseY));

				ImageNodeEventHandler.clearSelectedState();
				for (Node child : flowPane.getChildren()) {
					if (isInSelectRectangle(child)) {
						VBox vBox = (VBox) child;
						vBox.setStyle("-fx-background-color: #DEDEDE");
						Data.selectedImageList.add((ImageView) ((Label) (vBox.getChildren().get(0))).getGraphic());
					}
				}
				Data.mainLayoutController.getTipText().setText(String.format("共 %d 张图片( %.2f %s ) - 共选中 %d 张图片",
						Data.imageNodesList.size(), Data.sumOfImage, Data.unit, Data.selectedImageList.size()));
			}
		});
	}

	/**
	 * @param node is the node that needs test whether it is in selectRectangle
	 * @return whether the node is in selectRectangle
	 * @Target test whether the node is in selectRectangle or not
	 */
	private boolean isInSelectRectangle(Node node) {
		double nodeHalfSize = 60;
		double imageNodeCenterX = node.getLayoutX() + nodeHalfSize;
		double imageNodeCenterY = node.getLayoutY() + nodeHalfSize;
		double selectRectangleCenterX = selectRectangle.getX() + selectRectangle.getWidth() / 2;
		double selectRectangleCenterY = selectRectangle.getY() + selectRectangle.getHeight() / 2;
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
