package com.requiel.grandchallenge.scorekeeper;

import java.io.Serializable;

public class ElemAndScore<T> implements Serializable {

    public static long serialVersionUID = 1L;

    private T element;
    private int score;

    public ElemAndScore(T element, int score) {
        this.element = element;
        this.score = score;
    }

    public T getElement() {
        return element;
    }

    public void increase() {
        score ++;
    }

    public void decrease() {
        if(score > 0) {
            score--;
        }
    }

    @Override
    public String toString() {
        return element + " score: " + score;
    }

    public int getScore() {
        return score;
    }
}
