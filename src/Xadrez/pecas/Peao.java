package Xadrez.pecas;

import JogoTabuleiro.Posicao;
import JogoTabuleiro.Tabuleiro;
import Xadrez.Cor;
import Xadrez.PecaXadrez;

public abstract class Peao extends PecaXadrez {

	public Peao(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}
	
	@Override
	public boolean [][] possiveisMovimentos(){
		boolean [][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		if(getCor() == Cor.BRANCO) {
			p.setValores(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().posicaoExiste(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().posicaoExiste(p2) && getmovendoCount() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		else {
			p.setValores(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().posicaoExiste(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().posicaoExiste(p2) && getmovendoCount() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if(getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if(getTabuleiro().posicaoExiste(p) && existePecaAdversaria(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
		}
		
	    return mat;
	}
	
	@Override
	public String toString() {
		return "p";
	}
	
	

}
