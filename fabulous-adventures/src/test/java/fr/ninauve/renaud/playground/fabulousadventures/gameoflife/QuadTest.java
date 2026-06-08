package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static fr.ninauve.renaud.playground.fabulousadventures.gameoflife.Quad.*;
import static fr.ninauve.renaud.playground.fabulousadventures.gameoflife.Quads.*;
import static org.assertj.core.api.Assertions.assertThat;

class QuadTest {

    @Test
    void should_cache_empty() {
        Quad empty1 = createEmpty(3);
        Quad empty2 = createEmpty(3);

        assertThat(empty1.width()).isEqualTo(8);
        assertThat(empty1).isSameAs(empty2);
    }

    @Test
    void should_set_level_0() {
        QuadPoint quadPoint = new QuadPoint(0, 0);
        Quad actual = set(DEAD, quadPoint, ALIVE);
        assertThat(get(actual, quadPoint))
                .isEqualTo(ALIVE);
    }

    @Test
    void should_set_level_1() {
        Quad quad = createEmpty(1);
        QuadPoint quadPoint = new QuadPoint(0, -1);
        Quad actual = set(quad, quadPoint, ALIVE);
        assertThat(get(actual, quadPoint))
                .isEqualTo(ALIVE);
        assertThat(actual.northWest())
                .isEqualTo(DEAD);
        assertThat(actual.northEast())
                .isEqualTo(DEAD);
        assertThat(actual.southWest())
                .isEqualTo(DEAD);
        assertThat(actual.southEast())
                .isEqualTo(ALIVE);
    }

    @Test
    void should_set() {
        String square3 = """
                XXXXXXXX
                X......X
                X......X
                X......X
                X......X
                X......X
                X......X
                XXXXXXXX
                """;
        Quad quad = fromString(square3);
        assertQuad(quad, square3);
    }

    @Test
    void should_embiggen() {
        Quad actual = embiggen(square(3));

        assertThat(actual.level())
                .isEqualTo(4);

        assertQuad(actual, """
                ................
                ................
                ................
                ................
                ....XXXXXXXX....
                ....X......X....
                ....X......X....
                ....X......X....
                ....X......X....
                ....X......X....
                ....X......X....
                ....XXXXXXXX....
                ................
                ................
                ................
                ................
                """);

        assertThat(hasAllEmptyEdges(actual))
                .isTrue();
    }

    @Test
    void should_test() {
        Quad z3 = fromString("""
                XXXXXXXX
                ......X.
                .....X..
                ....X...
                ...X....
                ..X.....
                .X......
                XXXXXXXX
                """);

        assertQuad(z3, """
                XXXXXXXX
                ......X.
                .....X..
                ....X...
                ...X....
                ..X.....
                .X......
                XXXXXXXX
                """);
    }

    @Test
    void should_center() {
        Quad actual = center(cross3());

        assertQuad(actual, """
                X..X
                .XX.
                .XX.
                X..X
                """);
    }

    @Test
    void should_north() {
        Quad actual = north(cross3());

        assertQuad(actual, """
                ....
                ....
                X..X
                .XX.
                """);
    }

    private Quad cross3() {
        return fromString("""
                X......X
                .X....X.
                ..X..X..
                ...XX...
                ...XX...
                ..X..X..
                .X....X.
                X......X
                """);
    }

    private Quad square(int level) {
        Quad quad = createEmpty(level);
        long w = quad.width() / 2;
        List<QuadPoint> alives = LongStream.range(-w, w)
                .boxed()
                .flatMap(i -> Stream.of(
                        new QuadPoint(i, w - 1),
                        new QuadPoint(i, -w),
                        new QuadPoint(-w, i),
                        new QuadPoint(w - 1, i)))
                .toList();

        for (QuadPoint alive : alives) {
            quad = set(quad, alive, ALIVE);
        }
        return quad;
    }

    private Quad fromString(String str) {
        List<String> lines = str.lines().toList().reversed();
        long width = lines.getFirst().length();
        int level = levelFromWidth(width);
        Quad quad = createEmpty(level);
        long w = width / 2;
        for (long y = w-1; y >= -w; y--) {
            String line = lines.get((int) (y + w));
            for (long x = -w; x < w; x++) {
                char c = line.charAt((int) (x + w));
                QuadPoint point = new QuadPoint(x, y);
                if (c == 'X') {
                    quad = set(quad, point, ALIVE);
                } else {
                    quad = set(quad, point, DEAD);
                }
            }
        }
        return quad;
    }

    private int levelFromWidth(long width) {
        long actual = width;
        int level = 0;
        while (actual != 1) {
            level++;
            actual = actual >> 1;
        }
        return level;
    }

    private List<String> print(Quad quad) {
        long w = quad.width() / 2;
        List<String> actualLines = new ArrayList<>();
        for (long y = w-1; y >= -w; y--) {
            StringBuilder sb = new StringBuilder();
            for (long x = -w; x < w; x++) {
                QuadPoint point = new QuadPoint(x, y);
                Quad actualLeaf = get(quad, point);
                if (actualLeaf == ALIVE) {
                    sb.append("X");
                } else {
                    sb.append(".");
                }
            }
            actualLines.add(sb.toString());
        }
        return actualLines;
    }

    private void assertQuad(Quad actual, String expected) {
        List<String> actualLines = print(actual);
        assertThat(actualLines)
                .containsExactlyElementsOf(expected.lines().toList());
    }
}