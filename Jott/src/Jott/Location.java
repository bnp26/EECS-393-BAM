package Jott;

public class Location {

    private final double LETTER_HEIGHT = 15;
    private final double LETTER_WIDTH = 8.40625;

	private int lineNum;
//	private int wordNum;
	private int letterNum;

    public Location() {
        lineNum = 0;
  //      wordNum = 0;
        letterNum = 0;
    }

	public Location(int line, int letter) {
		lineNum = line;
//		wordNum = word;
		letterNum = letter;
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

	public Location pixelsToLocation(double x, double y) {
		Location loc = new Location(0,0);

        Double lineNumber = new Double(y/LETTER_HEIGHT);
        Double letterNumber = new Double(x/LETTER_WIDTH);

        int intLineNumber = lineNumber.intValue();
        int intLetterNumber = letterNumber.intValue();

        System.out.println("("+intLineNumber + ", " + intLetterNumber+")");

        loc.changeLocation(intLineNumber, intLetterNumber);
		return loc;
	}

	public double getXPixelValue() {
        double xLoc = 0;
        xLoc = LETTER_WIDTH * letterNum;
        return xLoc;
    }

    public double getYPixelValue() {
        double yLoc = 0;
        yLoc = LETTER_HEIGHT * lineNum;
        return yLoc;
    }
}
