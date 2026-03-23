package Model.DCA;

import Model.Piece.Piece;
import java.util.List;

// La cartouche "Mouvement en L"
public class MouvementDeCavalier extends DecorateurMouvement {

    public MouvementDeCavalier(CalculateurMouvement m)
    {
        super(m);
    }

    @Override
    public List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc) {
        List<int[]> coups = super.getCoups(row, col, board, isBlanc);

        int[][] sauts =
                {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };

        for (int[] s : sauts) {
            int r = row + s[0];
            int c = col + s[1];

            // Si le saut atterrit bien sur le plateau...
            if (r >= 0 && r < 8 && c >= 0 && c < 8) {
                // Et que la case est vide OU contient un ennemi...
                if (board[r][c] == null || board[r][c].getCouleur() != isBlanc) {
                    // On ajoute ce coup à notre carnet !
                    coups.add(new int[]{r, c});
                }
            }
        }

        // 4. On renvoie le carnet mis à jour
        return coups;
    }
}