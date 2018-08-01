package com.requiel.grandchallenge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreKeeper<T> implements Serializable {

    public static long serialVersionUID = 1L;

    private int podiumSize;

    /**
     * Keeps track at which index in the array every element is
     */
    private HashMap<T, Integer> positions = new HashMap<>();

    /**
     * Has all the elements in ascending order of score
     */
    private ArrayList<ElemAndScore<T>> inOrder = new ArrayList<>();

    public ScoreKeeper(int podiumSize) {
        this.podiumSize = podiumSize;
    }

    public boolean increase(T element) {
        if (positions.containsKey(element)) {
            int oldPosition = positions.get(element);

            ElemAndScore elemAndScore = inOrder.get(oldPosition);
            elemAndScore.increase();

            int newPosition = oldPosition - 1;

            while (newPosition >= 0 && elemAndScore.getScore() > inOrder.get(newPosition).getScore()) {
                positions.put(inOrder.get(newPosition).getElement(), newPosition + 1);
                newPosition--;
            }

            newPosition++;

            if (oldPosition != newPosition) {
                move(oldPosition, newPosition);
                return true;
            } else {
                return false;
            }

        } else {
            inOrder.add(new ElemAndScore<T>(element, 1));
            positions.put(element, inOrder.size() - 1);
            if (inOrder.size() <= podiumSize) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean decrease(T element) {
        if (positions.containsKey(element)) {
            int oldPosition = positions.get(element);

            ElemAndScore elemAndScore = inOrder.get(oldPosition);
            elemAndScore.decrease();

            if (elemAndScore.getScore() <= 0) {
                positions.remove(element);
                inOrder.remove(oldPosition);
                if(inOrder.size() <= podiumSize) {
                    return true;
                } else {
                    return false;
                }
            } else {

                int newPosition = oldPosition + 1;

                while (newPosition < inOrder.size() && elemAndScore.getScore() < inOrder.get(newPosition).getScore()) {
                    positions.put(inOrder.get(newPosition).getElement(), newPosition + 1);
                    newPosition++;
                }

                newPosition--;

                if (oldPosition != newPosition) {

                    move(oldPosition, newPosition);
                    return true;
                } else {
                    return false;
                }
            }

        } else {
            return false;
        }
    }

    private void move(int oldPosition, int newPosition) {
        ElemAndScore<T> elemAndScore = inOrder.remove(oldPosition);
        inOrder.add(newPosition, elemAndScore);
    }

    public List<T> getPodium() {
        ArrayList<T> out = new ArrayList<>();
        for (int i = 0; i < podiumSize; i++) {
            if(i < inOrder.size()) {
                out.add(inOrder.get(i).getElement());
            } else {
                out.add(null);
            }
        }
        return out;
    }
}
