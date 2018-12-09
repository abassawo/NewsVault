package com.abasscodes.newsy.screens.mainscreen.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.abasscodes.newsy.utils.dragandrop.ItemTouchHelperAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardParentViewHolder> implements ItemTouchHelperAdapter {
    private final List<DashboardView> dashboardViews = new ArrayList<>();

    @Override
    public DashboardParentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(DashboardParentViewHolder.Companion.getLAYOUT_ID(), parent, false);
        return new DashboardParentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DashboardParentViewHolder holder, int position) {
        holder.bind(dashboardViews.get(position));
    }

    @Override
    public int getItemCount() {
        return dashboardViews.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(dashboardViews, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(dashboardViews, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setItems(List<DashboardView> items) {
        this.dashboardViews.clear();
        this.dashboardViews.addAll(items);
        notifyDataSetChanged();
    }

    public List<DashboardView> getItems() {
        return dashboardViews;
    }
}
