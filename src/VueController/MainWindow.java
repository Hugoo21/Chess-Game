package VueController;

import javax.swing.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        super("titre de l'application");

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };
        addWindowListener(l);
        setSize(800,800);
        new Board(this);
        setVisible(true);
    }
}