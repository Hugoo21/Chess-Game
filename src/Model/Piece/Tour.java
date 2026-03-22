package Model.Piece;

import Model.DCA.CalculateurMouvement;
import Model.DCA.MouvementDroit;
import Model.DCA.MouvementDeBase;

import java.util.ArrayList;
import java.util.List;

public class Tour extends Piece
{
    private CalculateurMouvement calculateur;
    public Tour(boolean isBlanc)
    {
        super(isBlanc, "Tour");
        this.calculateur = new MouvementDroit( new MouvementDeBase() );

    }

    @Override
    public String getSymbol()
    {
        return isBlanc ? "♖" : "♜";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board)
    {
        return calculateur.getCoups(row, col, board, this.isBlanc);
    }
}