package com.abasscodes.newsy.utils;

import android.widget.ImageView;

import com.abasscodes.newsy.R;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class PicassoUtil {

    public static void setImage(ImageView target, String imageUrl) {
        Picasso.with(target.getContext()).load(imageUrl).placeholder(R.drawable.news_placeholder).into(target);
    }
}
