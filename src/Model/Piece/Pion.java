package Model.Piece;

import Model.DCA.CalculateurMouvement;
import Model.DCA.MouvementDeBase;
import Model.DCA.MouvementDePion;

import java.util.ArrayList;
import java.util.List;

public class Pion extends Piece {
    private CalculateurMouvement calculateur;

    public Pion(boolean isBlanc) {
        super(isBlanc, "Pion");
        calculateur = new MouvementDePion( new MouvementDeBase());
    }

    @Override
    public String getSymbol() {
        return isBlanc ? "♙" : "♟";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board) {
        return calculateur.getCoups(row, col, board, this.isBlanc);
    }
}