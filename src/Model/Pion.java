package Model;

import java.util.ArrayList;
import java.util.List;

public class Pion extends Piece
{

    public Pion(boolean isBlanc)
    {
        super(isBlanc, "Pion");
    }

    @Override
    public String getSymbol()
    {
        return isBlanc ? "♙" : "♟";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board)
    {
        List<int[]> moves = new ArrayList<>();
        // Les blancs montent (row diminue), les noirs descendent
        int dir = isBlanc ? -1 : 1;
        int startRow = isBlanc ? 6 : 1;

        // Avance d'une case
        int nextRow = row + dir;
        if (inBounds(nextRow, col) && board[nextRow][col] == null)
        {
            moves.add(new int[]{nextRow, col});
            // Avance de deux cases depuis la position initiale
            if (row == startRow && board[row + 2 * dir][col] == null)
            {
                moves.add(new int[]{row + 2 * dir, col});
            }
        }

        // Captures diagonales
        for (int dc : new int[]{-1, 1})
        {
            int nc = col + dc;
            if (inBounds(nextRow, nc))
            {
                Piece target = board[nextRow][nc];
                if (target != null && target.getCouleur() != isBlanc)
                {
                    moves.add(new int[]{nextRow, nc});
                }
            }
        }
        return moves;
    }

    private boolean inBounds(int r, int c) // vérifie que l'on joue bien dans le plateau (et non en dehors)
    {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }
}