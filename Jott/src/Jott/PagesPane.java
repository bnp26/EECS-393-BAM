package Jott;

import java.util.ArrayList;

public class PagesPane {
	
	private ArrayList<Page> pages;
	
	
	public PagesPane() {
		pages = new ArrayList<Page>();
	}
	
	public void addPage(Page page) {
		pages.add(page);
	}
	
	private boolean hasPage(String name) {
		if(pages.size() == 0)
			return false;
		
		for(Page page : pages)
		{
			if(page.getName() == name)
				return true;
		}
		
		return false;	
	}
	
	
}
