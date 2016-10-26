package Jott;

public class Cursor {
	private Location location;
	private Letter previous;
	private Letter next;
	
	public Letter getPrevious() {
		return previous;
	}
	public void setPrevious(Letter previous) {
		this.previous = previous;
	}
	public Letter getNext() {
		return next;
	}
	public void setNext(Letter next) {
		this.next = next;
	}
	
	public Letter insertLetter(char letter) {
		return null;
	}
	
	public Letter delete(){
		Letter newNext = next.getNext();
		Letter oldNext = next;
		setNext(newNext);
		
		return oldNext;
	}
	
	public Letter backspace(){
		Letter newPrevious = previous.getPrevious();
		Letter oldPrevious = previous;
		setPrevious(newPrevious);
		
		return oldPrevious;
	}
	
	public void move(Location loc){
		location = loc;
	}
	
	public void move(int line, int word, int letter){
		location.changeLocation(line, word, letter);
	}
	
	public void enter(){}
	//Make Page Create the cursor.
	
}
