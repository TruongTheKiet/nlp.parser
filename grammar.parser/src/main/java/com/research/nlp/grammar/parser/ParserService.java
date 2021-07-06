package com.research.nlp.grammar.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class ParserService {
    @Autowired
    private WordDictionaryParser wordDictionaryParser;

    public Map<String, List<String>> process() throws IOException {
        return wordDictionaryParser.getTerminateWord();
    }
}
