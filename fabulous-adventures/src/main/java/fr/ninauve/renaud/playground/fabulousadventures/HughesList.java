package fr.ninauve.renaud.playground.fabulousadventures;

import java.util.function.Function;
import java.util.stream.Stream;

public record HughesList<T>(Function<FabulousStack<T>, FabulousStack<T>> h) {

    static <T> HughesList<T> newHughes() {
        return new HughesList<>(Function.identity());
    }
    static <T> HughesList<T> newHughes(T item) {
        return new HughesList<>(stack -> stack.push(item));
    }

    public FabulousStack<T> toStack() {
        return h.apply(FabulousStack.newStack());
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
