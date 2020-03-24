package albumirekisteri;

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
	 * @param nro viitenumero , jonka mukaan poistetaan
	 * @return montako poistettiin
	 */
	public int poista(@SuppressWarnings("unused") int nro) {
		return 0;
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
	 * rekisteri.annaAlbumi(3) === levy1; #THROWS IndexOutOfBoundsException 
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 4;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 5;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 6;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 7;
	 * rekisteri.lisaa(levy1); rekisteri.getAlbumit() === 8;
	 * rekisteri.lisaa(levy1); #THROWS SailoException
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
     * @return tietorakenne jossa viitteet löydettyihin harrastuksiin
     * @example
     * <pre name="test">
     * 
     * TESTIT
     * 
     * </pre>
     */
    public List<Kappale> annaKappaleet(Albumi albumi) {      
        return kappaleet.annaKappaleet(albumi.getTunnusNro());
    }

	
	
	/**
	 * Luetaan tiedostot
	 * @param nimi jota k�ytet��n lukemisessa
	 * @throws SailoException jos lukeminen ep�onnistuu
	 */
	public void lueTiedostosta(String nimi) throws SailoException {
		albumit.lueTiedostosta(nimi);
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
		/*
		try {
		    kappaleet.tallenna();
        } catch (SailoException e) {
            virhe += e.getMessage();
        }
        */
		if ( virhe.length() > 0)
		    throw new SailoException(virhe);
		
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


    

    
	
	
	
	
	
	
	

}
