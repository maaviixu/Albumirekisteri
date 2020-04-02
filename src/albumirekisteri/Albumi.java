package albumirekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Albumirekisterin albumi, joka osaa mm. huolehtia tunnusNro:sta
 * @author maaviixu
 *
 */
public class Albumi {
	
	
	private int		tunnusNro;
	private String	nimi			= "";
	private String	artisti			= "";
	private int		julkaisuVuosi	= 0;
	private String 	formaatti		= "";
	private String 	yhtio			= "";
	private String	lisatietoja		= "";
	
	private static int seuraavaNro	= 1;
	
	
	/**
	 * @return albumin nimi
	 * @example
	 * <pre name="test">
	 * Albumi levy1 = new Albumi();
	 * levy1.vastaaAlbumi();
	 * levy1.getNimi() =R= "Jytäkesä .*";
	 * </pre>
	 */
	public String getNimi() {
		return nimi;
	}
	
	
	/**
	 * Tekee testialbumin
	 */
	public void vastaaAlbumi() {
		nimi = "Jytäkesä" + " " + rand(1000, 9999);
		artisti = "Jaakko ja Teppo";
		julkaisuVuosi = 1962;
		formaatti = "LP";
		yhtio = "Matulan valinta";
		lisatietoja = "Kesä -62 jäi monen mieleen niin kuin tämä albumikin";
	}
	
	/**
	 * Tulostetaan albumin tiedot
	 * @param out tietovirta johon tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi + " " + formaatti);
		out.println(artisti + " " + julkaisuVuosi  + " " + yhtio);
		out.println(lisatietoja);
		
	}
	
	
	/**
	 * Tulsotetaan albumin tiedot
	 * @param os tietovirta johon tulostetaan
	 */
	public void tulosta(OutputStream os) {
		tulosta(new PrintStream(os));
	}
	
	/**
	 * Antaa albumille seuraavan rekisterinumeron.
	 * @return albumin uusi tunnunNro
	 * @example
	 * <pre name="test">
	 * Albumi levy1 = new Albumi();
	 * levy1.getTunnusNro() === 0;
	 * levy1.rekisteroi();
	 * Albumi levy2 = new Albumi();
	 * levy2.rekisteroi();
	 * int n1 = levy1.getTunnusNro();
	 * int n2 = levy2.getTunnusNro();
	 * n1 === n2-1;
	 * </pre>
	 */
	public int rekisteroi() {
		tunnusNro = seuraavaNro;
		seuraavaNro++;
		return tunnusNro;
	}

	
	/**
	 * Palauttaa albumin tunnusnumeron
	 * @return albumin tunnusnumero
	 */
	public int getTunnusNro() {
		return tunnusNro;
	}
	
	/**
	 * Asettaa tunnusnumeron ja varmistaa, että seuraavaNro on
	 * aina suurempi kuin tähän mennessä suurin
	 * @param nr asetettava tunnusnumero
	 */
	private void setTunnusNro(int nr) {
	    tunnusNro = nr;
	    if (tunnusNro >= seuraavaNro) {
	        seuraavaNro = tunnusNro + 1;
	    }
	}
	
	
	/**
	 * Palauttaa albumin tiedot merkkijonona jonka voi tallentaa
	 * @return albumi tolppaeroteltuna merkkijonona
	 * @example
	 * <pre name="test">
	 * Albumi albumi = new Albumi();
	 * albumi.parse("     5    | JYTÄÄ   | Matti ja Mara-katti");
	 * albumi.toString().startsWith("5|JYTÄÄ|Matti ja Mara-katti|") === true;
	 * </pre>
	 */
	@Override
	public String toString() {
	    return "" + 
	            getTunnusNro() + "|" +
	            nimi + "|" +
	            artisti + "|" +
	            julkaisuVuosi + "|" +
	            formaatti + "|" +
	            yhtio + "|" +
	            lisatietoja;
	}
	
	/**
	 * Hakee albumin tiedot tolpalla erotellusta merkkijonosta.
	 * Pitää huolen, että seuraavaNro on suurempi kuin tuleva tunnusNro.
	 * @param rivi josta albumin tiedot otetaan
	 * @example
	 * <pre name="test">
	 * 
	 * TESTIT
	 * 
	 * 
	 * </pre>
	 */
	public void parse(String rivi) {
	    StringBuilder sb = new StringBuilder(rivi);
	    setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
	    nimi = Mjonot.erota(sb, '|', nimi);
	    artisti = Mjonot.erota(sb, '|', artisti);
	    julkaisuVuosi = Mjonot.erota(sb, '|', julkaisuVuosi);
	    formaatti = Mjonot.erota(sb, '|', formaatti);
	    yhtio = Mjonot.erota(sb, '|', yhtio);
	    lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
	    
	        
	}
	
	
	/**
	 * Arvotaan satunnainen kokonaisluku välille [ala,yla]
	 * @param ala arvonnan alaraja
	 * @param yla arvonnan yläraja
	 * @return satunnainen luku väliltä [ala, yla]
	 */
	public static int rand(int ala, int yla) {
		double n = (yla-ala)*Math.random() + ala;
		return (int)Math.round(n);
	}
	
	/**
	 * Testiohjelma albumille.
	 * @param args ei käytössä
	 */
	public static void main(String args[]) {
		Albumi levy = new Albumi(), levy2 = new Albumi();
		levy.rekisteroi();
		levy2.rekisteroi();
		
		levy.tulosta(System.out);
		levy.vastaaAlbumi();
		levy.tulosta(System.out);
		
		levy2.vastaaAlbumi();
		levy2.tulosta(System.out);
		
	}

}

