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
	int nbCases, nbMines, caseChoisie, nbCoups, nbCaseDecouverte, nbChoixRestant;
	Scanner clavier = new Scanner(System.in);
	boolean bombeDecouverte = false;
		
	/**
	 * Gère l'affichage du jeu
	 */
	void afficher(){
		boolean perdu = false, gagné = false;
		
		do
		{
			nbCaseDecouverte = 0;
			// on parcous la matrice pour compter le nombre de cases découvertes
			for(int x=0; x<nbCases; x++){
				for(int y=0; y<nbCases; y++){
					if(tableauCases[x][y].isDecouverte())
						nbCaseDecouverte++; 
				}
			}
			
			nbChoixRestant = nbCases*nbCases - nbCaseDecouverte - nbMines; // on en déduis le nombre de cases qu'il reste à découvrir
			if(nbChoixRestant==0)
				gagné = true; // si on a plus aucune case à découvrir on a gagné
			
			System.out.println("\nGRILLE | " + nbMines + " mines | "+(nbChoixRestant)+" choix restants | "+nbCoups+" coups effectués");
			
			// on affiche les numéros de cases
			int z=0; 
			for(int x=0; x<nbCases; x++) {
				for(int y=0; y<nbCases; y++)
					System.out.printf("%03d ", ++z);
				System.out.println();
			}
			
			//on affiche l'état de la grille
			System.out.println("\nGRILLE OUVERTE");
			for(int x=0; x<nbCases; x++) {
				for(int y=0; y<nbCases; y++) {
					if(tableauCases[x][y].isMine() && bombeDecouverte)
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
				// si l'utilisateur n'entre pas un nombre
				try { caseChoisie = clavier.nextInt();  } 
				catch (InputMismatchException e) { System.out.println("ERREUR : Entrez un nombre !"); break; }
				//sinon on decouvre la case associee
				decouvrirCase();
				nbCoups++;
			}
		}while(!perdu && !gagné);
	}
	
	/**
	 * Action déclenchée lorsque l'on cherche à découvrir une case
	 */
	void decouvrirCase() {
		int X=0, Y=0, z=0;
		// conversion entre les coordonnées de la matrice et le numéro de la case
		for(int x=0; x<nbCases; x++)
			for(int y=0; y<nbCases; y++)
				if(++z == caseChoisie) { X=x; Y=y; }
		
		tableauCases[X][Y].setDecouverte(true);
		if(tableauCases[X][Y].isMine())
			bombeDecouverte = true;
		
		// si on trouve zéro, on cherche s'il n'y en a pas d'autres autour
		if(tableauCases[X][Y].getValeur()==0)
			propagationZero(X, Y);
	}
	
	/*
	 * Algorithme récursif qui permet la propagation des zéros
	 * @param X : coordonnées X de la case découverte (abscisses)
	 * @param Y : coordonnées Y de la case découverte (ordonnées)
	 */
	void propagationZero(int X, int Y)
	{
		for(int a=-1; a<2; a++)
			for(int b=-1; b<2; b++)
			{ // on parcours toutes les cases adjacentes
				try {
					if(tableauCases[X+a][Y+b].getValeur() == 0)
					{
						tableauCases[X+a][Y+b].setDecouverte(true);
						tableauCases[X][Y].setDrapeau(true);	// on note les zéros déjà découvert
						if(!tableauCases[X+a][Y+b].isDrapeau()) // pour éviter un StackOverflowError
							propagationZero(X+a, Y+b);			// appel récursif avec nouvelles coordonnées
						
						// parcours des cases adjacentes des nouvelles coordonnées
						for(int A=-1; A<2; A++)
							for(int B=-1; B<2; B++)
								// découvre les cases ajacentes aux zéros propagés
								tableauCases[X+a+A][Y+b+B].setDecouverte(true);
					}
				}
				catch(ArrayIndexOutOfBoundsException e) {} // si on dépasse la taille de la matrice
			}
	}
	
	/**
	 * Méthode pour régler la difficulté
	 */
	void reglageDifficulté() {
		
		int difficulté = 0;
		System.out.println("\nChoissisez la difficulté :\n(1) Facile \n(2) Moyen \n(3) Difficile\n");
		try { difficulté = clavier.nextInt();  } // si l'utilisateur n'entre pas un entier
		catch (InputMismatchException e) { System.out.println("ERREUR : Entrez un nombre !"); }
		
		switch(difficulté)
		{
			case 1:
				nbCases = 10; nbMines = 10; break;
			case 2:
				nbCases = 15; nbMines = 20; break;
			case 3:
				nbCases = 20; nbMines = 50; break;
			default:
				nbCases = 15; nbMines = 20; break;	
		}
	}
	
	/**
	 * Initialise la matrice de cases, place les mines sur la grille,
	 * calcule le nombre de mines présentes sur les cases adjacentes
	 */
	void initialisation() {
		reglageDifficulté();
		
		//déclarations
		int i=0, x=0, y=0, a=0, b=0, nbMinesAdj=0;
		tableauCases = new Case[nbCases][nbCases];
		
		//on initialise la matrice de cases
		for(x=0; x<nbCases; x++)
			for(y=0; y<nbCases; y++)
				tableauCases[x][y] = new Case();

		// on place les mines de manière aléatoire
		while (i<nbMines)
		{
			x = ThreadLocalRandom.current().nextInt(0, nbCases);
			y = ThreadLocalRandom.current().nextInt(0, nbCases);
			if(tableauCases[x][y].isMine() == false) {	//si la case est vide (n'a pas de mine)
				tableauCases[x][y].setMine(true);		// on ajoute une mine et on incremente i
				i++;
			}
		}
		
		// on calcule le nombre de mines autour des cases non minées
		 for(x=0; x<nbCases; x++)
			 for(y=0; y<nbCases; y++)
			 { // on parcours toutes les cases
				nbMinesAdj=0;
				for(a=-1; a<2; a++)
					for(b=-1; b<2; b++)	{ // on parcours les cases adjacentes
						try { if(tableauCases[x+a][y+b].isMine()) nbMinesAdj++;} // on compte le nombre de mines
						catch(ArrayIndexOutOfBoundsException e) {} // évite un plantage si on dépasse la taille de la matrice
					}
			 		tableauCases[x][y].setValeur(nbMinesAdj);
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
		grille.initialisation();
		grille.afficher();
	}
}
