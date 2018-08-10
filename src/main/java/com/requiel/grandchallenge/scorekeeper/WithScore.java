package com.requiel.grandchallenge.scorekeeper;

public class WithScore<T> implements Comparable<WithScore<T>> {
    private T element;
    private double score;

    public WithScore(T element, double score) {
        this.element = element;
        this.score = score;
    }

    @Override
    public int compareTo(WithScore<T> o) {
        return Double.compare(this.score, o.score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WithScore<?> withScore = (WithScore<?>) o;

        return element != null ? element.equals(withScore.element) : withScore.element == null;
    }

    @Override
    public int hashCode() {
        return element != null ? element.hashCode() : 0;
    }

    public T getElement() {
        return element;
    }

    public double getScore() {
        return score;
    }
}
