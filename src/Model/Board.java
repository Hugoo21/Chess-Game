package Model;

import Model.DCA.MouvementDeBase;
import Model.DCA.MouvementDePion;
import Model.Piece.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board
{

    private Piece[][] grid = new Piece[8][8];
    private int[] dernierCoup = null;
    private Map<String, Integer> positionsDejaJouees = new HashMap<>();

    public Board()
    {
        initPieces();
    }

    private void initPieces()
    {
        // pièces noires
        grid[0][0] = new Tour(false);
        grid[0][1] = new Cavalier(false);
        grid[0][2] = new Fou(false);
        grid[0][3] = new Reine(false);
        grid[0][4] = new Roi(false);
        grid[0][5] = new Fou(false);
        grid[0][6] = new Cavalier(false);
        grid[0][7] = new Tour(false);
        for (int c = 0; c < 8; c++)
        {
            grid[1][c] = new Pion(false);
        }

        // pièces blanches
        for (int c = 0; c < 8; c++)
        {
            grid[6][c] = new Pion(true);
        }
        grid[7][0] = new Tour(true);
        grid[7][1] = new Cavalier(true);
        grid[7][2] = new Fou(true);
        grid[7][3] = new Reine(true);
        grid[7][4] = new Roi(true);
        grid[7][5] = new Fou(true);
        grid[7][6] = new Cavalier(true);
        grid[7][7] = new Tour(true);
    }

    public Piece getPiece(int row, int col)
    {
        return grid[row][col];
    }

    private String hashPosition(boolean whiteTurn)
    {
        StringBuilder sb = new StringBuilder(); // plus optimisé qu'une string niveau mémoire : on crée une seule string modifiable (là où une String classique créerait de nouveaux objets à chaque modification)
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
            {
                Piece p = grid[r][c];
                sb.append(p == null ? "." : p.getSymbol() + p.getCouleur());
            }
            sb.append(whiteTurn); // le couleur du joueur qui doit jouer rentre aussi en compte
            return sb.toString();
    }

    public void enregistrerPosition(boolean whiteTurn)
    {
        String hash = hashPosition(whiteTurn);
        if (positionsDejaJouees.containsKey(hash))
        {
            positionsDejaJouees.put(hash, positionsDejaJouees.get(hash) + 1);
        }
        else
        {
            positionsDejaJouees.put(hash, 1);
        }
    }

    public boolean isRepetition()
    {
        return positionsDejaJouees.values().stream().anyMatch(v -> v >= 3);
    }

    public boolean isStalemate(boolean couleur)
    {
        if (isKingInCheck(couleur))
        {
            return false;
        }
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
            {
                Piece p = grid[r][c];
                if (p != null && p.getCouleur() == couleur && !getCoupsLegaux(r, c).isEmpty())
                {
                    return false;
                }
            }
        return true;
    }

    public List<int[]> getCoupsLegaux(int row, int col)
    {
        Piece piece = grid[row][col];
        if (piece == null)
        {
            return List.of();
        }

        List<int[]> candidats = getCandidats(row, col, piece);
        List<int[]> legaux = new ArrayList<>();

        for (int[] mv : candidats)
        {
            if (!putInCheck(row, col, mv[0], mv[1], piece.getCouleur()))
            {
                legaux.add(mv);
            }
        }
        return legaux;
    }

    private List<int[]> getCandidats(int row, int col, Piece piece)
    {
        if (piece instanceof Pion)
        {
            MouvementDePion pion_mouvement = new MouvementDePion(new MouvementDeBase());
            pion_mouvement.setDernierCoup(dernierCoup);
            return pion_mouvement.getCoups(row, col, grid, piece.getCouleur());
        }
        return piece.coupsPossibles(row, col, grid);
    }

    private boolean putInCheck(int fromR, int fromC, int toR, int toC, boolean couleur)
    {
        // Sauvegarde
        Piece moving = grid[fromR][fromC];
        Piece captured = grid[toR][toC];

        // Simulation
        grid[toR][toC] = moving;
        grid[fromR][fromC] = null;

        boolean enEchec = isKingInCheck(couleur);

        // Restauration
        grid[fromR][fromC] = moving;
        grid[toR][toC] = captured;

        return enEchec;
    }

    public boolean isKingInCheck(boolean couleur) {
        int kingRow = -1, kingCol = -1;
        outer:
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                Piece p = grid[r][c];
                if (p instanceof Roi && p.getCouleur() == couleur)
                {
                    kingRow = r; kingCol = c;
                    break outer;
                }
            }
        }
        if (kingRow == -1)
        {
            return false; // sécurité
        }

        // Vérifie si une pièce adverse attaque le roi
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                Piece p = grid[r][c];
                if (p != null && p.getCouleur() != couleur)
                {
                    for (int[] mv : p.coupsPossibles(r, c, grid))
                    {
                        if (mv[0] == kingRow && mv[1] == kingCol)
                        {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(boolean couleur)
    {
        if (!isKingInCheck(couleur))
        {
            return false;
        }

        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                Piece p = grid[r][c];
                if (p != null && p.getCouleur() == couleur && !getCoupsLegaux(r, c).isEmpty())
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean movePiece(int fromR, int fromC, int toR, int toC)
    {
        List<int[]> legaux = getCoupsLegaux(fromR, fromC);
        boolean legal = legaux.stream().anyMatch(m -> m[0] == toR && m[1] == toC);
        if (!legal)
        {
            return false;
        }

        // Roque
        Piece pieceMoved = grid[fromR][fromC];
        System.out.println("--- DÉBUG MOUVEMENT ---");
        System.out.println("Pièce : " + pieceMoved.getNom());
        System.out.println("Déjà bougé (AVANT) : " + pieceMoved.isAlreadyMoved());

        if (pieceMoved instanceof Roi && Math.abs(toC - fromC) >= 2 )
        {
            int colTourDepart;
            int colTourArrivee;
            if (toC > fromC)
            {
                colTourDepart = 7;
                colTourArrivee = toC - 1;
            }
            else
            {
                colTourDepart = 0;
                colTourArrivee = toC + 1;
            }

            grid[toR][colTourArrivee] = grid[fromR][colTourDepart] ;
            grid[fromR][colTourDepart] = null;
            grid[toR][colTourArrivee].setAlreadyMoved(true);

        }

        // prise en-passant
        if (pieceMoved instanceof Pion && Math.abs(toC - fromC) == 1 && grid[toR][toC] == null)
        {
            grid[fromR][toC] = null;
        }

        grid[toR][toC] = grid[fromR][fromC];
        grid[fromR][fromC] = null;
        pieceMoved.setAlreadyMoved(true);
        System.out.println("Déjà bougé (APRÈS) : " + pieceMoved.isAlreadyMoved());
        System.out.println("-----------------------");

        dernierCoup = new int[]{fromR, fromC, toR, toC};

        return true;
    }

    public boolean canBePromoted()
    {
        for (int c = 0; c < 8; c++)
        {
            if (grid[0][c] instanceof Pion && grid[0][c].getCouleur())
            {
                return true;
            }

            if (grid[7][c] instanceof Pion && !grid[7][c].getCouleur())
            {
                return true;
            }
        }
        return false;
    }

    public int[] getPromotionCase()
    {
        for (int c = 0; c < 8; c++)
        {
            if (grid[0][c] instanceof Pion && grid[0][c].getCouleur())
            {
                return new int[]{0, c};
            }

            if (grid[7][c] instanceof Pion && !grid[7][c].getCouleur())
            {
                return new int[]{7, c};
            }
        }
        return null;
    }

    public void promote(int row, int col, String choice)
    {
        boolean white = grid[row][col].getCouleur();
        grid[row][col] = switch (choice)
        {
            case "Tour" -> new Tour(white);
            case "Fou" -> new Fou(white);
            case "Cavalier" -> new Cavalier(white);
            default -> new Reine(white);
        };
    }

    public GameState.CellInfo[][] buildSnapshot()
    {
        GameState.CellInfo[][] snap = new GameState.CellInfo[8][8];
        for (int r = 0; r < 8; r++)
        {
            for (int c = 0; c < 8; c++)
            {
                Piece p = grid[r][c];
                if (p == null)
                {
                    snap[r][c] = null;
                }
                else
                {
                    snap[r][c] = new GameState.CellInfo(p.getSymbol(), p.getCouleur());
                }
            }
        }
        return snap;
    }
}