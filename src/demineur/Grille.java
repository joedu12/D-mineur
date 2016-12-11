package demineur;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Classe principale du jeu
 * @author joevin alice guilhem
 *
 */
public class Grille {
	Case tableauCases[][];
	enum Difficulte {Facile, Moyen, Difficile};
	int nbCases, nbMines, caseChoisie, nbCoups, nbCaseDecouverte, nbChoixRestant;
	Difficulte difficulte;
	Scanner clavier = new Scanner(System.in);
	boolean bombeDecouverte = false;
		
	/**
	 * Gère l'affichage du jeu
	 */
	void afficher(){
		boolean perdu = false, gagné = false;
		do
		{
			nbCaseDecouverte = 0; // on parcous la matrice pour compter le nombre de cases découvertes
			for(int x=0; x<nbCases; x++) for(int y=0; y<nbCases; y++) if(tableauCases[x][y].isDecouverte()) nbCaseDecouverte++; 
			nbChoixRestant = nbCases*nbCases - nbCaseDecouverte - nbMines; // on en déduis le nombre de cases à découvrir
			if(nbChoixRestant==0) gagné = true; // si on a plus aucune case à découvrir on a gagné
			System.out.println("\nGRILLE | " + nbMines + " mines | "+(nbChoixRestant)+" choix restants | "+nbCoups+" coups effectués");
			
			int z=0; // on affiche les numéros de cases
			for(int x=0; x<nbCases; x++) {
				for(int y=0; y<nbCases; y++)
					System.out.printf("%03d ", ++z);
				System.out.println();
			}
			
			System.out.println("\nGRILLE OUVERTE");
			for(int x=0; x<nbCases; x++) {
				for(int y=0; y<nbCases; y++) {
					if(tableauCases[x][y].isBombe() && bombeDecouverte)
						{ System.out.print(" X "); perdu=true;}
					else if(tableauCases[x][y].isDecouverte() || bombeDecouverte)
						System.out.print(" "+tableauCases[x][y].getValeur()+" ");
					else
						System.out.print(" - ");
				}
				System.out.println();
			}

			
			if(perdu)		{ System.out.println("\nPERDU !");}
			else if(gagné)	{ System.out.println("\nGAGNÉ !");}
			else {
				System.out.print("\nChoisir une case : ");
				try { caseChoisie = clavier.nextInt();  } // si l'utilisateur n'entre pas un nombre
				catch (InputMismatchException e) { System.out.println("ERREUR : Entrez un nombre !"); break; }
				
				decouvrirCase();
				nbCoups++;
			}
		}while(!perdu && !gagné);
	}
	
	/**
	 * Action déclenchée lorsque l'on cherche à découvrir une case
	 */
	void decouvrirCase() {
		int x=0, y=0, X=0, Y=0, a=0, b=0, z=0;
		// conversion entre les coordonnées de la matrice et le numéro de la case
		for(x=0; x<nbCases; x++)
			for(y=0; y<nbCases; y++)
				if(++z == caseChoisie) { X=x; Y=y; }
		
		tableauCases[X][Y].setDecouverte(true);
		if(tableauCases[X][Y].isBombe()) bombeDecouverte = true;


			for(a=-1; a<2; a++)
				for(b=-1; b<2; b++)
				{ // propagation des zéros (pas tout à fait)
					try {
						if(tableauCases[X+a][Y+b].getValeur() == 0)
							tableauCases[X+a][Y+b].setDecouverte(true);
					}
					catch(ArrayIndexOutOfBoundsException e) {} // si on dépasse la taille de la matrice
				}

	}
	
	/**
	 * Méthode pour régler la difficulté
	 * @param Énumération Difficulté
	 */
	void reglageDifficulte(Difficulte diff) {
		switch(diff)
		{
			case Facile		: nbCases = 10; nbMines = 10; break;
			case Moyen		: nbCases = 15; nbMines = 20; break;
			case Difficile	: nbCases = 20; nbMines = 30; break;
		}
		
		this.difficulte = diff;
	}
	
	/**
	 * Initialise la matrice de cases, place les mines sur la grille,
	 * calcule le nombre de mines présentes sur les cases adjacentes
	 */
	void initialisation() {
		// on initialise la matrice de cases
		int i=0, x=0, y=0, a=0, b=0, z=0;
		tableauCases = new Case[nbCases][nbCases];
		
		for(x=0; x<nbCases; x++)
			for(y=0; y<nbCases; y++)
				tableauCases[x][y] = new Case();

		// on place les mines
		while (i<nbMines)
		{
			x = ThreadLocalRandom.current().nextInt(0, nbCases);
			y = ThreadLocalRandom.current().nextInt(0, nbCases);
			if(tableauCases[x][y].isBombe() == false) {	// on s'assure qu'il n'y a pas déjà une mine à cette case y
				tableauCases[x][y].setBombe(true);		// sinon on n'incrémente pas i
				i++;
			}
		}
		
		// on calcule le nombre de mines autour des cases non minées
		 for(x=0; x<nbCases; x++)
			 for(y=0; y<nbCases; y++)
			 { // on parcours toutes les cases
				z=0;
				for(a=-1; a<2; a++)
					for(b=-1; b<2; b++)	{ // on parcours les cases adjacentes
						try { if(tableauCases[x+a][y+b].isBombe()) z++;} // on compte le nombre de bombes
						catch(ArrayIndexOutOfBoundsException e) {} // évite un plantage si on dépasse la taille de la matrice
					}
			 		tableauCases[x][y].setValeur(z);
			 }
	}
	
	/**
	 * Méthode statique lancée au démarrage du jeu
	 */
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
