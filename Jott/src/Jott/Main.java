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
			NotebooksPane notebooksPane = new NotebooksPane();
			Region secondRegion = getNotePaneRegion(page);
			Scene scene = new Scene(page,600,700);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public Region getNotePaneRegion(Region region)
	{
		Region newRegion = new Region();
		while(region.getChildrenUnmodifiable().get(0).getClass() == Region.class) {
			System.out.println("trying to find Region");
		}
		return newRegion;
		
	}
}
