package com.dmoralest;


import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dmoralest on 6/20/16.
 */
public class Db {

  public static class Entry {
    private String chemblId;
    private String inchiKey;

    private Entry(String chemblId, String inchiKey) {
      this.chemblId = chemblId;
      this.inchiKey = inchiKey;
    }

    public String getChemblId() {
      return this.chemblId;
    }

    public String getInchiKey() {
      return this.inchiKey;
    }
  }

  private Map<String, List<Db.Entry>> stems = new HashMap<>();

  private Db() {
  }

  public Map<String, List<Db.Entry>> getStems() {
    return this.stems;
  }

  public static Db readFromFile(String filePath) throws IOException {
    Db db = new Db();
    Lemmatizer lemmatizer = new Lemmatizer();

    try (FileReader reader = new FileReader(filePath);
         BufferedReader bufferedReader = new BufferedReader(reader, 15000);) {

      String inputLine;
      int line = 0;

      while ((inputLine = bufferedReader.readLine()) != null) {
        line++;

        if (line == 1) continue;

        String[] args = inputLine.split("\\s+");

        if (args.length != 4) {
          System.out.println(String.format("Error at line %d. Invalid number of columns. Skipping ...", line));
          continue;
        }
        Db.Entry entry = new Db.Entry(args[0], args[3]);

        String[] codes = entry.getInchiKey().split("-");

        for (String code : codes) {

          List<String> lemmas = lemmatizer.lemmatize(code);

          for (String lemma : lemmas) {
            if (db.stems.get(lemma) == null) {
              List<Entry> entries = new ArrayList<>();
              db.stems.put(lemma, entries);
            } else {
              db.stems.get(lemma).add(entry);
            }
          }
        }
      }
    }
    return db;
  }

}
