package com.abasscodes.newsy.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import com.abasscodes.newsy.data.database.DatabaseHelper;
import com.abasscodes.newsy.data.database.SerializableArticle;
import com.abasscodes.newsy.data.database.Serializer;
import com.abasscodes.newsy.models.NewsApiResponse;
import com.abasscodes.newsy.models.NytResponse;
import com.abasscodes.newsy.settings.SharePreferencesManager;
import com.abasscodes.newsy.settings.UserSettings;

public class ContentUtil {

  public static String getContentUrl(String url) {
    return url.replace("https://", "https://outline.com/")
      .replace("http://", "https://outline.com/");
  }

  public static String getContentUrl(Context context, NewsApiResponse.Article article) {
    return checkAdminPrivilege(context) ? getContentUrl(article.getUrl()) : article.getUrl();
  }

  public static String getContentUrl(Context context, NytResponse.Result article) {
    return checkAdminPrivilege(context) ? getContentUrl(article.getUrl()) : article.getUrl();
  }

  private static boolean checkAdminPrivilege(Context context) {
    UserSettings sharedPrefs = SharePreferencesManager.getInstance(context);
    return sharedPrefs.hasAdminPriv();
  }

  public static void saveResult(Context context, NytResponse.Result result) {
    DatabaseHelper dbHelper = new DatabaseHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Serializer serializer = Serializer.getInstance(db);
    serializer.addFavorite(SerializableArticle.from(result));
    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT)
      .show();
  }

  public static void saveArticle(Context context, NewsApiResponse.Article article) {
    DatabaseHelper dbHelper = new DatabaseHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    Serializer serializer = Serializer.getInstance(db);
    serializer.addFavorite(SerializableArticle.from(article));
    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT)
      .show();
  }
}
