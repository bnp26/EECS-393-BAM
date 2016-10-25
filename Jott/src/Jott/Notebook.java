package Jott;

import java.util.ArrayList;

public class Notebook {
	
	private String name; // this is equal to the key in the key/value pair in the notebooks pane HashMap.
	private PagePane pagePane;
	
	public Notebook() {
		name = null;
	}
	
	public Notebook(String name) {
		this.name = name;
		this.pagePane = new PagePane();
	}
	
	/**
	 *@author bnp26
	 *<p>
	 *populateNotebooks uses a connection to MongoDB to populate the of 
	 *the selected notebook upon selecting a notebook with it's assiciated pages.
	 *</p>
	 *@param 
	 *@return 
	 *
	*/
	public void populateNotebook() {}
	
	/**
	 * returns the page object of the passed pageID.
	 * 
	 * @param String pageID
	 * 
	 * @return Page
	 * @see Page.java
	 */
	public Page getPage(String pageID) {
		return null;
	}
	
	/**
	 * <p>Creates a new page object in both the current page and the MongoDB server. 
	 * Returns the ID of the newly created page.</p>
	 * 
	 * @return String 
	 * 
	 */
	public String createPage(String newName) { return null;}
	
	/**
	 * <p>Renames the currently selected Notebook to the entered parameter.</p>
	 * 
	 * @param String newName
	 * 
	 * @return void
	 */
	public void renameNotebook() {}
	
	@Override
	public String toString(){
		return name;
	}
	
	public int compareTo(Notebook notebook) {
		return this.name.compareTo(notebook.toString());
	}
}