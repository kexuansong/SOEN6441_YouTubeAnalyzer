package models;

import java.math.BigInteger;

public class SearchingResults {
    private String videoTitle;
    private String channelTitle;
    private String sentiment;
    private Long timeAgo;
    private String query;
    private String VideoId;

    public SearchingResults(String videoTitle, String title,Long publishDate, String Sentiment, String videoId) {
        this.videoTitle = videoTitle;
        this.channelTitle = title;
        this.timeAgo = publishDate;
        this.sentiment = Sentiment;
        this.VideoId = videoId;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getSentiment() {
        return sentiment;
    }

    public Long getTimeAgo() {
        return timeAgo;
    }

    public String getQuery() {
        return query;
    }
}
