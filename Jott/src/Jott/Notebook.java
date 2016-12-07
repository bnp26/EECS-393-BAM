package Jott;

public class Notebook {
	
	private String name; // this is equal to the key in the key/value pair in the notebooks pane HashMap.
	private PagesPane pagesPane;
	private MongoDB mdb;

	public Notebook() {
		name = null;
		mdb = new MongoDB();
	}
	
	public Notebook(String name) {
		this.name = name;
                
                // need to create a notebook in such a way
                // there is no way to create a DB in mongo
                // need to create it with a collection or a instance of data
                // we are not going to show this page when we are viewing the pages
                
                //mdb.createPage(name, "*!!!!*");
		this.pagesPane = new PagesPane();
		mdb = new MongoDB();
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
            /*
            ArrayList<String> notebookList = mdb.getNotebooks();
            if (arrayList.contains("Notebook Name") {
                ArrayList<String> pageList = mdb.getPages("Notebook Name");

                if (pageList.size()>1)
                    pageList.remove("*!!!!*") //Remove the dummy page
                    // However this should be done if there is more than one page,
                    // else the whole DB is dropped

                pageList.get(pageID); // This will only give you the name of the page
                Then you can use the name of the pagee for line operations

                // However to do stuff like insertLine, deleteLine etc.
                // we call it with arguments
                // ADD A LINE: String notebook, String page, int linenum, String linestr
                // DELETE A LINE: String notebook, String page, int linenum
            }
            */

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