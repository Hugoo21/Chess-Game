package Model.Piece;

import Model.DCA.CalculateurMouvement;
import Model.DCA.MouvementDeBase;
import Model.DCA.MouvementDeRoi;

import java.util.ArrayList;
import java.util.List;

public class Roi extends Piece
{
    private CalculateurMouvement calculateur;
    public Roi(boolean isBlanc)
    {
        super(isBlanc, "Roi");
        calculateur = new MouvementDeRoi( new MouvementDeBase());
    }

    @Override
    public String getSymbol()
    {
        return isBlanc ? "♔" : "♚";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board)
    {
        return calculateur.getCoups(row, col, board, this.isBlanc);
    }
}