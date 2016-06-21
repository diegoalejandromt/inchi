package com.dmoralest;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by dmoralest on 6/21/16.
 */
public class MapperTest {

  @Test
  public void rankTest() throws IOException {

    String dictionaryPath = System.getProperty("dictionary.file");
    String dbPath = System.getProperty("chembl.file");

    File dbFile = Files.gunzipFile(dbPath);

    Dictionary dict = Dictionary.loadDictionary(dictionaryPath);
    Db db = Db.readFromFile(dbFile.getPath());

    long start = System.currentTimeMillis();
    Mapper mapper = Mapper.rankWords(10, dict, db);
    long end = System.currentTimeMillis();

    double ratio = (end - start) / (Math.log(dict.getLines()));

    System.out.println("The ratio is " + ratio);

  }
}
