package Model.Piece;
import Model.DCA.CalculateurMouvement;
import Model.DCA.MouvementDeBase;
import Model.DCA.MouvementDeCavalier;

import java.util.List;

public class Cavalier extends Piece
{
    private CalculateurMouvement calculateur;

    public Cavalier(boolean isBlanc)
    {
        super(isBlanc, "Cavalier");
        this.calculateur = new MouvementDeCavalier(new MouvementDeBase());
    }

    @Override
    public String getSymbol()
    {
        return isBlanc ? "♘" : "♞";
    }

    @Override
    public List<int[]> coupsPossibles(int row, int col, Piece[][] board)
    {
        return calculateur.getCoups(row, col, board, this.isBlanc);
    }
}