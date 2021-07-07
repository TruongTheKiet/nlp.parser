package com.research.nlp.grammar.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class State {
    private static int seq = -1;
    private int id;
    private String lhs;
    private RHS rhs;
    private int i, j;
    private List<State> traces = new ArrayList<>();

    public State(List<State> traces, String lhs, RHS rhs, int i, int j, State s) {
        this.id = seq++;
        this.lhs = lhs;
        this.rhs = rhs;
        this.i = i;
        this.j = j;
        if (traces != null && !traces.isEmpty()) {
            this.traces.addAll(traces);
        }
        if (s != null) {
            this.traces.add(s);
        }
    }

    public List<State> getTraces() {
        return traces;
    }

    public int getId() {
        return id;
    }

    public String getLHS() {
        return lhs;
    }

    public RHS getRHS() {
        return rhs;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public String getPriorToDot() {
        return rhs.getPriorToDot();
    }

    public String getAfterDot() {
        return rhs.getAfterDot();
    }

    public boolean isDotLast() {
        return rhs.isDotLast();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return i == state.i &&
                j == state.j &&
                Objects.equals(lhs, state.lhs) &&
                Objects.equals(rhs, state.rhs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lhs, rhs, i, j);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        for (State trace : traces) {
            joiner.add(String.valueOf(trace.getId()));
        }

        return String.format("<<%s>> %s -> %s [%d, %d] {%s}", id, lhs, rhs, i, j, joiner.toString());
    }
}
