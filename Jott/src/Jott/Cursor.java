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
		
		return null;
	}
	
	public Letter backspace(){
		return null;
	}
	
	public void move(Location loc){
		location = loc;
	}
	
	public void move(int line, int word, int letter){
		location.changeLocation(line, word, letter);
	}
	
	public void enter(){}
	
	
}
