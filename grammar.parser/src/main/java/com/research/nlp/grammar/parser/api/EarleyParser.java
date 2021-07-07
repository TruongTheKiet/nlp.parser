package com.research.nlp.grammar.parser.api;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class EarleyParser {
    private Grammar grammar;
    private String[] sentence;
    private Chart[] charts;

    public EarleyParser() {
    }

    public EarleyParser(Grammar grammar) throws IOException {
        this.grammar = grammar;
    }

    public Chart[] getCharts() {
        return charts;
    }

    private void predictor(State s) {
        String lhs = s.getAfterDot();
        RHS[] rhs = grammar.getRHS(lhs);
        int j = s.getJ();
        for (int i = 0; i < rhs.length; i++) {
            State ns = new State(null, lhs, rhs[i].addDot(), j, j, null);
            charts[j].addState(ns);
        }
    }

    private void scanner(State s) {
        String lhs = s.getAfterDot();
        RHS[] rhs = grammar.getRHS(lhs);
        int i = s.getI();
        int j = s.getJ();
        if (j >= sentence.length) {
            return;
        }
        Set<String> meanings = grammar.getDictionaryTerminal().get(sentence[j]);
        if (meanings.contains(lhs)) {
            String[] word = {sentence[j]};
            RHS rhs1 = new RHS(word);
            State ns = new State(null, lhs, rhs1.addDotLast(), j, j + 1, null);
            charts[j + 1].addState(ns);
        }
    }

    private void completer(State s) {
        String lhs = s.getLHS();
        for (int a = 0; a < charts[s.getI()].size(); a++) {
            State st = charts[s.getI()].getState(a);
            String after = st.getAfterDot();
            if (after != null && lhs.compareTo(after) == 0) {
                State ns = new State(st.getTraces(), st.getLHS(), st.getRHS().moveDot(), st.getI(), s.getJ(), s);
                charts[s.getJ()].addState(ns);
            }
        }
    }

    public boolean parseSentence(String[] words) {
        boolean isValidWord = Stream.of(words).allMatch(word -> grammar.getDictionaryTerminal().containsKey(word));
        if(!isValidWord) {
            throw new RuntimeException("Input string exist character invalid");
        }
        sentence = words;
        charts = new Chart[sentence.length + 1];
        for (int i = 0; i < charts.length; i++) charts[i] = new Chart();
        String[] start1 = {"@", "S"};
        RHS startRHS = new RHS(start1);
        State start = new State(null, "$", startRHS, 0, 0, null);
        charts[0].addState(start);
        for (int i = 0; i < charts.length; i++) {
            for (int j = 0; j < charts[i].size(); j++) {
                State st = charts[i].getState(j);
                String next_term = st.getAfterDot();
                if (st.isDotLast()) /*State's RHS = ... @*/
                    completer(st);
                else if (grammar.isPartOfSpeech(next_term)) /*RHS = ... @ A ..., where A is a part of speech.*/
                    scanner(st);
                else
                    predictor(st); /*A is NOT a part of speech.*/
            }
        }
        /*Determine whether a successful parse.*/
        String[] fin = {"S", "@"};
        RHS finRHS = new RHS(fin);
        State finish = new State(null, "$", finRHS, 0, sentence.length, null);
        State last = charts[sentence.length].getState(charts[sentence.length].size() - 1);
        return finish.equals(last);
    }
}