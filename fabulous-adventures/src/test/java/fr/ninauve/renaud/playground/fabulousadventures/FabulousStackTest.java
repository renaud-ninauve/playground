package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FabulousStackTest {

    @Nested
    class EmptyStack {
        @Test
        void isEmpty_should_return_true() {
            FabulousStack<String> stack = FabulousStack.newStack();
            assertThat(stack.isEmpty()).isTrue();
            assertThat(stack.stream()).isEmpty();
        }

        @Test
        void pop_should_throw() {
            FabulousStack<String> stack = FabulousStack.newStack();
            assertThatThrownBy(stack::pop);
        }

        @Test
        void peek_should_throw() {
            FabulousStack<String> stack = FabulousStack.newStack();
            assertThatThrownBy(stack::peek);
        }
    }

    @Nested
    class NotEmpty {
        @Test
        void isEmpty_should_return_false() {
            FabulousStack<String> stack = notEmptyStack();
            assertThat(stack.isEmpty()).isFalse();
        }

        private static FabulousStack<String> notEmptyStack() {
            return FabulousStack.<String>newStack()
                    .push("A")
                    .push("B")
                    .push("C");
        }
    }
}