package Model;

import java.util.ArrayList;
import java.util.List;

public class Tour extends Piece {

    public Tour(boolean isBlanc) { super(isBlanc, "Tour"); }

    @Override
    public String getSymbol() { return isBlanc ? "♖" : "♜"; }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board) {
        List<int[]> moves = new ArrayList<>();
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        for (int[] d : dirs) {
            for (int i = 1; i < 8; i++) {
                int r = row + d[0]*i, c = col + d[1]*i;
                if (r < 0 || r >= 8 || c < 0 || c >= 8) break;
                if (board[r][c] == null) { moves.add(new int[]{r, c}); }
                else {
                    if (board[r][c].getCouleur() != isBlanc) moves.add(new int[]{r, c});
                    break;
                }
            }
        }
        return moves;
    }
}