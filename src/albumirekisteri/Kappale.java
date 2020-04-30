package albumirekisteri;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Kappale, joka osaa mm. itse huolehtia tunnusnumerostaan.
 * 
 * @author maaviixu
 *
 */
public class Kappale implements Cloneable, Tietue {
	
	

		private int tunnusNro;
		private int albumiNro;
		private String nimi;
		private double kesto;
		
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
			nimi = "Täytä nimi";
			kesto = 0.0;						
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
		 * Palautetaan kappaleen kesto
		 * @return kappaleen kesto
		 */
		public double getKesto() {
		    return kesto;
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
		 * @example
		 * <pre name="test">
		 * Kappale kap = new Kappale();
         * kap.parse("      4 |       6 |      Jytää |     3.44 ");
         * kap.toString() === "4|6|Jytää|3.44";
		 * </pre>
		 */
        public void parse(String rivi) {            
            StringBuilder sb = new StringBuilder(rivi);            
            for (int k = 0; k < getKenttia(); k++) {
                aseta(k, Mjonot.erota(sb, '|'));
            }
        }


        private void setTunnusNro(int nr) {
            tunnusNro = nr;
            if ( tunnusNro >= seuraavaNro ) seuraavaNro = tunnusNro + 1;
            
        }
        
        
        /**
         * Palauttaa kappaleen tiedot merkkijonona, jonka voi tallentaa tiedostoon.
         * @return kappale tolppaeroteltuna merkkijonona
         * @example
         * <pre name="test">
         * Kappale kap = new Kappale();
         * kap.parse("      4 |       6 |      Jytää |     3.44 ");
         * kap.toString() === "4|6|Jytää|3.44";
         * </pre>
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            String erotin = "";
            for (int k = 0; k < getKenttia(); k++) {
                sb.append(erotin);
                sb.append(anna(k));
                erotin = "|";
            }
            return sb.toString();

        }


        /**
         * @return kappaleen kenttien lukumäärä
         */
        @Override
        public int getKenttia() {
            return 4;
        }


        /**
         * @return ensimmäinen käyttäjän syötettävän kentän indeksi
         */
        @Override
        public int ekaKentta() {            
            return 2;
        }


        /**
         * @param k minkä kentän sisältö halutaan
         * @return valitun kentän sisältö
         * @example
         * <pre name="test">
         * Kappale kap = new Kappale();
         * kap.parse(" 4 | 6 | Jytää | 3.44 ");
         * kap.anna(0) === "4";
         * kap.anna(1) === "6";
         * kap.anna(2) === "Jytää";
         * kap.anna(3) === "3.44";
         * </pre>
         */
        @Override
        public String anna(int k) {
            switch (k) {
            case 0:
                return "" + tunnusNro;
            case 1:
                return "" + albumiNro;
            case 2:
                return "" + nimi;
            case 3:
                return "" + kesto;
            default:
                return "???";
            }           
        }


        /**
         * @param k minkä kentän kysymys halutaan 
         * @return valitun kentän kysymysteksti
         */
        @Override
        public String getKysymys(int k) {
            switch (k) {
                case 0:
                    return "tunnus nro";
                case 1:
                    return "albumin nro";
                case 2:
                    return "kappaleen nimi";
                case 3:
                    return "kappaleen kesto";
                default:
                    return "???";
            }
        }
        
        /**
         * Asetetaan valitun kentän sisältö. Palautetaan null, jos onnistuu,
         * muuten virheteksti
         * @param k minkä kentän sisältö asetetaan
         * @param s asetettava sisältö merkkijonona
         * @return null, jos ok. muuten virheteksti
         * @example
         * <pre name="test">
         * Kappale kap = new Kappale();
         * kap.aseta(3, "3.456") === "Sekunnit väärin";
         * kap.aseta(3, "3.34") === null;
         * </pre>
         */
        @Override
        public String aseta(int k, String s) {
            String st = s.trim();
            StringBuilder sb = new StringBuilder(st);
            switch (k) {
                case 0:
                    setTunnusNro(Mjonot.erota(sb, '&', getTunnusNro()));
                    return null;
                case 1:
                    albumiNro = Mjonot.erota(sb,  '&', albumiNro);
                    return null;
                case 2:
                    nimi = st;
                    return null;
                case 3:                                       
                    try {
                        int min = (int) Mjonot.erotaEx(sb, '.' , kesto);
                        int sek = (int) Mjonot.erotaEx(sb, '.', kesto);
                        if (min < 0) return "Minuutit väärin";
                        if (sek < 0 || sek > 59) return "Sekunnit väärin";
                        kesto = Double.parseDouble(st);
                    } catch (NumberFormatException ex) {
                        return "Kesto väärin " + ex.getMessage();
                    }
                    return null;
                    
                default:
                    return "Väärä kentän indeksi";
            }
        }
        
        /**
         * Tehdään identtinen klooni kappaleesta
         * @return object kloonattu kappaleesta
         * @example
         * <pre name="test">
         * #THROWS CloneNotSupportedException 
         * Kappale kap = new Kappale();
         * kap.parse(" 4 | 6 | Jytää | 3.44 ");
         * Kappale kopio = kap.clone();
         * kopio.toString() === kap.toString();
         * kap.parse(" 1 | 2 | Rytää | 6.54 ");
         * kopio.toString().equals(kap.toString()) === false;
         * </pre>
         */
        @Override
        public Kappale clone() throws CloneNotSupportedException {
            return (Kappale)super.clone();
        }




        
		
		
	
	

}
