package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static fr.ninauve.renaud.playground.fabulousadventures.FabulousDeque.newDeque;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FabulousDequeTest {

    @Nested
    class EmptyDeque {
        @Test
        void isEmpty_should_return_true() {
            FabulousDeque<String> deque = newDeque();
            assertThat(deque.isEmpty()).isTrue();
            assertThat(deque.stream()).isEmpty();
        }

        @Test
        void popLeft_should_throw() {
            FabulousDeque<String> deque = newDeque();
            assertThatThrownBy(deque::popLeft);
        }

        @Test
        void poRight_should_throw() {
            FabulousDeque<String> deque = newDeque();
            assertThatThrownBy(deque::popRight);
        }

        @Test
        void left_should_throw() {
            FabulousDeque<String> deque = newDeque();
            assertThatThrownBy(deque::left);
        }

        @Test
        void right_should_throw() {
            FabulousDeque<String> deque = newDeque();
            assertThatThrownBy(deque::right);
        }
    }

    @Nested
    class OneElement {
        @Test
        void isEmpty_should_return_false() {
            FabulousDeque<String> deque = dequeOfOneElement("A");
            assertThat(deque.isEmpty()).isFalse();
        }

        @Test
        void popLeft_should_return_empty() {
            FabulousDeque<String> deque = dequeOfOneElement("A");
            assertThat(deque.popLeft().isEmpty()).isTrue();
        }

        @Test
        void popRight_should_return_empty() {
            FabulousDeque<String> deque = dequeOfOneElement("A");
            assertThat(deque.popRight().isEmpty()).isTrue();
        }

        @Test
        void left_should_return_element() {
            FabulousDeque<String> deque = dequeOfOneElement("A");
            assertThat(deque.left()).isEqualTo("A");
        }

        @Test
        void right_should_return_element() {
            FabulousDeque<String> deque = dequeOfOneElement("A");
            assertThat(deque.right()).isEqualTo("A");
        }

        @Test
        void stream_should_return_element() {
            FabulousDeque<String> deque = dequeOfOneElement("A");
            assertThat(deque.stream()).containsExactly("A");
        }
    }

    @Nested
    class MoreThanOneElement {
        @Test
        void isEmpty_should_return_false() {
            FabulousDeque<String> deque = dequeOfOneElement("A")
                    .pushLeft("B");
            assertThat(deque.isEmpty()).isFalse();
        }

        @Test
        void popLeft_when_left_has_one_element() {
            FabulousDeque<String> deque = dequeOfOneElement("A")
                    .pushLeft("B");
            assertThat(deque.popLeft().stream()).containsExactly("A");
        }

        @Test
        void popLeft_when_left_has_no_element() {
            FabulousDeque<String> deque = dequeOfOneElement("A")
                    .pushRight("B");
            assertThat(deque.popLeft().stream()).containsExactly("B");
        }

        @Test
        void popRight_when_right_has_one_element() {
            FabulousDeque<String> deque = dequeOfOneElement("A")
                    .pushRight("B");
            assertThat(deque.popRight().stream()).containsExactly("A");
        }

        @Test
        void popRight_when_right_has_no_element() {
            FabulousDeque<String> deque = dequeOfOneElement("A")
                    .pushLeft("B");
            assertThat(deque.popRight().stream()).containsExactly("B");
        }

        @Test
        void left_when_left_has_one_element() {
            FabulousDeque<String> deque = dequeOfOneElement("A")
                    .pushLeft("B");
            assertThat(deque.left()).isEqualTo("B");
        }

        @Test
        void left_when_left_has_no_elements() {
            FabulousDeque<String> deque = dequeOfOneElement("A")
                    .pushRight("B");
            assertThat(deque.left()).isEqualTo("A");
        }

        @Test
        void stream_should_return_elements() {
            FabulousDeque<String> deque = dequeOfOneElement("MIDDLE-A")
                    .pushLeft("LEFT-A")
                    .pushRight("RIGHT-A")
                    .pushLeft("LEFT-B")
                    .pushRight("RIGHT-B");
            assertThat(deque.stream()).containsExactly("LEFT-B", "LEFT-A", "RIGHT-B", "RIGHT-A", "MIDDLE-A");
        }
    }

    public static FabulousDeque<String> dequeOfOneElement(String value) {
        return FabulousDeque.<String>newDeque().pushLeft(value);
    }
}