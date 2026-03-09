import VueController.*;
import javax.swing.*;
import Model.Game;

public class Main
{
    public static void main(String[] args)
    {
        Game game = new Game();
        MainWindow window = new MainWindow(game);
        game.addObserver(window);
    }
}
