package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FabulousStackTest {

    @Test
    void should_be_empty_when_new() {
        FabulousStack<String> stack = FabulousStack.newStack();
        assertThat(stack.isEmpty()).isTrue();
        assertThat(stack.stream()).isEmpty();
    }
}