package me.readhub.android.md.model.entity;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;

import java.util.List;

public class Topic {

    private String id;

    private DateTime createdAt;

    private TopicNelData nelData;

    @SerializedName("newsArray")
    private List<TopicNews> newsList;

    private long order;

    private DateTime publishDate;

    @SerializedName("relatedTopicArray")
    private List<Object> relatedTopicList;

    private String summary;

    private String title;

    private DateTime updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public TopicNelData getNelData() {
        return nelData;
    }

    public void setNelData(TopicNelData nelData) {
        this.nelData = nelData;
    }

    public List<TopicNews> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<TopicNews> newsList) {
        this.newsList = newsList;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public DateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(DateTime publishDate) {
        this.publishDate = publishDate;
    }

    public List<Object> getRelatedTopicList() {
        return relatedTopicList;
    }

    public void setRelatedTopicList(List<Object> relatedTopicList) {
        this.relatedTopicList = relatedTopicList;
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

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
