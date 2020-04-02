package albumirekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kappale, joka osaa mm. itse huolehtia tunnusnumerostaan.
 * 
 * @author maaviixu
 *
 */
public class Kappale {
	
	

		private int tunnusNro;
		private int albumiNro;
		private String nimi;
		private String kesto;
		
		private static int seuraavaNro = 1;
		
		/**
		 * Alsutetaan kappale. Toistaiseksi ei tarvitse tehd� mit��n.
		 */
		public Kappale() {
			// Vielä ei tarvita mitään
		}
		
		
		/**
		 * Alustetaan tietyn albumin kappale
		 * @param albumiNro albumin viitenumero
		 */
		public Kappale(int albumiNro) {
			this.albumiNro = albumiNro;
		}
		
		
		/**
		 * Apumetodi, jolla saadaan t�ytetty� testiarvot Kappaleelle.
		 * Kappaleen kesto arvotaan, jotta kappaleella ei olisi samoja tietoja.
		 * @param nro viite albumiin, jonka kappaleesta on kyse
		 */
		public void vastaaMaailmanParasLaulu(int nro) {
			albumiNro = nro;
			nimi = "Maailman paras laulu";
			double arpa = (Math.random()*10);
			kesto = Double.toString(arpa);
			
		}
		
		
		/**
		 * Tulostetaan kappaleen tiedot
		 * @param out teitovirta johon tulostetaan
		 */
		public void tulosta(PrintStream out) {
			out.println(nimi + " " + kesto);
		}
		
		
		/**
		 * Tulostetaan albumin tiedot
		 * @param os tietovirta johon tulostetaan
		 */
		public void tulosta(OutputStream os) {
			tulosta(new PrintStream(os));
		}
		
		
		
		/**
		 * Antaa kappaleelle seuraavan rekisterinumeron.
		 * @return kappaleen uusi tunnnusNro;
		 * @example
		 * <pre name="test">
		 * Kappale laulu1 = new Kappale();
		 * laulu1.getTunnusNro() === 0;
		 * laulu1.rekisteroi();
		 * Kappale laulu2 = new Kappale();
		 * laulu2.rekisteroi();
		 * int n1 = laulu1.getTunnusNro();
		 * int n2 = laulu2.getTunnusNro();
		 * n1 === n2-1;
		 * </pre>
		 */
		public int rekisteroi() {
			tunnusNro = seuraavaNro;
			seuraavaNro++;
			return tunnusNro;
		}
		
		
		/**
		 * Palautetaan kappaleen oma id
		 * @return kappeleen id
		 */
		public int getTunnusNro() {
			return tunnusNro;
		}
		
		/**
		 * Palautetaan mille albumile kappale kuuluu
		 * @return albumin id
		 */
		public int getAlbumiNro() {
			return albumiNro;
		}
		
		
		
		/**
		 * Testiohjelma Kappaleelle
		 * @param args ei k�yt�ss�
		 */
		public static void main(String[] args) {
			Kappale kap = new Kappale();
			kap.vastaaMaailmanParasLaulu(1);
			kap.tulosta(System.out);
		}

		/**
		 * Selvittää kappaleet tiedot | erotellusta merkkijonosta
		 * @param rivi josta harrastuksen tiedot otetaan
		 */
        public void parse(String rivi) {
            StringBuilder sb = new StringBuilder(rivi);
            setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
            albumiNro = (Mjonot.erota(sb, '|', albumiNro));
            nimi = Mjonot.erota(sb, '|', nimi);
            kesto = (Mjonot.erota(sb, '|', kesto));                                   
        }


        private void setTunnusNro(int nr) {
            tunnusNro = nr;
            if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
            
        }
        
        
        /**
         * Palauttaa kappaleen tiedot merkkijonona, jonka voi tallentaa tiedostoon.
         * @return kappale tolppaeroteltuna merkkijonona
         */
        @Override
        public String toString() {
            return "" + getTunnusNro() + "|" + albumiNro + "|" + nimi + "|" + kesto;
        }




        
		
		
	
	

}
