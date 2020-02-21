package fxAlbumit;
	
import albumirekisteri.Rekisteri;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Matti
 * @version 13.1.2020
 *
 */
public class AlbumitMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			final FXMLLoader ldr = new FXMLLoader(getClass().getResource("AlbumitGUIView.fxml"));
			final Pane root = (Pane)ldr.load();
			final AlbumitGUIController albumitGUICtrl = (AlbumitGUIController)ldr.getController();
			final Scene scene = new Scene(root);
								
			scene.getStylesheets().add(getClass().getResource("albumit.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Albumit");
			primaryStage.show();
			
			Rekisteri rekisteri = new Rekisteri();
			albumitGUICtrl.setRekisteri(rekisteri);
			
 

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
