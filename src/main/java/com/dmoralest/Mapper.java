package com.dmoralest;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by dmoralest on 6/20/16.
 */
public class Mapper {


  public static class Word implements Comparable<Mapper.Word> {
    private String inchiKey;
    private String word;
    private String chemblId;

    public Word(String chemblId, String inchiKey, String word) {
      this.chemblId = chemblId;
      this.inchiKey = inchiKey;
      this.word = word;
    }

    public String getChemblId() {
      return this.chemblId;
    }

    public String getWord() {
      return this.word;
    }

    public String getInchiKey() {
      return this.inchiKey;
    }

    public int compareTo(Mapper.Word o) {
      return -Integer.valueOf(this.word.length()).compareTo(o.word.length());
    }

    public String toString() {
      return String.format("%s, %s, %s", inchiKey, word, chemblId);
    }
  }

  private TreeSet<Mapper.Word> wordRank = new TreeSet<Mapper.Word>();
  private int n;
  private Dictionary dict;
  private Db db;

  public TreeSet<Mapper.Word> getWordRank() {
    return this.wordRank;
  }

  public int getN() {
    return this.n;
  }

  private Mapper() {
  }

  public static Mapper rankWords(int n, Dictionary dict, Db db) {
    List<Integer> lengths = dict.getDescendingSizes();
    Mapper mapper= new Mapper();
    mapper.n= n;

    for (int length : lengths) {

      Optional<Set<Dictionary.DictionaryWord>> wordsOpt = dict.getWordsByLength(length);

      if (wordsOpt.isPresent()) {
        Set<Dictionary.DictionaryWord> words = wordsOpt.get();
        System.out.println("Processing words with length " + length);

        for (Dictionary.DictionaryWord word : words) {

          for (String root : word.getStems()) {
            List<Db.Entry> entries = db.getStems().get(root);

            if (entries==null) {
              continue;
            }

            for (Db.Entry entry : entries) {
              if (entry.getInchiKey().contains(word.getWord())) {

                mapper.wordRank.add(new Mapper.Word(entry.getChemblId(), entry.getChemblId(), word.getWord()));

                if (mapper.wordRank.size() > n) {
                  Mapper.Word last = mapper.wordRank.pollLast();
                  return mapper;
                }
              }
            }
          }
        }
      }
    }
    return mapper;
  }
}
