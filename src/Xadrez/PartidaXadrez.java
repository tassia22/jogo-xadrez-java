package Xadrez;

import JogoTabuleiro.Peca;
import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.pecas.Rei;
import Xadrez.pecas.Torre;

public class PartidaXadrez {
	
	private int vez;
	private Cor jogadorAtual;
	private Tabuleiro tabuleiro;
	
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
		trocaVez();
		return (PecaXadrez)capturaPeca;
	}
	
	private Peca fazerMover(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removePeca(origem);
		Peca capturaPeca = tabuleiro.removePeca(destino);
		tabuleiro.colocaPeca(p, destino);
		return capturaPeca;
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
	
	private void colocaNovaPeca( char coluna, int linha, PecaXadrez peca) {
		tabuleiro.colocaPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	} 
	
	private void iniciaPartida() {
		
		colocaNovaPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 1, new Rei(tabuleiro, Cor.PRETO));
		
		colocaNovaPeca('c', 7, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('c', 8, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 7, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 7, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('e', 8, new Torre(tabuleiro, Cor.BRANCO));
		colocaNovaPeca('d', 9, new  Rei(tabuleiro, Cor.PRETO));
		
	        
	}
	
	

}
