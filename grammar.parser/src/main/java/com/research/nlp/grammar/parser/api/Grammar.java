package com.research.nlp.grammar.parser.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class Grammar {
    @Autowired
    private GrammarService grammarService;

    @Autowired
    private WordDictionaryParser wordDictionaryParser;

    private Map<String, RHS[]> Rules = new HashMap<>();
    private Set<String> POS = new HashSet<>();
    private Map<String, Set<String>> dictionaryTerminal = new HashMap<>();

    @PostConstruct
    public void initData() throws IOException {
        this.Rules = grammarService.getGrammar();
        this.dictionaryTerminal = wordDictionaryParser.getTerminateWord();
        this.POS = wordDictionaryParser.getPOS(this.dictionaryTerminal);
    }


    public RHS[] getRHS(String lhs) {
        RHS[] rhs = null;
        if (Rules.containsKey(lhs)) {
            rhs = Rules.get(lhs);
        }
        return rhs;
    }

    public boolean isPartOfSpeech(String s) {
        return POS.contains(s);
    }

    public Map<String, RHS[]> getRules() {
        return Rules;
    }

    public void setRules(Map<String, RHS[]> rules) {
        Rules = rules;
    }

    public Set<String> getPOS() {
        return POS;
    }

    public void setPOS(Set<String> POS) {
        this.POS = POS;
    }

    public Map<String, Set<String>> getDictionaryTerminal() {
        return dictionaryTerminal;
    }

    public void setDictionaryTerminal(Map<String, Set<String>> dictionaryTerminal) {
        this.dictionaryTerminal = dictionaryTerminal;
    }
}
