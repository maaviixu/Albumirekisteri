package albumirekisteri;



/**
 * Albumirekisterin albumit, joka osaa mm. lis채t채 uuden albumin
 * @author maaviixu
 *
 */
public class Albumit {
	
	private static final int	MAX_ALBUMIT		= 8;
	private int					lkm				= 0;
	private String 				tiedostonNimi	= "";
	private Albumi				alkiot[]		= new Albumi[MAX_ALBUMIT];
	
	/**
	 * Oletusmuodostaja
	 */
	public Albumit() {
		// Atribuuttien oma alustus riitt狎
	}
	
	
	/**
	 * Lis채채 uuden albumin tietorakenteeseen. Ottaa j채senen omistukseensa.
	 * @param albumi lis채tt채v채n albumin viite. Huom tietorakenne muuttuu omistajaksi
	 * @throws SailoException jos tietorakenne on jo t채ynn채
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
		if (lkm >= alkiot.length) throw new SailoException("Liikaa alkiota");
		alkiot[lkm] = albumi;
		lkm++;
	}
	
	
	/**
	 * Palauttaa viitteen i:teen albumiin
	 * @param i monennenko albumin viite halutaan
	 * @return viite j채seneen, jonka indeksi on i
	 * @throws IndexOutOfBoundException jos ei ole sallitulla alueella
	 */
	public Albumi anna(int i) throws IndexOutOfBoundsException {
		if (i < 0 || lkm <= i)
			throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
		return alkiot[i];
	}
	
	
	/**
	 * Lukee albumeiden tiedostosta. Kesken
	 * @param hakemisto tiedoston hakemisto
	 * @throws SailoException jos lukeminen ep占퐋nnistuu
	 */
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/albumit.dat";
		throw new SailoException("Ei osata lukea viel占� tiedostoa " + tiedostonNimi);		
	}
	
	
	/**
	 * Tallentaa albumeiden tiedostoon. Kesken.
	 * @throws SailoException jos tallentaminen ep占퐋nnistuu
	 */
	public void tallenna() throws SailoException {
		throw new SailoException("Ei osata viel占� tallentaa tiedostoa " + tiedostonNimi);		
	}
	
	
	/**
	 * Palauttaa albumeiden lukum채채r채n.
	 * @return albumeiden lukum채채r채
	 */
	public int getLkm() {
		return lkm;
	}
	
	
	
	
	
	
	/**
	 * Testiohjelma albumeille
	 * @param args ei k채yt철ss채
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
