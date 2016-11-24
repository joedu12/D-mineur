package demineur;

import java.util.Scanner;

public class Grille {

	static int numCase = 121;
	static int nbCoup = 0;
	static int nbMine = 13;
	static int choixRestant = 200;
	
	public static void main(String[] args) {
		System.out.println("******************************");
		System.out.println("\tJEU DU DÉMINEUR");
		System.out.println("******************************\n");
		
		System.out.println("GRILLE | nombre : mines : " + nbMine + " - choix restants : " + choixRestant + " - nbCoups : " + nbCoup);
		
		for(int i=1; i<numCase; i++)
		{
			System.out.printf("%03d ", i);
			if(i % 10 == 0) System.out.println(" ");
		}
		
		System.out.println("\nGRILLE OUVERTE");
		for(int i=1; i<numCase; i++)
		{
			System.out.print(" "+1+" ");
			if(i % 10 == 0) System.out.println(" ");
		}
		
		Scanner clavier = new Scanner(System.in);
		System.out.println("\nChoisir une case : ");
		int choix = clavier.nextInt();

	}

}
