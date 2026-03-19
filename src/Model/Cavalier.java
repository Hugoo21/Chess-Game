package Model;

import java.util.ArrayList;
import java.util.List;

public class Cavalier extends Piece
{

    public Cavalier(boolean isBlanc)
    {
        super(isBlanc, "Cavalier");
    }

    @Override
    public String getSymbol()
    {
        return isBlanc ? "♘" : "♞";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board)
    {
        List<int[]> moves = new ArrayList<>();
        int[][] sauts = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
        for (int[] s : sauts)
        {
            int r = row + s[0], c = col + s[1];
            if (r >= 0 && r < 8 && c >= 0 && c < 8)
            {
                if (board[r][c] == null || board[r][c].getCouleur() != isBlanc)
                {
                    moves.add(new int[]{r, c});
                }
            }
        }
        return moves;
    }
}