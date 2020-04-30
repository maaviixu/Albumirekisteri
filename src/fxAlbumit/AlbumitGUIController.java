package fxAlbumit;

import fi.jyu.mit.fxgui.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import fi.jyu.mit.fxgui.StringGrid;
import static fxAlbumit.TietueDialogController.getFieldId;


import albumirekisteri.Rekisteri;

import java.io.PrintStream;
import java.net.URL;
import java.util.Collection;
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
	 @FXML private GridPane gridAlbumi;
	 @FXML private ListChooser<Albumi> chooserAlbumit;
	 @FXML private StringGrid<Kappale> tableKappaleet;
	 @FXML private ComboBoxChooser<String> cbKentat;

	 
	 
	 @FXML private void handleHakuehto() { 
	        hae(0); 
	    }

	 
	 @FXML
	 void handleTallenna() {
		 tallenna();
	 }
	 
	 @FXML
	 void handleUusiAlbumi() {
		 uusiAlbumi();		 
	 }
	 
	 @FXML
	 void handleMuokkaaAlbumia() {
		 muokkaa(kentta);		 			 
	 }
	 
	 @FXML
	 void handleTietoja() {
		 ModalController.showModal(AlbumitGUIController.class.getResource("TietojaGUIView.fxml"),
				 "Tietoja", null, "");
	 }
	 
	 @FXML
	 void handlePoista() {
		 poistaAlbumi();
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
	 void handleLisaakap() {
	     uusiKappale();
	 }
	 
	 @FXML
	 void handlePoistakap() {
	     poistaKappale();
	 }
	 
	 @FXML
	 void handleHae() {
		 hae();
	 }
	 
	 
	 // ==========================================
	 
	 private Rekisteri rekisteri;
	 private Albumi albumiKohdalla;
	 private TextField edits[];
	 private int kentta = 0;
	 private static Albumi apualbumi = new Albumi();
	 private static Kappale apukappale = new Kappale();
	 
	 
	 
	 /**
	  * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
	  * yksi iso tekstikenttä, johon voidaan tulostaa albumien tiedot.
	  * Alustetaan myös albumilistan kuuntelija
	  */
	 protected void alusta() {		 
		 chooserAlbumit.clear();
		 chooserAlbumit.addSelectionListener(e -> naytaAlbumi());
		 
		 
		 cbKentat.clear();
		 for (int k = apualbumi.ekaKentta(); k < apualbumi.getKenttia(); k++) 
		     cbKentat.add(apualbumi.getKysymys(k), null);
		 
	     cbKentat.getSelectionModel().select(0); 	        
	     edits = TietueDialogController.luoKentat(gridAlbumi, apualbumi);

		 
		 for (TextField edit : edits)
		     if (edit != null) {
		         edit.setEditable(false);
		         edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); });  
	             edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
	             edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaa(kentta);});
		     }
		 
		 int eka = apukappale.ekaKentta();
		 int lkm = apukappale.getKenttia();
		 String[] otsikot = new String[lkm-eka];
		 for (int i = 0, k = eka; k < lkm; i++, k++) {
		     otsikot[i] = apukappale.getKysymys(k);
		     
		     
		 }
		 
		 tableKappaleet.initTable(otsikot);
		 tableKappaleet.setEditable(true);
         tableKappaleet.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
         tableKappaleet.setPlaceholder(new Label("Ei vielä kappaleita"));
         tableKappaleet.setColumnWidth(-1, 200);
         tableKappaleet.setOnMouseClicked( e -> { if ( e.getClickCount() > 1 ) muokkaaKappaletta(); } );
         tableKappaleet.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaaKappaletta();}); 

	 }
	 
	 private void muokkaaKappaletta() {
	     int r = tableKappaleet.getRowNr();
	     if ( r < 0 ) return;
	     Kappale kap = tableKappaleet.getObject(r);
	     if ( kap == null) return;
	     int k = tableKappaleet.getColumnNr()+kap.ekaKentta();
	     try {
	         kap = TietueDialogController.kysyTietue(null, kap.clone(), k);
	         if (kap == null) return;
	         rekisteri.korvaaTaiLisaa(kap);
	         naytaKappaleet(albumiKohdalla);
	         tableKappaleet.selectRow(r);
	     } catch (CloneNotSupportedException  e) { /* clone on tehty */
	     } catch (SailoException e) {
	         Dialogs.showMessageDialog("Ongelmia lisäämisessä: " + e.getMessage());
	     }
	 }
	 
	 

    private void muokkaa(int k) {
	     if (albumiKohdalla == null) return;
	     try {
	         Albumi albumi;
	         albumi = TietueDialogController.kysyTietue(null, albumiKohdalla.clone(), k);
	         if (albumi == null) return;
	         rekisteri.korvaaTaiLisaa(albumi);
	         hae(albumi.getTunnusNro());
	     } catch (CloneNotSupportedException e ) {
	         //
	     } catch (SailoException e) { 
	            Dialogs.showMessageDialog(e.getMessage()); 
	        } 

	     
	 }
	 
	 
	 /**
	  * Näyttää listasta valitun albumin tiedot, tilapäisesti yhteen isoon edit-kenttään
	  */
	 protected void naytaAlbumi() {
		 albumiKohdalla = chooserAlbumit.getSelectedObject();
		 
		 if (albumiKohdalla == null) return;
		 
		 TietueDialogController.naytaTietue(edits, albumiKohdalla);
		 naytaKappaleet(albumiKohdalla);
		 double kesto = rekisteri.annaKesto(albumiKohdalla.getTunnusNro());
		 edits[edits.length-1].setText("" + kesto);
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
	  * @param jnr albumin numero, joka aktivoidaan haun j�lkeen
	  */
	 protected void hae(int jnr) {
	     int jnro = jnr;
	     if (jnro <= 0) {
	         Albumi kohdalla = albumiKohdalla;
	         if (kohdalla != null) jnro = kohdalla.getTunnusNro();
	     }
	     
	     int k = cbKentat.getSelectionModel().getSelectedIndex() + apualbumi.ekaKentta();
	     String ehto = hakuehto.getText();
	     if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
	     
		 chooserAlbumit.clear();
		 		 		 
		 int index = 0;
		 Collection<Albumi> albumit;
		 try {
	            albumit = rekisteri.etsi(ehto, k);
	            int i = 0;
	            for (Albumi albumi:albumit) {
	                if (albumi.getTunnusNro() == jnro) index = i;
	                chooserAlbumit.add(albumi.getNimi(), albumi);
	                i++;
	            }
	        } catch (SailoException ex) {
	            Dialogs.showMessageDialog("Jäsenen hakemisessa ongelmia! " + ex.getMessage());
	        }

		 /*
		 for (int i = 0; i < rekisteri.getAlbumit(); i++) {
			 Albumi albumi = rekisteri.annaAlbumi(i);
			 if ( albumi.getTunnusNro() == jnro) index = i;
			 chooserAlbumit.add(albumi.getNimi(), albumi);
		 }
		 */
		 chooserAlbumit.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
	 }
	 
	 /**
	  * Luo uuden albumin jota aletaan editoimaan.
	  */
	 protected void uusiAlbumi() {
		 try {
		     Albumi uusi = new Albumi();
		     uusi = TietueDialogController.kysyTietue(null, uusi, 1);
		     if (uusi == null) return;
		     uusi.rekisteroi();
			 rekisteri.lisaa(uusi);
			 hae(uusi.getTunnusNro());
		 } catch (SailoException e) {
			 Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
			 return;
		 }
	 }
	 
	 private void naytaKappaleet(Albumi albumi) {
	     tableKappaleet.clear();
	     if ( albumi == null ) return;
	     
	     List<Kappale> kappaleet = rekisteri.annaKappaleet(albumi);
         if ( kappaleet.size() == 0) return;
         for ( Kappale kap : kappaleet)
             naytaKappale(kap);
	 }
	 
	 private void naytaKappale(Kappale kap) {
	     int kenttia = kap.getKenttia();
	     String[] rivi = new String[kenttia-kap.ekaKentta()];
	     for (int i = 0, k = kap.ekaKentta(); k < kenttia; i++, k++) {
	         rivi[i] = kap.anna(k);
	     }
	     tableKappaleet.add(kap,rivi);
	         
	 }
	 
	 /**
	 * Luo uuden kappaleen
	 */
	protected void uusiKappale() {
	    if (albumiKohdalla == null ) return;
	    try {
	        Kappale uusi = new Kappale(albumiKohdalla.getTunnusNro());
	        uusi = TietueDialogController.kysyTietue(null, uusi, 0);
	        if ( uusi == null ) return;
            uusi.rekisteroi();
            rekisteri.korvaaTaiLisaa(uusi);
            naytaKappaleet(albumiKohdalla);
            tableKappaleet.selectRow(1000); // Järjestää viimeisen rivin valituksi
	    } catch (SailoException e) {
	        Dialogs.showMessageDialog("Lisääminen epäonnistui: " + e.getMessage());

	    }	    
	 }
	 
	 
	 private void hae() {
		 Dialogs.showMessageDialog("Haetaan albumit! Mutta ei toimi vielä");
	 }

	 private void tulosta() {
		 Dialogs.showMessageDialog("Tulostetaan! Mutta ei toimi vielä");
	 }

	 
	 private void sulje() {
		 Dialogs.showMessageDialog("Suljetaan ohjelma! Mutta ei toimi vielä");
	 }
	 
	 private void tallenna() {
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
		 lueTiedosto();
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
     * Alustaa rekisterin lukemalla sen valitun nimisestä tiedostosta
     * @return null, jos onnistuu, muuten virhe tekstinä
     */
    protected String lueTiedosto() {        
        try {
            rekisteri.lueTiedostosta();
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage(); 
            if ( virhe != null ) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
    }
    
    /**
     * Poistetaan kappaletaulukosta valitulla kohdalla oleva kappale
     */
    private void poistaKappale() {
        int rivi = tableKappaleet.getRowNr();
        if(rivi < 0 ) return;
        Kappale kappale = tableKappaleet.getObject();
        if (kappale == null) return;
        rekisteri.poistaKappale(kappale);
        naytaKappaleet(albumiKohdalla);
        int kappaleita = tableKappaleet.getItems().size();
        if (rivi >= kappaleita) rivi = kappaleita-1;
        tableKappaleet.getFocusModel().focus(rivi);
        tableKappaleet.getSelectionModel().select(rivi);
    }
    
    
    /**
     * Poistetaan listalta valittu albumi
     */
    private void poistaAlbumi() {
        Albumi albumi = albumiKohdalla;
        if (albumi == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko albumi: " + albumi.getNimi(), "Kyllä", "Ei"))
            return;
        rekisteri.poista(albumi);
        int index = chooserAlbumit.getSelectedIndex();
        hae(0);
        chooserAlbumit.setSelectedIndex(index);
    }

    
    
    
    
    
    
    
    
    
    
    
    
}












