package Xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import JogoTabuleiro.Peca;
import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	
	private List<Peca> pecaDoTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturada = new ArrayList<>();
	
	public PartidaXadrez() {
		tabuleiro = new Tabuleiro(8,8);
		vez = 1;
		jogadorAtual = Cor.BRANCO;
		iniciaPartida();
	}
	
	
	
	public int getVez() {
		return vez;
	}

	public Cor getJogadorAtual() {
		return jogadorAtual;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
		
	} 

	public PecaXadrez[][] getPecas(){
		
		PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for(int i = 0; i < tabuleiro.getLinhas(); i++) {
			for(int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
			}
		}
		
		return mat;
	}
	
	public boolean[][] possivelMovimento(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validaPosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).possiveisMovimentos();
		
		
	}
	
	public PecaXadrez performDeXadrez(PosicaoXadrez posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validaPosicaoOrigem(origem);
		validaPosicaoDestino(origem, destino);
		Peca capturaPeca = fazerMover(origem, destino);
		
		if(testeCheck(jogadorAtual)) {
			desfazMovimento(origem, destino, capturaPeca);
			throw new XadrezException("voce nao pode se colocar em check");
		}
		
		check = (testeCheck(oponente(jogadorAtual))) ? true : false;
		
		if(testeCheckMate(jogadorAtual)) {
			checkMate = true;
		}else {
			trocaVez();
		}
		return (PecaXadrez)capturaPeca;
	}
	
	private Peca fazerMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturaPeca = tabuleiro.removePeca(destino);
		tabuleiro.colocaPeca(p, destino);
		
		if(capturaPeca != null) {
			pecaDoTabuleiro.remove(capturaPeca);
			pecasCapturada.add(capturaPeca);
		}
		return capturaPeca;
	}
	
	private void desfazMovimento(Posicao origem,Posicao destino, Peca capturaPeca) {
		Peca p = tabuleiro.removePeca(destino);
		tabuleiro.colocaPeca(p, origem);
		
		if (capturaPeca != null) {
			tabuleiro.colocaPeca(capturaPeca, destino);
			pecasCapturada.remove(capturaPeca);
			pecaDoTabuleiro.add(capturaPeca);
			
			
		}
	}
	
	private void validaPosicaoOrigem(Posicao posicao) {
		if(!tabuleiro.temUmaPeca(posicao)) {
			throw new XadrezException("não existe peça na posição de origem");
		}
		if(jogadorAtual != ((PecaXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezException("a peca escolhida nao e sua");
		}
		if(tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezException("nao existe movimentos possiveis para a peça escolhida");
		}
	}
	
	private void validaPosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).possivelMovimento(destino)) {
			throw new XadrezException("a peca escolhida nao pode se mover para a posicao de destino");
		}
	}
	
	public void trocaVez() {
		vez++;
		jogadorAtual = (jogadorAtual) == Cor.BRANCO ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : cor.BRANCO;
	}
	
	private PecaXadrez Rei(Cor cor) {
		List<Peca> list = pecaDoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		for(Peca p : list) {
			if(p instanceof Rei) {
				return (PecaXadrez)p;
			}
		}
		
		throw new IllegalStateException("Nao existe " + cor + " Rei no tabuleiro");
	}
	
	private boolean testeCheck(Cor cor) {
		Posicao posicaoRei = Rei(cor).getPosicaoXadrez().toPosicao();
		List<Peca> pecasOponentes =  pecaDoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for(Peca p : pecasOponentes) {
			boolean[][] mat = p.possiveisMovimentos();
			if (mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true;
				
			}
			
		}
		
		return false;
	}
	
	private boolean testeCheckMate(Cor cor) {
		if(!testeCheck(cor)) {
			return false;
		}
		
		List<Peca> list = pecaDoTabuleiro.stream().filter(x ->((PecaXadrez)x).getCor() == cor).collect(Collectors.toList());
		
		for(Peca p : list) {
			boolean[][] mat = p.possiveisMovimentos();
			for(int i = 0; i <tabuleiro.getLinhas(); i++) {
				for(int j = 0; j < tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaXadrez)p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i, j);
						Peca capturaPeca = fazerMover(origem, destino);
						boolean testeCheck = testeCheck(cor);
						desfazMovimento(origem, destino, capturaPeca);
						if(!testeCheck) {
							return false;
						}
					}
				}
			}
		
			
			
		}
		
		return true;
		
	}
	
	
	
	
	private void colocaNovaPeca( char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecaDoTabuleiro.add(peca);
	} 
	
	private void iniciaPartida() {
		colocaNovaPeca('h', 7, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 1, new Rei(tabuleiro, Cor.PRETO));
		
		colocaNovaPeca('b', 8, new Torre(tabuleiro, Cor.PRETO));
		colocaNovaPeca('a', 8, new Rei(tabuleiro, Cor.PRETO));
		
	        
	}
	
	

}
