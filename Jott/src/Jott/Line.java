package Jott;

import java.util.LinkedList;
import java.util.StringJoiner;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import sun.print.BackgroundServiceLookup;

public class Line {

	public final static double LINE_HEIGHT = 16;

	private int lineNum;
	private String lineValue;
	private boolean isEdited;
	private Label line;

	public Line(int lineNum) {
		this.lineNum = lineNum;
		isEdited = true;
		lineValue = "";
		line = new Label(lineValue);
		line.setBackground(new Background(new BackgroundFill(Paint.valueOf("YELLOW"), null, null)));
		//line.setBorder(new Border(new BorderStroke(Paint.valueOf("black"), null, new CornerRadii(1.0), BorderStroke.THIN)));
//		line.setPrefSize(Page.PAGE_WIDTH, LINE_HEIGHT);
//		line.setMaxSize(Page.PAGE_WIDTH, 16.0);
		line.setScaleZ(1.0);
		line.setFont(Font.font(java.awt.Font.MONOSPACED, 14));
		line.setTextAlignment(TextAlignment.LEFT);
		line.setVisible(true);
	}

	public void setLineNum(int num) {
		lineNum = num;
	}

	public int getLineNum() {
		return lineNum;
	}

	public void setLineValue(String str) {
		lineValue = str;
	}

	public String getLineValue() {
		return lineValue;
	}

	public void insertLetter(Location loc, char letter) {
	    System.out.println("location = (" + loc.getLineNum() + ", " + loc.getLetterNum() + ")");
	    System.out.print("letter = " + letter);
		if(loc.getLineNum() != lineNum) {
			System.out.println("trying to add letter to wrong line");
			return;
		}
		if(loc.getLetterNum()>lineValue.length()){
			System.out.println("trying to add a letter to a line where it can't");
			return;
		}
        String startValue = lineValue.substring(0, loc.getLetterNum());
		String endValue = lineValue.substring(loc.getLetterNum());
		lineValue = startValue + letter + endValue;
		System.out.println(lineValue);
		updateLabel();
	}
	
	public void moveLine(int newLineNum) {
		this.lineNum = newLineNum;
	}

	public Label getLabel() {
        return line;
    }

    private void updateLabel() {
        this.line.setText(lineValue);
    }
}
