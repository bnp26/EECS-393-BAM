package Jott;

public class Cursor extends Glyph{
	
	public Glyph insertLetter(char letter) {
        Letter newLetter = new Letter(letter);

        newLetter.setPrevious((Letter)this.getPrevious());
        newLetter.setNext((Glyph)this);

        return null;
	}
	
	public Letter delete(){
		Letter oldNext = (Letter)this.getNext();
        Letter newNext = (Letter)this.getNext().getNext();

		setNext(newNext);
		
		return oldNext;
	}
	
	public Letter backspace(){
		Letter oldPrevious = (Letter)this.getPrevious();
		Letter newPrevious = (Letter)this.getPrevious().getPrevious();
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
