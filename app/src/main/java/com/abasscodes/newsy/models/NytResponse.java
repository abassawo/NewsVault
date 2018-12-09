package com.abasscodes.newsy.models;
import android.os.Parcel;
import android.os.Parcelable;

import com.abasscodes.newsy.utils.ContentUtil;

import java.util.List;


public class NytResponse  {
    String section;
    String status, copyright;
    private String lastUpdated;
    private int numResults;
    private List<Result> results;


    public List<Result> getResults() {
        return results;
    }

    public static class Result implements Parcelable {
        private String section;
        private String _abstract;
        private String subsection;

        private String title;
        private String url;
        private String imageUrl;
        private String byline;
        private String item_type;
        private String updated_date;
        private String created_date;
        private String published_date;
        private Multimedia[] multimedia;


        protected Result(Parcel in) {
            section = in.readString();
            _abstract = in.readString();
            subsection = in.readString();
            title = in.readString();
            url = in.readString();
            byline = in.readString();
            imageUrl = in.readString();
            item_type = in.readString();
            updated_date = in.readString();
            created_date = in.readString();
            published_date = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(section);
            dest.writeString(_abstract);
            dest.writeString(subsection);
            dest.writeString(title);
            dest.writeString(url);
            dest.writeString(byline);
            dest.writeString(imageUrl);
            dest.writeString(item_type);
            dest.writeString(updated_date);
            dest.writeString(created_date);
            dest.writeString(published_date);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel in) {
                return new Result(in);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };

        public Multimedia getMultimedia(int index) {
            return multimedia[index];
        }

        public Multimedia[] getMultimedia ()
        {
            return multimedia;
        }

        public void setMultimedia (Multimedia[] multimedia)
        {
            this.multimedia = multimedia;
        }


        public String getSection() {
            return section;
        }

        public String get_abstract() {
            return _abstract;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }

        public boolean hasMultimedia() {
            return multimedia.length > 0;
        }

        public String getBestImagePath() {
            if(imageUrl != null) {
                return imageUrl;
            }
            String image = null;
            //Get largest quality from end of multimedia array
            if(multimedia == null) return null;

            for (int i = multimedia.length - 1; i > 0; i--) {
               image = multimedia[multimedia.length - 1].getUrl();
               if(image != null) {
                   imageUrl = image;
                   return image;
               }
            }
            return image;
        }
    }

    public class Multimedia {

        private String height;

        private String subtype;

        private String width;

        private String caption;

        private String copyright;

        private String format;

        private String type;

        private String url;

        public String getHeight ()
        {
            return height;
        }

        public void setHeight (String height)
        {
            this.height = height;
        }

        public String getSubtype ()
        {
            return subtype;
        }

        public void setSubtype (String subtype)
        {
            this.subtype = subtype;
        }

        public String getWidth ()
        {
            return width;
        }

        public void setWidth (String width)
        {
            this.width = width;
        }

        public String getCaption ()
        {
            return caption;
        }

        public void setCaption (String caption)
        {
            this.caption = caption;
        }

        public String getCopyright ()
        {
            return copyright;
        }

        public void setCopyright (String copyright)
        {
            this.copyright = copyright;
        }

        public String getFormat ()
        {
            return format;
        }

        public void setFormat (String format)
        {
            this.format = format;
        }

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public String getUrl ()
        {
            return url;
        }

        public void setUrl (String url)
        {
            this.url = url;
        }
    }
}
