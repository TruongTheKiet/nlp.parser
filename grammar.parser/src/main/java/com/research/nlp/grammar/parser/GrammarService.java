package com.research.nlp.grammar.parser;

import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GrammarService {
    public Map<String, RHS[]> getGrammar() throws IOException {
        Map<String, List<String[]>> wordDictionary = new HashMap<>();
        InputStream stream = new ClassPathResource("Grammar.txt").getInputStream();
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
