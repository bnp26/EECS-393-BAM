package Jott;

import javafx.scene.layout.VBox;

public class Notebook {
	
	private String name; // this is equal to the key in the key/value pair in the notebooks pane HashMap.
	private PagesPane pagesPane;

	public Notebook() {
		name = null;
	}
	
	public Notebook(String name, VBox pagesVBox) {
		this.name = name;
                
                // need to create a notebook in such a way
                // there is no way to create a DB in mongo
                // need to create it with a collection or a instance of data
                // we are not going to show this page when we are viewing the pages
                
                //mdb.createPage(name, "*!!!!*");
		this.pagesPane = new PagesPane(name, pagesVBox);
	}

	/**
	 * returns the page object of the passed pageID.
	 * 
	 * @param pageID
	 * 
	 * @return Page
	 * @see Page
	 */
	public Page getPage(String pageID) {
            int index = this.pagesPane.getPages().indexOf(pageID);
            if(index == -1)
            	return null;
			else
				return this.pagesPane.getPages().get(index);
	}
        
	/**
	 * <p>Renames the currently selected Notebook to the entered parameter.</p>
	 *
	 * @param String
	 *
	 * @return void
	 */
        
        // Instead of renaming a notebook we have a method for renaming a page!?
        
	public void renameNotebook(String newName) {
		name = newName;
	}

	public PagesPane getPagesPane() {return pagesPane;}

	@Override
	public String toString(){
		return name;
	}
	
	public int compareTo(Notebook notebook) {
		return this.name.compareTo(notebook.toString());
	}
}