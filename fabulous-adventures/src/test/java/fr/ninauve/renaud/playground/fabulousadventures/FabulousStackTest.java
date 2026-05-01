package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FabulousStackTest {

    @Test
    void should_be_empty_when_new() {
        FabulousStack<String> stack = FabulousStack.newStack();
        assertThat(stack.isEmpty()).isTrue();
        assertThat(stack.stream()).isEmpty();
    }

    @Test
    void should_throw_when_pop_empty_stack() {
        FabulousStack<String> stack = FabulousStack.newStack();
        assertThatThrownBy(stack::pop);
    }

    @Test
    void should_throw_when_peek_empty_stack() {
        FabulousStack<String> stack = FabulousStack.newStack();
        assertThatThrownBy(stack::peek);
    }
}