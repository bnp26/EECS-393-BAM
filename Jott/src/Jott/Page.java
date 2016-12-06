package Jott;

import java.util.LinkedList;

import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class Page {

	private String name;
	private LinkedList<Line> lines;
	private Cursor cursor;
    private VBox vBox;

	private Button pageButton;

	public static final double PAGE_WIDTH = 740;

	public Page(String name, VBox vBox) {
		this.name = name;
		//setting up flow pane event listeners
		this.vBox = vBox;

		lines = new LinkedList<Line>();
		Cursor cursor = new Cursor();
		this.cursor = cursor;
        int counter = 0;
        while(counter < 20)
        {
            lines.add(new Line(counter));
            counter++;
        }
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

	public void setVBox(VBox pane) {
        this.vBox = pane;
    }

    public VBox getVBox() {
        return vBox;
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
        int lettersSize = 60;
		Line line = new Line(this.lines.size()-1);

		if(cursor == null) {
            System.out.println("cursor is null");
            cursor = new Cursor();
        }
        lines.add(line);
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
        pageButton.getGraphic().setScaleX(0.5);
        pageButton.getGraphic().setScaleY(0.5);
		addLinesToPage();
    }

    public void deselectPage() {
		removeLinesFromPage();
		pageButton.getStyleClass().remove("jott_current_page_item");
        pageButton.setGraphic(new ImageView());
    }

    public void addLinesToPage() {
		for(Line line:lines){
			if(!vBox.getChildren().contains(line))
				this.vBox.getChildren().add(line.getLabel());
		}
	}

	public void removeLinesFromPage() {
		this.vBox.getChildren().remove(0, this.vBox.getChildren().size());
	}
}
