package Model;

import java.util.List;

public class MouvementDiagonale extends DecorateurMouvement {

    public MouvementDiagonale(CalculateurMouvement m) { super(m); }

    @Override
    public List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc) {
        List<int[]> coups = super.getCoups(row, col, board, isBlanc);

        int[][] dirs = {{1,1},{-1,-1},{-1,1},{1,-1}};

        return coups;
    }
}
