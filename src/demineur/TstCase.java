package demineur;

/**
 * Classe qui permet tester ma classe Case
 * @author joevin guilhem alice
 */
public class TstCase {

	public static void main(String[] args) {
		Case c = new Case();
		c.setBombe(true);
		c.setDecouverte(false);
		c.setDrapeau(false);
		c.setValeur(3);
		
		System.out.print("Case c est ");
		if(c.isBombe())			System.out.print(" bombe ");
		if(c.isDecouverte())	System.out.print(" drapeau ");
		if(c.isDrapeau())		System.out.print(" bombe ");
		
		Case tableauCases[][] = new Case[5][5];
		for(int x=0; x<5; x++)
			for(int y=0; y<5; y++)
				tableauCases[x][y] = new Case();
		
		int valeur = tableauCases[1][1].getValeur();
		
		System.out.println("\nValeur de la case X=5, Y=5 : "+valeur);
		
		tableauCases[3][2].setBombe(true);
		
		boolean bombePrésente = false;
		for(int x=0; x<5; x++)
			for(int y=0; y<5; y++)
				if(tableauCases[x][y].isBombe())
					bombePrésente = true;
		
		if(bombePrésente)
			System.out.println("Il y a une bombe dans la matrice !");
	}
}
