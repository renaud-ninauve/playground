package fr.ninauve.renaud.playground.fabulousadventures;

import java.util.stream.Stream;

public sealed interface FabulousDeque<T> extends Streamable<T> {
    static <T> FabulousDeque<T> newDeque() {
        return new Empty<>();
    }

    FabulousDeque<T> pushLeft(T value);

    FabulousDeque<T> pushRight(T value);

    FabulousDeque<T> popLeft();

    FabulousDeque<T> popRight();

    T left();

    T right();

    boolean isEmpty();

    record Empty<T>() implements FabulousDeque<T> {
        @Override
        public FabulousDeque<T> pushLeft(T value) {
            return new OneElement<>(value);
        }

        @Override
        public FabulousDeque<T> pushRight(T value) {
            return new OneElement<>(value);
        }

        @Override
        public FabulousDeque<T> popLeft() {
            throw new UnsupportedOperationException("can't popLeft an empty deque");
        }

        @Override
        public FabulousDeque<T> popRight() {
            throw new UnsupportedOperationException("can't popRight an empty deque");
        }

        @Override
        public T left() {
            throw new UnsupportedOperationException("can't left an empty deque");
        }

        @Override
        public T right() {
            throw new UnsupportedOperationException("can't right an empty deque");
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

    record OneElement<T>(T element) implements FabulousDeque<T> {

        @Override
        public FabulousDeque<T> pushLeft(T value) {
            return new MoreThanOneElement<>(value, FabulousDeque.newDeque(), element);
        }

        @Override
        public FabulousDeque<T> pushRight(T value) {
            return new MoreThanOneElement<>(element, FabulousDeque.newDeque(), value);
        }

        @Override
        public FabulousDeque<T> popLeft() {
            return newDeque();
        }

        @Override
        public FabulousDeque<T> popRight() {
            return newDeque();
        }

        @Override
        public T left() {
            return element;
        }

        @Override
        public T right() {
            return element;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Stream<T> stream() {
            return Stream.of(element);
        }
    }

    record MoreThanOneElement<T>(T left, FabulousDeque<T> middle, T right) implements FabulousDeque<T> {

        @Override
        public FabulousDeque<T> pushLeft(T value) {
            return new MoreThanOneElement<>(value, middle.pushLeft(left), right);
        }

        @Override
        public FabulousDeque<T> pushRight(T value) {
            return new MoreThanOneElement<>(left, middle.pushRight(value), right);
        }

        @Override
        public FabulousDeque<T> popLeft() {
            return middle.isEmpty()
                    ? new OneElement<>(right)
                    : new MoreThanOneElement<>(middle().left(), middle.popLeft(), right);
        }

        @Override
        public FabulousDeque<T> popRight() {
            return middle.isEmpty()
                    ? new OneElement<>(left)
                    : new MoreThanOneElement<>(left, middle.popRight(), middle.right());
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Stream<T> stream() {
            Stream<T> stream = Stream.concat(Stream.of(left), middle.stream());
            return Stream.concat(stream, Stream.of(right));
        }
    }
}
