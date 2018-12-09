package com.abasscodes.newsy.screens.shownewslist.ui;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.abasscodes.newsy.models.NytResponse;
import java.util.ArrayList;
import java.util.List;

public class NytArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {
    private final List<NytResponse.Result> results = new ArrayList<>();
    private int layoutRes = ArticleViewHolder.Companion.getLayoutid();

    public NytArticleAdapter(@LayoutRes Integer layoutRes, List<NytResponse.Result> objects) {
        this.layoutRes = layoutRes;
        setResults(objects);
    }

    public NytArticleAdapter(List<NytResponse.Result> objects) {
        setResults(objects);
    }

    public NytArticleAdapter() {

    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        return new ArticleViewHolder(view);
    }

    public void setResults(List<NytResponse.Result> results) {
        this.results.clear();
        this.results.addAll(results);
        notifyDataSetChanged();
    }

    public List<NytResponse.Result> getResults() {
        return results;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.bind(results.get(position));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
