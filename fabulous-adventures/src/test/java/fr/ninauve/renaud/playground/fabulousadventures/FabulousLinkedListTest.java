package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FabulousLinkedListTest {

    @Test
    void should_stream() {
        FabulousLinkedList<Integer> list = FabulousLinkedList
                .of(1)
                .push(2)
                .push(3)
                .push(4)
                .push(5);

        assertThat(list.stream())
                .containsExactly(5, 4, 3, 2, 1);
    }
}