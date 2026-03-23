package Model.Piece;

import Model.DCA.CalculateurMouvement;
import Model.DCA.MouvementDeBase;
import Model.DCA.MouvementDiagonale;
import Model.DCA.MouvementDroit;

import java.util.ArrayList;
import java.util.List;

public class Reine extends Piece
{
    private CalculateurMouvement calculateur;
    public Reine(boolean isBlanc)
    {
        super(isBlanc, "Dame");
        this.calculateur = new MouvementDroit( new MouvementDiagonale(new MouvementDeBase() ) );
    }

    @Override
    public String getSymbol()
    {
        return isBlanc ? "♕" : "♛";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board)
    {
        return calculateur.getCoups(row, col, board, this.isBlanc);
    }
}