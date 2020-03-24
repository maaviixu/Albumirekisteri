package fxAlbumit;

import fi.jyu.mit.fxgui.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;


import albumirekisteri.Rekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import albumirekisteri.Albumi;
import albumirekisteri.Kappale;
import albumirekisteri.SailoException;


/**
 * @author Matti
 * @version 13.1.2020
 *
 */
public class AlbumitGUIController implements Initializable {
		         
     @FXML private TextField hakuehto;
	 @FXML private ScrollPane panelAlbumi;	 
	 @FXML private ListChooser<Albumi> chooserAlbumit;

	 @FXML
	 void handleTallenna() {
		 tallenna();
	 }
	 
	 @FXML
	 void handleUusiAlbumi() {
		 uusiAlbumi();
		 //ModalController.showModal(AlbumitGUIController.class.getResource("MuokkaaGUIView.fxml"),
		//		 "Albumi", null, "");
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
	     // Tähän lisää kappale 
	     // Albumin id parametrikis
	     // mallia uusiAlbumista
	     uusiKappale();
	     
		 
	     //tulosta();
	 }
	 
	 @FXML
	 void handleHae() {
		 hae();
	 }
	 
	 
	 // ==========================================
	 
	 private String rekisterinnimi = "albumit";
	 private Rekisteri rekisteri;
	 private Albumi albumiKohdalla;
	 private TextArea areaAlbumi = new TextArea();
	 
	 
	 
	 /**
	  * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
	  * yksi iso tekstikentt�, johon voidaan tulostaa albumien tiedot.
	  * Alustetaan my�s albumilistan kuuntelija
	  */
	 protected void alusta() {
		 panelAlbumi.setContent(areaAlbumi);
		 areaAlbumi.setFont(new Font("Courier New", 12));
		 panelAlbumi.setFitToHeight(true);
		 
		 chooserAlbumit.clear();
		 chooserAlbumit.addSelectionListener(e -> naytaAlbumi());
	 }
	 
	 
	 /**
	  * N�ytt�� lsitasta valitun albumin tiedot, tilap�isesti yhteen isoon edit-kentt��n
	  */
	 protected void naytaAlbumi() {
		 albumiKohdalla = chooserAlbumit.getSelectedObject();
		 
		 if (albumiKohdalla == null) return;
		 
		 areaAlbumi.setText("");
		 try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaAlbumi)) {
			 tulosta(os,albumiKohdalla);
		 }
	 }
	 
	 
	 
	 
	 
	 /**
	  * Tulostetaan albumin tiedot
	  * @param os tietovirta johon tulostetaan
	  * @param albumi tulostettava albumi
	  */
	public void tulosta(PrintStream os, final Albumi albumi) {
	    os.println("----------------------------------------------");
	    albumi.tulosta(os);
	    os.println("----------------------------------------------");
	    List<Kappale> kappaleet = rekisteri.annaKappaleet(albumi);
	    for (Kappale kap:kappaleet)
	        kap.tulosta(os);
        
    }

    /**
	  * Hakee albumien tiedot listaan 
	  * @param jnro albumin numero, joka aktivoidaan haun j�lkeen
	  */
	 protected void hae(int jnro) {
		 chooserAlbumit.clear();
		 
		 int index = 0;
		 for (int i = 0; i < rekisteri.getAlbumit(); i++) {
			 Albumi albumi = rekisteri.annaAlbumi(i);
			 if ( albumi.getTunnusNro() == jnro) index = i;
			 chooserAlbumit.add(albumi.getNimi(), albumi);
		 }
		 chooserAlbumit.setSelectedIndex(index); // t�st� tulee muutosviesti joka n�ytt�� j�senen
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
	 
	 /**
	 * Luo uuden kappaleen
	 */
	protected void uusiKappale() {
	    if (albumiKohdalla == null ) return;
	    Kappale kap = new Kappale();
	    kap.rekisteroi();
	    kap.vastaaMaailmanParasLaulu(albumiKohdalla.getTunnusNro());
	    rekisteri.lisaa(kap);
	    hae(albumiKohdalla.getTunnusNro());
	 }
	 
	 
	 private void hae() {
		 Dialogs.showMessageDialog("Haetaan albumit! Mutta ei toimi viel�");
	 }
	 
	 /*
	 Käytetään tulosta painiketta kappaleen lisäämiseen. Muokataan projektin edetessä.
	 private void tulosta() {
		 Dialogs.showMessageDialog("Tulostetaan! Mutta ei toimi viel�");
	 }
	 */
	 
	 private void sulje() {
		 Dialogs.showMessageDialog("Suljetaan ohjelma! Mutta ei toimi viel�");
	 }
	 
	 private void poista() {
		 Dialogs.showMessageDialog("Poistetaan albumi! Mutta ei toimi viel�");
	 }
	 
	 private void tallenna() {
		 // Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi viel�");
	     try {
            rekisteri.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
	 }
	 
	 /**
	 * @param rekisteri Rekisteri jota käytetään tässä köyttöliittymässä
	 */
	public void setRekisteri(Rekisteri rekisteri) {
		 this.rekisteri = rekisteri;
		 naytaAlbumi();
	 }

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		alusta();
		
	}

    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }

    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = "albumit.dat";
        lueTiedosto(uusinimi);                    
        return true;
    }

    /**
     * Alsutaa rekisterin lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     */
    protected void lueTiedosto(String nimi) {
        rekisterinnimi = nimi;
        setTitle("Rekisteri - " + rekisterinnimi);
        String virhe = "Ei osata lukea vielä";
        // if (virhe != null)
            Dialogs.showMessageDialog(virhe);
    }
    
    private void setTitle(String title) {
        ModalController.getStage(hakuehto).setTitle(title);
    }

    
}












