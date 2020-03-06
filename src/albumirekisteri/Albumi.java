package albumirekisteri;

import java.io.*;


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
	 * levy1.getNimi() =R= "Jyt√§kes√§ .*";
	 * </pre>
	 */
	public String getNimi() {
		return nimi;
	}
	
	
	/**
	 * Tekee testialbumin
	 */
	public void vastaaAlbumi() {
		nimi = "Jyt‰kes‰" + " " + rand(1000, 9999);
		artisti = "Jaakko ja Teppo";
		julkaisuVuosi = 1962;
		formaatti = "LP";
		yhtio = "Matulan valinta";
		lisatietoja = "Kes‰ -62 j‰i monen mieleen niin kuin t‰m‰ albumikin";
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
	 * Arvotaan satunnainen kokonaisluku v√§lille [ala,yla]
	 * @param ala arvonnan alaraja
	 * @param yla arvonnan yl√§raja
	 * @return satunnainen luku v√§lilt√§ [ala, yla]
	 */
	public static int rand(int ala, int yla) {
		double n = (yla-ala)*Math.random() + ala;
		return (int)Math.round(n);
	}
	
	/**
	 * Testiohjelma albumille.
	 * @param args ei k√§yt√∂ss√§
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

