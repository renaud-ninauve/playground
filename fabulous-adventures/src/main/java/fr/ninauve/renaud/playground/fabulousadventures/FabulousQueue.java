package fr.ninauve.renaud.playground.fabulousadventures;

import java.util.stream.Stream;

public sealed interface FabulousQueue<T> extends Streamable<T> {
    static <T> FabulousQueue<T> newQueue() {
        return new Empty<>();
    }

    FabulousQueue<T> enqueue(T value);
    T peek();
    FabulousQueue<T> dequeue();
    boolean isEmpty();

    record Empty<T>() implements FabulousQueue<T> {
        @Override
        public FabulousQueue<T> enqueue(T value) {
            return new NotEmpty<>(FabulousStack.<T>newStack(), FabulousStack.<T>newStack().push(value));
        }
        @Override
        public T peek() {
            throw new UnsupportedOperationException("can't peek an empty queue");
        }
        @Override
        public FabulousQueue<T> dequeue() {
            throw new UnsupportedOperationException("can't dequeue an empty queue");
        }
        @Override
        public boolean isEmpty() {
            return true;
        }
        @Override
        public Stream<T> stream() {
            return Stream.empty();
        }
    }

    record NotEmpty<T>(FabulousStack<T> enqueues, FabulousStack<T> dequeues) implements FabulousQueue<T> {
        @Override
        public FabulousQueue<T> enqueue(T value) {
            return new NotEmpty<>(enqueues.push(value), dequeues);
        }
        @Override
        public T peek() {
            return dequeues().peek();
        }
        @Override
        public FabulousQueue<T> dequeue() {
            FabulousStack<T> newDequeues = dequeues.pop();
            if (newDequeues.isEmpty() && enqueues().isEmpty()) {
                return newQueue();
            } else if (newDequeues.isEmpty()) {
                return new NotEmpty<>(FabulousStack.newStack(), enqueues.reverse());
            }
            return new NotEmpty<>(enqueues, newDequeues);
        }
        @Override
        public boolean isEmpty() {
            return false;
        }
        @Override
        public Stream<T> stream() {
            return Stream.concat(
                    dequeues.stream(),
                    enqueues.reverse().stream()
            );
        }
    }
}
