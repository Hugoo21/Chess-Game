package VueController;

import javax.swing.*;
import java.awt.*;

public class Board {
    JPanel tab[][] = new JPanel[8][8];

    public Board(Window chessWindow)
    {
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());

        JPanel pi = new JPanel(new GridLayout(8, 8));

        for (int row=0; row<8; row++)
        {
            for (int column=0; column<8; column++)
            {
                tab[row][column] = new JPanel();
                if ((row + column)%2==0)
                {
                    tab[row][column].setBackground(Color.WHITE);
                }
                else
                {
                    tab[row][column].setBackground(Color.BLACK);
                }
                pi.add(tab[row][column]);
            }
        }
        jp.add(pi, BorderLayout.CENTER);
        chessWindow.add(jp);
        chessWindow.revalidate();
    }
}
