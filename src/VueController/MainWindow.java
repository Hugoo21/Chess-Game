package VueController;

import Model.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class MainWindow extends JFrame implements Observer
{
    private JPanel[][] tab = new JPanel[8][8];

    public MainWindow(Game game)
    {
        super("Chess Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);

        JPanel gridPanel = new JPanel(new GridLayout(8, 8));

        for (int row=0; row<8; row++)
        {
            for (int col=0; col<8; col++)
            {
                JPanel square = new JPanel(new BorderLayout());
                tab[row][col] = square;

                if ((row + col)%2 == 0)
                {
                    square.setBackground(Color.WHITE);
                }
                else
                {
                    square.setBackground(new Color(0x7b945a));
                }

                int r = row;
                int c = col;
                square.addMouseListener(new java.awt.event.MouseAdapter()
                {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e)
                    {
                        game.leftClick(r, c);
                    }
                });                gridPanel.add(square);
            }
        }

        placePieces();

        this.add(gridPanel);
        this.setVisible(true);
    }

    private ImageIcon loadIcon(String name)
    {
        String path = "src/Icons/" + name + ".png";
        File file = new File(path);

        if (!file.exists()) return null;

        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
        Image scaled = icon.getImage().getScaledInstance(
                800/8 - 10, 800/8 - 10, Image.SCALE_SMOOTH
        );
        return new ImageIcon(scaled);
    }

    private void addPiece(int row, int col, String name)
    {
        JLabel label = new JLabel(loadIcon(name));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        tab[row][col].add(label, BorderLayout.CENTER);
    }

    private void placePieces()
    {
        // black pieces
        addPiece(0, 0, "black-rook");
        addPiece(0, 1, "black-knight");
        addPiece(0, 2, "black-bishop");
        addPiece(0, 3, "black-queen");
        addPiece(0, 4, "black-king");
        addPiece(0, 5, "black-bishop");
        addPiece(0, 6, "black-knight");
        addPiece(0, 7, "black-rook");
        for (int col = 0; col < 8; col++)
            addPiece(1, col, "black-pawn");

        // White pieces
        addPiece(7, 0, "white-rook");
        addPiece(7, 1, "white-knight");
        addPiece(7, 2, "white-bishop");
        addPiece(7, 3, "white-queen");
        addPiece(7, 4, "white-king");
        addPiece(7, 5, "white-bishop");
        addPiece(7, 6, "white-knight");
        addPiece(7, 7, "white-rook");
        for (int col = 0; col < 8; col++)
            addPiece(6, col, "white-pawn");
    }

    @Override
    public void update(Observable o, Object arg)
    {
        for (int row = 0; row < 8; row++)
        {
            for (int column = 0; column < 8; column++)
            {
                if ((row + column)%2 == 0)
                {
                    tab[row][column].setBackground(Color.WHITE);
                }
                else
                {
                    tab[row][column].setBackground(new Color(0x7b945a));
                }
            }
        }
        if (arg instanceof int[] selected)
        {
            tab[selected[0]][selected[1]].setBackground(Color.RED);
        }
    }
}