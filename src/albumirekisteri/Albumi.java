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
	private int		julkaisuVuosi	= 0;
	private String 	formaatti		= "";
	private String 	yhtio			= "";
	private String	lisatietoja		= "";
	
	private static int seuraavaNro	= 1;
	
	
	/**
	 * 
	 * @return
	 */
	public String getNimi() {
		return nimi;
	}
	
	public void vastaaAlbumi() {
		nimi = "Jyt�kes�" + " " + rand(1000, 9999);
		julkaisuVuosi = 1962;
		formaatti = "LP";
		yhtio = "Matulan valinta";
		lisatietoja = "Kes� -62 j�i monen mieleen niin kuin t�m� albumikin";
	}
	
	/**
	 * Tulostetaan albumin tiedot
	 * @param out tietovirta johon tulostetaan
	 */
	public void tulosta(PrintStream out) {
		out.println(String.format("%03d", tunnusNro, 3) + "  " + nimi);
		out.println(julkaisuVuosi + " " + formaatti + " " + yhtio);
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
	
	public static int rand(int ala, int yla) {
		double n = (yla-ala)*Math.random() + ala;
		return (int)Math.round(n);
	}
	
	/**
	 * 
	 * @param args ei k�yt�ss�
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
