package com.abasscodes.newsy.screens.newssources.newsapi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.abasscodes.newsy.models.NewsApiResponse;
import java.util.ArrayList;
import java.util.List;

public class NewsApiArticleAdapter extends RecyclerView.Adapter<WsjViewHolder> {
  private final List<NewsApiResponse.Article> articles = new ArrayList<>();

  @Override
  public WsjViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    View view = LayoutInflater.from(context)
      .inflate(WsjViewHolder.Companion.getLayoutId(), parent, false);
    return new WsjViewHolder(view);
  }

  @Override
  public void onBindViewHolder(WsjViewHolder holder, int position) {
    holder.bind(articles.get(position));
  }

  public void setItems(List<NewsApiResponse.Article> articles) {
    if(articles != null) {
      this.articles.clear();
      this.articles.addAll(articles);
      notifyDataSetChanged();
    }
  }

  @Override
  public int getItemCount() {
    return articles.size();
  }

  public List<NewsApiResponse.Article> getArticles() {
    return articles;
  }
}