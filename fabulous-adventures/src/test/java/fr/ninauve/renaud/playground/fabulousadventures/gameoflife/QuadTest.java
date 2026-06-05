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
    void should_set() {
        Quad quad = createEmpty(4);
        QuadPoint quadPoint = new QuadPoint(5, -6);
        quad.set(quadPoint, ALIVE);
        assertThat(quad.get(quadPoint))
                .isEqualTo(ALIVE);
        assertThat(quad.get(new QuadPoint(1, 1)))
                .isEqualTo(DEAD);

        Quad level3 = quad.southWest();
        assertThat(level3.level()).isEqualTo(3);
        assertThat(level3.get(new QuadPoint(1, -2)))
                .isEqualTo(ALIVE);

        Quad level2 = level3.southWest();
        assertThat(level2.level()).isEqualTo(2);
        assertThat(level3.get(new QuadPoint(-1, 0)))
                .isEqualTo(ALIVE);

        Quad level1 = level2.northEast();
        assertThat(level1.level()).isEqualTo(1);
        assertThat(level1.get(new QuadPoint(0, 0)))
                .isEqualTo(ALIVE);

        Quad level0 = level1.northEast();
        assertThat(level0.level()).isEqualTo(0);
        assertThat(level0.get(new QuadPoint(0, 0)))
                .isEqualTo(ALIVE);
    }
}