package Model;

import java.util.List;

public class MouvementDroit extends DecorateurMouvement {

    public MouvementDroit(CalculateurMouvement m) { super(m); }

    @Override
    public List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc) {
        List<int[]> coups = super.getCoups(row, col, board, isBlanc);

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        return coups;
    }
}