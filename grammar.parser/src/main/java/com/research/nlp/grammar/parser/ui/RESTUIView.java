package com.research.nlp.grammar.parser.ui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.concurrent.ExecutionException;

@Controller
public class RESTUIView {
    @Value("${server.port:7080}")
    private String port;
    @Value("${server.host:http://localhost}")
    private String host;

    @GetMapping("/")
    public String index(Model model) throws ExecutionException, InterruptedException {
        model.addAttribute("host", host + ":"+port);
        return "index";
    }
}
