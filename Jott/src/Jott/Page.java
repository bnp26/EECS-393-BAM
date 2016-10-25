package Jott;

import java.util.LinkedList;

import javafx.scene.control.Label;

public class Page {
	
	private String name;
	private LinkedList<Line> lines;
	private Cursor cursor;
	
	private Label pageLabel;
	
	public Page(String name) {
		this.name = name;
		lines = new LinkedList<Line>();
		
		pageLabel = new Label();
	}
	
	public void setLabel(Label label){
		this.pageLabel = label;
	}
	
	public Label getLabel() {
		return pageLabel;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public LinkedList<Line> getLines() {
		return lines;
	}
	
	public void addLine() {
		Line line = new Line();
		int numLines = lines.size();
		
		lines.add(numLines, line);
	}
	
	public boolean removeLine(int lineNum) {
		if(lines.get(lineNum) == null) {
			return false;
		}
		else {
			lines.remove(lineNum);
			return true;
		}
	}

}
