package models;

import java.math.BigInteger;

public class SearchingResults {
    private String videoTitle;
    private String channelTile;
    private String sentiment;
    private Long timeAgo;
    private String query;

    public SearchingResults(String videoTitle, String channelTile,Long publishDate, String sentiment) {
        this.videoTitle = videoTitle;
        this.channelTile = channelTile;
        this.timeAgo = publishDate;
        this.sentiment = sentiment;
    }

    public void setQuery(String query) {
        this.query = query;
    }


}
