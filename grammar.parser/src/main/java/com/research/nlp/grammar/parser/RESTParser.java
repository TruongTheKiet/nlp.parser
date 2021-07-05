package com.research.nlp.grammar.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping (value = "rest")
public class RESTParser {
    @Autowired
    private ParserService parserService;

    @GetMapping("/parse")
    public String parse() {
        return this.parserService.process();
    }
}
