package VueController;

import Model.Game;
import Model.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MainWindow extends JFrame implements Observer
{

    private static final Color LIGHT_SQUARE   = new Color(240, 217, 181);
    private static final Color DARK_SQUARE    = new Color(181, 136,  99);
    private static final Color SELECTED_COLOR = new Color( 20, 160,  20, 200);
    private static final Color LEGAL_COLOR    = new Color( 20, 160,  20,  80);
    private static final Color CHECK_COLOR    = new Color(220,  50,  50, 180);

    private final Game game;
    private final JPanel[][] tab = new JPanel[8][8];
    private final JLabel[][] pieceLabels = new JLabel[8][8];
    private final JLabel statusLabel = new JLabel("", SwingConstants.CENTER);

    public MainWindow(Game game)
    {
        super("Chess Game");
        this.game = game;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(640, 680);
        this.setLocationRelativeTo(null);

        JPanel gridPanel = new JPanel(new GridLayout(8, 8));
        for (int row = 0; row < 8; row++)
        {
            for (int col = 0; col < 8; col++)
            {
                JPanel square = new JPanel(new BorderLayout());
                square.setBackground(baseColor(row, col));

                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 48));
                square.add(label, BorderLayout.CENTER);

                tab[row][col] = square;
                pieceLabels[row][col] = label;

                final int r = row, c = col;
                square.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        game.leftClick(r, c);
                    }
                });
                gridPanel.add(square);
            }
        }

        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(50, 50, 50));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setPreferredSize(new Dimension(640, 36));

        this.add(gridPanel, BorderLayout.CENTER);
        this.add(statusLabel, BorderLayout.SOUTH);

        game.addObserver(this);
        game.init();

        this.setVisible(true);
    }


    @Override
    public void update(Observable o, Object arg)
    {
        if (!(arg instanceof GameState state))
        {
            return;
        }

        SwingUtilities.invokeLater(() -> {
            int[] selected = state.getSelectedCell();
            List<int[]> legalMoves = state.getLegalMoves();

            for (int row = 0; row < 8; row++)
            {
                for (int col = 0; col < 8; col++)
                {
                    JPanel square = tab[row][col];
                    JLabel label  = pieceLabels[row][col];

                    Color bg = baseColor(row, col);
                    if (selected != null && selected[0] == row && selected[1] == col)
                    {
                        bg = SELECTED_COLOR;
                    }
                    else if (isLegalMove(legalMoves, row, col))
                    {
                        bg = LEGAL_COLOR;
                    }
                    square.setBackground(bg);

                    GameState.CellInfo cell = state.getCell(row, col);
                    if (cell == null)
                    {
                        label.setText("");
                    }
                    else
                    {
                        label.setText(cell.pieceSymbol);
                        label.setForeground(cell.isWhite ? Color.WHITE : Color.BLACK);
                    }
                }
            }

            statusLabel.setText("  " + state.getStatusMessage());
            statusLabel.setBackground(state.getStatusMessage().contains("Échec")
                    ? CHECK_COLOR : new Color(50, 50, 50));
        });
    }


    private Color baseColor(int row, int col) {
        if ((row + col) % 2 == 0)
        {
            return LIGHT_SQUARE;
        }
        else
        {
            return DARK_SQUARE;
        }
    }

    private boolean isLegalMove(List<int[]> moves, int row, int col)
    {
        if (moves == null)
        {
            return false;
        }

        return moves.stream().anyMatch(m -> m[0] == row && m[1] == col);
    }
}