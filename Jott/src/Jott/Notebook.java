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
	 * 
	 * @return 
	 * @see Page.java
	 */
	public Page getPage() {
		return null;
	}
	
	public void createPage() {}
	
	public void renameNotebook() {}
	
	@Override
	public String toString(){
		return name;
	}
}