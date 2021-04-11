package handler;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Platina
 */
public class FlowPaneEventHandler {
    private final FlowPane flowPane;
    public FlowPaneEventHandler(FlowPane flowPane) {
        this.flowPane = flowPane;
        setMouseClickBlank();
    }

    /**
     * When mouse click in the blank of flowPane,
     * clear the selected state of ImageNode in the flowPane
     */
    private void setMouseClickBlank(){
        flowPane.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Node clickNode = e.getPickResult().getIntersectedNode();
            if (!(clickNode instanceof Label) && !(clickNode instanceof Text) && !(clickNode instanceof VBox)) {
                ImageNodeEventHandler.clearSelectedState();
            }
        });
    }


}
