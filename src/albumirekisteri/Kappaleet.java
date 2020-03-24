package albumirekisteri;

import java.util.*;




/**
 * Rekisterin kappaleet, joka osaa mm. lis�t� uuden kappalee.
 * @author maaviixu
 *
 */
public class Kappaleet implements Iterable<Kappale> {
	
    private boolean         muutettu = false;
    private String          tiedostonPerusNimi = "albumit/kappaleet.dat";
	private String			tiedostonNimi = "";
	
	/** Taulukko kappaleista */
	private final Collection<Kappale> alkiot		= new ArrayList<Kappale>();
	
	
	/**
	 * Kappaleiden alustaminen
	 */
	public Kappaleet() {
		// Toistaiseksi ei tarvitse tehd� mit��n
	}
	
	
	/**
	 * Lis�� uuden kappaleen tietorkaneteeseen. Ottaa kappaleen omistuukdeensa
	 * @param kap lis�tt�v� kappale. Huom tietorakenne muuttuu omistajaksi
	 */
	public void lisaa(Kappale kap) {
		alkiot.add(kap);
	}
	
	/**
	 * Lukee rekisterin tiedostosta
	 * TODO kesken
	 * @param hakemisto teidoston hakemisto
	 * @throws SailoException jos lukeinen ep�onnistuu
	 */
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + ".kap";
		throw new SailoException("Ei osata viel� lukea tiedostoa " + tiedostonNimi); 
	}
	
	/**
	 * Tallentaa rekisterin tiedostoon.
	 * @throws SailoException jos tallentaminen epäonnistuu
	 */
	public void tallenna() throws SailoException {
		throw new SailoException("Ei osata vielä tallentaa tiedostoa " + tiedostonNimi); 
	}
	
	/**
	 * Palauttaa albumin kappaleiden lukum��r�n.
	 * @return kappaleiden lukum��r�
	 */
	public int getLkm() {
		return alkiot.size();
	}
	
	
	/**
	 * Iteraattori kaikkien kappaleiden l�pik�ymiseen
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
