package models;

import java.math.BigInteger;

public class SearchingResults {
    private String videoTitle;
    private String channelTitle;
    private Long timeAgo;
    private String ChannelID;
//    private String query;
    private String videoId;
    private String sentiment;


    public SearchingResults(String videoTitle, String title, Long publishDate, String videoId, String channel) {
        this.videoTitle = videoTitle;
        this.channelTitle = title;
        this.timeAgo = publishDate;
        this.videoId = videoId;
        this.ChannelID = channel;
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

    public String getVideoId(){ return videoId;}

//    public String getQuery() {
//        return query;
//    }
}
