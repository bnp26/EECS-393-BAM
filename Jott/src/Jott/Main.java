package Jott;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.fxml.*;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox page = (HBox) FXMLLoader.load(Main.class.getResource("static/JottPrototype.fxml"));
			
			Region secondRegion = getPagePaneRegion(page);
			Scene scene = new Scene(page,800,600);
			
			JottController controller = new JottController();
			
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
