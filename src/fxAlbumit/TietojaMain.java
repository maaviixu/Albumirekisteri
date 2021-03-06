package fxAlbumit;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author maaviixu
 * @version 30.1.2020
 *
 */
public class TietojaMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("TietojaGUIView.fxml"));
            final Pane root = ldr.load();
            //final TietojaGUIController tietojaCtrl = (TietojaGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("tietoja.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Tietoja");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k?yt?ss?
     */
    public static void main(String[] args) {
        launch(args);
    }
}