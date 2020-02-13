package fxAlbumit;

import fi.jyu.mit.fxgui.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * @author Matti
 * @version 13.1.2020
 *
 */
public class AlbumitGUIController {
	
	
	 @FXML
	 void handleTallenna() {
		 tallenna();
	 }
	 
	 @FXML
	 void handleUusiAlbumi() {
		 ModalController.showModal(AlbumitGUIController.class.getResource("MuokkaaGUIView.fxml"),
				 "Albumi", null, "");
	 }
	 
	 @FXML
	 void handleMuokkaaAlbumia() {
		 ModalController.showModal(AlbumitGUIController.class.getResource("MuokkaaGUIView.fxml"),
				 "Albumi", null, "");
	 }
	 
	 @FXML
	 void handleTietoja() {
		 ModalController.showModal(AlbumitGUIController.class.getResource("TietojaGUIView.fxml"),
				 "Tietoja", null, "");
	 }
	 
	 @FXML
	 void handlePoista() {
		 poista();
	 }
	 
	 @FXML
	 void handleSulje() {
		 sulje();
	 }
	
	 @FXML
	 void handleTulosta() {
		 tulosta();
	 }
	 
	 @FXML
	 void handleHae() {
		 hae();
	 }
	 
	 
	 // ==========================================
	 
	 private void hae() {
		 Dialogs.showMessageDialog("Haetaan albumit! Mutta ei toimi vielä");
	 }
	 
	 private void tulosta() {
		 Dialogs.showMessageDialog("Tulostetaan! Mutta ei toimi vielä");
	 }
	 
	 private void sulje() {
		 Dialogs.showMessageDialog("Suljetaan ohjelma! Mutta ei toimi vielä");
	 }
	 
	 private void poista() {
		 Dialogs.showMessageDialog("Poistetaan albumi! Mutta ei toimi vielä");
	 }
	 
	 private void tallenna() {
		 Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä");
	 }
}
