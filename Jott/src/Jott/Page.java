package Jott;

import java.util.LinkedList;

import javafx.scene.control.Button;

public class Page {
	
	private String name;
	private LinkedList<Line> lines;
	private Notebook parent;
	private Cursor cursor;
	
	private Button pageButton;
	
	public Page(String name) {
		this.name = name;
		lines = new LinkedList<Line>();
		
		pageButton = new Button();
	}
	
	public void setButton(Button button){
		this.pageButton = button;
	}
	
	public Button getButton() {
		return pageButton;
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
