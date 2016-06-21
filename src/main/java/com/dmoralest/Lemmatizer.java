package com.dmoralest;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
/**
 * Created by dmoralest on 6/21/16.
 */
public class Lemmatizer {

  protected StanfordCoreNLP pipeline;

  public Lemmatizer() {
    Properties props;
    props = new Properties();
    props.put("annotators", "tokenize, ssplit, pos, lemma");

    this.pipeline = new StanfordCoreNLP(props);
  }

  public List<String> lemmatize(String text)
  {
    List<String> lemmas = new ArrayList<>();

    Annotation document = new Annotation(text);

    this.pipeline.annotate(document);

    List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
    for(CoreMap sentence: sentences) {
      for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {
        lemmas.add(token.get(CoreAnnotations.LemmaAnnotation.class));
      }
    }
    return lemmas;
  }
}
