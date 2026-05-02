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

        @Test
        void reverse_should_return_empty() {
            FabulousStack<String> stack = newStack();
            assertThat(stack.reverse().isEmpty()).isTrue();
        }

        @Test
        void reverseOnto_should_return_ontoTail() {
            FabulousStack<String> stack = newStack();
            FabulousStack<String> ontoTail = NotEmpty.stackOf("A", "B", "C");
            FabulousStack<String> actual = stack.reverseOnto(ontoTail);
            assertThat(actual).isEqualTo(ontoTail);
        }

        @Test
        void concatenate_should_return_other() {
            FabulousStack<String> stack = newStack();
            FabulousStack<String> other = NotEmpty.stackOf("A", "B", "C");
            FabulousStack<String> actual = stack.concatenate(other);
            assertThat(actual).isEqualTo(other);
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

        @Test
        void should_reverse() {
            FabulousStack<String> stack = stackOf("A", "B", "C");
            FabulousStack<String> actual = stack.reverse();

            assertThat(actual.stream())
                    .containsExactly("A", "B", "C");
        }

        @Test
        void should_reverseOnto() {
            FabulousStack<String> stack = stackOf("A", "B", "C");
            FabulousStack<String> ontoTail = NotEmpty.stackOf("D", "E", "F");
            FabulousStack<String> actual = stack.reverseOnto(ontoTail);
            assertThat(actual).isEqualTo(stackOf("D", "E", "F", "C", "B", "A"));
        }

        @Test
        void should_concatenate() {
            FabulousStack<String> stack = stackOf("A", "B", "C");
            FabulousStack<String> other = NotEmpty.stackOf("D", "E", "F");
            FabulousStack<String> actual = stack.concatenate(other);
            assertThat(actual).isEqualTo(stackOf("D", "E", "F", "A", "B", "C"));
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