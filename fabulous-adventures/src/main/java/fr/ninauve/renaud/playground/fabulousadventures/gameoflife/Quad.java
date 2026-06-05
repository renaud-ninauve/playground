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

    default boolean contains(QuadPoint point) {
        if (level() == 0) {
            return point.equals(new QuadPoint(0, 0));
        }
        long w = width() / 2;
        return point.x() >= -w
                && point.x() < w
                && point.y() >= -w
                && point.y() < w;
    }

    default Quad get(QuadPoint point) {
        if (!contains(point)) {
            throw new IllegalArgumentException();
        }
        if (level() == 0) {
            return this;
        }
        if (isEmpty()) {
            return DEAD;
        }
        long w = width() / 4;
        if (level() == 1) {
            return northEast().get(new QuadPoint(0, 0));
        }
        if (point.x() < 0) {
            final long newX = point.x() + w;
            if (point.y() < 0) {
                // SW
                final long newY = point.y() + w;
                return southWest().get(new QuadPoint(newX, newY));
            } else {
                // NW
                final long newY = point.y() - w;
                return northWest().get(new QuadPoint(newX, newY));
            }
        } else {
            final long newX = point.x() - w;
            if (point.y() < 0) {
                // SE
                final long newY = point.y() + w;
                return southEast().get(new QuadPoint(newX, newY));
            } else {
                // NE
                final long newY = point.y() - w;
                return northEast().get(new QuadPoint(newX, newY));
            }
        }
    }

    default Quad set(QuadPoint point, Quad quad) {
        if (!contains(point)) {
            throw new IllegalArgumentException();
        }
        if (quad == DEAD && isEmpty()) {
            return this;
        }
        if (level() == 0) {
            return quad;
        }
        Quad northWest = northWest();
        Quad northEast = northEast();
        Quad southWest = southWest();
        Quad southEast = southEast();
        if (level() == 1) {
            northEast = quad;
        } else {
            long w = width() / 4;
            if (point.x() < 0) {
                final long newX = point.x() + w;
                if (point.y() < 0) {
                    // SW
                    final long newY = point.y() + w;
                    southWest = southWest.set(new QuadPoint(newX, newY), quad);
                } else {
                    // NW
                    final long newY = point.y() - w;
                    northWest = northWest.set(new QuadPoint(newX, newY), quad);
                }
            } else {
                final long newX = point.x() - w;
                if (point.y() < 0) {
                    // SE
                    final long newY = point.y() + w;
                    southEast = southEast.set(new QuadPoint(newX, newY), quad);
                } else {
                    // NE
                    final long newY = point.y() - w;
                    northEast = northEast.set(new QuadPoint(newX, newY), quad);
                }
            }
        }
        return createQuad(northWest, northEast, southWest, southEast);
    }

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
            return this == DEAD;
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
