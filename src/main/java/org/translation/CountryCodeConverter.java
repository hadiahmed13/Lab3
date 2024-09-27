package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryCodeConverter {

    private final Map<String, String> countryNameToCodeMap = new HashMap<>();
    private final Map<String, String> countryCodeToNameMap = new HashMap<>();

    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    public CountryCodeConverter(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] split = line.split("\t");
                if (split.length >= 3) {
                    String countryName = split[0];
                    String countryCode = split[2]; // Alpha-3 code
                    countryNameToCodeMap.put(countryName, countryCode);
                    countryCodeToNameMap.put(countryCode, countryName);
                }
            }
        } catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String fromCountryCode(String code) {
        return countryCodeToNameMap.get(code.toUpperCase());
    }

    public String fromCountry(String country) {
        return countryNameToCodeMap.get(country);
    }

    public int getNumCountries() {
        return countryNameToCodeMap.size();
    }
}
