package models;

import java.math.BigInteger;

public class SearchingResults {
    private String videoTitle;
    private String channelTile;
    private String sentiment;
    private Long timeAgo;
    private String query;
    private String videoId;

    public SearchingResults(String videoTitle, String channelTile,Long publishDate, String sentiment, String videoId) {
        this.videoTitle = videoTitle;
        this.channelTile = channelTile;
        this.timeAgo = publishDate;
        this.sentiment = sentiment;
        this.videoId = videoId;
    }

    public void setQuery(String query) {
        this.query = query;
    }


}
