package org.translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String QUIT = "quit";

    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        runProgram(translator);
    }

    public static void runProgram(Translator translator) {
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        while (true) {
            String countryName = promptForCountry(translator);
            if (QUIT.equals(countryName)) {
                break;
            }
            String countryCode = countryCodeConverter.fromCountry(countryName);
            if (countryCode == null) {
                System.out.println("Invalid country name.");
                continue;
            }

            String languageName = promptForLanguage(translator, countryCode);
            if (QUIT.equals(languageName)) {
                break;
            }
            String languageCode = languageCodeConverter.fromLanguage(languageName);
            if (languageCode == null) {
                System.out.println("Invalid language name.");
                continue;
            }

            String translation = translator.translate(countryCode, languageCode);
            System.out.println(countryName + " in " + languageName + " is " + translation);
            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equals(textTyped)) {
                break;
            }
        }
    }

    private static String promptForCountry(Translator translator) {
        List<String> countryCodes = translator.getCountries();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        List<String> countryNames = new ArrayList<>();
        for (String code : countryCodes) {
            String name = countryCodeConverter.fromCountryCode(code);
            if (name != null) {
                countryNames.add(name);
            }
        }
        countryNames.sort(String::compareTo);
        countryNames.forEach(System.out::println);
        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    private static String promptForLanguage(Translator translator, String countryCode) {
        List<String> languageCodes = translator.getCountryLanguages(countryCode);
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
        List<String> languageNames = new ArrayList<>();
        for (String code : languageCodes) {
            String name = languageCodeConverter.fromLanguageCode(code);
            if (name != null) {
                languageNames.add(name);
            }
        }
        languageNames.sort(String::compareTo);
        languageNames.forEach(System.out::println);
        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}