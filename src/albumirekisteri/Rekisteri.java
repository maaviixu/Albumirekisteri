package albumirekisteri;

import java.util.Collection;
import java.util.List;

/**
 * Rekisteri-luokka, joka huolehtii albumeista, artisteista
 * ja kappaleista. 
 * @author maaviixu
 *
 */
public class Rekisteri {
	private final Albumit albumit = new Albumit();
	private final Kappaleet kappaleet = new Kappaleet();
	
	/**
	 * Palauttaa rekisterin albumeiden lukumäärän
	 * @return albumeiden lukumäärä
	 */
	public int getAlbumit() {
		return albumit.getLkm();		
	}
	
	
	/**
	 * Poistaa albumeista, artisteista ja kappaleista ne joilla on nro.
	 * Kesken.
	 * @param albumi albumi joka poistetaan
	 * @return montako poistettiin
	 */
	public int poista(Albumi albumi) {
		if (albumi == null) return 0;
		int ret = albumit.poista(albumi.getTunnusNro());
		kappaleet.poistaAlbuminKappaleet(albumi.getTunnusNro());
		return ret;
	}
	
	
	/**
	 * Poistaa tämän kappaleen
	 * @param kappale poistettava kappale
	 */
	public void poistaKappale(Kappale kappale) {
	    kappaleet.poista(kappale);
	}
	
	
	
	
	/**
	 * Lisää rekisteriin uuden albumin.
	 * @param albumi lisättävä albumi
	 * @throws SailoException jos lisäystä ei voida tehdä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Rekisteri rekisteri = new Rekisteri();
	 * Albumi levy1 = new Albumi(), levy2 = new Albumi();
	 * levy1.rekisteroi(); levy2.rekisteroi();
	 * rekisteri.getAlbumit() === 0;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 1;
	 * rekisteri.lisaa(levy2); rekisteri.getAlbumit() === 2;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 3;
	 * rekisteri.annaAlbumi(0) === levy1;
	 * rekisteri.annaAlbumi(1) === levy2;
	 * rekisteri.annaAlbumi(2) === levy1; 
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 4;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 5;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 6;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 7;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 8;
	 * </pre>
	 */
	public void lisaa(Albumi albumi) throws SailoException {
		albumit.lisaa(albumi);
	}
	
	
	/**
     * Lisätään uusi kappale rekisteriin
     * @param kap lisättävä kappale
     */
    public void lisaa(Kappale kap) {
        kappaleet.lisaa(kap);
        
    }
	
	
	/**
	 * Palauttaa i:n albumin
	 * @param i monesko albumi palautetaan
	 * @return viite i:teen albumiin
	 * @throws IndexOutOfBoundsException jos i v��rin
	 */
	public Albumi annaAlbumi(int i) throws IndexOutOfBoundsException {
		return albumit.anna(i);
	}
	
	
	/**
     * Haetaan kaikki albumin kappaleet
     * @param albumi albumi jolle kappaleita haetaan
     * @return tietorakenne jossa viitteet löydettyihin kappaleisiin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * Kappaleet kappaleet = new Kappaleet();
     * Kappale kap1 = new Kappale();
     * kap1.vastaaMaailmanParasLaulu(1);
     * Kappale kap2 = new Kappale();
     * kap2.vastaaMaailmanParasLaulu(2);
     * Kappale kap11 = new Kappale();
     * kap11.vastaaMaailmanParasLaulu(1);
     * kappaleet.lisaa(kap1);
     * kappaleet.lisaa(kap2);
     * kappaleet.lisaa(kap11);
     * List<Kappale> loytyneet;
     * loytyneet = kappaleet.annaKappaleet(3);
     * loytyneet.size() === 0;
     * loytyneet = kappaleet.annaKappaleet(1);
     * loytyneet.size() === 2;
     * </pre>
     */
    public List<Kappale> annaKappaleet(Albumi albumi) {      
        return kappaleet.annaKappaleet(albumi.getTunnusNro());
    }
    
    /**
     * Korvaa kappaleen tietorakenteessa. Ottaa kappaleen omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva kappale. Jos ei löydy,
     * niin lisätään uutena kappaleena.
     * @param kappale lisättävän kappaleen viite
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    public void korvaaTaiLisaa(Kappale kappale) throws SailoException {
        kappaleet.korvaaTaiLisaa(kappale);
    }

	
	
	/**
	 * Luetaan tiedostot
	 * @throws SailoException jos lukeminen epäonnistuu
	 */
	public void lueTiedostosta() throws SailoException {
	    
		albumit.lueTiedostosta("albumit/albumit");
		kappaleet.lueTiedostosta("albumit/kappaleet");
	}
	
	
	/**
	 * Tallentaa rekisterin tiedot tiedostoon
	 * @throws SailoException jos tallentamisessa ongelmia
	 */
	public void tallenna() throws SailoException {
	    String virhe = "";
		try {
            albumit.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
		
		try {
		    kappaleet.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        
		if ( virhe.length() > 0)
		    throw new SailoException(virhe);
		
	}
	
	/**
	 * Ralauttaa hakuehtoon vastaavat viitteet
	 * @param hakuehto hakuehto
	 * @param k etsittävän kentän indeksi
	 * @return tietorakenne löytyneistä albumeista
	 * @throws SailoException jos jotakin menee väärin
	 */
	public Collection<Albumi> etsi(String hakuehto, int k) throws SailoException{
	    return albumit.etsi(hakuehto, k);
	}
	
	/**
	 * Testiohjelma rekisterist�
	 * @param args ei käytössä
	 */
	public static void main(String args[]) {
		Rekisteri rekisteri = new Rekisteri();
		
		try {
			//rekisteri.lueTiedostosta("ei hajua...");
			
			Albumi levy1 = new Albumi(), levy2 = new Albumi();
			levy1.rekisteroi();
			levy1.vastaaAlbumi();
			levy2.rekisteroi();
			levy2.vastaaAlbumi();
			
			rekisteri.lisaa(levy1);
			rekisteri.lisaa(levy2);
			
			System.out.println("============= Rekisterin testi =================");
			
			for (int i = 0; i < rekisteri.getAlbumit(); i++) {
				Albumi albumi = rekisteri.annaAlbumi(i);
				System.out.println("Albumi paikassa: " + i);
				albumi.tulosta(System.out);
			}
		} catch (SailoException ex) {
			System.out.println(ex.getMessage());
		}
		
		
	}


    /**
     * Korvaa albumin tietorakenteessa. Ottaa albumin omistukseensa.
     * Etsitään samalla tunnusnumerolla oleva albumi. Jos ei löydy,
     * niin lisätään uutena jäsenenä.
     * @param albumi lisättävän albumin viite. Huom tietorakenne muuttuu omistajaksi
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    public void korvaaTaiLisaa(Albumi albumi) throws SailoException {
        albumit.korvaaTaiLisaa(albumi);
        
    }

    /**
     * @param id albumin id numero
     * @return palauttaa albumin kappaleiden kokonaiskesto 
     */
    public double annaKesto(int id) {
        return kappaleet.kokonaiskesto(id);
    }
    

    
	
	
	
	
	
	
	

}
