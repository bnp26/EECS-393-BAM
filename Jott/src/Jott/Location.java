package Jott;

import javafx.scene.text.Text;
import javafx.stage.Screen;

public class Location {

    // Constant values for Height and Width for a character/letter
    private final double LETTER_HEIGHT = 270/Math.rint(new Text("").getLayoutBounds().getHeight());
    private final double LETTER_WIDTH = 126.09375/Math.rint(new Text("").getLayoutBounds().getHeight());

	private int lineNum;
//	private int wordNum;
	private int letterNum;

    // Constructor for location, initializing to 0
    public Location() {
        lineNum = 0;
  //      wordNum = 0;
        letterNum = 0;
        System.out.println("letter hight = " + LETTER_HEIGHT + "\nletter width = " + LETTER_WIDTH);
    }

    // Constructor for location with arguments
    // line&letter
	public Location(int line, int letter) {
		lineNum = line;
//		wordNum = word;
		letterNum = letter;
        System.out.println("letter hight = " + LETTER_HEIGHT + "\nletter width = " + LETTER_WIDTH);
	}
	
	public int getLineNum() {
		return lineNum;
	}
	
	public void setLineNum(int line) {
		lineNum = line;
	}
	
/*	public int getWordNum() {
		return wordNum;
	}

	public void setWordNum(int word) {
		wordNum = word;
	}
*/
	public int getLetterNum() {
		return letterNum;
	}
	
	public void setLetterNum(int letter) {
		letterNum = letter;
	}
	
	public void changeLocation(int line, int letter)
	{
		lineNum = line;
//		wordNum = word;
		letterNum = letter;
	}

        // Method for converting the pixels given in the argument
        // to Location instance
	public Location pixelsToLocation(double x, double y) {
            Location loc = new Location(0,0);
            // Calculating the Double values for 
            // lineNumber&letterNumber
            Double lineNumber = new Double(y/LETTER_HEIGHT);
            Double letterNumber = new Double(x/LETTER_WIDTH);

            // creating integer values from the doubles
            int intLineNumber = lineNumber.intValue();
            int intLetterNumber = letterNumber.intValue();

            System.out.println("("+intLineNumber + ", " + intLetterNumber+")");

            // Call changeLocation method with the new values
            // calculated for lineNumber & letterNumber
            loc.changeLocation(intLineNumber, intLetterNumber);
		return loc;
	}

        // Return the X-pixel value for the location
	public double getXPixelValue() {
            double xLoc = 0;
            // Multiply the letterNum with the constant value
            // for a single character
            xLoc = LETTER_WIDTH * letterNum;
            return xLoc;
        }

        // Return the Y-pixel value for the location
        public double getYPixelValue() {
            //the 2 is a constant to offset the cursor a little.
            // Multiply the lineNum with the constant value
            // for a single character
            double yLoc = LETTER_HEIGHT * lineNum + 2;
            return yLoc;
        }
}
