package VueController;

import Model.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import javax.imageio.*;
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
                JPanel square = new JPanel();
                tab[row][col] = square;

                if ((row + col)%2 == 0)
                {
                    square.setBackground(Color.WHITE);
                }
                else
                {
                    square.setBackground(Color.BLACK);
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
                });
                gridPanel.add(square);
            }
        }
        this.add(gridPanel);
        this.setVisible(true);
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
                    tab[row][column].setBackground(Color.BLACK);
                }
            }
        }
        if (arg instanceof int[] selected)
        {
            tab[selected[0]][selected[1]].setBackground(Color.RED);
        }
    }
}