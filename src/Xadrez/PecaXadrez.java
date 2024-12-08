package Xadrez;

import JogoTabuleiro.Peca;
import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;

public  abstract class PecaXadrez extends Peca{
	
	private Cor cor;
	private int movendoCount;

	public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cor getCor() {
		return cor;
	}
	
	public int getmovendoCount() {
		return movendoCount;
	}
	
	public void incrementandoMovimentoCount() {
		movendoCount++;
	}
	
	public void decrementandoMovimentoCount() {
		movendoCount--;
	}
	
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromPosicao(posicao);
	}
	
	protected boolean existePecaAdversaria(Posicao posicao) {
		PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() != cor;
	}

	
}
