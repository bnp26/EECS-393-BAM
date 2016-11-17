package Jott;

import java.util.LinkedList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class Page {
	
	private String name;
	private LinkedList<Line> lines;
	private FlowPane lineFlowPane;
	private Notebook parent;
	private Cursor cursor;

	private Button pageButton;
	
	public Page(String name) {
		this.name = name;
		lines = new LinkedList<Line>();
		Cursor cursor = new Cursor();
        lineFlowPane = new FlowPane();
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

	public Cursor getCursor() {
		return cursor;
	}

	public void setCursor(Cursor cursor) {
		this.cursor = cursor;
	}

	public void addLine() {
		Line line = new Line();
        FlowPane flowPane = line.getFlowPane();
        flowPane.setPrefSize(480, 28);

		int numLines = lines.size();
		lines.add(numLines-1, line);
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

	public void selectPage() {
        pageButton.getStyleClass().add("jott_current_page_item");
        Image imageDecline = new Image(getClass().getResourceAsStream("static/ic_label_black_24dp_2x.png"));
        pageButton.setGraphic(new ImageView(imageDecline));
    }

    public void deselectPage() {
        pageButton.getStyleClass().remove("jott_current_page_item");
        pageButton.setGraphic(new ImageView());
    }
}
