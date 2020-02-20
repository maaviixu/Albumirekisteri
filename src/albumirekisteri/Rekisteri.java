package albumirekisteri;


/**
 * Rekisteri-luokka, joka huolehtii albumeista, artisteista
 * ja kappaleista. 
 * @author maaviixu
 *
 */
public class Rekisteri {
	private final Albumit albumit = new Albumit();
	
	/**
	 * Palauttaa rekisterin albumeiden m‰‰r‰n
	 * @return albumeiden m‰‰r‰
	 */
	public int getAlbumit() {
		return albumit.getLkm();		
	}
	
	
	/**
	 * Poistaa albumeista, artisteista ja kappaleista ne joilla on nro.
	 * Kesken.
	 * @param nro viitenumero , jonka mukaan poistetaan
	 * @return montako j‰sent‰ poistettiin
	 */
	public int poista(@SuppressWarnings("unused") int nro) {
		return 0;
	}
	
	
	/**
	 * Lis‰‰ rekisteriin uuden albumin.
	 * @param albumi lis‰tt‰v‰ albumi
	 * @throws SailoException jos lis‰yst‰ ei voida tehd‰
	 * @example
	 * <pre name="test">
	 * 
	 * </pre>
	 */
	public void lisaa(Albumi albumi) throws SailoException {
		albumit.lisaa(albumi);
	}
	
	
	/**
	 * Palauttaa i:n albumin
	 * @param i monesko albumi palautetaan
	 * @return viite i:teen albumiin
	 * @throws IndexOutOfBoundsException jos i v‰‰rin
	 */
	public Albumi annaAlbumi(int i) throws IndexOutOfBoundsException {
		return albumit.anna(i);
	}
	
	
	/**
	 * Lukee albumin tiedot tiedostosta
	 * @param nimi jota k‰ytet‰‰n lukemisessa
	 * @throws SailoException jos lukeminen ep‰onnistuu
	 */
	public void lueTiedostosta(String nimi) throws SailoException {
		albumit.lueTiedostosta(nimi);
	}
	
	
	/**
	 * Tallentaa albumiden tiedot tiedostoon
	 * @throws SailoException jos tallentamisessa ongelmia
	 */
	public void tallenna() throws SailoException {
		albumit.tallenna();
		// TODO: yrit‰ tallentaa toinen vaikka toinen ep‰onnistuisi
	}
	
	/**
	 * Testiohjelma rekisterist‰
	 * @param args
	 */
	public static void main(String args[]) {
		Rekisteri rekisteri = new Rekisteri();
		
		try {
			//rekisteri.lueTiedostosta("ei hajua...");
			
			Albumi levy1 = new Albumi(), levy2 = new Albumi();
			levy1.rekisteroi();
			levy1.vastaaAlbumi();
			levy2.rekisteroi();
			levy2.vastaaAlbumi();
			
			rekisteri.lisaa(levy1);
			rekisteri.lisaa(levy2);
			
			System.out.println("============= Rekisterin testi =================");
			
			for (int i = 0; i < rekisteri.getAlbumit(); i++) {
				Albumi albumi = rekisteri.annaAlbumi(i);
				System.out.println("Albumi paikassa: " + i);
				albumi.tulosta(System.out);
			}
		} catch (SailoException ex) {
			System.out.println(ex.getMessage());
		}
		
		
	}
	
	
	
	
	
	
	

}
