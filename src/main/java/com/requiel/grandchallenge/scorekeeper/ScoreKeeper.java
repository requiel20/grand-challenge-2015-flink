package com.requiel.grandchallenge.scorekeeper;

import java.io.Serializable;
import java.util.List;

public interface ScoreKeeper<T> extends Serializable {

    boolean increase(T element);

    boolean decrease(T element);

    List<T> getPodium();
}
