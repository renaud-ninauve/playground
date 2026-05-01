package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static fr.ninauve.renaud.playground.fabulousadventures.FabulousQueue.newQueue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FabulousQueueTest {
    @Nested
    class EmptyStack {
        @Test
        void isEmpty_should_return_true() {
            FabulousQueue<String> queue = newQueue();
            assertThat(queue.isEmpty()).isTrue();
            assertThat(queue.stream()).isEmpty();
        }

        @Test
        void dequeue_should_throw() {
            FabulousQueue<String> queue = newQueue();
            assertThatThrownBy(queue::dequeue);
        }

        @Test
        void peek_should_throw() {
            FabulousQueue<String> queue = newQueue();
            assertThatThrownBy(queue::peek);
        }
    }

    @Nested
    class NotEmpty {
        @Test
        void isEmpty_should_return_false() {
            FabulousQueue<String> queue = queueOf("A");
            assertThat(queue.isEmpty()).isFalse();
        }

        @Test
        void stream_should_list_elements() {
            FabulousQueue<String> queue = queueOf("A", "B", "C");
            assertThat(queue.stream()).containsExactly("A", "B", "C");
        }

        @Test
        void peek_should_return_oldest() {
            FabulousQueue<String> queue = queueOf("A", "B", "C");
            assertThat(queue.peek()).isEqualTo("A");
        }

        @Test
        void dequeue_should_return_most_recents() {
            FabulousQueue<String> queue = queueOf("A", "B", "C");
            assertThat(queue.dequeue().stream()).containsExactly("B", "C");
        }

        @Test
        void dequeue_2_times_should_return_most_recents() {
            FabulousQueue<String> queue = queueOf("A", "B", "C");
            assertThat(queue.dequeue().dequeue().stream()).containsExactly("C");
        }

        @Test
        void dequeue_all_return_empty() {
            FabulousQueue<String> queue = queueOf("A", "B", "C");
            FabulousQueue<String> actual = queue.dequeue().dequeue().dequeue();
            assertThat(actual.isEmpty()).isTrue();
        }

        private static FabulousQueue<String> queueOf(String... values) {
            FabulousQueue<String> queue = newQueue();
            for (String value : values) {
                queue = queue.enqueue(value);
            }
            return queue;
        }
    }
}