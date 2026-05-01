package fr.ninauve.renaud.playground.fabulousadventures;

import java.util.stream.Stream;

public sealed interface FabulousStack<T> extends Streamable<T> {
    static <T> FabulousStack<T> newStack() {
        return new Empty<>();
    }

    FabulousStack<T> push(T value);
    T peek();
    T pop();
    boolean isEmpty();

    record Empty<T>() implements FabulousStack<T> {
        @Override
        public FabulousStack<T> push(T value) {
            return null;
        }
        @Override
        public T peek() {
            return null;
        }
        @Override
        public T pop() {
            return null;
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
}
