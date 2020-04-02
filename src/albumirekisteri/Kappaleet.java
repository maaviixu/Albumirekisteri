package albumirekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;




/**
 * Rekisterin kappaleet, joka osaa mm. lisätä uuden kappalee.
 * @author maaviixu
 *
 */
public class Kappaleet implements Iterable<Kappale> {
	
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "albumit/kappaleet";
	
	
	/** Taulukko kappaleista */
	private final Collection<Kappale> alkiot		= new ArrayList<Kappale>();
	
	
	/**
	 * Kappaleiden alustaminen
	 */
	public Kappaleet() {
		// Toistaiseksi ei tarvitse tehdä mitään
	}
	
	
	/**
	 * Lisää uuden kappaleen tietorkaneteeseen. Ottaa kappaleen omistuukdeensa
	 * @param kap lisättävä kappale. Huom tietorakenne muuttuu omistajaksi
	 */
	public void lisaa(Kappale kap) {
		alkiot.add(kap);
		muutettu = true;
	}
	
	/**
	 * Lukee kappaleet tiedostosta
	 * @param tied tiedoston nimen alkuosa
	 * @throws SailoException jos lukeinen epäonnistuu
	 */
	public void lueTiedostosta(String tied) throws SailoException {
	    setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {

            String rivi;
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Kappale kap = new Kappale();
                kap.parse(rivi); // voisi olla virhekäsittely
                lisaa(kap);
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
	 * Tallentaa rekisterin tiedostoon.
	 * @throws SailoException jos tallentaminen epäonnistuu
	 */
	public void tallenna() throws SailoException {
	    if ( !muutettu ) return;
	    
	    File fbak = new File(getBakNimi());
	    File ftied = new File(getTiedostonNimi());
	    fbak.delete();
	    
	    
	    try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Kappale kap : this) {
                fo.println(kap.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;	    		
	}
	

    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param tied tallennutstiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
        
    }
    
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallenukseen
     * @return tallennustiedoston nimi
     */
    private String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


	/**
	 * Palauttaa tiedoston nimen, jota käytetään tallenukseen
	 * @return tallennustiedoston nimi
	 */
	public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }


    /**
	 * Palauttaa albumin kappaleiden lukumäärän.
	 * @return kappaleiden lukumäärä
	 */
	public int getLkm() {
		return alkiot.size();
	}
	
	
	/**
	 * Iteraattori kaikkien kappaleiden läpikäymiseen
	 * @return kappaleiteraattori
	 * @example
	 * <pre name="test">
	 * 
	 * Teepp� testit
	 * 
	 * </pre>
	 */
	@Override
	public Iterator<Kappale> iterator() {
		return alkiot.iterator();
	}
	
	
	
	/**
	 * Haetaan kaikki albumin kappaleet
	 * @param tunnusnro albumin tunnusnumero joille kappaleita haetaan
	 * @return teitorakenne jossa viitteet l�ydettyihin kappaleisiin
	 * @example
	 * <pre name="test">
	 * 
	 * teepp� testit
	 * 
	 * 
	 * </pre>
	 */
	public List<Kappale> annaKappaleet(int tunnusnro) {
		List<Kappale> loydetyt = new ArrayList<Kappale>();
		for (Kappale kap : alkiot)
			if (kap.getAlbumiNro() == tunnusnro) loydetyt.add(kap);
		return loydetyt;
	}
	
	
	/**
	 * @param args ei käytössä
	 */
	public static void main(String[] args) {
		Kappaleet laulut = new Kappaleet();
		Kappale laulu1 = new Kappale();
		laulu1.vastaaMaailmanParasLaulu(2);
		Kappale laulu2 = new Kappale();
		laulu2.vastaaMaailmanParasLaulu(1);
		Kappale laulu3 = new Kappale();
		laulu3.vastaaMaailmanParasLaulu(1);
		Kappale laulu4 = new Kappale();
		laulu4.vastaaMaailmanParasLaulu(3);
		
		laulut.lisaa(laulu1);
		laulut.lisaa(laulu2);
		laulut.lisaa(laulu3);
		laulut.lisaa(laulu4);
		laulut.lisaa(laulu2);
		
		System.out.println("==================== Kappaleiden testi ================");
		
		List<Kappale> kappaleet2 = laulut.annaKappaleet(1);
		
		for (Kappale kap : kappaleet2) {
			System.out.println(kap.getTunnusNro() + " ");
			kap.tulosta(System.out);
		}
		
	}
	
	
	
	

}
