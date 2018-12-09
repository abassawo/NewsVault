package com.abasscodes.newsy.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.abasscodes.newsy.utils.ContentUtil;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsApiResponse {
    @SerializedName("articles")
    private List<Article> articles;

    private String totalResults;

    private String status;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClassPojo [articles = " + articles + ", totalResults = " + totalResults + ", status = " + status + "]";
    }

    public class Source {
        private String id, name;
    }


    public static class Article implements Parcelable {
        private Source source;
        private String author, title, description, url, urlToImage, publishedAt;

        protected Article(Parcel in) {
            author = in.readString();
            title = in.readString();
            description = in.readString();
            url = in.readString();
            urlToImage = in.readString();
            publishedAt = in.readString();
        }

        public static final Creator<Article> CREATOR = new Creator<Article>() {
            @Override
            public Article createFromParcel(Parcel in) {
                return new Article(in);
            }

            @Override
            public Article[] newArray(int size) {
                return new Article[size];
            }
        };

        public String getAuthor() {
            return author;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public Source getSource() {
            return source;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(author);
            parcel.writeString(title);
            parcel.writeString(description);
            parcel.writeString(url);
            parcel.writeString(urlToImage);
            parcel.writeString(publishedAt);
        }
    }
}