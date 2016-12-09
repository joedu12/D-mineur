package demineur;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Grille {

	enum Difficulte {Facile, Moyen, Difficile};
	int nbCases, nbMines, caseChoisie;
	Difficulte difficulte;
	Scanner clavier = new Scanner(System.in);
	Case tableauCases[][];
	boolean perdu = false;
	
	void afficher(){
		do
		{
			System.out.println("\nGRILLE | " + nbMines + " mines | x choix restants | x coups restants");
			
			int z=0; // j'affiche les numéros de cases
			for(int x=0; x<nbCases; x++) {
				for(int y=0; y<nbCases; y++)
					System.out.printf("%03d ", ++z);
				System.out.println();
			}
			
			System.out.println("\nGRILLE OUVERTE");
			for(int x=0; x<nbCases; x++)	{
				for(int y=0; y<nbCases; y++) {
					if(tableauCases[x][y].isBombe() && perdu)
						System.out.print(" X ");
					else if(tableauCases[x][y].isDecouverte())
						System.out.print(" "+tableauCases[x][y].getValeur()+" ");
					else
						System.out.print(" - ");
				}
				System.out.println();
			}
			
			System.out.print("\nChoisir une case : ");
			caseChoisie = clavier.nextInt();
			decouvrirCase();
		} while(caseChoisie!=0);
	}
	
	void decouvrirCase() {
		int x=0, y=0, X=0, Y=0, z=0;
		for(x=0; x<nbCases; x++)
			for(y=0; y<nbCases; y++)
				if(++z == caseChoisie) { X=x; Y=y; }
		
		tableauCases[X][Y].setDecouverte(true);
		if(tableauCases[X][Y].isBombe()) perdu = true;

		z = calculBombe(X, Y);
		tableauCases[X][Y].setValeur(z);
	}
	
	int calculBombe(int X, int Y) {
		// calcule le nombre de bombes autour de cette case
		int x=0, y=0, z=0;
		for(x=-1; x<2; x++)
			for(y=-1; y<2; y++)	{
				try { if(tableauCases[X+x][Y+y].isBombe()) z++;}
				catch(ArrayIndexOutOfBoundsException e) {} // évite un planton si on dépasse la taille de la matrice
			}
		return z;
	}
	
	void reglageDifficulte(Difficulte diff) {
		switch(diff)
		{
			case Facile		: nbCases = 10; nbMines = 10; break;
			case Moyen		: nbCases = 15; nbMines = 20; break;
			case Difficile	: nbCases = 20; nbMines = 30; break;
		}
		
		this.difficulte = diff;
	}
	
	void initialisation() {
		// on initialise la matrice de cases
		int i=0, x=0, y=0;
		tableauCases = new Case[nbCases][nbCases];
		
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
		
		/* Afficher les mines
		 System.out.println("\nMINES : ");
		 for(x=0; x<nbCases; x++)
		 {
			 for(y=0; y<nbCases; y++)
			 {
			 	if (tableauCases[x][y].isBombe()) System.out.print(" * ");
			 	else System.out.print("   ");
			 }
			 System.out.println(" ");
		 } */
	}
	 
	public static void main(String[] args) {
		System.out.println("******************************");
		System.out.println("\tJEU DU DÉMINEUR");
		System.out.println("******************************");
		
		Grille grille = new Grille();
		grille.reglageDifficulte(Difficulte.Facile);
		grille.initialisation();
		grille.afficher();
	}
}
