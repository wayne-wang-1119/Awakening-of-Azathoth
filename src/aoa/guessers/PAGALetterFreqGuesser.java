package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.*;

import java.util.HashSet;

import java.util.TreeMap;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        int n = pattern.length();

        List<String> wordbank = new ArrayList<>(); //store all words that satisfy conditions for -

        for (String word : words){
            if(word.length() == n){
                int check = 0; //initialized checker
                for (int i=0; i<pattern.length(); i++){
                    char cur = pattern.charAt(i);
                    if (cur == word.charAt(i)){ // if it is a match, we easily add by one and update seen
                        check ++;
                    } else if (cur == '-' && (!guesses.contains(word.charAt(i)))) {
                        check++; //if not and we are at -, we can replace by the char in the word, if it is not guessed yet
                    }
                }
                if (check == n){wordbank.add(word);}
                }
        }
            System.out.println(wordbank);

            Map <Character, Integer> ref = new TreeMap<>();
            for (String w : wordbank){
                for (int i=0; i<w.length(); i++){
                    char cur = w.charAt(i);
                    if (!guesses.contains(cur)){
                        if (ref.containsKey(cur)){
                            int count = ref.get(cur);
                            ref.put(cur, count+1);
                        }else {
                            ref.put(cur, 1);
                        }
                    }
                }
            }

            char res = 0;
            int count = 0;
            for (char c : ref.keySet()){
                int cur = ref.get(c);
                if (cur>count){
                    res = c;
                    count = cur;
                }
            }
            if (count > 0){return res;}
        return '?';
        }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
