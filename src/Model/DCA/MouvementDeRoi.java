package Model.DCA;

import Model.Piece.Piece;
import Model.Piece.Tour;

import java.util.ArrayList;
import java.util.List;

public class MouvementDeRoi extends DecorateurMouvement{
    public MouvementDeRoi(CalculateurMouvement m)
    {
        super(m);

    }

    @Override
    public List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc)
    {
        List<int[]> coups = new ArrayList<>();
        for (int dr = -1; dr <= 1; dr++)
        {
            for (int dc = -1; dc <= 1; dc++)
            {
                if (dr == 0 && dc == 0) continue;
                int r = row + dr, c = col + dc;
                if (r >= 0 && r < 8 && c >= 0 && c < 8)
                {
                    if (board[r][c] == null || board[r][c].getCouleur() != isBlanc)
                    {
                        coups.add(new int[]{r, c});
                    }
                }
            }
        }
        Piece imKing = board[row][col];
        if (!imKing.isAlreadyMoved())
        {
            Piece tourDroite = board[row][7];
            Piece tourGauche = board[row][0];

            //petit Roque
            if (tourDroite instanceof Tour && !tourDroite.isAlreadyMoved())
            {
                if (board[row][5] == null && board[row][6] == null)
                {
                    coups.add(new int[]{row, col + 2});
                }
            }
            //grand Roque
            if (tourGauche instanceof Tour && !tourGauche.isAlreadyMoved())
            {
                if (board[row][1] == null && board[row][2] == null && board[row][3] == null)
                {
                    coups.add(new int[]{row, col - 2});
                }
            }

        }
        return coups;
    }
}
