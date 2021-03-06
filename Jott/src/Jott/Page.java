package Jott;

import java.util.LinkedList;

import com.jfoenix.controls.JFXButton;
import com.mongodb.Mongo;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;

public class Page {

	private String name;
	private LinkedList<Line> lines;
	private Cursor cursor;
    private VBox vBox;

	private JFXButton pageButton;

	public static final double PAGE_WIDTH = 751;

	public Page(String name) {
		this.name = name;

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

	public Page(String name, VBox vBox) {
		this.name = name;
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

	public void setButton(JFXButton button){
		this.pageButton = button;
	}

	public JFXButton getButton() {
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

	public void createNewLine(int lineNum) {
		Line line = new Line(lineNum);
		Line endLine = new Line(lines.size());
		lines.add(lines.size(), endLine);

		int counter = lineNum;

		while(!lines.get(counter).getLineValue().equals(""))
			counter++;

		if(counter == 1) {
			lines.set(counter + 1, lines.get(counter));
			lines.get(counter + 1).setLineNum(counter + 1);
		}
		else if(!(counter <= 0)) {
			lines.set(counter + 1, lines.get(counter));
			lines.get(counter + 1).setLineNum(counter + 1);

			for (int x = counter - 2; x > lineNum; x--) {
				Line linePlaceHolder = lines.get(x);
				linePlaceHolder.setLineNum(linePlaceHolder.getLineNum() + 1);
				lines.set(x + 1, linePlaceHolder);
			}
		}

		lines.set(lineNum, line);
		cursor.move(lineNum, 0);
		removeLinesFromPage();
		addLinesToPage();
	}

	public void selectPage() {
        pageButton.getStyleClass().add("jott_current_page_item");
        Image imageDecline = new Image(getClass().getResourceAsStream("static/ic_label_black_24dp_2x.png"));
        pageButton.setGraphic(new ImageView(imageDecline));
        pageButton.getGraphic().setScaleX(0.5);
        pageButton.getGraphic().setScaleY(0.5);
        this.getCursor().getCursorImage().setVisible(true);
        this.getCursor().move(this.getCursor().getLocation());
		addLinesToPage();
    }

    public void deselectPage() {
		pageButton.getStyleClass().remove("jott_current_page_item");
        pageButton.setGraphic(new ImageView());
        this.getCursor().getCursorImage().setVisible(false);
		removeLinesFromPage();
    }

    public void addLinesToPage() {
		for(Line line:lines){
			if(!vBox.getChildren().contains(line.getLabel()))
				this.vBox.getChildren().add(line.getLabel());
		}
	}

	public void removeLinesFromPage() {
		this.vBox.getChildren().remove(0, this.vBox.getChildren().size());
	}
}
