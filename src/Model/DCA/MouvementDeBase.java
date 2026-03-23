package Model.DCA;

import Model.Piece.Piece;

import java.util.List;

public class MouvementDeBase implements CalculateurMouvement
{

    @Override
    public List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc)
    {
        return new java.util.ArrayList<>();
    }
}
