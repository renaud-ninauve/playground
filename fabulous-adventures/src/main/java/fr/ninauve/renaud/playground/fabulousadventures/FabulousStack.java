package fr.ninauve.renaud.playground.fabulousadventures;

import java.util.function.Function;
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
    FabulousStack<T> concatenate(FabulousStack<T> other);
    FabulousStack<T> append(T a);

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
        public FabulousStack<T> concatenate(FabulousStack<T> other) {
            return other;
        }
        @Override
        public FabulousStack<T> append(T a) {
            return FabulousStack.<T>newStack().push(a);
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
        public FabulousStack<T> concatenate(FabulousStack<T> other) {
            return this.reverse().reverseOnto(other);
        }

        @Override
        public FabulousStack<T> append(T a) {
            return this.concatenate(FabulousStack.<T>newStack().push(a));
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

    static <T> HughesList<T> newHughes() {
        return new HughesList<>(Function.identity());
    }
    static <T> HughesList<T> newHughes(T item) {
        return new HughesList<>(stack -> stack.push(item));
    }

    record HughesList<T>(Function<FabulousStack<T>, FabulousStack<T>> h) {

        public FabulousStack<T> toStack() {
            return h.apply(newStack());
        }
        private static <T> HughesList<T> fromStack(FabulousStack<T> from) {
            return new HughesList<>(from::concatenate);
        }
        public HughesList<T> push(T value) {
            return concatenate(newHughes(value), this);
        }
        public T peek() {
            return toStack().peek();
        }
        public HughesList<T> pop() {
            return fromStack(toStack().pop());
        }
        public boolean isEmpty() {
            return toStack().isEmpty();
        }
        public static <T> HughesList<T> concatenate(HughesList<T> left, HughesList<T> right) {
            return new HughesList<>(s -> left.h.apply(right.h.apply(s)));
        }
        public HughesList<T> append(T a) {
            return concatenate(this, newHughes(a));
        }
        public Stream<T> stream() {
            return toStack().stream();
        }
    }
}
