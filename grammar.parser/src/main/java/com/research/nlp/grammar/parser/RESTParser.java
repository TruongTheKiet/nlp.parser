package com.research.nlp.grammar.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping(value = "rest")
public class RESTParser {
    @Autowired
    private ParserService parserService;

    @GetMapping("/parse")
    public Map<String, List<String>> parse() throws IOException {
        return this.parserService.process();
    }
}
