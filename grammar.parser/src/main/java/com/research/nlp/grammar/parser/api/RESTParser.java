package com.research.nlp.grammar.parser.api;

import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.io.IOException;

@RestController()
@RequestMapping(value = "rest")
public class RESTParser {
    @Autowired
    private ParserService parserService;

    @Autowired
    private GrammarService grammarService;

    @PostMapping("/parse")
    public ResponseDTO parse(@NotNull @RequestBody String inputText) throws IOException {
        return new ResponseDTO(this.parserService.process(inputText));
    }
}
