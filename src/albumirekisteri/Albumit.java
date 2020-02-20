package albumirekisteri;



/**
 * Albumirekisterin albumit, joka osaa mm. lis‰t‰ uuden albumin
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
		// Atribuuttien oma alustus riitt‰‰
	}
	
	
	/**
	 * Lis‰‰ uuden albumin tietorakenteeseen. Ottaa j‰senen omistukseensa.
	 * @param albumi lis‰tt‰v‰n j‰senen viite. Huom tietorakenne muuttuu omistajaksi
	 * @example
	 * <pre name="test">
	 * 
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
	 * @return viite j‰seneen, jonka indeksi on i
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
	 * @throws SailoException jos lukeminen ep‰onnistuu
	 */
	public void lueTiedostosta(String hakemisto) throws SailoException {
		tiedostonNimi = hakemisto + "/albumit.dat";
		throw new SailoException("Ei osata lukea viel‰ tiedostoa " + tiedostonNimi);		
	}
	
	
	/**
	 * Tallentaa albumeiden tiedostoon. Kesken.
	 * @throws SailoException jos tallentaminen ep‰onnistuu
	 */
	public void tallenna() throws SailoException {
		throw new SailoException("Ei osata viel‰ tallentaa tiedostoa " + tiedostonNimi);		
	}
	
	
	/**
	 * Palauttaa rekisterin albumeiden m‰‰r‰n.
	 * @return albumeiden lukum‰‰r‰.
	 */
	public int getLkm() {
		return lkm;
	}
	
	
	
	
	
	
	/**
	 * Testiohjelma albumeille
	 * @param args ei k‰ytˆss‰
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
			albumit.lisaa(levy);
			albumit.lisaa(levy);
			albumit.lisaa(levy);
			albumit.lisaa(levy);
			albumit.lisaa(levy);
			albumit.lisaa(levy);
			albumit.lisaa(levy);
			albumit.lisaa(levy);
			
			
			System.out.println("============= Albumit testi =================");
			
			for ( int i = 0; i < albumit.getLkm(); i++) {
				Albumi albumi = albumit.anna(i);
				System.out.println("J‰sen nro: " + i);
				albumi.tulosta(System.out);
			}
			
			
		} catch (SailoException ex) {
			System.out.println(ex.getMessage());
		}
		
	}

}
