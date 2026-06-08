package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static fr.ninauve.renaud.playground.fabulousadventures.gameoflife.Quad.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Quads {

    public static boolean contains(Quad quad, QuadPoint point) {
        if (quad.level() == 0) {
            return point.equals(new QuadPoint(0, 0));
        }
        long w = quad.width() / 2;
        return point.x() >= -w
                && point.x() < w
                && point.y() >= -w
                && point.y() < w;
    }

    public static Quad get(Quad quad, QuadPoint point) {
        if (!contains(quad, point)) {
            throw new IllegalArgumentException();
        }
        if (quad.level() == 0) {
            return quad;
        }
        if (quad.isEmpty()) {
            return DEAD;
        }
        long w = quad.width() / 4;
        if (point.x() < 0) {
            final long newX = point.x() + w;
            if (point.y() < 0) {
                // SW
                final long newY = point.y() + w;
                QuadPoint newPoint = quad.level() == 1 ? new QuadPoint(0, 0) : new QuadPoint(newX, newY);
                return get(quad.southWest(), newPoint);
            } else {
                // NW
                final long newY = point.y() - w;
                QuadPoint newPoint = quad.level() == 1 ? new QuadPoint(0, 0) : new QuadPoint(newX, newY);
                return get(quad.northWest(), newPoint);
            }
        } else {
            final long newX = point.x() - w;
            if (point.y() < 0) {
                // SE
                final long newY = point.y() + w;
                QuadPoint newPoint = quad.level() == 1 ? new QuadPoint(0, 0) : new QuadPoint(newX, newY);
                return get(quad.southEast(), newPoint);
            } else {
                // NE
                final long newY = point.y() - w;
                QuadPoint newPoint = quad.level() == 1 ? new QuadPoint(0, 0) : new QuadPoint(newX, newY);
                return get(quad.northEast(), newPoint);
            }
        }
    }

    public static Quad set(Quad quad, QuadPoint point, Quad newQuad) {
        if (!contains(quad, point)) {
            throw new IllegalArgumentException();
        }
        if (newQuad == DEAD && quad.isEmpty()) {
            return quad;
        }
        if (quad.level() == 0) {
            return newQuad;
        }
        Quad northWest = quad.northWest();
        Quad northEast = quad.northEast();
        Quad southWest = quad.southWest();
        Quad southEast = quad.southEast();
        long w = quad.width() / 4;
        if (point.x() < 0) {
            final long newX = point.x() + w;
            if (point.y() < 0) {
                // SW
                final long newY = point.y() + w;
                QuadPoint newPoint = quad.level() == 1 ? new QuadPoint(0, 0) : new QuadPoint(newX, newY);
                southWest = set(quad.southWest(), newPoint, newQuad);
            } else {
                // NW
                final long newY = point.y() - w;
                QuadPoint newPoint = quad.level() == 1 ? new QuadPoint(0, 0) : new QuadPoint(newX, newY);
                northWest = set(quad.northWest(), newPoint, newQuad);
            }
        } else {
            final long newX = point.x() - w;
            if (point.y() < 0) {
                // SE
                final long newY = point.y() + w;
                QuadPoint newPoint = quad.level() == 1 ? new QuadPoint(0, 0) : new QuadPoint(newX, newY);
                southEast = set(quad.southEast(), newPoint, newQuad);
            } else {
                // NE
                final long newY = point.y() - w;
                QuadPoint newPoint = quad.level() == 1 ? new QuadPoint(0, 0) : new QuadPoint(newX, newY);
                northEast = set(quad.northEast(), newPoint, newQuad);
            }
        }
        return createQuad(northWest, northEast, southWest, southEast);
    }

    public static Quad embiggen(Quad quad) {
        if (quad.level() == 0) {
            throw new IllegalArgumentException();
        }
        if (quad.level() >= MAX_LEVEL) {
            return quad;
        }
        Quad empty = createEmpty(quad.level() - 1);
        Quad nw = createQuad(empty, empty, empty, quad.northWest());
        Quad ne = createQuad(empty, empty, quad.northEast(), empty);
        Quad sw = createQuad(empty, quad.southWest(), empty, empty);
        Quad se = createQuad(quad.southEast(), empty, empty, empty);
        return createQuad(nw, ne, sw, se);
    }

    public static boolean hasAllEmptyEdges(Quad quad) {
        return quad.northWest().northWest().isEmpty()
                && quad.northWest().northEast().isEmpty()
                && quad.northEast().northWest().isEmpty()
                && quad.northEast().northEast().isEmpty()
                && quad.northEast().southEast().isEmpty()
                && quad.southEast().northEast().isEmpty()
                && quad.southEast().southEast().isEmpty()
                && quad.southEast().southWest().isEmpty()
                && quad.southWest().southEast().isEmpty()
                && quad.southWest().southWest().isEmpty()
                && quad.southWest().northWest().isEmpty()
                && quad.northWest().southWest().isEmpty();
    }

    public static Quad center(Quad quad) {
        return createQuad(
                quad.northWest().southEast(),
                quad.northEast().southWest(),
                quad.southWest().northEast(),
                quad.southEast().northWest());
    }

    public static Quad north(Quad quad) {
        return createQuad(
                quad.northWest().northEast(),
                quad.northEast().northWest(),
                quad.northWest().southEast(),
                quad.northEast().southWest());
    }

    public static Quad south(Quad quad) {
        return createQuad(
                quad.southWest().northEast(),
                quad.southEast().northWest(),
                quad.southWest().southEast(),
                quad.southEast().southWest());
    }

    public static Quad east(Quad quad) {
        return null;
    }

    public static Quad west(Quad quad) {
        return null;
    }
}
