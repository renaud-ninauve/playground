package fr.ninauve.renaud.playground.fabulousadventures.gameoflife;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Memoizer<A, R> {
    private Map<A, R> memo = new HashMap<>();
    private final Function<A, R> operation;

    public Memoizer(Function<A, R> operation) {
        this.operation = operation;
    }

    public R apply(A value) {
        return memo.computeIfAbsent(value, operation);
    }

    public int size() {
        return memo.size();
    }

    public void clear() {
        memo.clear();
    }

    public void clear(Map<A, R> memo) {
        this.memo = memo;
    }
}
