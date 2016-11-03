package Jott;

import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.*;

public class Page {
	
	private String name;
	private LinkedList<Line> lines;
	private Notebook parent;
	private Cursor cursor;
	
	private Button pageButton;
	
	public Page(String name) {
		this.name = name;
		lines = new LinkedList<Line>();
		
		Cursor cursor = new Cursor();
	}

	public void setButton(Button button){
		this.pageButton = button;
		pageButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				pageButton.getStyleClass().add("jott_current_page_item");
				Image imageDecline = new Image(getClass().getResourceAsStream("static/ic_label_black_24dp/web/ic_label_black_24dp_1x.png"));
				pageButton.setGraphic(new ImageView(imageDecline));
			}
		});
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
