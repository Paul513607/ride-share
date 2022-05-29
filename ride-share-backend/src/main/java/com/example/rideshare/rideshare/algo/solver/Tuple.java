package com.example.rideshare.rideshare.algo.solver;

import java.util.Comparator;

public class Tuple<F,S extends Comparable<S>> implements Comparable<Tuple<F, S>> {
    private F first;
    private S second;

    public Tuple(F first, S second){
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    @Override
    public int compareTo(Tuple<F, S> o) {
        return this.second.compareTo(o.second);
    }
}
