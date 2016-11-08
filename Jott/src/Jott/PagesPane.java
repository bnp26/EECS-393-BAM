package Jott;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class PagesPane {
	
	private ArrayList<Page> pages;
	private Page selectedPage;

	public PagesPane() {
		pages = new ArrayList<Page>();
	}
	
	public void addPage(Page page) {
		pages.add(page);
		Button button = page.getButton();

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectPage(page);
            }
        });

        selectPage(page);
		page.setButton(button);
        selectedPage = page;
	}

	public void selectPage(Page page) {
        if(selectedPage == null) {
            selectedPage = page;
            selectedPage.selectPage();
            return;
        }
        selectedPage.deselectPage();
        selectedPage = page;
        page.selectPage();

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
