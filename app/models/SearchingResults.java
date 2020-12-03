package models;

import java.math.BigInteger;

public class SearchingResults {
    private String videoTitle;
    private String channelTitle;
    private String sentiment;
    private Long timeAgo;
    private String query;
    private String videoId;
    private String channelId;
    private String ViewCount;
    private String DateTime;


    public SearchingResults(String videoTitle, String channelTitle,Long publishDate, String sentiment, String videoId,String channelId) {
        this.videoTitle = videoTitle;
        this.channelTitle = channelTitle;
        this.timeAgo = publishDate;
        this.sentiment = sentiment;
        this.videoId = videoId;
        this.channelId = channelId;
        this.ViewCount = ViewCount;
        this.DateTime = DateTime;
    }

    public void setQuery(String query) {
        this.query = query;
    }


}
