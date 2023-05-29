package aoa.choosers;

import java.util.*;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;

    //private Map<String, List<String>> ref = new TreeMap<>();
    public EvilChooser(int wordLength, String dictionaryFile) {
        this.wordPool = FileUtils.readWords(dictionaryFile);
        if (wordLength < 1) throw new IllegalArgumentException();
        if (wordLength == Integer.MAX_VALUE) throw new IllegalStateException();
        String temp = "";
        for (int i=0; i<wordLength; i++){
            temp = temp + '-';
        }



        List <String> chosenWords = new ArrayList<>();
        for (String word : this.wordPool){
            if (word.length() == wordLength){
                chosenWords.add(word);
            }
        }
        if (chosenWords.isEmpty()) throw new IllegalStateException();
        this.wordPool = chosenWords;
        this.pattern = temp;

    }

    @Override
    public int makeGuess(char letter) {
        Map<String, List<String>> ref = new TreeMap<>();
        Map<String, List<String>> new_reference = new TreeMap<>();

        for (String w : wordPool){
            if (ref.isEmpty()){
                List <String> curr = new ArrayList<>();
                curr.add(w);
                ref.put(pattern, curr);
            }else {
                List<String> curr = ref.get(pattern);
                curr.add(w);
                ref.put(pattern, curr);
            }
        }

        for (String pat : ref.keySet()) {
            List<String> cur_list = ref.get(pat);
            for (String word : cur_list) {
                String new_pattern = "";
                for (int i = 0; i < word.length(); i++) {
                    char cur_pattern = pat.charAt(i);
                    if (pat.charAt(i) == '-' && word.charAt(i) == letter) {
                        cur_pattern = letter;
                    }
                    new_pattern = new_pattern + cur_pattern;
                }
                if (new_reference.containsKey(new_pattern)) {
                    List<String> temp = new_reference.get(new_pattern);
                    temp.add(word);
                    new_reference.put(new_pattern, temp);
                } else {
                    List<String> temp = new ArrayList<>();
                    temp.add(word);
                    new_reference.put(new_pattern, temp);
                }
            }
        }
        Map<String, List<String>> final_reference = new TreeMap<>();
        int count = 0;
        String new_pattern = "";
        List<String> picked = new ArrayList<>();
        for (String p : new_reference.keySet()) {
            List<String> cur_list = new_reference.get(p);
            if (cur_list.size() > count) {
                new_pattern = p;
                count = cur_list.size();
                picked = cur_list;
            }
        }
        wordPool = picked;

        pattern = new_pattern;

        for (String word : wordPool) {
            if (final_reference.containsKey(pattern)) {
                List<String> temp = final_reference.get(pattern);
                temp.add(word);
                final_reference.put(pattern, temp);
            } else {
                List<String> temp = new ArrayList<>();
                temp.add(word);
                final_reference.put(pattern, temp);
            }
        }
        ref = final_reference;
        int occurances = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == letter) {
                occurances++;
            }
        }
        return occurances;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        int numWords = wordPool.size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        String res = wordPool.get(randomlyChosenWordNumber);
        return res;
    }
}