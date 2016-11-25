package demineur;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Grille {

	enum Difficulté {Facile, Moyen, Difficile};
	boolean tableauMines[];
	int nbCases, nbMines, choix;
	Difficulté difficulté;
	Scanner clavier = new Scanner(System.in);
	
	void afficher(){
		do
		{
			System.out.println("GRILLE | " + nbMines + " mines | x choix restants | x coups restants");
			
			for(int i=1; i<nbCases; i++)
			{
				System.out.printf("%03d ", i);
				if(i % 10 == 0) System.out.println(" ");
			}
			
			System.out.println("\nGRILLE OUVERTE");
			for(int i=1; i<nbCases; i++)
			{ 
				System.out.print(" "+1+" ");
				if(i % 10 == 0) System.out.println(" ");
			}
			
			System.out.println("\nChoisir une case : ");
			choix = clavier.nextInt();			
		} while(choix != 0);
	} 
	
	void réglageDifficulté(Difficulté diff) {
		switch(diff)
		{
			case Facile		: nbCases =  31; nbMines =  8; break;
			case Moyen		: nbCases =  61; nbMines = 15; break;
			case Difficile	: nbCases = 121; nbMines = 25; break;
		}
		
		this.difficulté = diff;
	}
	
	void initialisation() {
		int i=0, y=0;
		tableauMines = new boolean[nbCases];

		while (i<nbMines)
		{
			y = ThreadLocalRandom.current().nextInt(0, nbCases);
			if(tableauMines[y] == false) {  // on s'assure qu'il n'y a pas déjà une mine à cette case y
				tableauMines[y] = true;		// sinon on n'incrémente pas i
				i++;
			}
		}
		
		/* Afficher les mines
		 System.out.println("\nMINES : ");
		 for(i=1; i<nbCases; i++)
		 { 
		 	if (tableauMines[i]) System.out.print(" * ");
		 	else System.out.print("   ");
		 	if(i % 10 == 0) System.out.println(" ");
		 } */		
	}
	
	public static void main(String[] args) {
		System.out.println("******************************");
		System.out.println("\tJEU DU DÉMINEUR");
		System.out.println("******************************\n");
		
		Grille grille = new Grille();
		grille.réglageDifficulté(Difficulté.Difficile);
		grille.initialisation();
		grille.afficher();
	}

}
