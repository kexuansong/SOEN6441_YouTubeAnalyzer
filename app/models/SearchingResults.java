package models;

import java.math.BigInteger;

public class SearchingResults {
    private String videoTitle;
    private String channelTitle;
    private Long timeAgo;
    private String ChannelID;
    private String query;
    private String videoId;
    private String sentiment;
    private BigInteger viewCount;


    public SearchingResults(String videoTitle, String title, Long publishDate,BigInteger view ,String videoId, String channel, String query) {
        this.videoTitle = videoTitle;
        this.channelTitle = title;
        this.timeAgo = publishDate;
        this.viewCount =view;
        this.videoId = videoId;
        this.ChannelID = channel;
        this.query = query;
    }

//    public void setQuery(String query) {
//        this.query = query;
//    }

    public String getChannelID(){return ChannelID;}
    public String getVideoTitle() {
        return videoTitle;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }
    
    public String getSentiment() {
        return sentiment;
    }

    public Long getTimeAgo() {
        return timeAgo;
    }

    public BigInteger getViewCount() {
        return viewCount;
    }

    public String getVideoId(){ return videoId;}

    public String getQuery() {
        return query;
    }
}
