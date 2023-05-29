package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Collections;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {

        if (wordLength < 1) throw new IllegalArgumentException();
        if (wordLength == Integer.MAX_VALUE) throw new IllegalStateException();
        String temp = "";
        for (int i=0; i<wordLength; i++){
            temp = temp + '-';
        }
        pattern = temp;
        String chosenWord1 = "";

        List<String> words = FileUtils.readWords(dictionaryFile);

        List <String> chosenWords = new ArrayList<>();
        for (String word : words){
            if (word.length() == wordLength){
                chosenWords.add(word);
            }
        }
        if (chosenWords.isEmpty()) throw new IllegalStateException();
        Collections.sort(chosenWords);
        int randomlyChosenWordNumber = StdRandom.uniform(chosenWords.size());
        chosenWord1 = chosenWords.get(randomlyChosenWordNumber);
        chosenWord = chosenWord1;

    }

    @Override
    public int makeGuess(char letter) {
        int res = 0;
        for (int i=0; i<chosenWord.length(); i++){
            char cur = chosenWord.charAt(i);
            if (cur == letter){
                res ++;
                char [] update = pattern.toCharArray();
                update[i] = letter;
                pattern=String.valueOf(update);
            }
        }
        return res;
    }

    @Override
    public String getPattern() {

        return pattern;
    }

    @Override
    public String getWord() {
        String res = chosenWord;
        return res;
    }
}
