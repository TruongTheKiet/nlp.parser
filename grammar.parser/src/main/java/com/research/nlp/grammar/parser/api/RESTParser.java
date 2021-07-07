package com.research.nlp.grammar.parser.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping(value = "rest")
public class RESTParser {
    @Autowired
    private ParserService parserService;

    @Autowired
    private GrammarService grammarService;

    @PostMapping("/parse")
    public ResponseDTO parse(@NonNull @RequestBody String inputText) throws IOException {
        return new ResponseDTO(this.parserService.process(inputText));
    }
}
