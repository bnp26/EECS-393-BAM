package Jott;

import java.util.LinkedList;

public class Line {
	
	private int lineNum;
	private LinkedList<Word> words;
	private boolean isEdited;
	
	public Line() {
		lineNum = 0;
		words = new LinkedList<Word>();
		isEdited = true;
	}
	
	public Line(int line, LinkedList<Word> words) {
		this.lineNum = line;
		this.words = words;
		this.isEdited = true;
	}
	
	public void moveLine(int newLineNum) {
		this.lineNum = newLineNum;
	}
	
}
