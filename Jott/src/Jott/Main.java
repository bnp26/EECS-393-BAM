package Jott;
	
import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MongoDB mongodb = new MongoDB();
			mongodb.insertLine("Notebook1", "page 1", 3, "abcdefg");
			System.out.println(mongodb.getLine("Notebook1", "page 1", 3));
			HBox page = (HBox) FXMLLoader.load(Main.class.getResource("static/JottPrototype.fxml"));

            Region secondRegion = getPagePaneRegion(page);
			Scene scene = new Scene(page,640, 480);

			NotebooksPane notebooksPane = new NotebooksPane();
			PagesPane pagesPane = new PagesPane();

			JottController controller = new JottController(notebooksPane, pagesPane);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
