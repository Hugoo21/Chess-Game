package Model;

import java.util.Observable;

public class Game extends Observable
{

    private int[] selectedCell = null;

    public void leftClick(int row, int col) {
        if (selectedCell != null && selectedCell[0] == row && selectedCell[1] == col)
        {
            selectedCell = null;
        }
        else
        {
            selectedCell = new int[]{row, col};
        }
        synchronized (this)
        {
            setChanged();
            notifyObservers(selectedCell);
        }
    }
}

