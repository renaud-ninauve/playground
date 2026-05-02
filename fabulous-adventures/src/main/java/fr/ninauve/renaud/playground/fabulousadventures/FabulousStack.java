package fr.ninauve.renaud.playground.fabulousadventures;

import java.util.stream.Stream;

public sealed interface FabulousStack<T> extends Streamable<T> {
    static <T> FabulousStack<T> newStack() {
        return new Empty<>();
    }

    FabulousStack<T> push(T value);
    T peek();
    FabulousStack<T> pop();
    boolean isEmpty();
    FabulousStack<T> reverse();
    FabulousStack<T> reverseOnto(FabulousStack<T> ontoTail);

    record Empty<T>() implements FabulousStack<T> {
        @Override
        public FabulousStack<T> push(T value) {
            return new NotEmpty<>(value, this);
        }
        @Override
        public T peek() {
            throw new UnsupportedOperationException("can't peek an empty stack");
        }
        @Override
        public FabulousStack<T> pop() {
            throw new UnsupportedOperationException("can't pop an empty stack");
        }
        @Override
        public boolean isEmpty() {
            return true;
        }
        @Override
        public FabulousStack<T> reverse() {
            return this;
        }
        @Override
        public FabulousStack<T> reverseOnto(FabulousStack<T> ontoTail) {
            return ontoTail;
        }

        @Override
        public Stream<T> stream() {
            return Stream.empty();
        }
    }

    record NotEmpty<T>(T value, FabulousStack<T> tail) implements FabulousStack<T> {

        @Override
        public FabulousStack<T> push(T value) {
            return new NotEmpty<>(value, this);
        }
        @Override
        public T peek() {
            return value;
        }
        @Override
        public FabulousStack<T> pop() {
            return tail;
        }
        @Override
        public boolean isEmpty() {
            return false;
        }
        @Override
        public FabulousStack<T> reverse() {
            return reverseOnto(FabulousStack.newStack());
        }

        @Override
        public FabulousStack<T> reverseOnto(FabulousStack<T> ontoTail) {
            FabulousStack<T> result = ontoTail;
            FabulousStack<T> current = this;
            for(; !current.isEmpty(); current = current.pop()) {
                result = result.push(current.peek());
            }
            return result;
        }

        @Override
        public Stream<T> stream() {
            Stream.Builder<T> stream = Stream.builder();
            FabulousStack<T> current = this;
            while(!(current instanceof FabulousStack.Empty<T>)) {
                if (current instanceof FabulousStack.NotEmpty<T>(T currentValue, FabulousStack<T> currentTail)) {
                    stream.accept(currentValue);
                    current = currentTail;
                }
            }
            return stream.build();
        }
    }
}
