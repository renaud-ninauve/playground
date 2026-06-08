package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static fr.ninauve.renaud.playground.fabulousadventures.gameoflife.Quad.*;
import static fr.ninauve.renaud.playground.fabulousadventures.gameoflife.Quads.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        Quad quad = createEmpty(4);
        List<QuadPoint> alives = IntStream.range(-4, 4)
                .boxed()
                .flatMap(i -> Stream.of(
                        new QuadPoint(i, 3),
                        new QuadPoint(i, -4),
                        new QuadPoint(-4, i),
                        new QuadPoint(3, i)))
                .toList();

        for (QuadPoint alive : alives) {
            quad = set(quad, alive, ALIVE);
        }

        List<String> actual = print(quad);

        assertThat(actual).containsExactlyElementsOf("""
                0000000000000000
                0000000000000000
                0000000000000000
                0000000000000000
                0000111111110000
                0000100000010000
                0000100000010000
                0000100000010000
                0000100000010000
                0000100000010000
                0000100000010000
                0000111111110000
                0000000000000000
                0000000000000000
                0000000000000000
                0000000000000000
                """.lines().toList());
    }

    @Test
    void should_embiggen() {
        Quad actual = createEmpty(3);
        List<QuadPoint> alives = IntStream.range(-4, 4)
                .boxed()
                .flatMap(i -> Stream.of(
                        new QuadPoint(i, 3),
                        new QuadPoint(i, -4),
                        new QuadPoint(-4, i),
                        new QuadPoint(3, i)))
                .toList();

        for (QuadPoint alive : alives) {
            actual = set(actual, alive, ALIVE);
        }

        actual = embiggen(actual);

        assertThat(actual.level())
                .isEqualTo(4);

        List<String> actualLines = print(actual);

        assertThat(actualLines).containsExactlyElementsOf("""
                0000000000000000
                0000000000000000
                0000000000000000
                0000000000000000
                0000111111110000
                0000100000010000
                0000100000010000
                0000100000010000
                0000100000010000
                0000100000010000
                0000100000010000
                0000111111110000
                0000000000000000
                0000000000000000
                0000000000000000
                0000000000000000
                """.lines().toList());

        assertThat(hasAllEmptyEdges(actual))
                .isTrue();
    }

    @Test
    void should_center() {
        Quad quad = createEmpty(3);
        List<QuadPoint> alives = IntStream.range(-4, 4)
                .boxed()
                .flatMap(i -> Stream.of(
                        new QuadPoint(i, 3),
                        new QuadPoint(i, -4),
                        new QuadPoint(-4, i),
                        new QuadPoint(3, i)))
                .toList();

        for (QuadPoint alive : alives) {
            quad = set(quad, alive, ALIVE);
        }

        quad = embiggen(quad);

        Quad actual = center(quad);

        List<String> actualLines = print(actual);

        assertThat(actualLines).containsExactlyElementsOf("""
                11111111
                10000001
                10000001
                10000001
                10000001
                10000001
                10000001
                11111111
                """.lines().toList());
    }

    private List<String> print(Quad quad) {
        long w = quad.width() / 2;
        List<String> actualLines = new ArrayList<>();
        for (long x = -w; x < w; x++) {
            StringBuilder sb = new StringBuilder();
            for (long y = -w; y < w; y++) {
                QuadPoint point = new QuadPoint(x, y);
                Quad actualLeaf = get(quad, point);
                if (actualLeaf == ALIVE) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            actualLines.add(sb.toString());
        }
        return actualLines;
    }
}