package fr.ninauve.renaud.playground.fabulousadventures;

import org.junit.jupiter.api.Test;

import static fr.ninauve.renaud.playground.fabulousadventures.FabulousStackTest.stackOf;
import static fr.ninauve.renaud.playground.fabulousadventures.HughesList.newHughes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class HughesListTest {
    @Test
    void isEmpty_should_return_false() {
        HughesList<String> stack = hughesOf("A");
        assertThat(stack.isEmpty()).isFalse();
    }
    @Test
    void stream_should_list_elements() {
        HughesList<String> stack = hughesOf("A", "B", "C");
        assertThat(stack.stream()).containsExactly("C", "B", "A");
    }

    @Test
    void peek_should_return_last() {
        HughesList<String> stack = hughesOf("A", "B", "C");
        assertThat(stack.peek()).isEqualTo("C");
    }

    @Test
    void pop_should_return_tail() {
        HughesList<String> stack = hughesOf("A", "B", "C");
        assertThat(stack.pop().stream()).containsExactly("B", "A");
    }

    @Test
    void should_concatenate_empty() {
        HughesList<String> stack = hughesOf("A", "B", "C");
        HughesList<String> actual = HughesList.concatenate(stack, newHughes());
        assertThat(actual.toStack()).isEqualTo(stackOf("A", "B", "C"));
    }

    @Test
    void should_concatenate() {
        HughesList<String> stack = hughesOf("A", "B", "C");
        HughesList<String> other = hughesOf("D", "E", "F");
        HughesList<String> actual = HughesList.concatenate(stack, other);
        assertThat(actual.toStack()).isEqualTo(stackOf("D", "E", "F", "A", "B", "C"));
    }

    @Test
    void should_append() {
        HughesList<String> stack = hughesOf("A", "B", "C");
        HughesList<String> actual = stack.append("D");
        assertThat(actual.toStack()).isEqualTo(stackOf("D", "A", "B", "C"));
    }

    private static HughesList<String> hughesOf(String... values) {
        HughesList<String> stack = newHughes();
        for(String value: values) {
            stack = stack.push(value);
        }
        return stack;
    }
}