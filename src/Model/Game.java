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
    private boolean partieTerminee = false;

    public void leftClick(int row, int col)
    {
        if (partieTerminee)
        {
            return;
        }

        if (board.canBePromoted()) // on ne peut pas jouer un coup tant que le joueur n'a pas choisi sa promotion
        {
            return;
        }

        Piece clicked = board.getPiece(row, col);

        if (selectedCell == null)
        {
            // Sélectionner une pièce de la bonne couleur
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
                    board.enregistrerPosition(whiteTurn);
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

    public void promote(String choice)
    {
        int[] c = board.getPromotionCase();
        if (c == null)
        {
            return;
        }
        board.promote(c[0], c[1], choice);
        publish();
    }

    private void publish()
    {
        List<int[]> legalMoves;

        if (selectedCell == null)
        {
            legalMoves = List.of();
        }
        else
        {
            legalMoves = board.getCoupsLegaux(selectedCell[0], selectedCell[1]);
        }

        String status = buildStatus();

        GameState state = new GameState(
                board.buildSnapshot(),
                selectedCell,
                legalMoves,
                whiteTurn,
                status,
                board.canBePromoted(),
                board.getPromotionCase()
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
            partieTerminee = true;
            return (whiteTurn ? "Noirs" : "Blancs") + " gagnent ! Échec et mat.";
        }

        if (board.isRepetition())
        {
            partieTerminee = true;
            return "Nul par répétition";
        }

        if (board.isStalemate(whiteTurn))
        {
            partieTerminee = true;
            return "Nul par pat";
        }

        if (board.isKingInCheck(whiteTurn))
        {
            return (whiteTurn ? "Blancs" : "Noirs") + " : Échec au roi !";
        }
        return (whiteTurn ? "Blancs" : "Noirs") + " jouent.";
    }

    // appelée au démarrage
    public void init()
    {
        board.enregistrerPosition(whiteTurn);
        publish();
    }
}