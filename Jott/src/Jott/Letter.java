package Jott;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class Letter extends Glyph{

	private char value;

        // Constructor for Letter takes a character as an argument
	public Letter(char val) {
		super();
		value = val;
                // Create a Label instance with the character value
                Label newLabel = setUpLabel(value);
	}
	
        // Constructor for Letter that takes character and previous Glyph instance
	public Letter(char val, Glyph previous) {
		super();
		value = val;
                // setting the instance to the previous Glyph
		this.setPrevious(previous);
	}

        // Letter Constrcutor with character next&previous Glpyhs
	public Letter(char val, Glyph next, Glyph previous) {
		super();
		value = val;
                // setting the next&previous Glyphs of the Letter
		this.setNext(next);
		this.setPrevious(previous);
	}

	private Label setUpLabel(char val) {
            Label newLabel = new Label();
            // Setting the Label instance with 
            // text value of character passed in the argument
            newLabel.setText(new String(val+""));
            return newLabel;
        }
	
	public char getValue() {
		return value;
	}
	
	public void setValue(char newValue) {
		value = newValue;
	}
}
