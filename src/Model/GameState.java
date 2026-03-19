package Model;

import java.util.List;

public class GameState
{

    public static class CellInfo
    {
        public final String pieceSymbol;
        public final boolean isWhite;
        public CellInfo(String symbol, boolean isWhite)
        {
            this.pieceSymbol = symbol;
            this.isWhite = isWhite;
        }

        public String getIconName()
        {
            String color;
            if (isWhite)
            {
                color = "white";
            }
            else
            {
                color = "black";
            }

            return switch (pieceSymbol)
            {
                case "♔", "♚" -> color + "-king";
                case "♕", "♛" -> color + "-queen";
                case "♖", "♜" -> color + "-rook";
                case "♗", "♝" -> color + "-bishop";
                case "♘", "♞" -> color + "-knight";
                case "♙", "♟" -> color + "-pawn";
                default -> null;
            };
        }
    }

    private final CellInfo[][] board;
    private final int[] selectedCell;       // null si rien de sélectionné
    private final List<int[]> legalMoves;   // cases accessibles depuis selectedCell
    private final boolean whiteTurn;
    private final String statusMessage;

    public GameState(CellInfo[][] board, int[] selectedCell,
                     List<int[]> legalMoves, boolean whiteTurn, String statusMessage)
    {
        this.board = board;
        this.selectedCell = selectedCell;
        this.legalMoves = legalMoves;
        this.whiteTurn = whiteTurn;
        this.statusMessage = statusMessage;
    }

    public CellInfo getCell(int row, int col)
    {
        return board[row][col];
    }

    public int[] getSelectedCell()
    {
        return selectedCell;
    }

    public List<int[]> getLegalMoves()
    {
        return legalMoves;
    }

    public boolean isWhiteTurn()
    {
        return whiteTurn;
    }

    public String getStatusMessage()
    {
        return statusMessage;
    }
}