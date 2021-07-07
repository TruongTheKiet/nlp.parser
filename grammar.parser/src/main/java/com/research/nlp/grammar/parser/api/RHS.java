package com.research.nlp.grammar.parser.api;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class RHS {
    private String[] terms;
    private boolean hasDot = false;
    private int dot = -1;
    private final static String DOT = "@";

    public RHS(String[] t) {
        terms = t;
        for (int i = 0; i < terms.length; i++) {
            if (terms[i].compareTo(DOT) == 0) {
                dot = i;
                hasDot = true;
                break;
            }
        }
    }

    public String[] getTerms() {
        return terms;
    }

    public String getPriorToDot() {
        if (hasDot && dot > 0) return terms[dot - 1];
        return "";
    }

    public String getAfterDot() {
        if (hasDot && dot < terms.length - 1) return terms[dot + 1];
        return "";
    }

    public boolean hasDot() {
        return hasDot;
    }

    public boolean isDotLast() {
        if (hasDot) return (dot == terms.length - 1);
        return false;
    }

    public boolean isDotFirst() {
        if (hasDot) return (dot == 0);
        return false;
    }

    public RHS addDot() {
        String[] t = new String[terms.length + 1];
        t[0] = DOT;
        for (int i = 1; i < t.length; i++) t[i] = terms[i - 1];
        return new RHS(t);
    }

    public RHS addDotLast() {
        String[] t = new String[terms.length + 1];
        for (int i = 0; i < t.length - 1; i++) t[i] = terms[i];
        t[t.length - 1] = DOT;
        return new RHS(t);
    }

    public RHS moveDot() {
        String[] t = new String[terms.length];
        for (int i = 0; i < t.length; i++) {
            if (terms[i].compareTo(DOT) == 0) {
                t[i] = terms[i + 1];
                t[i + 1] = DOT;
                i++;
            } else t[i] = terms[i];
        }
        return new RHS(t);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RHS rhs = (RHS) o;
        return hasDot == rhs.hasDot && dot == rhs.dot && Arrays.equals(terms, rhs.terms);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(hasDot, dot);
        result = 31 * result + Arrays.hashCode(terms);
        return result;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ");
        for (String term : terms) {
            joiner.add(term);
        }
        return joiner.toString();
    }

}
