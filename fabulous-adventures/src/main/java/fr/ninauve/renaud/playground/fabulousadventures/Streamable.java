package fr.ninauve.renaud.playground.fabulousadventures;

import java.util.stream.Stream;

public interface Streamable<T> {
    Stream<T> stream();
}
