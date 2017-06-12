package me.readhub.android.md.model.entity;

import org.joda.time.DateTime;

public class News {

    private long id;

    private String siteName;

    private String authorName;

    private String url;

    private String summary;

    private String title;

    private DateTime publishDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(DateTime publishDate) {
        this.publishDate = publishDate;
    }

}
