package com.research.nlp.grammar.parser;

import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class WordDictionaryParser {
    public Map<String, List<String>> getTerminateWord() throws IOException {
        Map<String, List<String>> wordDictionary = new HashMap<>();
        InputStream stream = new ClassPathResource("Penn_english.txt").getInputStream();
        try (Scanner scanner = new Scanner(stream)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (Objects.nonNull(line)) {
                    String[] words = line.split(" \t\t");
                    wordDictionary.put(words[0], getWordTags(words[1]));
                }
            }
        }
        return wordDictionary;
    }

    private List<String> getWordTags(String word) {
        List<String> wordTags = new ArrayList<>();
        if (Objects.nonNull(word)) {
            String[] tags = word.split("#");
            for (String tag : tags) {
                wordTags.add(getTag(tag));
            }
            return wordTags;
        }
        return Collections.emptyList();
    }

    private String getTag(@NonNull String tag) {
        return tag.split("\t")[1].split(" ")[0];
    }
}
