package Jott;

import java.util.LinkedList;
import java.util.StringJoiner;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class Line {

	private int lineNum;
	private String lineValue;
	private boolean isEdited;
	private Label line;
	
	public Line() {
		lineNum = 0;
		isEdited = true;
        lineValue = " ";
		line = new Label("												");
	}

	public String getLineValue() {
		return lineValue;
	}

	public void insertLetter(Location loc, char letter) {
		int letterLoc = loc.getLetterNum();

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(lineValue);

		stringBuilder.insert(letterLoc, letter);
	}
	
	public void moveLine(int newLineNum) {
		this.lineNum = newLineNum;
	}

	public Label getLabel() {
        return line;
    }

    public void updateLabel() {
        this.line.setText(lineValue);
    }
}
