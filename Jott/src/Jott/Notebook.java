package Jott;

import java.util.ArrayList;

public class Notebook {
	
	private ArrayList<Page> pages;
	private String name;
	
	/**
	 *@author bnp26
	 *<p>
	 *populatePages uses a connection to MongoDB to populate the pages of 
	 *the selected notebook upon selecting a notebook
	 *</p>
	 *@param 
	 *@return 
	 *
	*/
	public void populatePages() {}
	
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
}