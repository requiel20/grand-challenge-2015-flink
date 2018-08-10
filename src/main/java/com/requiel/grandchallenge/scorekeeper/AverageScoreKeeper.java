package com.requiel.grandchallenge.scorekeeper;

import java.util.HashMap;
import java.util.TreeSet;

public class AverageScoreKeeper<T> implements java.io.Serializable {

    protected int podiumSize;

    /**
     * Keeps track how many elements of each we have.
     */
    protected HashMap<T, Integer> counts = new HashMap<>();

    protected TreeSet<WithScore<T>> scores = new TreeSet<>();

    public AverageScoreKeeper(int podiumSize) {
        this.podiumSize = podiumSize;
    }

    public boolean add(T element, double score) {
        return false;
    }

    public boolean remove(T element, double score) {
        return false;
    }

}
