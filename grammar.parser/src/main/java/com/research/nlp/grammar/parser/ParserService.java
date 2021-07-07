package com.research.nlp.grammar.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParserService {
    @Autowired
    private EarleyParser earleyParser;

    @Autowired
    private Grammar grammar;
    String tree = "";

    public String process() throws IOException {
        String[] sentence1 = {"John", "called", "Mary"};
        String[] sentence2 = {"John", "called", "Mary", "from", "Denver"};
        EarleyParser parser = new EarleyParser(grammar);
//        test(sentence1, parser);
        test(sentence2, parser);
        return tree;
    }


    void test(String[] sent, EarleyParser parser) {
        StringBuffer out = new StringBuffer();
        for (int i = 0; i < sent.length - 1; i++) out.append(sent[i] + " ");
        out.append(sent[sent.length - 1] + ".");
        String sentence = out.toString();
        System.out.println("\nSentence: \"" + sentence + "\"");
        boolean successful = parser.parseSentence(sent);
        System.out.println("Parse Successful:" + successful);
        Chart[] charts = parser.getCharts();
        System.out.println("");
        System.out.println("Charts produced by the sentence\"" + sentence + "\"");
        for (int i = 0; i < charts.length; i++) {
            System.out.println("Chart " + i + ":");
            System.out.println(charts[i]);
        }

        List<State> allStates = new ArrayList<>();
        for (int i = 0; i < charts.length; i++) {
            allStates.addAll(charts[i].getAllStates());
        }

        List<State> roots = charts[charts.length - 1].getAllStates().stream()
                .filter(state -> "S".equals(state.getLHS())).collect(Collectors.toList());
        // for each tree root, print its child node recursively LNR
        for (State root : roots) {
            printTree(root);
        }

    }

    private void printTree(State root) {
        for (String term : root.getRHS().getTerms()) {
            if (term.compareTo("@") == 0) {
                continue;
            }
            boolean closed = true;
            if (root.getTraces().isEmpty()) {
                closed = false;
                tree += " " + term + " ";
            } else {
                System.out.print(" (" + term);
            }
            for (State trace : root.getTraces()) {
                if (trace.getLHS().equals(term)) {
                    printTree(trace);
                }
            }
            if (closed) {
                tree +=" ) ";
            }
        }
    }
}
