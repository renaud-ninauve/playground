package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static fr.ninauve.renaud.playground.fabulousadventures.gameoflife.Quad.*;
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
        Quad actual = DEAD.set(quadPoint, ALIVE);
        assertThat(actual.get(quadPoint))
                .isEqualTo(ALIVE);
    }

    @Test
    void should_set_level_1() {
        Quad quad = createEmpty(1);
        QuadPoint quadPoint = new QuadPoint(0, -1);
        Quad actual = quad.set(quadPoint, ALIVE);
        assertThat(actual.get(quadPoint))
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
            quad = quad.set(alive, ALIVE);
        }

        List<String> actual = new ArrayList<>();
        for (int x = -8; x < 8; x++) {
            StringBuilder sb = new StringBuilder();
            for (int y = -8; y < 8; y++) {
                QuadPoint point = new QuadPoint(x, y);
                Quad actualLeaf = quad.get(point);
                if (actualLeaf == ALIVE) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            actual.add(sb.toString());
        }

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
            quad = quad.set(alive, ALIVE);
        }

        Quad actual = quad.embiggen();

        assertThat(actual.level())
                .isEqualTo(4);
        List<String> actualLines = new ArrayList<>();
        for (int x = -8; x < 8; x++) {
            StringBuilder sb = new StringBuilder();
            for (int y = -8; y < 8; y++) {
                QuadPoint point = new QuadPoint(x, y);
                Quad actualLeaf = actual.get(point);
                if (actualLeaf == ALIVE) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
            }
            actualLines.add(sb.toString());
        }

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
    }
}