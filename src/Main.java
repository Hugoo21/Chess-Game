import VueController.MainWindow;
import Model.Game;
import javax.swing.SwingUtilities;

public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> {
            Game leJeu = new Game();
            new MainWindow(leJeu);
        });
    }
}