package albumirekisteri;

/**
 * @author Viitanen
 * @version 29.4.2020
 *
 */
public interface Tietue {
    
    /**
     * @return tietueen kenttien lukumäärä
     */
    public abstract int getKenttia();
    
    /**
     * @return ensimmäinen käyttäjän syötettävän kentän indeksi
     */
    public abstract int ekaKentta();

    /**
     * @param k minkä kentän kysymys halutaan
     * @return valitun kentän kysymysteksti
     */
    public abstract String getKysymys(int k);

    /**
     * @param k minkä kentän sisältö halutaan
     * @return valitun kentän sisältö
     */
    public abstract String anna(int k);

    /**
     * @param k minkä kentän sisältö asetetaan
     * @param s asetettava sisältö merkkijonona
     * @return null jos ok, muuten virheteksti
     */
    public abstract String aseta(int k, String s);

    /**
     * @return kloonattu tietue
     * @throws CloneNotSupportedException jos kloonausta ei tueta
     */
    public abstract Tietue clone() throws CloneNotSupportedException;

    @Override
    public abstract String toString();


}
