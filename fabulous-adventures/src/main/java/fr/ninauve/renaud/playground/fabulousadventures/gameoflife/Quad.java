package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

public sealed interface Quad {

    Quad northWest();
    Quad northEast();
    Quad southWest();
    Quad southEast();

    int level();
    boolean isEmpty();

    record Leaf() implements Quad {

        @Override
        public Quad northWest() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Quad northEast() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Quad southWest() {
            throw new UnsupportedOperationException();
        }
        @Override
        public Quad southEast() {
            throw new UnsupportedOperationException();
        }
        @Override
        public int level() {
            return 0;
        }
        @Override
        public boolean isEmpty() {
            return this == DEAD;
        }
    }

    Quad DEAD = new Leaf();
    Quad ALIVE = new Leaf();
}
