package Jott;

public class Location {
	private int lineNum;
	private int wordNum;
	private int letterNum;
	
	public Location(int line, int word, int letter) {
		lineNum = line;
		wordNum = word;
		letterNum = letter;
	}
	
	public int getLineNum() {
		return lineNum;
	}
	
	public void setLineNum(int line) {
		lineNum = line;
	}
	
	public int getWordNum() {
		return wordNum;
	}
	
	public void setWordNum(int word) {
		wordNum = word;
	}
	
	public int getLetterNum() {
		return letterNum;
	}
	
	public void setLetterNum(int letter) {
		letterNum = letter;
	}
	
	public void changeLocation(int line, int word, int letter)
	{
		lineNum = line;
		wordNum = word;
		letterNum = letter;
	}
}
