package com.dmoralest;


import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Hello world!
 */
public class App {

  private static final String DICTIONARY_URL= "https://raw.githubusercontent.com/jonbcard/scrabble-bot/master/src/dictionary.txt";
  private static final String CHEMBL_URL= "http://ftp.ebi.ac.uk/pub/databases/chembl/ChEMBLdb/latest/chembl_21_chemreps.txt.gz";

  public static void main(String[] args) {

    int n= 10;

    if (args.length==1) {
      try {
        n= Integer.parseInt(args[0]);
      } catch (NumberFormatException ex) {
        System.out.println(String.format("Invalid argument %s. Must be an integer.", args[0]));
      }
    }

    Optional<String> dictionaryPathOpt= Optional.ofNullable(System.getProperty("dictionary.file"));
    Optional<String> dbPathOpt= Optional.ofNullable(System.getProperty("chembl.file"));

    try {
      String dictionaryPath;

      if (dictionaryPathOpt.isPresent()) {
        dictionaryPath= dictionaryPathOpt.get();
      } else {
        dictionaryPath= Files.downloadToDisk(DICTIONARY_URL).getPath();
      }

      String dbGzippedFilePath;

      if (dbPathOpt.isPresent()){
        dbGzippedFilePath= dbPathOpt.get();
      } else {
        dbGzippedFilePath=Files.downloadToDisk(CHEMBL_URL).getPath();
      }

      System.out.println("Gunzipping file @ "+ dbGzippedFilePath);
      File dbFile = Files.gunzipFile(dbGzippedFilePath);
      System.out.println("Gunzipping file completed");

      Dictionary dict = Dictionary.loadDictionary(dictionaryPath);
      Db db= Db.readFromFile(dbFile.getPath());

      Mapper mapper= Mapper.rankWords(n, dict, db);

      mapper.getWordRank().iterator().forEachRemaining(word -> System.out.println(word));

    } catch (IOException ioe) {
      System.out.println("Something went wrong with IO");
      ioe.printStackTrace();
    }

  }

}
