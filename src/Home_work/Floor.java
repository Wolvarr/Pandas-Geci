package Home_work;

import java.util.ArrayList;

public class Floor {

    private ArrayList<Tile> tiles;
    private int pandaCount;

    public Floor()
    {
        tiles = new ArrayList<Tile>();
        pandaCount=0;
    }

    public void addTile(Tile t)
    {
        tiles.add(t);
    }

    public void changePandaCount( int i)
    {
        pandaCount += i;
    }

}
