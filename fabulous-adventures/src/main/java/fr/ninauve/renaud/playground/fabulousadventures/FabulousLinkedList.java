package fr.ninauve.renaud.playground.fabulousadventures;

import java.util.stream.Stream;

public sealed interface FabulousLinkedList<T> {
    static <T> FabulousLinkedList<T> of(T value) {
        return new NotEmpty<>(value, new Empty<>());
    }

    FabulousLinkedList<T> push(T value);
    FabulousLinkedList<T> reverse();
    Stream<T> stream();

    record NotEmpty<T>(T value, FabulousLinkedList<T> tail) implements FabulousLinkedList<T> {
        public FabulousLinkedList<T> push(T value) {
            return new NotEmpty<>(value, this);
        }

        @Override
        public FabulousLinkedList<T> reverse() {
            FabulousLinkedList<T> reversed = new Empty<>();
            FabulousLinkedList<T> current = this;
            while (!(current instanceof FabulousLinkedList.Empty<T>)) {
                if (current instanceof FabulousLinkedList.NotEmpty<T>(
                        T currentValue, FabulousLinkedList<T> currentTail
                )) {
                    reversed = reversed.push(currentValue);
                    current = currentTail;
                }
            }
            return reversed;
        }

        public Stream<T> stream() {
            Stream.Builder<T> stream = Stream.builder();
            FabulousLinkedList<T> current = this;
            while(!(current instanceof FabulousLinkedList.Empty<T>)) {
                if (current instanceof FabulousLinkedList.NotEmpty<T>(T currentValue, FabulousLinkedList<T> currentTail)) {
                    stream.accept(currentValue);
                    current = currentTail;
                }
            }
            return stream.build();
        }
    }

    record Empty<T>() implements FabulousLinkedList<T> {
        public FabulousLinkedList<T> push(T value) {
            return new NotEmpty<>(value, this);
        }
        public Stream<T> stream() {
            return Stream.empty();
        }

        @Override
        public FabulousLinkedList<T> reverse() {
            return this;
        }
    }
}
