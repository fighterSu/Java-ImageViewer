package handler;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * @author Platina
 */
public class MouseDraggedEventHandler {
    private double width;
    private double height;

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    private double previousMouseX;
    private double previousMouseY;
    private static final Rectangle RECTANGLE = new Rectangle();
    public static Pane pane = new Pane();

    public MouseDraggedEventHandler() {
        RECTANGLE.setArcHeight(10);
        RECTANGLE.setArcWidth(10);
        RECTANGLE.setStyle("-fx-fill: #C5C5C5");
        setPaneDragEvent();
    }

    /**
     * set eventHandler for mouse event on pane
     */
    private void setPaneDragEvent(){
        pane.setOnMousePressed(this::handleMousePressed);
        pane.setOnMouseDragged(this::handleMouseDragged);
        pane.setOnMouseReleased(this::handleMouseReleased);
    }

    /**
     * get the point of mouse when mouse is pressed
     * @param mouseEvent is  mouse pressed event
     */
    private void handleMousePressed(MouseEvent mouseEvent){
        previousMouseX = mouseEvent.getX();
        previousMouseY = mouseEvent.getY();
        System.out.println(previousMouseX + " " + previousMouseY);
    }

    /**
     * delete the rectangle
     * @param mouseEvent is the mouse released event
     */
    private void handleMouseReleased(MouseEvent mouseEvent){
        RECTANGLE.setX(0);
        RECTANGLE.setY(0);
        RECTANGLE.setHeight(0);
        RECTANGLE.setWidth(0);
    }

    /**
     * paint the rectangle by mouse dragging
     * @param mouseEvent is the mouse moved event
     */
    private void handleMouseDragged(MouseEvent mouseEvent){
        width = mouseEvent.getX() - previousMouseX;
        height = mouseEvent.getY() - previousMouseY;
        RECTANGLE.setX(previousMouseX);
        RECTANGLE.setY(previousMouseY);
        RECTANGLE.setWidth(width);
        RECTANGLE.setHeight(height);
    }
}
