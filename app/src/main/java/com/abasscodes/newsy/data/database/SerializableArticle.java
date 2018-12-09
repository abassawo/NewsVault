package com.abasscodes.newsy.data.database;

import com.abasscodes.newsy.models.NewsApiResponse;
import com.abasscodes.newsy.models.NytResponse;
import java.util.UUID;

public class SerializableArticle {
  private final String title;
  private String id;
  private String url;
  private String imageUrl;

  public SerializableArticle(String id, String title, String url, String imageUrl) {
    this.id = id;
    this.title = title;
    this.url = url;
    this.imageUrl = imageUrl;
  }

  public String getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  public static SerializableArticle from(NewsApiResponse.Article article) {
    return new SerializableArticle(String.valueOf(UUID.randomUUID()), article.getTitle(), article.getUrl(),
      article.getUrlToImage());
  }

  public static SerializableArticle from(NytResponse.Result result) {
    return new SerializableArticle(String.valueOf(UUID.randomUUID()), result.getTitle(), result.getUrl(),
      result.getBestImagePath());
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public boolean equals(Object obj) {
    SerializableArticle article2 = (SerializableArticle) obj;
    return this.title.equals(article2.title) && this.url.equals(article2.url);
  }

  @Override
  public int hashCode() {
    return title.hashCode() * url.hashCode();
  }
}
