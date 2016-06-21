package com.dmoralest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by dmoralest on 6/20/16.
 */
public class Dictionary {

  public static class DictionaryWord {
    private String word;
    private List<String> stems;

    public DictionaryWord(String word, List<String> stems) {
      this.word = word;
      this.stems = stems;
    }

    public String getWord() {
      return this.word;
    }

    public List<String> getStems() {
      return this.stems;
    }
  }

  private Map<Integer, Set<DictionaryWord>> dictWords;
  private int lines= 0;

  private Dictionary() {
  }

  public int getLines() {
    return this.lines;
  }

  public List<Integer> getDescendingSizes() {
    List<Integer> sizes = new ArrayList<Integer>(dictWords.keySet());
    Collections.sort(sizes, Collections.<Integer>reverseOrder());
    return sizes;
  }

  public Optional<Set<DictionaryWord>> getWordsByLength(int n) {
    return Optional.ofNullable(dictWords.get(n));
  }

  public static Dictionary loadDictionary(String filePath) throws IOException {

    Map<Integer, Set<DictionaryWord>> lines = new HashMap<Integer, Set<DictionaryWord>>();
    Dictionary dict = new Dictionary();

    try (FileReader reader = new FileReader(filePath);
         BufferedReader bufferedReader = new BufferedReader(reader);) {

      String inputLine;

      while ((inputLine = bufferedReader.readLine()) != null) {
        dict.lines++;
        int length = inputLine.length();

        Set<DictionaryWord> words = lines.get(length);

        if (words == null) {
          words= new HashSet<DictionaryWord>();
          lines.put(length, words);
        }

        List<String> lemmas= new ArrayList<>();
        lemmas.add(inputLine.substring(0, Math.min(3, inputLine.length())));
        words.add(new DictionaryWord(inputLine, lemmas));
      }
    }

    dict.dictWords = lines;

    return dict;
  }
}
