package demineur;

class Point {
	// attributs privés
	int x,y;
	
	// constructeur
	public Point(int x, int y) { this.x = x; this.y = y;}
	
	// méthodes (mutateurs et assesseurs)
	public int getX() { return x; }
	public int getY() { return y; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
}

public class Case extends Point {
	// attributs privés
	boolean estBombe, estDecouverte, estDrapeau;

	// constructeur qui hérite du constructeur parent
	public Case(int x, int y) { super(x, y); }
	
	// méthodes (mutateurs et assesseurs)
	public boolean getBombe()		{ return estBombe; }
	public boolean getDecouverte() 	{ return estDecouverte; }
	public boolean getDrapeau()		{ return estDrapeau; }
	public void setBombe(boolean b)		{ estBombe = b; }
	public void setDecouverte(boolean d){ estDecouverte = d; }
	public void setDrapeau(boolean d)	{ estDrapeau = d; }
	
	
	/* On pourrait se passer de tout les mutateurs et acesseurs si on rendait les attributs publics
	 * ... mais c'est peut-être une mauvaise pratique -> voir avec M. Pitiot
	 */
}
