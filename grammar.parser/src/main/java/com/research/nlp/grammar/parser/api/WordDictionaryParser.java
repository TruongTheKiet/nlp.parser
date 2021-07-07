package com.research.nlp.grammar.parser.api;

import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordDictionaryParser {
    public Map<String, Set<String>> getTerminateWord() throws IOException {
        Map<String, Set<String>> wordDictionary = new HashMap<>();
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

    public Set<String> getPOS(@NonNull Map<String, Set<String>> dictionary) {
        return dictionary.entrySet().stream() //
                .map(Map.Entry::getValue) //
                .flatMap(Set::stream) //
                .collect(Collectors.toSet());
    }

    private Set<String> getWordTags(String word) {
        Set<String> wordTags = new HashSet<>();
        if (Objects.nonNull(word)) {
            String[] tags = word.split("#");
            for (String tag : tags) {
                wordTags.add(getTag(tag));
            }
            return wordTags;
        }
        return Collections.emptySet();
    }

    private String getTag(@NonNull String tag) {
        return tag.split("\t")[1].split(" ")[0];
    }
}
