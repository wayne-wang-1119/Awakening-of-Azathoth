package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        Map <Character, Integer> res = new TreeMap<>();
        for (String word : words){
            for (int i=0; i<word.length(); i++){
                char letter = word.charAt(i);
                if (res.containsKey(letter)){
                    int count = res.get(letter);
                    res.put(letter, count+1);
                }
                else{
                    res.put(letter, 1);
                }
            }
        }
        return res;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        Map <Character, Integer> compare = this.getFrequencyMap();
        char res = 0;
        int count = 0;
        
        for (char key : compare.keySet()){
            if (!guesses.contains(key)) {
                int cur_count = compare.get(key);
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
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
