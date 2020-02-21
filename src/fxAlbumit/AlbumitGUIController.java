package fxAlbumit;

import fi.jyu.mit.fxgui.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;


import albumirekisteri.Rekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import albumirekisteri.Albumi;
import albumirekisteri.SailoException;


/**
 * @author Matti
 * @version 13.1.2020
 *
 */
public class AlbumitGUIController implements Initializable {
	
	

	 
	 @FXML private ScrollPane panelAlbumi;
	 
	 @FXML private ListChooser<Albumi> chooserAlbumit;

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
	 
	 private Rekisteri rekisteri;
	 private Albumi albumiKohdalla;
	 private TextArea areaAlbumi = new TextArea();
	 
	 
	 
	 /**
	  * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
	  * yksi iso tekstikentt‰, johon voidaan tulostaa albumien tiedot.
	  * Alustetaan myˆs albumilistan kuuntelija
	  */
	 protected void alusta() {
		 panelAlbumi.setContent(areaAlbumi);
		 areaAlbumi.setFont(new Font("Courier New", 12));
		 panelAlbumi.setFitToHeight(true);
		 
		 chooserAlbumit.clear();
		 chooserAlbumit.addSelectionListener(e -> naytaAlbumi());
	 }
	 
	 
	 /**
	  * N‰ytt‰‰ lsitasta valitun albumin tiedot, tilap‰isesti yhteen isoon edit-kentt‰‰n
	  */
	 protected void naytaAlbumi() {
		 albumiKohdalla = chooserAlbumit.getSelectedObject();
		 
		 if (albumiKohdalla == null) return;
		 
		 areaAlbumi.setText("");
		 try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAlbumi)) {
			 albumiKohdalla.tulosta(os);
		 }
	 }
	 
	 
	 
	 
	 
	 /**
	  * Hakee albumien tiedot listaan 
	  * @param jnro albumin numero, joka aktivoidaan haun j‰lkeen
	  */
	 protected void hae(int jnro) {
		 chooserAlbumit.clear();
		 
		 int index = 0;
		 for (int i = 0; i < rekisteri.getAlbumit(); i++) {
			 Albumi albumi = rekisteri.annaAlbumi(i);
			 if ( albumi.getTunnusNro() == jnro) index = i;
			 chooserAlbumit.add(albumi.getNimi(), albumi);
		 }
		 chooserAlbumit.setSelectedIndex(index); // t‰st‰ tulee muutosviesti joka n‰ytt‰‰ j‰senen
	 }
	 
	 /**
	  * Luo uuden albumin jota aletaan editoimaan.
	  */
	 protected void uusiAlbumi() {
		 Albumi uusi = new Albumi();
		 uusi.rekisteroi();
		 uusi.vastaaAlbumi();
		 try {
			 rekisteri.lisaa(uusi);
		 } catch (SailoException e) {
			 Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
			 return;
		 }
		 hae(uusi.getTunnusNro());
	 }
	 
	 
	 private void hae() {
		 Dialogs.showMessageDialog("Haetaan albumit! Mutta ei toimi viel‰");
	 }
	 
	 private void tulosta() {
		 Dialogs.showMessageDialog("Tulostetaan! Mutta ei toimi viel‰");
	 }
	 
	 private void sulje() {
		 Dialogs.showMessageDialog("Suljetaan ohjelma! Mutta ei toimi viel‰");
	 }
	 
	 private void poista() {
		 Dialogs.showMessageDialog("Poistetaan albumi! Mutta ei toimi viel‰");
	 }
	 
	 private void tallenna() {
		 Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi viel‰");
	 }
	 
	 public void setRekisteri(Rekisteri rekisteri) {
		 this.rekisteri = rekisteri;
		 naytaAlbumi();
	 }

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
		
	}
}
