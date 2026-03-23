package Model.DCA;

import  Model.Piece.Piece;
import Model.Piece.Pion;

import java.util.ArrayList;
import java.util.List;

public class MouvementDePion extends DecorateurMouvement
{
    private int[] dernierCoup;

    public MouvementDePion(CalculateurMouvement m)
    {
        super(m);
        this.dernierCoup = null;
    }

    public void setDernierCoup(int[] dernierCoup)
    {
        this.dernierCoup = dernierCoup;
    }

    @Override
    public List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc)
    {
        List<int[]> moves = new ArrayList<>();
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

                if (dernierCoup != null)
                {
                    int src_ligne = dernierCoup[0];
                    int src_col = dernierCoup[1];
                    int dest_ligne = dernierCoup[2];
                    int dest_col = dernierCoup[3];

                    Piece dernierePiece = board[dest_ligne][dest_col];

                    // ensemble des conditions requises pour une prise en passant
                    // la dernière joué doit être un pion,
                    // ayant avancée de 2 cases, sur la même ligne et sur une colonne adjacente qu'un pion allié

                    boolean etaitPion = dernierePiece instanceof Pion;
                    boolean estAdverse = dernierePiece != null && dernierePiece.getCouleur() != isBlanc;
                    boolean avanceDeDeux = Math.abs(dest_ligne - src_ligne) == 2;
                    boolean memeLigne = dest_ligne == row;
                    boolean colonneAdjacente = Math.abs(dest_col - col) == 1;

                    if (etaitPion && estAdverse && avanceDeDeux && memeLigne && colonneAdjacente)
                    {
                        moves.add(new int[]{nextRow, dest_col});
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
