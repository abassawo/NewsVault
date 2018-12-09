package com.abasscodes.newsy.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
  protected Context context;
  protected T item;

  public BaseViewHolder(View view) {
    super(view);
    this.context = itemView.getContext();
  }

  public void bind(T item) {
    this.item = item;
  }
}
