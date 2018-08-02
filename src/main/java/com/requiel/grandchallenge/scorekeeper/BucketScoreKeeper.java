package com.requiel.grandchallenge.scorekeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BucketScoreKeeper<T> implements ScoreKeeper<T> {

    public static long serialVersionUID = 1L;

    private int podiumSize;

    /**
     * Keeps track at which bucket in the array every element is.
     */
    private HashMap<T, Integer> positions = new HashMap<>();

    /**
     * Has buckets of elements in descending order of score. Bucket at index 0
     * keeps element of score 1.
     */
    private ArrayList<Set<T>> inOrder = new ArrayList<>();

    public BucketScoreKeeper(int podiumSize) {
        this.podiumSize = podiumSize;
    }

    @Override
    public boolean increase(T element) {
        if (positions.containsKey(element)) {
            int newBucket = moveToNextBucket(element);

            if (lastBucketOnPodium() > newBucket) {
                return false;
            } else {
                return true;
            }
        } else {
            //new element
            addNewElement(element);

            if (lastBucketOnPodium() > 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    @Override
    public boolean decrease(T element) {
        if (positions.containsKey(element)) {
            int newBucket = moveToPreviousBucket(element);

            if (lastBucketOnPodium() > newBucket + 1) {
                return false;
            } else {
                return true;
            }
        } else {
            //unknown element
            return false;
        }
    }

    /**
     * @return the new bucket index
     */
    private int moveToNextBucket(T element) {
        int oldBucket = positions.get(element);

        positions.put(element, oldBucket + 1);

        inOrder.get(oldBucket).remove(element);

        if (inOrder.size() <= oldBucket + 1) {
            addBucketsUntil(oldBucket + 1);
        }

        inOrder.get(oldBucket + 1).add(element);
        return oldBucket + 1;
    }

    private int lastBucketOnPodium() {
        int elementsOnPodium = 0;
        int lastBucketOnPodium = inOrder.size() - 1;

        for (int i = inOrder.size() - 1; i >= 0; i--) {
            lastBucketOnPodium = i;
            elementsOnPodium += inOrder.get(i).size();

            if (elementsOnPodium >= podiumSize) {
                break;
            }
        }
        return lastBucketOnPodium;
    }

    private void addNewElement(T element) {
        positions.put(element, 0);

        //first element
        if (inOrder.isEmpty()) {
            inOrder.add(new HashSet<>());
        }

        inOrder.get(0).add(element);
    }

    private void addBucketsUntil(int untilIndex) {
        for (int i = inOrder.size(); i < untilIndex + 1; i++) {
            inOrder.add(new HashSet<>());
        }
    }

    /**
     * @return the index of the new bucket
     */
    private int moveToPreviousBucket(T element) {
        int oldBucket = positions.get(element);

        if (oldBucket == 0) {
            //remove element
            positions.remove(element);
            inOrder.get(0).remove(element);
        } else {
            //move to previous bucket
            positions.put(element, oldBucket - 1);

            inOrder.get(oldBucket).remove(element);

            inOrder.get(oldBucket - 1).add(element);
        }
        return oldBucket - 1;
    }

    @Override
    public List<T> getPodium() {
        ArrayList<T> podium = new ArrayList<>();
        int bucketToAdd = inOrder.size() - 1;

        while (podium.size() < podiumSize) {
            if (bucketToAdd >= 0) {
                if (podiumSize - podium.size() >= inOrder.get(bucketToAdd).size()) {
                    //if all the bucket fits add all
                    podium.addAll(inOrder.get(bucketToAdd));
                } else {
                    //if just a part of the bucket fits, put that part
                    Iterator<T> iterator = inOrder.get(bucketToAdd).iterator();

                    while (podium.size() < podiumSize && iterator.hasNext()) {
                        podium.add(iterator.next());
                    }
                }
                bucketToAdd--;
            } else {
                podium.add(null);
            }
        }

        return podium;
    }
}
