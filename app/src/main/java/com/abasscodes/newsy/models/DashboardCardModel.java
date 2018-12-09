package com.abasscodes.newsy.models;

import java.util.List;

@SuppressWarnings("ALL")
public class DashboardCardModel<T> {

  private String source;
  private List<T> objects;

  public static DashboardCardModel<NytResponse.Result> ofNyt(List<NytResponse.Result> results) {
    return new DashboardCardModel<>("Nyt", results);
  }

  public static DashboardCardModel<NewsApiResponse.Article> ofWsj(List<NewsApiResponse.Article> articles) {
    return new DashboardCardModel<>("Wsj", articles);
  }

  public DashboardCardModel(String source, List<T> results) {
    this.source = source;
    this.objects = results;
  }

  @Override
  public String toString() {
    return source + " " + objects.size();
  }

  public List<T> getObjects() {
    return objects;
  }

  public String getType() {
    return source;
  }
}
