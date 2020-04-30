package albumirekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Albumirekisterin albumi, joka osaa mm. huolehtia tunnusNro:sta
 * @author maaviixu
 *
 */
public class Albumi implements Cloneable, Tietue {
	
	
	private int		tunnusNro;
	private String	nimi			= "";
	private String	artisti			= "";
	private int    	julkaisuVuosi	= 0;
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
     * Palauttaa albumin kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    @Override
    public int getKenttia() {
        return 8;
    }

    /**
     * Ensimmäinen kenttä
     * @return ensimmäisen kentän indeksi
     */
    @Override
    public int ekaKentta() {
        return 1;
    }
    
    /**
     * Alustetaan albumin merkkijono-atribuutti tyhjiksi jonoiksi
     * ja tunnusnro = 0
     */
    public Albumi() {
        // Toistaiseksi ei tarvita mitään 
    }

    /**
     * Palauttaa k:n albumin kenttää vastaavan kysymyksen
     * @param k monennenko kentän kysymys palautetaan 
     * @return k:n kenttää vastaava kysymys
     */
    @Override
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Tunnus nro";
        case 1: return "Albumin nimi";
        case 2: return "Artisti";
        case 3: return "Julkaisuvuosi";
        case 4: return "Formaatti";
        case 5: return "Levy-yhtiö";
        case 6: return "Lisätietoja";
        case 7: return "Kokonaiskesto";
        default: return "Hupsistakeikkaa";
        }
    }

    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono joka asetetaan kentän arvoksi 
     * @return null, jos asettaminen onnistuu, muuten vastaava virheilmoitus
     * @example
     * <pre name="test">
     * Albumi albumi = new Albumi();
     * albumi.aseta(1, "Jytää") === null;
     * albumi.aseta(3, "Jytää") === "Julkaisuvuosi väärin jono = \"Jytää\"";
     * albumi.aseta(3, "1990") === null;
     * </pre>
     */
    @Override
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '&', getTunnusNro()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2: 
            artisti = tjono;
            return null;
        case 3: 
            try {
                julkaisuVuosi = Mjonot.erotaEx(sb, '&' , julkaisuVuosi);
            } catch (NumberFormatException ex) {
                return "Julkaisuvuosi väärin " + ex.getMessage();
            }
            return null;
        case 4: 
            formaatti = tjono;
            return null;
        case 5:
            yhtio = tjono;
            return null;
        case 6:
            lisatietoja = tjono;
            return null;
        case 7:
            return null;
        default:
            return "HUPSISTA";
        }

    }

    /**
     * Antaa k:n kentön sisällön merkkijonona
     * @param k monennenko kentän sisältö palautetaan 
     * @return kentän sisältö merkkijonona
     */
    @Override
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + nimi;
        case 2: return "" + artisti;
        case 3: return "" + julkaisuVuosi;
        case 4: return "" + formaatti;
        case 5: return "" + yhtio;
        case 6: return "" + lisatietoja;
        case 7: return "";
        default: return "Hupsistakeikkaa";
        }
    }
	

    
	/**
	 * Apumetodi, jolla saadaan täytettyä testiarvot albumille
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
	    StringBuilder sb = new StringBuilder("");
        String erotin = "";
        for (int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin = "|";
        }
        return sb.toString();
	}
	
	/**
	 * Hakee albumin tiedot tolpalla erotellusta merkkijonosta.
	 * Pitää huolen, että seuraavaNro on suurempi kuin tuleva tunnusNro.
	 * @param rivi josta albumin tiedot otetaan
	 * @example
	 * <pre name="test">
     * Albumi albumi = new Albumi();
     * albumi.parse("     5    | JYTÄÄ   | Matti ja Mara-katti");
     * albumi.toString().startsWith("5|JYTÄÄ|Matti ja Mara-katti|") === true;
	 * albumi.rekisteroi();
	 * int j = albumi.getTunnusNro();
	 * albumi.parse(""+(j+10));
	 * albumi.rekisteroi();
	 * albumi.getTunnusNro() === j+10+1;
	 * </pre>
	 */
	public void parse(String rivi) {
	    StringBuilder sb = new StringBuilder(rivi);	   
	    for (int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
   
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

    
    
    /**
     * Tehdään identtinen klooni albumista
     * return Object kloonattu albumi
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException
     * Albumi albumi = new Albumi();
     * albumi.parse("1|Heviä|Musta sapatti");
     * Albumi kopio = albumi.clone();
     * kopio.toString() === albumi.toString();
     * albumi.parse("3|Rokkia|Kirka");
     * kopio.toString().equals(albumi.toString()) === false;
     * </pre>
     */
    @Override
    public Albumi clone() throws CloneNotSupportedException {
        Albumi uusi;
        uusi = (Albumi) super.clone();
        return uusi;
    }
    
    /**
     * Tutkii onko albumin tiedot samat kuin parametrina tuodun
     * albuin tiedot
     * @param albumi albumi johon viitataan
     * @return true jos kaikki tiedot samat, muuten false
     * @example
     * <pre name="test">
     * Albumi albumi1 = new Albumi();
     * albumi1.parse("3|Rokkia|Kirka");
     * Albumi albumi2 = new Albumi();
     * albumi2.parse("3|Rokkia|Kirka");
     * Albumi albumi3 = new Albumi();
     * albumi3.parse("3|Rokkia|Kirko");
     * albumi1.equals(albumi2) === true;
     * albumi2.equals(albumi3) === false;
     * albumi2.equals(albumi1) === true;
     * </pre>
     */
    public boolean equals(Albumi albumi) {
        if ( albumi == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(albumi.anna(k)) ) return false;
        return true;

    }

    @Override
    public boolean equals(Object albumi) {
        if ( albumi instanceof Albumi ) return equals((Albumi)albumi);
        return false;
    }



    @Override
    public int hashCode() {
        return tunnusNro;
    }

    

    
}

