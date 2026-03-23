package Model.Piece;

import Model.DCA.CalculateurMouvement;
import Model.DCA.MouvementDeBase;
import Model.DCA.MouvementDiagonale;

import java.util.ArrayList;
import java.util.List;

public class Fou extends Piece
{
    private CalculateurMouvement calculateur;

    public Fou(boolean isBlanc)
    {
        super(isBlanc, "Fou");
        this.calculateur = new MouvementDiagonale( new MouvementDeBase() );
    }

    @Override
    public String getSymbol()
    {
        return isBlanc ? "♗" : "♝";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board)
    {
        return calculateur.getCoups(row, col, board, this.isBlanc);
    }
}