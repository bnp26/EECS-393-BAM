package Jott;
	
import com.jfoenix.controls.JFXButton;
import com.mongodb.Mongo;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.*;

import java.util.ArrayList;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MongoDB mongodb = new MongoDB();

			HBox page = (HBox) FXMLLoader.load(Main.class.getResource("static/JottPrototype.fxml"));

            Region secondRegion = getPagePaneRegion(page);
			Scene scene = new Scene(page,900, 600);

			page.getChildren().get(1).setOnKeyPressed(page.getChildren().get(1).getOnKeyPressed());
			page.getChildren().get(1).setOnKeyPressed(page.getChildren().get(1).getOnKeyPressed());


			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			System.out.println("Resolution: " + primaryScreenBounds.getHeight() + ", " + primaryScreenBounds.getWidth());

			primaryStage.setScene(scene);
			primaryStage.show();
			//populateData(mongodb, controller);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void populateData(MongoDB mongoDB, JottController controller) {
		NotebooksPane notebooksPane = controller.getNotebooksPane();

		ArrayList<String> notebooks = mongoDB.getNotebooks();

		for(String notebook:notebooks) {

			if(notebook.equals("admin") || notebook.equals("local"))
				continue;

			notebooksPane.createNewNotebook(notebook);

			Notebook current = notebooksPane.getNotebook(notebook);

			ArrayList<String> pages = mongoDB.getPages(current.toString());
			for(String page : pages) {
				if(page.equals("-**BLANK**-") || page.equals("system.indexes"))
					continue;
				controller.setPagesPane(current.getPagesPane());

				long pageLength = mongoDB.getPageLength(notebook, page);
				System.out.println(pageLength);
				Page thePage = createNewPage(page, current.getPagesPane());

				for(int x = 1; x <= pageLength; x++) {
					Line line = new Line(x-1);
					line.setLineValue(mongoDB.getLine(notebook, page, x));

					thePage.getLines().add(line);
				}
				JFXButton button = createPageButton(page);
				thePage.setButton(button);
				current.getPagesPane().addPage(thePage);
			}
		}
	}

	public Page createNewPage(String name, PagesPane pagesPane) {

		if(name == null)
		{
			name = "new page";
		}

		//creates a new page object
		Page page = new Page(name);

		//creates the new page button
		JFXButton newPageButton = createPageButton(name);

		page.setButton(newPageButton);
		//adds the new button to the pagesVBox but puts it always at the end of the list but above the add new page button

		pagesPane.addPage(page);

		newPageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				pagesPane.selectPage(page);
				System.out.println("selected page: " + page.getName());
			}
		});
		return page;
	}

	private JFXButton createPageButton(String name) {

		JFXButton newPage = new JFXButton();
		//setting up the new page button
		newPage.setText(name);

		newPage.getStyleClass().add("jott_page_item");

		return newPage;
	}

	private void setUpGUI() {
		HBox mainWindow = new HBox();
		VBox primaryVBox, secondaryVBox, trinaryVBox, pagesVBox;

		primaryVBox = new VBox();
		secondaryVBox = new VBox();
		trinaryVBox = new VBox();

		mainWindow.getChildren().add(primaryVBox);
		primaryVBox.getChildren().add(secondaryVBox);
		secondaryVBox.getChildren().add(trinaryVBox);

		ComboBox<Notebook> notebookComboBox = new ComboBox<Notebook>();

		trinaryVBox.getChildren().add(notebookComboBox);

		ScrollPane pagesScrollPane = new ScrollPane();
		pagesVBox = new VBox();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Region getPagePaneRegion(Region region)
	{
		Region newRegion = new Region();
		while(region.getChildrenUnmodifiable().get(0).getClass() == Region.class) {
			System.out.println("trying to find Region");
		}
		return newRegion;
	}
}
/*
Notebook 
	-name (unique)

Page
	-name (also unique)
	-notebook (Foreign Key)

Line
	-linenum
	-linestr
	-Page (Forgein Key)


//Notebook <- Page (page has a foreign key to Notebook
//Page <- Line (Line has a foreign key to PAge)
*/
