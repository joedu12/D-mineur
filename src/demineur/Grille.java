package demineur;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Grille {
	enum Difficulté {Facile, Moyen, Difficile};
	int nbCases, nbMines, chercherX, chercherY;
	Difficulté difficulté;
	Scanner clavier = new Scanner(System.in);
	Case tableauCases[][];
	
	void afficher(){
		do
		{
			System.out.println("\nGRILLE | " + nbMines + " mines | x choix restants | x coups restants");

			for(int x=0; x<nbCases; x++)	{
				for(int y=0; y<nbCases; y++) {
					if(tableauCases[x][y].isDecouverte())
						System.out.print(" "+tableauCases[x][y].getValeur()+" ");						
					else
						System.out.print(" * ");
				}
				System.out.println();
			}
			
			System.out.print("\nChoisir une case, x : ");
			chercherX = clavier.nextInt();
			System.out.print("\nChoisir une case, y : ");
			chercherY = clavier.nextInt();
			tableauCases[chercherX][chercherY].setDecouverte(true);
			
		} while(chercherX != 0);
	} 
	
	void réglageDifficulté(Difficulté diff) {
		switch(diff)
		{
			case Facile	: nbCases = 10; nbMines = 10; break;
			case Moyen	: nbCases = 15; nbMines = 20; break;
			case Difficile	: nbCases = 20; nbMines = 30; break;
		}
		
		this.difficulté = diff;
	}
	
	void initialisation() {
		tableauCases = new Case[nbCases][nbCases]; // on initialise la matrice de cases
		
		int i=0, x=0, y=0;
		
		for(x=0; x<nbCases; x++)
			for(y=0; y<nbCases; y++)
				tableauCases[x][y] = new Case();

		while (i<nbMines)
		{
			x = ThreadLocalRandom.current().nextInt(0, nbCases);
			y = ThreadLocalRandom.current().nextInt(0, nbCases);
			if(tableauCases[x][y].isBombe() == false) {	// on s'assure qu'il n'y a pas déjà une mine à cette case y
				tableauCases[x][y].setBombe(true);		// sinon on n'incrémente pas i
				i++;
			}
		}
		
		/* Afficher les mines */
		 System.out.println("\nMINES : ");
		 for(x=0; x<nbCases; x++)
		 {
			 for(y=0; y<nbCases; y++)
			 {
			 	if (tableauCases[x][y].isBombe()) System.out.print(" * ");
			 	else System.out.print("   ");
			 }
			 System.out.println(" ");
		 }
	}
	 
	public static void main(String[] args) {
		System.out.println("******************************");
		System.out.println("\tJEU DU DÉMINEUR");
		System.out.println("******************************");
		
		Grille grille = new Grille();
		grille.réglageDifficulté(Difficulté.Facile);
		grille.initialisation();
		grille.afficher();
	}
}
