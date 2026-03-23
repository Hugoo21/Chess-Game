package Model.DCA;

import Model.Piece.Piece;

import java.util.List;

public class MouvementDiagonale extends DecorateurMouvement {

    public MouvementDiagonale(CalculateurMouvement m) { super(m); }

    @Override
    public List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc) {
        List<int[]> coups = super.getCoups(row, col, board, isBlanc);

        int[][] dirs = {{1,1},{-1,-1},{-1,1},{1,-1}};

        for (int[] d : dirs)
        {
            for (int i = 1; i < 8; i++)
            {
                int r = row + d[0]*i, c = col + d[1]*i;
                if (r < 0 || r >= 8 || c < 0 || c >= 8)
                {
                    break;
                }
                if (board[r][c] == null)
                {
                    coups.add(new int[]{r, c});
                }
                else
                {
                    if (board[r][c].getCouleur() != isBlanc)
                    {
                        coups.add(new int[]{r, c});
                    }
                    break;
                }
            }
        }
        return coups;
    }
}
