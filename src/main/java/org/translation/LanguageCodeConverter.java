package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageCodeConverter {

    private final Map<String, String> codeToLanguageMap = new HashMap<>();
    private final Map<String, String> languageToCodeMap = new HashMap<>();

    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    public LanguageCodeConverter(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] split = line.split("\t");
                if (split.length >= 2) {
                    String languageName = split[0];
                    String languageCode = split[1];
                    codeToLanguageMap.put(languageCode, languageName);
                    languageToCodeMap.put(languageName, languageCode);
                }
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String fromLanguageCode(String code) {
        return codeToLanguageMap.get(code);
    }

    public String fromLanguage(String language) {
        return languageToCodeMap.get(language);
    }

    public int getNumLanguages() {
        return codeToLanguageMap.size();
    }
}
