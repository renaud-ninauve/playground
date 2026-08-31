package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

import static fr.ninauve.renaud.playground.fabulousadventures.gameoflife.Quad.*;
import static fr.ninauve.renaud.playground.fabulousadventures.gameoflife.Quads.*;

public class HashLife {
    private Quad cells = createEmpty(9);
    private int stepCallCount = 0;

    public void clear() {
        cells = createEmpty(9);
    }

    public boolean isAlive(long x, long y) {
        QuadPoint point = new QuadPoint(x, y);
        return contains(cells, point)
                && get(cells, point) != DEAD;
    }

    public void setAlive(long x, long y, boolean isAlive) {
        QuadPoint point = new QuadPoint(x, y);
        while(!contains(cells, point)) {
            cells = embiggen(cells);
        }
        cells = set(cells, point, isAlive ? ALIVE : DEAD);
    }
}
