package Model;

import Model.Piece.Piece;

import java.util.List;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class Game extends Observable
{

    private final Board board = new Board();
    private int[] selectedCell = null;
    private boolean whiteTurn = true;

    public void leftClick(int row, int col)
    {
        Piece clicked = board.getPiece(row, col);

        if (selectedCell == null)
        {
            // Sélectionner une pièce du bon camp
            if (clicked != null && clicked.getCouleur() == whiteTurn)
            {
                selectedCell = new int[]{row, col};
            }
        }
        else
        {
            int selRow = selectedCell[0], selCol = selectedCell[1];

            if (selRow == row && selCol == col)
            {
                selectedCell = null;
            }
            else if (clicked != null && clicked.getCouleur() == whiteTurn)
            {
                selectedCell = new int[]{row, col};
            }
            else
            {
                // Tentative de déplacement
                boolean moved = board.movePiece(selRow, selCol, row, col);
                if (moved)
                {
                    whiteTurn = !whiteTurn;
                    selectedCell = null;
                }
                else
                {
                    selectedCell = null; // coup invalide
                }
            }
        }

        publish();
    }

    private void publish()
    {
        List<int[]> legalMoves = (selectedCell == null)
                ? List.of()
                : board.getCoupsLegaux(selectedCell[0], selectedCell[1]);

        String status = buildStatus();

        GameState state = new GameState(
                board.buildSnapshot(),
                selectedCell,
                legalMoves,
                whiteTurn,
                status
        );

        synchronized (this)
        {
            setChanged();
            notifyObservers(state);
        }
    }

    private String buildStatus()
    {
        if (board.isCheckmate(whiteTurn))
        {
            return (whiteTurn ? "Noirs" : "Blancs") + " gagnent ! Échec et mat.";
        }
        if (board.isKingInCheck(whiteTurn))
        {
            return (whiteTurn ? "Blancs" : "Noirs") + " : Échec au roi !";
        }
        return (whiteTurn ? "Blancs" : "Noirs") + " jouent.";
    }

    /** Appelé au démarrage pour afficher l'état initial */
    public void init()
    {
        publish();
    }
}