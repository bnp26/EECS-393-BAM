package Jott;

import java.util.LinkedList;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class Line {

	private int lineNum;
	private String lineValue;
	private boolean isEdited;
	private FlowPane lineContainer;
	
	public Line() {
		lineNum = 0;
		isEdited = true;
        lineValue = "                                                                         ";
		lineContainer = new FlowPane();
        lineContainer.setPrefSize(9*220, 16);
        for(int x = 9; x < 9*220; x+=9)
            lineContainer.getChildren().add(new Label(" "));
	    lineContainer.setVisible(true);
	}
	
	public void moveLine(int newLineNum) {
		this.lineNum = newLineNum;
	}

	public void setFlowPane(FlowPane flowPane) {
		this.lineContainer = flowPane;
	}

	public FlowPane getFlowPane() {
		return lineContainer;
	}
}
