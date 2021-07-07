package com.research.nlp.grammar.parser.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GrammarService {
    @Value("${grammar.is-read-default:true}")
    private boolean isReadEmbeddedGrammar;
    @Value("${grammar.grammar-file-path:./Grammar.txt}")
    private String grammarFilePath;

    public Map<String, RHS[]> getGrammar() throws IOException {
        Map<String, List<String[]>> wordDictionary = new HashMap<>();
        InputStream stream = null;
        if(isReadEmbeddedGrammar){
            stream = new ClassPathResource("Grammar.txt").getInputStream();
        }
        else{
            File file = new File(grammarFilePath);
            stream = new FileInputStream(file);
        }
        try (Scanner scanner = new Scanner(stream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (Objects.nonNull(line)) {
                    String[] gramItem = line.split(">");
                    String[] rightPharseGram = gramItem[1].split("\\|");
                    List<String[]> vpGram = new ArrayList<>();
                    for (String vp : rightPharseGram) {
                        vpGram.add(vp.split(" "));
                    }
                    List<String[]> currentGrammar = wordDictionary.get(gramItem[0]);
                    if (Objects.isNull(currentGrammar)) {
                        currentGrammar = vpGram;
                    } else {
                        currentGrammar.addAll(vpGram);
                    }
                    wordDictionary.put(gramItem[0], currentGrammar);
                }
            }
        }
        HashMap<String, RHS[]> rules = new HashMap<>();
        for (Map.Entry<String, List<String[]>> entry : wordDictionary.entrySet()) {
            rules.put(entry.getKey(), getRHS(entry.getValue()));
        }
        return rules;
    }

    private RHS[] getRHS(@NonNull List<String[]> value) {
        RHS[] result = new RHS[value.size()];

        Set<RHS> rhs = value.stream() //
                .map(RHS::new) //
                .collect(Collectors.toSet());
        return rhs.toArray(result);
    }
}
