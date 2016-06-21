package com.dmoralest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

/**
 * Created by dmoralest on 6/20/16.
 */
public class Files {

  public static File downloadToDisk(String url) throws IOException {
    Path path= java.nio.file.Files.createTempFile("inchi", "file");
    System.out.println("Downloading file @ " + url);

    try (FileOutputStream out = new FileOutputStream(path.toFile())) {
      Response resp = Request.Get(url).execute();
      HttpResponse httpResp = resp.returnResponse();

      IOUtils.copy(httpResp.getEntity().getContent(), out);
    }
    return path.toFile();
  }

  public static File gunzipFile(String filePath) throws IOException{

    Path path= java.nio.file.Files.createTempFile("inchi_gunziped", "file");

    try (GZIPInputStream gzis = new GZIPInputStream(new FileInputStream(filePath));
         FileOutputStream out = new FileOutputStream(path.toFile());){
      IOUtils.copy(gzis, out);
    }
    return path.toFile();
  }
}
