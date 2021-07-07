package com.research.nlp.grammar.parser.api;

import java.util.List;
import java.util.Vector;

public class Chart {
    Vector<State> chart;

    public Chart() {
        chart = new Vector<State>();
    }

    public void addState(State s) {
        if (!chart.contains(s)) {
            chart.add(s);
        }
    }

    public List<State> getAllStates() {
        return this.chart;
    }

    public State getState(int i) {
        if (i < 0 || i >= chart.size()) return null;
        return (State) chart.get(i);
    }

    public int size() {
        return chart.size();
    }

    @Override
    public String toString() {
        String res = "";
        for (State state : chart) {
            res += state.toString() + "\n";
        }

        return res;
    }
}
