package demineur;

/**
 * Classe qui permet de donner des propriétés à nos cases
 * @author joevin guilhem alice
 */
public class Case {
	// attributs privés
	boolean mine, decouverte, drapeau;
	int valeur;
	
	// méthodes (mutateurs et assesseurs)
	public boolean isMine()		{ return mine; }
	public boolean isDecouverte() 	{ return decouverte; }
	public boolean isDrapeau()		{ return drapeau; }
	public int getValeur()			{ return valeur; }
	public void setMine(boolean b)		{ mine = b; }
	public void setDecouverte(boolean d){ decouverte = d; }
	public void setDrapeau(boolean d)	{ drapeau = d; }
	public void setValeur(int v)		{ valeur = v;}
}
