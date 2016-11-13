package Jott;

public class Cursor extends Glyph{
	
	public Letter insertLetter(char letter) {
		return null;
	}
	
	public Letter delete(){
		Letter oldNext = this.getNext();
		Letter newNext = this.getNext().getNext();

		setNext(newNext);
		
		return oldNext;
	}
	
	public Letter backspace(){
		Letter oldPrevious = this.getPrevious();
		Letter newPrevious = this.getPrevious().getPrevious();
		setPrevious(newPrevious);
		
		return oldPrevious;
	}
	
	public void move(Location loc){
		this.setLocation(loc);
	}
	
	public void move(int line, int word, int letter){
		this.getLocation().changeLocation(line, word, letter);
	}
	
	public void enter(){
		
	}
	//Make Page Create the cursor.
	
}
