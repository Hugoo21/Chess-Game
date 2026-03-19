package Model;

import java.util.ArrayList;
import java.util.List;

public class Roi extends Piece
{

    public Roi(boolean isBlanc)
    {
        super(isBlanc, "Roi");
    }

    @Override
    public String getSymbol()
    {
        return isBlanc ? "♔" : "♚";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board)
    {
        List<int[]> moves = new ArrayList<>();
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
                        moves.add(new int[]{r, c});
                    }
                }
            }
        }
        if (!this.alreadyMoved)
        {
            Piece tourDroite = board[row][7];
            Piece tourGauche = board[row][0];

            //petit Roque
            if (tourDroite instanceof Tour && !tourDroite.alreadyMoved)
            {
                if (board[row][5] == null && board[row][6] == null)
                {
                    moves.add(new int[]{row, col + 2});
                }
            }
            //grand Roque
            if (tourGauche instanceof Tour && !tourGauche.alreadyMoved)
            {
                if (board[row][1] == null && board[row][2] == null && board[row][3] == null)
                {
                    moves.add(new int[]{row, col - 2});
                }
            }

        }
        return moves;
    }
}