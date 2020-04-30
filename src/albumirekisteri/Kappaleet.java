package albumirekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.regex.Pattern;




/**
 * Rekisterin kappaleet, joka osaa mm. lisätä uuden kappalee.
 * @author maaviixu
 *
 */
public class Kappaleet implements Iterable<Kappale> {
	
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "albumit/kappaleet";
	
	
	/** Taulukko kappaleista */
	private final List<Kappale> alkiot		= new ArrayList<Kappale>();
	
	
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
	 * Poistaa valitun kappaleen
	 * @param kappale poistettava kappale
	 * @return true jos löytyi poistettava tietue
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException 
     * #import java.io.File;
     * Kappaleet kappaleet = new Kappaleet();
     * Kappale kap1 = new Kappale();
     * kap1.vastaaMaailmanParasLaulu(1);
     * Kappale kap2 = new Kappale();
     * kap2.vastaaMaailmanParasLaulu(2);
     * Kappale kap3 = new Kappale();
     * kap3.vastaaMaailmanParasLaulu(3);
     * kappaleet.lisaa(kap1);
     * kappaleet.lisaa(kap2);
     * kappaleet.poista(kap3) === false;
     * kappaleet.poista(kap2) === true;
	 * </pre>
	 */
	public boolean poista(Kappale kappale) {
	    boolean ret = alkiot.remove(kappale);
	    if (ret) muutettu = true;
	    return ret;
	}
	
	/**
	 * Poistaa kaikki tietyn albumin kappaleet
	 * @param tunnusNro viite siihen, mihin liittyvät tietueet poistetaan
	 * @return montako poistettiin
	 * @example
	 * <pre name="test">
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
     * kappaleet.poistaAlbuminKappaleet(1) === 2;
	 * </pre>
	 */
	public int poistaAlbuminKappaleet(int tunnusNro) {
	    int n = 0;
	    for (Iterator<Kappale> it = alkiot.iterator(); it.hasNext();) {
	        Kappale kap = it.next();
	        if (kap.getAlbumiNro() == tunnusNro) {
	            it.remove();
	            n++;
	        }
	    }
	    if (n > 0) muutettu = true;
	    return n;
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
	 * #PACKAGEIMPORT
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
     * Iterator<Kappale> i2=kappaleet.iterator();
     * i2.next() === kap1;
     * i2.next() === kap2;
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
	public List<Kappale> annaKappaleet(int tunnusnro) {
		List<Kappale> loydetyt = new ArrayList<Kappale>();
		for (Kappale kap : alkiot)
			if (kap.getAlbumiNro() == tunnusnro) loydetyt.add(kap);
		return loydetyt;
	}
	
	/**
	 * Palauttaa albumin kappaleiden kokonaiskeston
	 * @param id albumin id
	 * @return kokonaiskesto
	 * @example
	 * <pre name="test">
	 * Kappaleet kappaleet = new Kappaleet();
     * Kappale kap1 = new Kappale(); 
     * Kappale kap2 = new Kappale();
     * Kappale kap3 = new Kappale();
     * kap1.parse(" 1 | 6 | Jytää | 3.44 ");
     * kap2.parse(" 2 | 7 | Jytää | 3.44 ");
     * kap3.parse(" 3 | 6 | Jytää | 3.44 ");
     * kappaleet.lisaa(kap1);
     * kappaleet.lisaa(kap2);
     * kappaleet.lisaa(kap3);
     * kappaleet.kokonaiskesto(6) ~~~ 7.28;
	 * </pre>
	 */
	public double kokonaiskesto(int id) {
	    double kesto = 0;
	    int kokmin = 0;
	    int koksek = 0;
	    for (Kappale kap : alkiot)
	        if (kap.getAlbumiNro() == id) {
	            kesto = kap.getKesto();
	            String s = "" + kesto;
	            String[] apu = s.split(Pattern.quote("."));
	            int min = Integer.parseInt(apu[0]);
	            int sek = Integer.parseInt(apu[1]);
	            kokmin = kokmin + min;
	            koksek = koksek + sek;
	        }
	    int jj = koksek%60;
	    kokmin += (koksek-jj)/60;
	    koksek = jj;
	    
	    return Double.parseDouble(kokmin + "." + koksek);
	}
	
	
	/**
	 * Korvaa kappaleen tietorakenteessa. Ottaa kappaleen omistukseensa.
	 * Etsitään samalla tunnusnumerolla oleva kappale. Jos ei löydy,
	 * niin lisätään uutena kappaleena
	 * @param kappale lisättävän kappaleen viite
	 * @throws SailoException jos tietorakenne on jo täynnä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
     * Kappaleet kappaleet = new Kappaleet();
     * Kappale kap1 = new Kappale(), kap2 = new Kappale(), kap3 = new Kappale();
     * kap1.rekisteroi();
     * kap2.rekisteroi();
     * kap3.rekisteroi();
     * kappaleet.getLkm() === 0;
     * kappaleet.korvaaTaiLisaa(kap1);
     * kappaleet.getLkm() === 1;
     * kappaleet.korvaaTaiLisaa(kap2);
     * kappaleet.getLkm() === 2;
	 * </pre>
	 */
	public void korvaaTaiLisaa(Kappale kappale) throws SailoException {
	    int id = kappale.getTunnusNro();
	    for (int i = 0; i < getLkm(); i++) {
	        if (alkiot.get(i).getTunnusNro() == id) {
	            alkiot.set(i, kappale);
	            muutettu = true;
	            return;
	        }
	    }
	    lisaa(kappale);
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
