package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        List <String> compare = new ArrayList();
        for (String word : words){
            if (word.length() == pattern.length()){
                int count = 0;
                for (int i=0; i<word.length(); i++){
                    if (pattern.charAt(i) != '-' && word.charAt(i) == pattern.charAt(i)){
                        count ++;
                    } else if (pattern.charAt(i) == '-') {
                        count ++;

                    }
                    if (count == pattern.length()){compare.add(word);}
                }
            }
        }
        Map <Character, Integer> ref = new TreeMap<>();
        for (String word : compare){
            for (int i=0; i<word.length(); i++){
                char letter = word.charAt(i);
                if (ref.containsKey(letter)){
                    int count = ref.get(letter);
                    ref.put(letter, count+1);
                }
                else{
                    ref.put(letter, 1);
                }
            }
        }
        char res = 0;
        int count = 0;

        for (char key : ref.keySet()){
            if (!guesses.contains(key)) {
                int cur_count = ref.get(key);
                if (cur_count > count) {
                    res = key;
                    count = cur_count;
                }
            }
        }
        if (count > 0){
            return res;
        }
        return '?';
    }

    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}