package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static fr.ninauve.renaud.playground.fabulousadventures.FabulousStack.newStack;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FabulousStackTest {

    @Nested
    class EmptyStack {
        @Test
        void isEmpty_should_return_true() {
            FabulousStack<String> stack = newStack();
            assertThat(stack.isEmpty()).isTrue();
            assertThat(stack.stream()).isEmpty();
        }

        @Test
        void pop_should_throw() {
            FabulousStack<String> stack = newStack();
            assertThatThrownBy(stack::pop);
        }

        @Test
        void peek_should_throw() {
            FabulousStack<String> stack = newStack();
            assertThatThrownBy(stack::peek);
        }
    }

    @Nested
    class NotEmpty {
        @Test
        void isEmpty_should_return_false() {
            FabulousStack<String> stack = stackOf("A");
            assertThat(stack.isEmpty()).isFalse();
        }
        @Test
        void stream_should_list_elements() {
            FabulousStack<String> stack = stackOf("A", "B", "C");
            assertThat(stack.stream()).containsExactly("C", "B", "A");
        }

        @Test
        void peek_should_return_last() {
            FabulousStack<String> stack = stackOf("A", "B", "C");
            assertThat(stack.peek()).isEqualTo("C");
        }

        @Test
        void pop_should_return_tail() {
            FabulousStack<String> stack = stackOf("A", "B", "C");
            assertThat(stack.pop().stream()).containsExactly("B", "A");
        }

        private static FabulousStack<String> stackOf(String... values) {
            FabulousStack<String> stack = newStack();
            for(String value: values) {
                stack = stack.push(value);
            }
            return stack;
        }
    }
}