package Aplicacao;


import java.util.Scanner;

import Xadrez.PartidaXadrez;
import Xadrez.PecaXadrez;
import Xadrez.PosicaoXadrez;

public class Programa {
	
	public static void main (String [] args) {
	Scanner scan = new Scanner(System.in);
	PartidaXadrez partidaXadrez = new PartidaXadrez();
	
	while(true) {
		UI.printTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		System.out.print("origem: ");
		PosicaoXadrez origem = UI.lerPosicaoxadrez(scan);
		System.out.println();
		System.out.print("destino: ");
		PosicaoXadrez destino = UI.lerPosicaoxadrez(scan);
		
		PecaXadrez capturaPeca = partidaXadrez.performDeXadrez(origem, destino);
		
	}
	
	
	
	
	
		
	}

}
