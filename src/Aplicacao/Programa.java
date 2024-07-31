package Aplicacao;


import java.util.InputMismatchException;
import java.util.Scanner;

import Xadrez.PartidaXadrez;
import Xadrez.PecaXadrez;
import Xadrez.PosicaoXadrez;
import Xadrez.XadrezException;

public class Programa {
	
	public static void main (String [] args) {
	Scanner scan = new Scanner(System.in);
	PartidaXadrez partidaXadrez = new PartidaXadrez();
	
	while(true) {
		
		try {
			UI.clarearTela();
			UI.printPartida(partidaXadrez);
			System.out.println();
			System.out.print("origem: ");
			PosicaoXadrez origem = UI.lerPosicaoxadrez(scan);
			
			boolean[][] possivelMovimento = partidaXadrez.possivelMovimento(origem);
			UI.clarearTela();
			UI.printTabuleiro(partidaXadrez.getPecas(), possivelMovimento);
			
			
			
			System.out.println();
			System.out.print("destino: ");
			PosicaoXadrez destino = UI.lerPosicaoxadrez(scan);
			
			
			PecaXadrez capturaPeca = partidaXadrez.performDeXadrez(origem, destino);
		
		}
		catch(XadrezException e) {
			System.out.println(e.getMessage());
			scan.nextLine();
			
		}
		catch(InputMismatchException e) {
			System.out.println(e.getMessage());
			scan.nextLine();
			
		}
		
	 }
	
	
	}

}
