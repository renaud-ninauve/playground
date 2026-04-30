package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FabulousLinkedListTest {

    @Test
    void should_stream() {
        FabulousLinkedList<Integer> actual = FabulousLinkedList
                .of(1)
                .push(2)
                .push(3)
                .push(4)
                .push(5);

        assertThat(actual.stream())
                .containsExactly(5, 4, 3, 2, 1);
    }

    @Test
    void should_reverse() {
        FabulousLinkedList<Integer> list = FabulousLinkedList
                .of(1)
                .push(2)
                .push(3)
                .push(4)
                .push(5);
        FabulousLinkedList<Integer> actual = list.reverse();

        assertThat(actual.stream())
                .containsExactly(1, 2, 3, 4, 5);
    }
}