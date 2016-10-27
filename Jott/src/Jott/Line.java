package Jott;

import java.util.LinkedList;

import javafx.scene.layout.HBox;

public class Line {
	
	private int lineNum;
	private LinkedList<Word> words;
	private boolean isEdited;
	private HBox lineContainer;
	
	public Line() {
		lineNum = 0;
		words = new LinkedList<Word>();
		isEdited = true;
		lineContainer = new HBox();
	}
	
	public Line(int line, LinkedList<Word> words) {
		this.lineNum = line;
		this.words = words;
		this.isEdited = true;
		lineContainer = new HBox();
	}
	
	public Line(int line, HBox container) {
		this.lineNum = line;
		this.lineContainer = container;
		this.isEdited = true;
		this.words = new LinkedList<Word>();
	}
	
	public Line(int line, LinkedList<Word> words, HBox container) {
		this.lineNum = line;
		this.words = words;
		this.lineContainer = container;
		this.isEdited = true;
	}
	
	public void moveLine(int newLineNum) {
		this.lineNum = newLineNum;
	}
	
}
