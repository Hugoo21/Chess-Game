package Model;

import java.util.List;

public interface CalculateurMouvement {
    List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc);
}
