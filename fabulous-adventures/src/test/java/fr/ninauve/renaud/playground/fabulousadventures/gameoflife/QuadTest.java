package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

import org.junit.jupiter.api.Test;

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
        QuadPoint quadPoint = new QuadPoint(5, -6);
        Quad actual = quad.set(quadPoint, ALIVE);
        assertThat(actual.get(quadPoint))
                .isEqualTo(ALIVE);
        assertThat(actual.get(new QuadPoint(1, 1)))
                .isEqualTo(DEAD);

        Quad level3 = actual.southEast();
        assertThat(level3.level()).isEqualTo(3);
        assertThat(level3.get(new QuadPoint(1, -2)))
                .isEqualTo(ALIVE);

        Quad level2 = level3.southEast();
        assertThat(level2.level()).isEqualTo(2);
        assertThat(level2.get(new QuadPoint(-1, 0)))
                .isEqualTo(ALIVE);

        Quad level1 = level2.northWest();
        assertThat(level1.level()).isEqualTo(1);
        assertThat(level1.get(new QuadPoint(0, -1)))
                .isEqualTo(ALIVE);

        Quad level0 = level1.southEast();
        assertThat(level0.level()).isEqualTo(0);
        assertThat(level0.get(new QuadPoint(0, 0)))
                .isEqualTo(ALIVE);
    }
}