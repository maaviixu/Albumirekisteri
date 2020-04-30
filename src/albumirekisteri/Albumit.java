package albumirekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Albumirekisterin albumit, joka osaa mm. lisätä uuden albumin
 * @author maaviixu
 *
 */
public class Albumit implements Iterable<Albumi> {
	
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
	 * albumit.lisaa(levy1); albumit.getLkm() === 4;
	 * albumit.lisaa(levy1); albumit.getLkm() === 5;
	 * albumit.lisaa(levy1); albumit.getLkm() === 6;
	 * albumit.lisaa(levy1); albumit.getLkm() === 7;
	 * albumit.lisaa(levy1); albumit.getLkm() === 8;
	 * Iterator<Albumi> alb = albumit.iterator();
	 * alb.next() === levy1;
	 * alb.next() === levy2;
	 * alb.next() === levy1;
	 * albumit.lisaa(levy1); albumit.getLkm() === 9;
     * albumit.lisaa(levy1); albumit.getLkm() === 10;
	 * </pre>
	 */
	public void lisaa(Albumi albumi) throws SailoException {
		if (lkm >= alkiot.length) {
		    Albumi uudet[] = new Albumi[lkm+20];
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
	 * Korvaa albumin tietorakenteessa. Ottaa albumin omistukseensa.
	 * Etsitään samalla tunnusnumerolla oleva albumi. Jos ei löydy,
	 * niin lisätään uutena albumina.
	 * @param albumi lisättävän albumin viite. Huom tietorakenne muuttuu omistajaksi
	 * @throws SailoException jos tietorakenne on jo täynnä
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException,CloneNotSupportedException
     * #PACKAGEIMPORT
	 * Albumit albumit = new Albumit();
     * Albumi levy1 = new Albumi(), levy2 = new Albumi();
     * levy1.rekisteroi();
     * levy2.rekisteroi();
     * albumit.getLkm() === 0;
     * albumit.korvaaTaiLisaa(levy1);
     * albumit.getLkm() === 1;
     * albumit.korvaaTaiLisaa(levy2);
     * albumit.getLkm() === 2;
	 * </pre>
	 */
	public void korvaaTaiLisaa(Albumi albumi) throws SailoException {
	    int id = albumi.getTunnusNro();
	    for (int i = 0; i < lkm; i++) {
	        if (alkiot[i].getTunnusNro() == id) {
	            alkiot[i] = albumi;
	            muutettu = true;
	            return;
	        }
	    }
	    lisaa(albumi);
	}
	
	
	/**
	 * Palauttaa viitteen i:teen albumiin
	 * @param i monennenko albumin viite halutaan
	 * @return viite jäseneen, jonka indeksi on i
	 * @throws IndexOutOfBoundsException jos ei ole sallitulla alueella
	 */
	protected Albumi anna(int i) throws IndexOutOfBoundsException {
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return alkiot[i];
	}
	
	
	/**
	 * Lukee albumeiden tiedostosta. Kesken
	 * @param tied tiedoston perusnimi
	 * @throws SailoException jos lukeminen epäonnistuu
	 */
	public void lueTiedostosta(String tied) throws SailoException {
	    setTiedostonPerusNimi(tied);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            kokoNimi = fi.readLine();
            if ( kokoNimi == null ) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");
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
	 * @throws SailoException jos tallentaminen epäonnistuu
	 */
	public void tallenna() throws SailoException {
	    if ( !muutettu ) return;
	    File ftied = new File("albumit/albumit.dat");
	    try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
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
	 * Poistetaa albumin jolla on valittu tunnusnro
	 * @param id poistettavan albumin tunnusnro
	 * @return 1 jos poistaminen onnistui, 0 jos ei löydy
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException  
     * Albumit albumit = new Albumit(); 
     * Albumi levy1 = new Albumi(), levy2 = new Albumi(), levy3 = new Albumi(); 
     * levy1.rekisteroi(); levy2.rekisteroi(); levy3.rekisteroi(); 
     * int id1 = levy1.getTunnusNro(); 
     * albumit.lisaa(levy1); albumit.lisaa(levy2); albumit.lisaa(levy3); 
     * albumit.poista(id1+1) === 1; 
     * albumit.annaId(id1+1) === null; albumit.getLkm() === 2; 
     * albumit.poista(id1) === 1; albumit.getLkm() === 1; 
     * albumit.poista(id1+3) === 0; albumit.getLkm() === 1; 
	 * </pre>
	 */
	public int poista(int id) {
	    int ind = etsiId(id);
	    if (ind < 0) return 0;
	    lkm--;
	    for (int i = ind; i < lkm; i++) 
	        alkiot[i] = alkiot[i+1];
	    alkiot[lkm] = null;
	    muutettu = true;
	    return 1;
	}
	
	
	/**
	 * 
	 * Palauttaa albumeiden lukumäärän.
	 * @return albumeiden lukumäärä
	 */
	public int getLkm() {
		return lkm;
	}
	
	/**
	 * @author Viitanen
	 * @version 29.4.2020
	 * * @example
     * <pre name="test">
     * #THROWS SailoException
     * #PACKAGEIMPORT
     * #import java.util.*;
     * Albumit albumit = new Albumit();
     * Albumi levy1 = new Albumi(), levy2 = new Albumi();
     * albumit.getLkm() === 0;
     * albumit.lisaa(levy1); albumit.getLkm() === 1;
     * albumit.lisaa(levy2); albumit.getLkm() === 2;
     * albumit.lisaa(levy1); albumit.getLkm() === 3;
     * albumit.lisaa(levy1); albumit.getLkm() === 4;
     * albumit.lisaa(levy1); albumit.getLkm() === 5;
     * albumit.lisaa(levy1); albumit.getLkm() === 6;
     * albumit.lisaa(levy1); albumit.getLkm() === 7;
     * albumit.lisaa(levy1); albumit.getLkm() === 8;
     * Iterator<Albumi> alb = albumit.iterator();
     * alb.next() === levy1;
     * alb.next() === levy2;
     * alb.next() === levy1;
     * albumit.lisaa(levy1); albumit.getLkm() === 9;
     * albumit.lisaa(levy1); albumit.getLkm() === 10;
     * </pre>
     */
	public class AlbumitIterator implements Iterator<Albumi> {
	    private int kohdalla = 0;
	    
	    @Override
	    public boolean hasNext() {
	        return kohdalla < getLkm();
	    }
	    
	    @Override
	    public Albumi next() throws NoSuchElementException {
	        if ( !hasNext() ) throw new NoSuchElementException("Ei ole");
            return anna(kohdalla++);
	    }
	    
	    @Override
	    public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
	}
	
	/**
	 * Palautetaan iteraattori albumeistaan
	 * @return albumi iteraattori
	 */
	@Override
	public Iterator<Albumi> iterator() {
	    return new AlbumitIterator();
	}
	
	/**
	 * Palauttaa hakuehtoon vastaavien albumien viitteet
	 * @param hakuehto hakuehto
	 * @param k kentän indeksi
	 * @return tietorakenne löytyneistä albumeista
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
	 * Albumit albumit = new Albumit();
	 * Albumi albumi1 = new Albumi();
     * albumi1.parse("1|Rokkia|Kirka");
     * Albumi albumi2 = new Albumi();
     * albumi2.parse("2|Jytää|Jukka");
     * Albumi albumi3 = new Albumi();
     * albumi3.parse("3|Folkia|Pekka");
     * albumit.lisaa(albumi1);
     * albumit.lisaa(albumi2);
     * albumit.lisaa(albumi3);
     * List<Albumi> loytyneet;
     * loytyneet = (List<Albumi>)albumit.etsi("*j*",1);
     * loytyneet.size() === 1;
	 * </pre>
	 */
	public Collection<Albumi> etsi(String hakuehto, int k) {
	    String ehto = "*";
	    if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
        if ( hk < 0 ) hk = 0;
        Collection<Albumi> loytyneet = new ArrayList<Albumi>();
        for (Albumi albumi : this) {
            if (WildChars.onkoSamat(albumi.anna(hk), ehto)) loytyneet.add(albumi);
        }
        return loytyneet;
	}
	
	/**
	 * Etsii albumin id:n perusteella
	 * @param id tunnusnro, jonka mukaan etsitään
	 * @return albumi jolla etsittävä id tai null
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
     * Albumit albumit = new Albumit();
     * Albumi levy1 = new Albumi(), levy2 = new Albumi(), levy3 = new Albumi();
     * levy1.rekisteroi();
     * levy2.rekisteroi();
     * levy3.rekisteroi();
     * int id1 = levy1.getTunnusNro();
     * albumit.lisaa(levy1);
     * albumit.lisaa(levy2);
     * albumit.lisaa(levy3);
     * albumit.annaId(id1) == levy1 === true;
     * albumit.annaId(id1+1) == levy2 === true;
     * albumit.annaId(id1+2) == levy3 === true;
	 * </pre>
	 */
	public Albumi annaId(int id) {
	    for (Albumi albumi : this) {
	        if (id == albumi.getTunnusNro()) return albumi;
	    }
	    return null;
	}
	
	/**
	 * Etsii albumin id:n  perusteella
	 * @param id tunnusnro, jonka mukaan etsitään
	 * @return löytyneen albumin indeksi tai -1 jos ei löydy
	 * @example
	 * <pre name="test">
	 * #THROWS SailoException
     * Albumit albumit = new Albumit();
     * Albumi levy1 = new Albumi(), levy2 = new Albumi(), levy3 = new Albumi();
     * levy1.rekisteroi();
     * levy2.rekisteroi();
     * levy3.rekisteroi();
     * int id1 = levy1.getTunnusNro();
     * albumit.lisaa(levy1);
     * albumit.lisaa(levy2);
     * albumit.lisaa(levy3);
     * albumit.etsiId(id1+1) === 1;
     * albumit.etsiId(id1+2) === 2;
	 * </pre>
	 */
	public int etsiId(int id) {
	    for (int i = 0; i < lkm; i++) {
	        if (id == alkiot[i].getTunnusNro()) return i;
	    }
	    return -1;
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
