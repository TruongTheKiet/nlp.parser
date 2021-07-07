package com.research.nlp.grammar.parser.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ParserService {
    Logger logger = LoggerFactory.getLogger(ParserService.class);

    @Autowired
    private EarleyParser earleyParser;

    @Autowired
    private Grammar grammar;

    public String process(@NonNull String inputString) throws IOException {
        inputString = inputString.trim();
        String[] words = inputString.split(" ");
        EarleyParser parser = new EarleyParser(grammar);
        return internalProcess(words, parser);
    }

    private String internalProcess(String[] words, EarleyParser parser) {
        boolean successful = parser.parseSentence(words);
        Chart[] charts = parser.getCharts();
        for (int i = 0; i < charts.length; i++) {
            logger.info("Chart " + i + ":");
            logger.info((charts[i].toString()));
        }

        List<State> allStates = new ArrayList<>();
        for (int i = 0; i < charts.length; i++) {
            allStates.addAll(charts[i].getAllStates());
        }

        List<State> roots = charts[charts.length - 1].getAllStates().stream() //
                .filter(state -> "$".equals(state.getLHS())) //
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(roots)){
            throw  new RuntimeException("Input string invalid grammar");
        }
        List<String> res = new ArrayList<>();
        for (State root : roots) {
            retrieveTree(root, res);
        }
        return String.join("", res);
    }

    private void retrieveTree(State root, List<String> result) {
        for (String term : root.getRHS().getTerms()) {
            if (term.compareTo("@") == 0) {
                continue;
            }
            boolean closed = true;
            if (root.getTraces().isEmpty()) {
                closed = false;
                result.add(" " + term + " ");
            } else {
                result.add("(" + term);
            }
            for (State trace : root.getTraces()) {
                if (trace.getLHS().equals(term)) {
                    retrieveTree(trace, result);
                }
            }
            if (closed) {
                result.add(")");
            }
        }
    }
}
