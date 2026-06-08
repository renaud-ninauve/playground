package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

import java.util.List;

public sealed interface Quad {

    Quad northWest();

    Quad northEast();

    Quad southWest();

    Quad southEast();

    int level();

    boolean isEmpty();

    default long width() {
        return 1L << level();
    }

    record Leaf(boolean isAlive) implements Quad {

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

    Quad DEAD = new Leaf(false);
    Quad ALIVE = new Leaf(true);

    int MAX_LEVEL = 64;

    record NotLeaf(Quad northWest, Quad northEast, Quad southWest, Quad southEast, int level) implements Quad {

        public NotLeaf {
            if (northWest.level() != northEast.level() ||
                    northEast.level() != southEast.level() ||
                    southEast.level() != southWest.level() ||
                    northWest.level() >= MAX_LEVEL)
                throw new IllegalArgumentException();
        }

        public NotLeaf(Quad northWest, Quad northEast, Quad southWest, Quad southEast) {
            this(northWest, northEast, southWest, southEast, northWest.level() + 1);
        }

        @Override
        public boolean isEmpty() {
            return southWest().isEmpty()
                    && southEast().isEmpty()
                    && northWest().isEmpty()
                    && northEast().isEmpty();
        }
    }

    Memoizer<List<Quad>, Quad> CREATE_QUAD =
            new Memoizer<>(quads -> new NotLeaf(quads.get(0), quads.get(1), quads.get(2), quads.get(3)));

    static Quad createQuad(Quad northWest, Quad northEast, Quad southWest, Quad southEast) {
        return CREATE_QUAD.apply(List.of(northWest, northEast, southWest, southEast));
    }

    Memoizer<Integer, Quad> CREATE_EMPTY =
            new Memoizer<>(Quad::createEmptyQuadNotMemo);

    static Quad createEmptyQuadNotMemo(int level) {
        if (level == 0) {
            return DEAD;
        }
        Quad empty = createEmptyQuadNotMemo(level - 1);
        return createQuad(empty, empty, empty, empty);
    }

    static Quad createEmpty(int level) {
        return CREATE_EMPTY.apply(level);
    }
}
