package Model;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Piece[][] grid = new Piece[8][8];

    public Board() {
        initPieces();
    }

    //Initialisation

    private void initPieces() {
        // Noirs
        grid[0][0] = new Tour(false);
        grid[0][1] = new Cavalier(false);
        grid[0][2] = new Fou(false);
        grid[0][3] = new Reine(false);
        grid[0][4] = new Roi(false);
        grid[0][5] = new Fou(false);
        grid[0][6] = new Cavalier(false);
        grid[0][7] = new Tour(false);
        for (int c = 0; c < 8; c++) grid[1][c] = new Pion(false);

        // Blancs
        for (int c = 0; c < 8; c++) grid[6][c] = new Pion(true);
        grid[7][0] = new Tour(true);
        grid[7][1] = new Cavalier(true);
        grid[7][2] = new Fou(true);
        grid[7][3] = new Reine(true);
        grid[7][4] = new Roi(true);
        grid[7][5] = new Fou(true);
        grid[7][6] = new Cavalier(true);
        grid[7][7] = new Tour(true);
    }

    public Piece getPiece(int row, int col) { return grid[row][col]; }

    //Coups légaux

    public List<int[]> getCoupsLegaux(int row, int col) {
        Piece piece = grid[row][col];
        if (piece == null) return List.of();

        List<int[]> candidats = piece.coupsPossibles(row, col, grid);
        List<int[]> legaux = new ArrayList<>();

        for (int[] mv : candidats) {
            if (!metEnEchec(row, col, mv[0], mv[1], piece.getCouleur()))
                legaux.add(mv);
        }
        return legaux;
    }

    //Vérification
    private boolean metEnEchec(int fromR, int fromC, int toR, int toC, boolean couleur) {
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
        // Trouve le roi
        int kingRow = -1, kingCol = -1;
        outer:
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p instanceof Roi && p.getCouleur() == couleur) {
                    kingRow = r; kingCol = c;
                    break outer;
                }
            }
        }
        if (kingRow == -1) return false; // sécurité

        // Vérifie si une pièce adverse attaque le roi
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.getCouleur() != couleur) {
                    for (int[] mv : p.coupsPossibles(r, c, grid)) {
                        if (mv[0] == kingRow && mv[1] == kingCol) return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(boolean couleur) {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                if (p != null && p.getCouleur() == couleur && !getCoupsLegaux(r, c).isEmpty())
                    return false;
            }
        return true;
    }

    // Déplacement

    public boolean movePiece(int fromR, int fromC, int toR, int toC) {
        List<int[]> legaux = getCoupsLegaux(fromR, fromC);
        boolean legal = legaux.stream().anyMatch(m -> m[0] == toR && m[1] == toC);
        if (!legal) return false;

        // Roque
        Piece moved = grid[fromR][fromC];

        if (moved instanceof Roi && Math.abs(toC - fromC) >= 2 ) {
            int colTourDepart = (toC > fromC) ? 7 : 0;
            int colTourArrivee = (toC > fromC) ? toC - 1 : toC + 1;

            grid[toR][colTourArrivee] = grid[fromR][colTourDepart] ;
            grid[fromR][colTourDepart] = null;
            grid[toR][colTourArrivee].setAlreadyMoved(true);

        }

        grid[toR][toC] = grid[fromR][fromC];
        grid[fromR][fromC] = null;

        // Promotion pion en dame
        if (moved instanceof Pion) {
            if ((moved.getCouleur() && toR == 0) || (!moved.getCouleur() && toR == 7))
                grid[toR][toC] = new Reine(moved.getCouleur());
        }
        return true;
    }

    public GameState.CellInfo[][] buildSnapshot() {
        GameState.CellInfo[][] snap = new GameState.CellInfo[8][8];
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++) {
                Piece p = grid[r][c];
                snap[r][c] = (p == null) ? null
                        : new GameState.CellInfo(p.getSymbol(), p.getCouleur());
            }
        return snap;
    }
}