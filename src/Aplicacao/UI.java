package Aplicacao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Xadrez.Cor;
import Xadrez.PartidaXadrez;
import Xadrez.PecaXadrez;
import Xadrez.PosicaoXadrez;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	
	//cores do texto
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	//cores do fundo
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	
	public static void clarearTela() {
		System.out.print("\003[H\003[2J");
		System.out.flush();
	}
	
	public static PosicaoXadrez lerPosicaoxadrez(Scanner scan){
		try{
			String s = scan.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("erro lendo posicao de xadrez. Valores validos são de a1 a h8.");
		}
		
	}
	
	public static void printPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> captura) {
		printTabuleiro(partidaXadrez.getPecas());
		System.out.println();
		printCapturaPecas(captura);
		System.out.println();
		System.out.println("vez: "+ partidaXadrez.getVez());
		System.out.println("aguardando o jogador: "+ partidaXadrez.getJogadorAtual());
		
		if(partidaXadrez.getCheck()) {
			System.out.println("CHECK!");
		}
	}

	public static void printTabuleiro(PecaXadrez[][] pecas) {

		for (int i = 0; i < pecas.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println(" a b c d e f g h");
	}
	
	public static void printTabuleiro(PecaXadrez[][] pecas, boolean[][] possivelMovimento) {

		for (int i = 0; i < pecas.length; i++) {
			System.out.print(8 - i + " ");
			for (int j = 0; j < pecas.length; j++) {
				printPeca(pecas[i][j], possivelMovimento[i][j]);
			}
			System.out.println();
		}
		System.out.println(" a b c d e f g h");
	}

	private static void printPeca(PecaXadrez peca, boolean background) {
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}

		if(peca == null) {
			System.out.print("-"+ ANSI_RESET);
		}else {
			if(peca.getCor() == Cor.BRANCO) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			}
			else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	private static void printCapturaPecas(List<PecaXadrez> captura) {
	//filtrando todas as pecas que seja da cor branca
	List<PecaXadrez> branco = captura.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
	//filtrando todas as pecas que seja da cor preta
	List<PecaXadrez> preto = captura.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList());
	
	System.out.println("capturando pecas:");
	System.out.println("Branco: ");
	System.out.print(ANSI_WHITE);
	System.out.println(Arrays.toString(branco.toArray()));
	System.out.print(ANSI_RESET);
	System.out.println("Preto: ");
	System.out.print(ANSI_YELLOW);
	System.out.println(Arrays.toString(preto.toArray()));
	System.out.print(ANSI_RESET);
	}

}
