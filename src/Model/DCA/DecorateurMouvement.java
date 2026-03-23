package Model.DCA;

import Model.Piece.Piece;

import java.util.List;

public abstract class DecorateurMouvement implements CalculateurMouvement
{
    protected CalculateurMouvement mouvementEnveloppe;

    public DecorateurMouvement(CalculateurMouvement m)
    {
        this.mouvementEnveloppe = m;
    }

    @Override
    public List<int[]> getCoups(int row, int col, Piece[][] board, boolean isBlanc)
    {
        // On demande au module d'en dessous de calculer ses coups
        return mouvementEnveloppe.getCoups(row, col, board, isBlanc);
    }
}