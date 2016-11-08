package Jott;

import java.util.LinkedList;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class Line {
	
	private int lineNum;
	private LinkedList<Word> words;
	private boolean isEdited;
	private FlowPane lineContainer;
	
	public Line() {
		lineNum = 0;
		words = new LinkedList<Word>();
		isEdited = true;
		lineContainer = new FlowPane();
	}
	
	public Line(int line, LinkedList<Word> words) {
		this.lineNum = line;
		this.words = words;
		this.isEdited = true;
		lineContainer = new FlowPane();
	}
	
	public Line(int line, FlowPane container) {
		this.lineNum = line;
		this.lineContainer = container;
		this.isEdited = true;
		this.words = new LinkedList<Word>();
	}
	
	public Line(int line, LinkedList<Word> words, FlowPane container) {
		this.lineNum = line;
		this.words = words;
		this.lineContainer = container;
		this.isEdited = true;
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
