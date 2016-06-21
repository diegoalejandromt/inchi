package com.dmoralest;

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
  private int lines;

  private Db() {
  }

  public Map<String, List<Db.Entry>> getStems() {
    return this.stems;
  }

  public int getLines() {
    return this.lines;
  }

  public static Db readFromFile(String filePath) throws IOException {
    Db db = new Db();

    try (FileReader reader = new FileReader(filePath);
         BufferedReader bufferedReader = new BufferedReader(reader, 15000);) {

      String inputLine;
      int line = 0;

      while ((inputLine = bufferedReader.readLine()) != null) {
        line++;

        if (line == 1) continue;
        db.lines++;

        String[] args = inputLine.split("\\s+");

        if (args.length != 4) {
          System.out.println(String.format("Error at line %d. Invalid number of columns. Skipping ...", line));
          continue;
        }
        Db.Entry entry = new Db.Entry(args[0], args[3]);

        String[] codes = entry.getInchiKey().split("-");

        for (String code : codes) {

          List<String> lemmas= new ArrayList<>();

          for (int i = 0; i < code.length()-3; i++) {
            lemmas.add(code.substring(i, Math.min(i+3, code.length())));
          }

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
