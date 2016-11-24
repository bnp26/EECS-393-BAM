package Jott;

import javafx.scene.control.Label;

public class Cursor extends Glyph{

    private Location location;

    public Cursor() {
        super();
        this.getLabel().setText("|");
        location = null;
    }

    public Cursor(Location loc) {
        super();
        this.getLabel().setText("|");
        this.setLocation(loc);
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
        this.getLabel().setLayoutX(loc.getXPixelValue());
        this.getLabel().setLayoutY(loc.getYPixelValue());
        this.getLabel().setText("|");
        this.getLabel().setVisible(true);
    }

	public void move(Location loc){
		this.setLocation(loc);
	}
	
	public void move(int line, int letter){
		this.getLocation().changeLocation(line, letter);
	}
	
	public void enter(){

	}
	//Make Page Create the cursor.

}
