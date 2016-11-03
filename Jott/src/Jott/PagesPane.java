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
	
	public ArrayList<Page> getPages() {
		return pages;
	}
	
	public boolean hasPage(String name) {
		if(pages.size() == 0)
			return false;
		
		for(Page page : pages)
		{
			System.out.println(page.getName() + "\t" +name);
			
			if(page.getName().equals(name))
				return true;
		}
		
		return false;	
	}
	
	public String toString() {
		String pagesList = "";
		
		for(Page page : pages)
			pagesList += "[" + page.getName() + "]";
		
		return pagesList;
	}
	
}
