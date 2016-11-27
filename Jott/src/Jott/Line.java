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

	//LETTER SIZES [x,y]
	// a = [9px,9px] b = [8px,11px] c = [7px,9px] d = [7px,11px] e = [8px,8px]
	// f = [5px,10px] g = [8px,11px] h = [9px,10px] i = [2px,10px] j = [5px,13px]
	// k = [7px,10px] l = [4px,10px] m = [13px,8px] n = [7px,8px] o = [9px,9px]
	// p = [7px,11px] q = [9px,11px] r = [8px,8px] s = [8px,8px] t = [8px,8px]
	// u = [8px,8px] v = [8px,8px] w = [8px,8px] x = [8px,8px] y = [8px,8px] z = [8px,8px]

	// A = [8px,8px] B = [8px,8px] C = [8px,8px] D = [8px,8px] E = [8px,8px]
	// F = [8px,8px] G = [8px,8px] H = [8px,8px] I = [8px,8px] J = [8px,8px]
	// K = [8px,8px] L = [8px,8px] M = [8px,8px] N = [8px,8px] O = [8px,8px]
	// P = [8px,8px] Q = [8px,8px] R = [8px,8px] S = [8px,8px] T = [8px,8px]
	// U = [8px,8px] V = [8px,8px] W = [8px,8px] X = [8px,8px] Y = [8px,8px] Z = [8px,8px]

	private int lineNum;
	private String lineValue;
	private boolean isEdited;
	private Label line;

	public Line(int lineNum) {
		this.lineNum = lineNum;
		isEdited = true;
		lineValue = "";
		line = new Label(lineValue);
		//line.setBackground(new Background(new BackgroundFill(Paint.valueOf("YELLOW"), null, null)));
		//line.setBorder(new Border(new BorderStroke(Paint.valueOf("black"), null, new CornerRadii(1.0), BorderStroke.THIN)));
		line.setPrefSize(Page.PAGE_WIDTH, LINE_HEIGHT);line.setMaxSize(Page.PAGE_WIDTH, 16.0);
		line.setScaleZ(1.0);
		line.setFont(Font.font("Monospaced", 14));
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
		if(loc.getLineNum() != lineNum) {
			System.out.println("trying to add letter to wrong line");
			return;
		}
		if(loc.getLetterNum()>lineValue.length()){
			System.out.println("trying to add wha letter to the line where it can't");
			return;
		}

		lineValue += letter;
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
