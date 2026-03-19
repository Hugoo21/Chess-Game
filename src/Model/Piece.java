package Model;

import java.util.List;

public abstract class Piece
{
    protected boolean isBlanc;
    protected String nom;
    protected boolean alreadyMoved;

    public Piece(boolean isBlanc, String nom)
    {
        this.isBlanc = isBlanc;
        this.nom = nom;
    }

    public boolean isAlreadyMoved()
    {
        return alreadyMoved;
    }

    public void setAlreadyMoved(boolean etat)
    {
        this.alreadyMoved = etat;
    }

    public boolean getCouleur()
    {
        return isBlanc;
    }

    public String getNom()
    {
        return nom;
    }

    /** Symbole unicode affiché dans la vue */
    public abstract String getSymbol();

    /**
     * Retourne toutes les cases candidates (sans tenir compte de l'échec au roi).
     * La validation finale (ne pas se mettre en échec) est faite dans Board.
     */
    public abstract List<int[]> coupsPossibles(int row, int col, Piece[][] board);

    /** Raccourci booléen utilisé en interne */
    public boolean iscoupvalide(int depRow, int depCol, int arrRow, int arrCol, Piece[][] board)
    {
        return coupsPossibles(depRow, depCol, board).stream().anyMatch(m -> m[0] == arrRow && m[1] == arrCol);
    }
}