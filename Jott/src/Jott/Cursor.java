package Jott;

public class Cursor extends Glyph{

    private Location location;

    public Cursor() {
        super();
        location = null;
    }

    public Cursor(Location loc) {
        super();
        location = loc;
    }

	public Glyph insertLetter(char letter) {
        Letter newLetter = new Letter(letter);

        newLetter.setPrevious(this.getPrevious());
        newLetter.setNext(this);

        this.setPrevious(newLetter);

        return newLetter;
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

	public Location getLocation() {
        return location;
    }

    public void setLocation(Location loc) {
        location = loc;
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
