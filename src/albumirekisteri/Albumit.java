package albumirekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Albumirekisterin albumit, joka osaa mm. lisätä uuden albumin
 * @author maaviixu
 *
 */
public class Albumit {
	
	private static final int	MAX_ALBUMIT		     = 8;
	private boolean             muutettu             = false;
	private int					lkm				     = 0;
	private String 				tiedostonPerusNimi	 = "albumit/albumit";	
	private Albumi				alkiot[]		     = new Albumi[MAX_ALBUMIT];
	
	private String              kokoNimi             = "";
	
	/**
	 * Oletusmuodostaja
	 */
	public Albumit() {
		// Atribuuttien oma alustus riitt��
	}
	
	
	/**
	 * Lisää uuden albumin tietorakenteeseen. Ottaa jäsenen omistukseensa.
	 * @param albumi lisättävän albumin viite. Huom tietorakenne muuttuu omistajaksi
	 * @throws SailoException jos tietorakenne on jo täynnä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Albumit albumit = new Albumit();
	 * Albumi levy1 = new Albumi(), levy2 = new Albumi();
	 * albumit.getLkm() === 0;
	 * albumit.lisaa(levy1); albumit.getLkm() === 1;
	 * albumit.lisaa(levy2); albumit.getLkm() === 2;
	 * albumit.lisaa(levy1); albumit.getLkm() === 3;
	 * albumit.anna(0) === levy1;
	 * albumit.anna(1) === levy2;
	 * albumit.anna(2) === levy1;
	 * albumit.anna(1) == levy1 === false;
	 * albumit.anna(1) == levy2 === true;
	 * albumit.anna(3) === levy1; #THROWS IndexOutOfBoundsException
	 * albumit.lisaa(levy1); albumit.getLkm() === 4;
	 * albumit.lisaa(levy1); albumit.getLkm() === 5;
	 * albumit.lisaa(levy1); albumit.getLkm() === 6;
	 * albumit.lisaa(levy1); albumit.getLkm() === 7;
	 * albumit.lisaa(levy1); albumit.getLkm() === 8;
	 * albumit.lisaa(levy1); #THROWS SailoException
	 * </pre>
	 */
	public void lisaa(Albumi albumi) throws SailoException {
		if (lkm >= alkiot.length) {
		    Albumi uudet[] = new Albumi[lkm*2];
		    for (int i = 0; i < alkiot.length; i++) {
		        uudet[i] = alkiot[i];
		    }
		    alkiot = uudet;
		}
		alkiot[lkm] = albumi;
		lkm++;
		muutettu = true;
	}
	
	
	/**
	 * Palauttaa viitteen i:teen albumiin
	 * @param i monennenko albumin viite halutaan
	 * @return viite jäseneen, jonka indeksi on i
	 * @throws IndexOutOfBoundsException jos ei ole sallitulla alueella
	 */
	public Albumi anna(int i) throws IndexOutOfBoundsException {
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return alkiot[i];
	}
	
	
	/**
	 * Lukee albumeiden tiedostosta. Kesken
	 * @param tied tiedoston perusnimi
	 * @throws SailoException jos lukeminen ep�onnistuu
	 * @example
	 * <pre name="test">
	 * 
	 * TESTIT
	 * 
	 * </pre>
	 */
	public void lueTiedostosta(String tied) throws SailoException {
	    setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
            // int maxKoko = Mjonot.erotaInt(rivi,10); // tehdään jotakin

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Albumi albumi = new Albumi();
                albumi.parse(rivi); // voisi olla virhekäsittely
                lisaa(albumi);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }

	}
	
	/**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }

	
	
	/**
	 * Palauttaa tiedoston nimen, jota käytetään tallennukseen
	 * @return tallennustiedoston nimi
	 */
	public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
	 * @param nimi tallennustiedoston perusnimi
	 */
	public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
        
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {        
        return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }




    /**
	 * Tallentaa albumeiden tiedostoon. Kesken.
	 * @throws SailoException jos tallentaminen ep�onnistuu
	 */
	public void tallenna() throws SailoException {
	    if ( !muutettu ) return;
	    File ftied = new File("albumit/albumit.dat");
	    try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
	        //fo.println(getKokoNimi());
	        fo.println("Albumit");
	        fo.println(alkiot.length);
	        for (int i = 0; i < getLkm(); i++) {
	            Albumi albumi = anna(i);
	            fo.println(albumi.toString());
	            
	        }
	    } catch (FileNotFoundException e) {
	        throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");	  	        
	    } catch (IOException e) {
	        throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
		muutettu = false;		
	}
	
	
	/**
	 * Palauttaa albumeiden lukumäärän.
	 * @return albumeiden lukumäärä
	 */
	public int getLkm() {
		return lkm;
	}
	
	
	
	
	
	
	/**
	 * Testiohjelma albumeille
	 * @param args ei käytössä
	 */
	public static void main(String args[]) {
		Albumit albumit = new Albumit();
		Albumi levy = new Albumi(), levy2 = new Albumi();
		
		levy.rekisteroi();
		levy.vastaaAlbumi();
		levy2.rekisteroi();
		levy2.vastaaAlbumi();
		
		try {
			albumit.lisaa(levy);
			albumit.lisaa(levy2);
			
			
			
			
			System.out.println("============= Albumit testi =================");
			
			for ( int i = 0; i < albumit.getLkm(); i++) {
				Albumi albumi = albumit.anna(i);
				System.out.println("Albumi nro: " + i);
				albumi.tulosta(System.out);
			}
			
			
		} catch (SailoException ex) {
			System.out.println(ex.getMessage());
		}
		
	}

}
