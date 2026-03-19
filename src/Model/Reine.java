package Model;

import java.util.ArrayList;
import java.util.List;

public class Reine extends Piece {
    private CalculateurMouvement calculateur;

    public Reine(boolean isBlanc) {
        super(isBlanc, "Dame");
        this.calculateur = new MouvementDroit(
                            new MouvementDiagonale(
                                new MouvementDeBase()));
    }

    @Override
    public String getSymbol() { return isBlanc ? "♕" : "♛"; }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board) {
        List<int[]> moves = calculateur.getCoups(row, col, board, this.isBlanc);
        return moves;
    }
}