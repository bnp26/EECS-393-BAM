package Jott;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PagesPane {
	
	private ArrayList<Page> pages;
	private Page selectedPage;
    private VBox pagesVBox;

    public PagesPane() {
    	pages = new ArrayList<Page>();
	}

	public PagesPane(String notebook, VBox pagesVBox) {
		this.pagesVBox = pagesVBox;
        pages = new ArrayList<Page>();
	}

	public Page createNewPage(String name) {

		if(name == null)
		{
			name = "new page";
		}

		//creates a new page object
		Page page = new Page(name, pagesVBox);

		//creates the new page button
		JFXButton newPageButton = createPageButton(name);

		page.setButton(newPageButton);
		//adds the new button to the pagesVBox but puts it always at the end of the list but above the add new page button

		addPage(page);
		return page;
	}

	private JFXButton createPageButton(String name) {

		JFXButton newPage = new JFXButton();
		//setting up the new page button
		newPage.setText(name);

		newPage.getStyleClass().add("jott_page_item");

		return newPage;
	}

	public void addPage(Page page) {
		pages.add(page);
		JFXButton button = page.getButton();

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                selectPage(page);
            }
        });

        if(selectedPage != null)
        	selectedPage.deselectPage();

		page.setButton(button);
        selectedPage = page;
		selectPage(page);
	}

	public void selectPage(Page page) {
        if(selectedPage == null) {
            selectedPage = page;
            selectedPage.selectPage();
            return;
        } else {
			selectedPage.deselectPage();
			selectedPage = page;
			page.selectPage();
		}
	}

    public Page getSelectedPage() {
		return selectedPage;
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
