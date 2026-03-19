package VueController;

import Model.Game;
import Model.GameState;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class MainWindow extends JFrame implements Observer
{

    private static final Color LIGHT_SQUARE   = new Color(234, 236, 211);
    private static final Color DARK_SQUARE    = new Color(123, 148,  90);
    private static final Color CHECK_COLOR    = new Color(220,  50,  50, 180);

    private final java.util.Map<String, ImageIcon> iconCache = new java.util.HashMap<>();

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

                    if (isLegalMove(legalMoves, row, col))
                    {
                        square.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    }
                    else
                    {
                        square.setBorder(null);
                    }
                    square.setBackground(bg);

                    GameState.CellInfo cell = state.getCell(row, col);
                    if (cell == null)
                    {
                        label.setText("");
                        label.setIcon(null);
                    }
                    else
                    {
                        label.setText("");
                        label.setIcon(loadIcon(cell.getIconName()));
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

    private ImageIcon loadIcon(String name) {
        if (name == null) return null;
        return iconCache.computeIfAbsent(name, n -> {
            String path = "src/Icons/" + n + ".png";
            File file = new File(path);
            if (!file.exists()) return null;
            Image scaled = new ImageIcon(file.getAbsolutePath())
                    .getImage()
                    .getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        });
    }
}